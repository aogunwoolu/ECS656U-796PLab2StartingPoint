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

	List<ManagedChannel> channels = new LinkedList<>();
	List<MatrixServiceGrpc.MatrixServiceBlockingStub> stubs = new LinkedList<>();

	int active_servers;
	int server_index = 0;

	public void setup(String[] ips) {
		for (String ip : ips) {
			channels.add(ManagedChannelBuilder.forAddress(ip, 9090).usePlaintext().build());
		}

		for (Channel channel : channels) {
			stubs.add(MatrixServiceGrpc.newBlockingStub(channel));
		}

		this.active_servers = ips.length;
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

		server_index = 0;

		int deadline = 10;
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

		for (ManagedChannel channel : channels) {
			channel.shutdown();
		}
			
		//String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		//resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='matrixA' /><input type='file' name='matrixB' /><br/><br/><input type='submit' value='Submit' /></form>";
		return result;
    }

	public void distributedAddition(int i, int j, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		MatrixReply T= stubs.get(server_index%active_servers).multiplyBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][j]).setB(inputB[i][j]).build());
		inputC[i][j] = T.getC();
		server_index++;
	}

	public void distributedMultiply(int i, int j, int k, Integer[][] inputA, Integer[][] inputB, Integer[][] inputC){
		Builder Requestbuilder = IndvRequestMatrices.newBuilder();

		MatrixReply T= stubs.get(server_index%active_servers).multiplyBlock(IndvRequestMatrices.newBuilder().setA(inputA[i][k]).setB(inputB[k][j]).build());
		server_index++;
		MatrixReply T2= stubs.get(server_index%active_servers).addBlock(IndvRequestMatrices.newBuilder().setA(inputC[i][j]).setB(T.getC()).build());

		inputC[i][j] = T2.getC();
		server_index++;
	}

	public Integer[][] mult(Integer[][] inputA, Integer[][] inputB){
		//https://en.wikipedia.org/wiki/Matrix_multiplication_algorithm

		server_index = 0;

		int deadline = 10;
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
		System.out.printf("Number of server needed: %s%n", numberServer);
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

		for (ManagedChannel channel : channels) {
			channel.shutdown();
		}
			
		//String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		//resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='matrixA' /><input type='file' name='matrixB' /><br/><br/><input type='submit' value='Submit' /></form>";
		return result;
    }
}
