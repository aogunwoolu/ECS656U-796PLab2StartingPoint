package com.example.grpc.server.grpcserver;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

@GrpcService
public class MatrixServiceImpl extends MatrixServiceGrpc.MatrixServiceImplBase
{
	@Override
	public void multiplyBlock(IndvRequestMatrices request, StreamObserver<MatrixReply> reply)
	{
		System.out.println("Request received from client:\n" + request);

		// List<Row> aList = request.getA().getRowsList();
		// List<Row> bList = request.getB().getRowsList();

		// //List<Integer> cList = new LinkedList<Integer>();
		// Integer c[][] = new Integer[aList.size()][aList.size()];
		// int row_size = aList.get(0).getColsList().size();

		// for(int i=0;i<aList.size();i++){  
		// 	for(int j=0;j<row_size;j++){    
		// 		c[i][j]=0;    
		// 		List<Integer> arow = aList.get(i).getColsList();  
		// 		for(int k=0;k<row_size;k++){    
		// 			List<Integer> brow = bList.get(k).getColsList();    
		// 			c[i][j]+=arow.get(k)*brow.get(j);      
		// 		}//end of k loop  
		// 		System.out.print(c[i][j]+" ");  //printing matrix element  
		// 	}//end of j loop  
		// 	System.out.println();//new line    
		// }    

		// MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		// MatrixN.Builder response = MatrixN.newBuilder();

		// for (Integer[] row : c) {
		// 	response.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		// }

		int a = request.getA();
		int b = request.getB();
		int c = a * b;

		MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		Requestbuilder.setC(c);
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}

	@Override
	public void addBlock(IndvRequestMatrices request, StreamObserver<MatrixReply> reply)
	{
		System.out.println("Request received from client:\n" + request);

		//List<Row> aList = request.getA().getRowsList();
		//List<Row> bList = request.getB().getRowsList();

		// //List<Integer> cList = new LinkedList<Integer>();
		// Integer c[][] = new Integer[aList.size()][aList.size()];

		// for(int i=0;i<aList.size();i++){   
		// 	List<Integer> arow = aList.get(i).getColsList();
		// 	List<Integer> brow = bList.get(i).getColsList();
		// 	for(int j=0;j<arow.size();j++){    
		// 		c[i][j]=arow.get(j)+brow.get(j);    //use - for subtraction  
		// 		System.out.print(c[i][j]+" ");    
		// 	}    
		// 	System.out.println();//new line    
		// }    

		// MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		// MatrixN.Builder response = MatrixN.newBuilder();

		// for (Integer[] row : c) {
		// 	response.addRows(Row.newBuilder().addAllCols(Arrays.asList(row)).build());
		// }

		int a = request.getA();
		int b = request.getB();
		int c = a + b;

		MatrixReply.Builder Requestbuilder = MatrixReply.newBuilder();

		Requestbuilder.setC(c);
		reply.onNext(Requestbuilder.build());
		reply.onCompleted();
	}
}
