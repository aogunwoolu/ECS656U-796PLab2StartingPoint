package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;

import com.example.grpc.server.grpcserver.MatrixRequest;
import com.example.grpc.server.grpcserver.MatrixReply;

import com.example.grpc.server.grpcserver.MatrixN;
import com.example.grpc.server.grpcserver.Row;
import com.example.grpc.server.grpcserver.RequestMatrices;
import com.example.grpc.server.grpcserver.IndvRequestMatrices;
import com.example.grpc.server.grpcserver.RequestMatrices;
import com.example.grpc.server.grpcserver.MatrixBlockReply;

import com.example.grpc.server.grpcserver.IndvRequestMatrices.Builder;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GRPCClientService {

	// array of ips for connecting with the server
	String[] ips;

	// setup of channels and stubs (available globally within the GRPCClientService)
	List<ManagedChannel> channels = new LinkedList<>();
	List<MatrixServiceGrpc.MatrixServiceBlockingStub> stubs = new LinkedList<>();

	// initialization of the start server index + the number of servers
	int active_servers;
	int server_index = 0;

	// constructor like setup of ip addresses and channels
	public void setup(String[] ips) {
		this.ips = ips;

		for (String ip : ips) {
			channels.add(ManagedChannelBuilder.forAddress(ip, 9090).usePlaintext().build());
		}

		for (Channel channel : channels) {
			stubs.add(MatrixServiceGrpc.newBlockingStub(channel));
		}

		this.active_servers = ips.length;
	}

	// overload of the setup method requiring no arguments
	public void setup() {
		setup(ips);
	}

	// pingpong method (for testing that the server is up)
    public String ping() {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090)
		.usePlaintext()
		.build();

		//PingPongServiceGrpc.PingPongServiceBlockingStub stub = stubs.get(channel);  
		PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);
		PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
        .setPing("")
        .build()); 

		channel.shutdown();        
		return helloResponse.getPong();
    }

	//footprint method, returns the time the calculation takes
	public double footPrint(
		MatrixServiceGrpc.MatrixServiceBlockingStub stub, 
		int n,
		int m,
		boolean mult
	) {
		long startTime = System.nanoTime();
		MatrixReply temp= (mult)? (stub.multiplyBlock(IndvRequestMatrices.newBuilder().setA(n).setB(m).build())) : (stub.addBlock(IndvRequestMatrices.newBuilder().setA(n).setB(m).build()));
		long endTime = System.nanoTime();
		long footprint= endTime-startTime;
		return footprint;
	}

	// individual addition method, which adds specific integers to be added by the server
	// the details of the calculation can be found at:
	// https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm
    public Integer[][] add(Integer[][] inputA, Integer[][] inputB){
		// create stubs and server channels from ips
		setup();

		// init server index to 0 (to start from the first server)
		server_index = 0;

		// setup the deadline - for addition, deadline is not used
		int deadline = 60;
		Integer n = inputA.length;

		// generate random server index for deadline footprinting
		Random r = new Random(); int min = 0; int max = channels.size() - 1;
		int random = r.nextInt(max-min) + min;

		// result matrix to be returned
		Integer[][] result = new Integer[n][n];

		//calling footprinting method to get timing of the calculation
		double footprint = footPrint(stubs.get(random), inputA[0][0], inputA[n-1][n-1], false);

		// calculating the number of servers needed to complete the calculation
		int numberServer= (int) Math.ceil((footprint*(int) Math.pow(n, 2))/deadline);


		System.out.println("--------------------------------------------------------------");
		System.out.println("BEGINNING ADDITION");
		System.out.println("--------------------------------------------------------------");
		System.out.printf("Number of server needed: %s%n", numberServer);
        System.out.printf("Footprint: %s seconds%n", footprint);
        System.out.println("--------------------------------------------------------------");

		// looping through the number of servers needed
		for(int i=0;i<n;i++){  //rows
			for(int j=0;j<n;j++){  //columns
				distributedAddition(i, j, inputA, inputB, result);
				System.out.print(result[i][j]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		return result;
    }

	// individual addition method for server communication
	public void distributedAddition(int i, int j, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		// create stubs and server channels from ips
		MatrixReply T= stubs.get(server_index%active_servers).addBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][j]).setB(inputB[i][j]).build());
		// inplace setting the value in the result matrix
		inputC[i][j] = T.getC();
		// incrementing the server index (next server)
		server_index++;
	}

	// blocking addition method for server communication
	public void distributedBlockAddition(Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		// setup for matrices of arbitrary sizes to be sent
		// MatrixN = Matrix of N size
		MatrixN.Builder Abuilder = MatrixN.newBuilder();
		MatrixN.Builder Bbuilder = MatrixN.newBuilder();

		// setup for the request object (storage of matrices A & B)
		RequestMatrices.Builder Requestbuilder = RequestMatrices.newBuilder();
		
		// adding values of quadrant to the first request matrix
		for (Integer[] row : inputA) {
			Abuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// adding values of quadrant to the second request matrix
		for (Integer[] row : inputB) {
			Bbuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// adding the matrices to the request object
		Requestbuilder.setA(Abuilder);
		Requestbuilder.setB(Bbuilder);
		
		// setting the request object to the server
		MatrixBlockReply A= stubs.get(server_index%active_servers).addBBlock(
			Requestbuilder.build()
		);

		// setting the response from the server into the result matrix (inplace)
		A.getC();
		MatrixN rows = A.getC();

		for (int i = 0; i < rows.getRowsCount(); i++) {
			Row row = rows.getRows(i);
			for (int j = 0; j < row.getColsCount(); j++) {
				inputC[i][j] = row.getCols(j);
			}
		}
	}

	// individual multiplication method, which multiplies specific integers to be multiplied by the server
	public void distributedMultiply(int i, int j, int k, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		// creates request object to be sent to the server
		Builder Requestbuilder = IndvRequestMatrices.newBuilder();

		for(int l=0;l<inputA.length;l++){  //rows
			for(int m=0;m<inputA.length;m++){  //columns
				System.out.print(inputC[l][m]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		System.out.println(inputC[i][j]);

		// sending the request object to the server (multiply)
		MatrixReply T= stubs.get(server_index%active_servers).multiplyBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][k]).setB(inputB[k][j]).build());
		server_index++;
		// as multiplication requires addition, we use addition as well
		MatrixReply T2= stubs.get(server_index%active_servers).addBlock(IndvRequestMatrices.newBuilder().setA((inputC[i][j] == null)? 0 : inputC[i][j]).setB(T.getC()).build());

		// inplace setting the value in the result matrix
		inputC[i][j] = T2.getC();
		server_index++;
	}

	// blocking multiplication method, which multiplies specific blocks of the matrix
	public void distributedBlockMultiply(Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		// setup for matrices of arbitrary sizes to be sent
		// MatrixN = Matrix of N size
		MatrixN.Builder Abuilder = MatrixN.newBuilder();
		MatrixN.Builder Bbuilder = MatrixN.newBuilder();

		// setup for the request object (storage of matrices A & B)
		RequestMatrices.Builder Requestbuilder = RequestMatrices.newBuilder();
		
		// adding values of quadrant to the first request matrix
		for (Integer[] row : inputA) {
			Abuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// adding values of quadrant to the second request matrix
		for (Integer[] row : inputB) {
			Bbuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// adding the matrices to the request object
		Requestbuilder.setA(Abuilder);
		Requestbuilder.setB(Bbuilder);
		
		// setting the request object to the server
		MatrixBlockReply A= stubs.get(server_index%active_servers).multiplyBBlock(
			Requestbuilder.build()
		);

		// setting the response from the server into the result matrix (inplace)
		A.getC();
		MatrixN mat = A.getC();

		Integer[][] result = new Integer[mat.getRowsCount()][mat.getRowsCount()];

		for (int i = 0; i < mat.getRowsCount(); i++) {
			Row row = mat.getRows(i);
			for (int j = 0; j < row.getColsCount(); j++) {
				result[i][j] = row.getCols(j);
			}
		}

		// using block addition to inplace add the result matrix to the input matrix
		distributedBlockAddition(inputC, result, inputC);

	}

	// footprinting method for the block multiplication
	public double blockFootPrint(
		MatrixServiceGrpc.MatrixServiceBlockingStub stub, 
		Integer[][] inputA,
		Integer[][] inputB,
		boolean mult
	) {		
		// setup the request to be sent to the server
		RequestMatrices.Builder Requestbuilder = RequestMatrices.newBuilder();

		MatrixN.Builder Abuilder = MatrixN.newBuilder();
		MatrixN.Builder Bbuilder = MatrixN.newBuilder();
		
		for (Integer[] row : inputA) {
			Abuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		for (Integer[] row : inputB) {
			Bbuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		Requestbuilder.setA(Abuilder);
		Requestbuilder.setB(Bbuilder);

		//start time of the operation
		long startTime = System.nanoTime();
		
		// sending the request object to the server (multiply)
		// doing nothing with result because we are only measuring the time
		MatrixBlockReply A= stubs.get(server_index%active_servers).multiplyBBlock(
			Requestbuilder.build()
		);
		
		//end time of the operation
		long endTime = System.nanoTime();

		// calculating the time taken
		long footprint= endTime-startTime;
		return footprint;
	}

	// main mathod for multiplying using the block method
	public Integer[][] blockMult(Integer[][] inputA, Integer[][] inputB){
		// setup ip addresses of the servers
		setup();

		// calculate block size of matrices + actual size
		Integer block_size = inputA.length/2;
		Integer n = inputA.length;

		// set deadline
		int deadline = 60;

		System.out.print("len: ");
		System.out.println(inputA.length);

		System.out.print("block size: ");
		System.out.println(block_size);

		//vectorised matrix
		Integer[][][] blocksA = new Integer[4][block_size][block_size];
		Integer[][][] blocksB = new Integer[4][block_size][block_size];

		//divide array into chunks
		// chucks of 2 x 2. This way the distributed algorithm can be done best
		for(int i=0;i<n;i++){  //rows
			for(int j=0;j<n;j++){  //columns
				if(i<block_size){
					if(j<block_size){
						blocksA[0][i][j] = inputA[i][j];
						blocksB[0][i][j] = inputB[i][j];
					}
					else{
						blocksA[1][i][j-block_size] = inputA[i][j];
						blocksB[1][i][j-block_size] = inputB[i][j];
					}
				}
				else{
					if(j<block_size){
						blocksA[2][i-block_size][j] = inputA[i][j];
						blocksB[2][i-block_size][j] = inputB[i][j];
					}
					else{
						blocksA[3][i-block_size][j-block_size] = inputA[i][j];
						blocksB[3][i-block_size][j-block_size] = inputB[i][j];
					}
				}
			}
		}

		// setup for footprinting, selects a random server
		Random r = new Random(); int min = 0; int max = channels.size() - 1;
		int random = r.nextInt(max-min) + min;

		// calls the footprinting method, finding how long it takes to do the operation
		double footprint = blockFootPrint(stubs.get(random), blocksA[0], blocksA[3], true);
		int calls = (int) Math.pow(n, 2);
		double ex_time = calls * footprint;

		// calculate how many servers are needed
		double numberServer = ex_time/deadline;

		// if less than one server needed provide one server
		if (numberServer < 1.00 ) numberServer = 1.00;
		// if more than one but less than 2 server needed use 2 servers
		if(numberServer <2.00 && numberServer > 1.00) numberServer = 2.00;

		// if the number of servers is greater than the number of servers available,
		if((numberServer > active_servers) ){
			// If more than the allocated servers needed to the operation to the allocated servers and if the deadline is unrealistic provide 
			// appropriate mesage and quit 
			numberServer = active_servers;
			if(deadline <= 50){
				System.out.println("Footprint: " + footprint + "\nFootprint x number of calls: " + (footprint*(int) Math.pow(n, 2)));
				System.out.println("The load exceeds the deadline, multiplication cannot be done!");
				return null;
			}
		}

		System.out.println("--------------------------------------------------------------");
		System.out.println("BEGINNING BLOCK MULTIPLICATION");
		System.out.println("--------------------------------------------------------------");
		System.out.printf("Number of servers needed: %s%n", numberServer);
        System.out.printf("Footprint: %s seconds%n", footprint);
        System.out.println("--------------------------------------------------------------");

		// setup result matrix (populate with zeros)
		Integer[][][] result = new Integer[4][block_size][block_size];

		for (int i = 0;i<4;i++){
			for (int j = 0;j<block_size;j++){
				for (int k = 0;k<block_size;k++){
					result[i][j][k] = 0;
				}
			}
		}

		// multiplication indexes for the mult operation
		int[][] multIndexes = {{0,0,0},{1,2,0},{0,1,1},{1,3,1},{2,0,2},{3,2,2},{2,1,3},{3,3,3}};
		

		// calling the distributed multiplication method
		for (int i = 0; i < multIndexes.length; i++) {
			distributedBlockMultiply(blocksA[multIndexes[i][0]], blocksB[multIndexes[i][1]], result[multIndexes[i][2]]);
			server_index++;
		}

		//unchunk array
		Integer[][] output = new Integer[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(i<block_size){
					if(j<block_size){
						output[i][j] = result[0][i][j];
					}
					else{
						output[i][j] = result[1][i][j-block_size];
					}
				}
				else{
					if(j<block_size){
						output[i][j] = result[2][i-block_size][j];
					}
					else{
						output[i][j] = result[3][i-block_size][j-block_size];
					}
				}
			}
		}

		return output;
	}

	// individual multiplication method
	public Integer[][] mult(Integer[][] inputA, Integer[][] inputB){
		//https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm

		//setup for the servers
		setup();

		// setup server index
		server_index = 0;

		// setup the deadline
		int deadline = 60;
		Integer n = inputA.length;

		Random r = new Random(); int min = 0; int max = channels.size() - 1;
		int random = r.nextInt(max-min) + min;

		Integer[][] result = new Integer[n][n];

		double footprint = footPrint(stubs.get(random), inputA[0][0], inputA[n-1][n-1], true);

		int numberServer= (int) Math.ceil((footprint*(int) Math.pow(n, 2))/deadline);

		if((numberServer > active_servers) ){
			// If more than the allocated servers needed to the operation to the allocated servers and if the deadline is unrealistic provide 
			// appropriate mesage and quit 
			numberServer = active_servers;
			if(deadline <= 50){
				System.out.println("Footprint: " + footprint + "\nFootprint x number of calls: " + (footprint*(int) Math.pow(n, 2)));
				System.out.println("The load exceeds the deadline, multiplication cannot be done!");
				return null;
			}
		}


		System.out.println("--------------------------------------------------------------");
		System.out.println("BEGINNING MULTIPLICATION");
		System.out.println("--------------------------------------------------------------");
		System.out.printf("Number of servers needed: %s%n", numberServer);
        System.out.printf("Footprint: %s seconds%n", footprint);
        System.out.println("--------------------------------------------------------------");

		for(int i=0;i<n;i++){  //rows
			for(int j=0;j<n;j++){  //columns
				for(int k=0;k<n;k++){     
					distributedMultiply(i, j, k, inputA, inputB, result);
				}//end of k loop  
				System.out.print(result[i][j]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		return result;
    }
}
