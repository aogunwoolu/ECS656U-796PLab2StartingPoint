// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: matrix.proto

package com.example.grpc.server.grpcserver;

public interface RequestMatricesOrBuilder extends
    // @@protoc_insertion_point(interface_extends:matrixmult.RequestMatrices)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.matrixmult.MatrixN A = 1;</code>
   */
  boolean hasA();
  /**
   * <code>.matrixmult.MatrixN A = 1;</code>
   */
  com.example.grpc.server.grpcserver.MatrixN getA();
  /**
   * <code>.matrixmult.MatrixN A = 1;</code>
   */
  com.example.grpc.server.grpcserver.MatrixNOrBuilder getAOrBuilder();

  /**
   * <code>.matrixmult.MatrixN B = 2;</code>
   */
  boolean hasB();
  /**
   * <code>.matrixmult.MatrixN B = 2;</code>
   */
  com.example.grpc.server.grpcserver.MatrixN getB();
  /**
   * <code>.matrixmult.MatrixN B = 2;</code>
   */
  com.example.grpc.server.grpcserver.MatrixNOrBuilder getBOrBuilder();
}
