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

	String[] ips;
	List<ManagedChannel> channels = new LinkedList<>();
	List<MatrixServiceGrpc.MatrixServiceBlockingStub> stubs = new LinkedList<>();

	int active_servers;
	int server_index = 0;

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

	public void setup() {
		setup(ips);
	}

    public String ping() {
        // ManagedChannel channel = channels.get(currentServer)
        // .usePlaintext()
        // .build(); 

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

    public Integer[][] add(Integer[][] inputA, Integer[][] inputB){
		//https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm
		setup();

		server_index = 0;

		int deadline = 60;
		Integer n = inputA.length;

		Random r = new Random(); int min = 0; int max = channels.size() - 1;
		int random = r.nextInt(max-min) + min;

		Integer[][] result = new Integer[n][n];

		double footprint = footPrint(stubs.get(random), inputA[0][0], inputA[n-1][n-1], false);

		int numberServer= (int) Math.ceil((footprint*(int) Math.pow(n, 2))/deadline);


		System.out.println("--------------------------------------------------------------");
		System.out.println("BEGINNING ADDITION");
		System.out.println("--------------------------------------------------------------");
		System.out.printf("Number of server needed: %s%n", numberServer);
        System.out.printf("Footprint: %s seconds%n", footprint);
        System.out.println("--------------------------------------------------------------");

		for(int i=0;i<n;i++){  //rows
			for(int j=0;j<n;j++){  //columns
				distributedAddition(i, j, inputA, inputB, result);
				System.out.print(result[i][j]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		// for (ManagedChannel channel : channels) {
		// 	channel.shutdown();
		// }
			
		//String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		//resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='matrixA' /><input type='file' name='matrixB' /><br/><br/><input type='submit' value='Submit' /></form>";
		return result;
    }

	public void distributedAddition(int i, int j, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		MatrixReply T= stubs.get(server_index%active_servers).addBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][j]).setB(inputB[i][j]).build());
		inputC[i][j] = T.getC();
		server_index++;
	}

	public void distributedMultiply(int i, int j, int k, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		Builder Requestbuilder = IndvRequestMatrices.newBuilder();

		for(int l=0;l<inputA.length;l++){  //rows
			for(int m=0;m<inputA.length;m++){  //columns
				System.out.print(inputC[l][m]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		System.out.println(inputC[i][j]);

		MatrixReply T= stubs.get(server_index%active_servers).multiplyBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][k]).setB(inputB[k][j]).build());
		server_index++;
		MatrixReply T2= stubs.get(server_index%active_servers).addBlock(IndvRequestMatrices.newBuilder().setA((inputC[i][j] == null)? 0 : inputC[i][j]).setB(T.getC()).build());

		inputC[i][j] = T2.getC();
		server_index++;
	}

	public void distributedBlockAddition(Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		MatrixN.Builder Abuilder = MatrixN.newBuilder();
		MatrixN.Builder Bbuilder = MatrixN.newBuilder();

		RequestMatrices.Builder Requestbuilder = RequestMatrices.newBuilder();
		
		for (Integer[] row : inputA) {
			Abuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		for (Integer[] row : inputB) {
			Bbuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		Requestbuilder.setA(Abuilder);
		Requestbuilder.setB(Bbuilder);
		
		MatrixBlockReply A= stubs.get(server_index%active_servers).addBBlock(
			Requestbuilder.build()
		);

		A.getC();
		MatrixN rows = A.getC();

		for (int i = 0; i < rows.getRowsCount(); i++) {
			Row row = rows.getRows(i);
			for (int j = 0; j < row.getColsCount(); j++) {
				inputC[i][j] = row.getCols(j);
			}
		}
	}

	public void distributedBlockMultiply(Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		MatrixN.Builder Abuilder = MatrixN.newBuilder();
		MatrixN.Builder Bbuilder = MatrixN.newBuilder();

		RequestMatrices.Builder Requestbuilder = RequestMatrices.newBuilder();
		
		for (Integer[] row : inputA) {
			Abuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		for (Integer[] row : inputB) {
			Bbuilder.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		Requestbuilder.setA(Abuilder);
		Requestbuilder.setB(Bbuilder);
		
		MatrixBlockReply A= stubs.get(server_index%active_servers).multiplyBBlock(
			Requestbuilder.build()
		);

		A.getC();
		MatrixN rows = A.getC();

		for (int i = 0; i < rows.getRowsCount(); i++) {
			Row row = rows.getRows(i);
			for (int j = 0; j < row.getColsCount(); j++) {
				inputC[i][j] += row.getCols(j);
			}
		}
	}

	public double blockFootPrint(
		MatrixServiceGrpc.MatrixServiceBlockingStub stub, 
		Integer[][] inputA,
		Integer[][] inputB,
		boolean mult
	) {		
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

		long startTime = System.nanoTime();
		
		MatrixBlockReply A= stubs.get(server_index%active_servers).multiplyBBlock(
			Requestbuilder.build()
		);
		
		long endTime = System.nanoTime();
		long footprint= endTime-startTime;
		return footprint;
	}

	public Integer[][] blockMult(Integer[][] inputA, Integer[][] inputB){
		setup();

		Integer block_size = inputA.length/2;
		Integer n = inputA.length;
		int deadline = 60;

		System.out.print("len: ");
		System.out.println(inputA.length);

		System.out.print("block size: ");
		System.out.println(block_size);

		//vectorised matrix
		Integer[][][] blocksA = new Integer[4][block_size][block_size];
		Integer[][][] blocksB = new Integer[4][block_size][block_size];

		//divide array into chunks
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

		Random r = new Random(); int min = 0; int max = channels.size() - 1;
		int random = r.nextInt(max-min) + min;

		double footprint = blockFootPrint(stubs.get(random), blocksA[0], blocksA[3], true);

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
		System.out.println("BEGINNING BLOCK MULTIPLICATION");
		System.out.println("--------------------------------------------------------------");
		System.out.printf("Number of servers needed: %s%n", numberServer);
        System.out.printf("Footprint: %s seconds%n", footprint);
        System.out.println("--------------------------------------------------------------");

		Integer[][][] result = new Integer[4][n][n];

		for (int i = 0;i<4;i++){
			for (int j = 0;j<n;j++){
				for (int k = 0;k<n;k++){
					result[i][j][k] = 0;
				}
			}
		}

		distributedBlockMultiply(blocksA[0], blocksB[0], result[0]);
		server_index++;
		distributedBlockMultiply(blocksA[1], blocksB[2], result[0]);
		server_index++;
		distributedBlockMultiply(blocksA[0], blocksB[1], result[1]);
		server_index++;
		distributedBlockMultiply(blocksA[1], blocksB[3], result[1]);
		server_index++;
		distributedBlockMultiply(blocksA[2], blocksB[0], result[2]);
		server_index++;
		distributedBlockMultiply(blocksA[3], blocksB[2], result[2]);
		server_index++;
		distributedBlockMultiply(blocksA[2], blocksB[1], result[3]);
		server_index++;
		distributedBlockMultiply(blocksA[3], blocksB[3], result[3]);
		server_index++;

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

	public Integer[][] mult(Integer[][] inputA, Integer[][] inputB){
		//https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm
		setup();

		server_index = 0;

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
					// if(i==0 && j==0){
					// 	result[i][j] = inputA[i][k]*inputB[k][j];
					// }
					// else{
					// 	distributedMultiply(i, j, k, inputA, inputB, result);
					// }      
					distributedMultiply(i, j, k, inputA, inputB, result);
				}//end of k loop  
				System.out.print(result[i][j]+" ");  //printing matrix element  
			}//end of j loop  
			System.out.println();//new line    
		}

		// for (ManagedChannel channel : channels) {
		// 	channel.shutdown();
		// }
			
		//String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		//resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='matrixA' /><input type='file' name='matrixB' /><br/><br/><input type='submit' value='Submit' /></form>";
		return result;
    }
}
