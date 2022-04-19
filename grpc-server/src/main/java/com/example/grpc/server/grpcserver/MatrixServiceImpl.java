package com.example.grpc.server.grpcserver;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

@GrpcService
public class MatrixServiceImpl extends MatrixServiceGrpc.MatrixServiceImplBase
{
	// service for block multiplication of two matrices
	@Override
	public void multiplyBBlock(RequestMatrices request, StreamObserver<MatrixBlockReply> reply)
	{
		System.out.println("Request received from client:\n" + request);

		// unpacks the 2 matrices from the request
		List<Row> aList = request.getA().getRowsList();
		List<Row> bList = request.getB().getRowsList();

		// creates the result matrix
		Integer c[][] = new Integer[aList.size()][aList.size()];
		int row_size = aList.get(0).getColsList().size();

		// multiplies the matrices
		for(int i=0;i<aList.size();i++){  
			for(int j=0;j<row_size;j++){    
				c[i][j]=0;    
				List<Integer> arow = aList.get(i).getColsList();  
				for(int k=0;k<row_size;k++){    
					List<Integer> brow = bList.get(k).getColsList();    
					c[i][j]+=arow.get(k)*brow.get(j);      
				}//end of k loop  
				System.out.print(c[i][j]+" "); 
			}//end of j loop  
			System.out.println();
		}    

		// creates the reply object
		MatrixBlockReply.Builder Requestbuilder = MatrixBlockReply.newBuilder();

		// creates the reply matrix
		MatrixN.Builder response = MatrixN.newBuilder();

		// loads the reply matrix with the result array
		for (Integer[] row : c) {
			response.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// loads the reply object with the reply matrix
		Requestbuilder.setC(response);

		// sends the reply object to the client
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}

	// service for block multiplication of two matrices
	@Override
	public void multiplyBlock(IndvRequestMatrices request, StreamObserver<MatrixReply> reply)
	{
		System.out.println("Request received from client:\n" + request);

		// gets the matrices from the request
		int a = request.getA();
		int b = request.getB();

		// multiplies the matrices
		int c = a * b;

		// creates the reply object
		MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		// loads the reply object with the result
		Requestbuilder.setC(c);

		// sends the reply object to the client
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}

	// service for block addition of two matrices
	@Override
	public void addBBlock(RequestMatrices request, StreamObserver<MatrixBlockReply> reply){
		System.out.println("Request received from client:\n" + request);

		// unpacks the 2 matrices from the request
		List<Row> aList = request.getA().getRowsList();
		List<Row> bList = request.getB().getRowsList();

		// creates the result matrix
		Integer c[][] = new Integer[aList.size()][aList.size()];

		// adds the matrices
		for(int i=0;i<aList.size();i++){   
			List<Integer> arow = aList.get(i).getColsList();
			List<Integer> brow = bList.get(i).getColsList();
			for(int j=0;j<arow.size();j++){    
				c[i][j]=arow.get(j)+brow.get(j);    //use - for subtraction  
				System.out.print(c[i][j]+" ");    
			}    
			System.out.println();//new line    
		}    

		// creates the reply object
		MatrixBlockReply.Builder Requestbuilder = MatrixBlockReply.newBuilder();

		// creates the reply matrix
		MatrixN.Builder response = MatrixN.newBuilder();

		// loads the reply matrix with the result array
		for (Integer[] row : c) {
			response.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		}

		// loads the reply object with the reply matrix
		Requestbuilder.setC(response);

		// sends the reply object to the client
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}

	// service for addition of two matrices
	@Override
	public void addBlock(IndvRequestMatrices request, StreamObserver<MatrixReply> reply)
	{
		System.out.println("Request received from client:\n" + request);

		// gets the matrices from the request
		int a = request.getA();
		int b = request.getB();

		// adds the matrices
		int c = a + b;

		// creates the reply object
		MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		// loads the reply object with the result
		Requestbuilder.setC(c);

		// sends the reply object to the client
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}
}
