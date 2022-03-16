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

import java.util.ArrayList;
import java.util.Arrays;

class Tuple<X, Y> { 
	public final X x; 
	public final Y y; 
	public Tuple(X x, Y y) { 
	  this.x = x; 
	  this.y = y; 
	} 
  } 

@Service
public class GRPCClientService {
	ManagedChannel channel1 = ManagedChannelBuilder.forAddress("35.225.79.176", 8080) .usePlaintext().build();
	ManagedChannel channel2 = ManagedChannelBuilder.forAddress("34.123.43.111", 8080) .usePlaintext().build();
	ManagedChannel channel3 = ManagedChannelBuilder.forAddress("35.239.236.128", 8080) .usePlaintext().build();

	MatrixServiceGrpc.MatrixServiceBlockingStub stub1 = MatrixServiceGrpc.newBlockingStub(channel1);
	MatrixServiceGrpc.MatrixServiceBlockingStub stub2 = MatrixServiceGrpc.newBlockingStub(channel2);
	MatrixServiceGrpc.MatrixServiceBlockingStub stub3 = MatrixServiceGrpc.newBlockingStub(channel3);

	ArrayList<Tuple<ManagedChannel, MatrixServiceGrpc.MatrixServiceBlockingStub>> Servers = Arrays.asList(
		new Tuple<ManagedChannel, MatrixServiceGrpc.MatrixServiceBlockingStub>(channel1, stub1),
		new Tuple<ManagedChannel, MatrixServiceGrpc.MatrixServiceBlockingStub>(channel2, stub2),
		new Tuple<ManagedChannel, MatrixServiceGrpc.MatrixServiceBlockingStub>(channel3, stub3)
	);

    public String ping() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build(); 

		PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);        
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
}
