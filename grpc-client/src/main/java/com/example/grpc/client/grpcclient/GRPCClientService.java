package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;
import com.example.grpc.server.grpcserver.MatrixRequest;
import com.example.grpc.server.grpcserver.MatrixReply;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GRPCClientService {

	// List<ManagedChannelBuilder> channels = Arrays.asList(
	// 	ManagedChannelBuilder.forAddress("35.225.79.176", 8080),
	// 	ManagedChannelBuilder.forAddress("34.123.43.111", 8080),
	// 	ManagedChannelBuilder.forAddress("35.239.236.128", 8080)
	// );

	// List<MatrixServiceGrpc.MatrixServiceBlockingStub> stubs = Arrays.asList(
	// 	MatrixServiceGrpc.newBlockingStub(channels.get(0)),
	// 	MatrixServiceGrpc.newBlockingStub(channels.get(1)),
	// 	MatrixServiceGrpc.newBlockingStub(channels.get(2))
	// );

	int currentServer = ThreadLocalRandom.current().nextInt(0, channels.size());

    public String ping() {
        // ManagedChannel channel = channels.get(currentServer)
        // .usePlaintext()
        // .build(); 

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090)
		.usePlaintext()
		.build();

		PingPongServiceGrpc.PingPongServiceBlockingStub stub = stubs.get(channel);        
		PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
        .setPing("")
        .build()); 

		channel.shutdown();        
		return helloResponse.getPong();
    }
    public String add(){
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090)
		.usePlaintext()
		.build();

		MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
		MatrixReply A=stub.addBlock(MatrixRequest.newBuilder()
			.setA00(1)
			.setA01(2)
			.setA10(5)
			.setA11(6)
			.setB00(1)
			.setB01(2)
			.setB10(5)
			.setB11(6)
			.build());
			
		String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='file' /><br/><br/><input type='submit' value='Submit' /></form>";
		return resp;
    }
	public String mult(int[][] inputA, int[][] inputB){
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090)
		.usePlaintext()
		.build();

		System.out.println(inputA.length);
		System.out.println(inputB.length);

		// MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
		// MatrixReply A=stub.addBlock(MatrixRequest.newBuilder()
		// 	.setA00(1)
		// 	.setA01(2)
		// 	.setA10(5)
		// 	.setA11(6)
		// 	.setB00(1)
		// 	.setB01(2)
		// 	.setB10(5)
		// 	.setB11(6)
		// 	.build());
			
		//String resp= "<table><tr><td>"+ A.getC00()+"</td><td>"+A.getC01()+"</td><tr><td>"+A.getC10()+"</td><td>"+A.getC11()+"<td></tr></table>\n";
		//resp += "<br/><form method='POST' action='/upload' enctype='multipart/form-data'><input type='file' name='file' /><br/><br/><input type='submit' value='Submit' /></form>";
		return "";
    }
}
