// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: matrix.proto

package com.example.grpc.server.grpcserver;

public final class MatrixMult {
  private MatrixMult() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrixmult_MatrixRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_matrixmult_MatrixRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrixmult_RequestMatrices_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_matrixmult_RequestMatrices_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrixmult_MatrixN_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_matrixmult_MatrixN_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrixmult_Row_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_matrixmult_Row_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrixmult_MatrixReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_matrixmult_MatrixReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014matrix.proto\022\nmatrixmult\"w\n\rMatrixRequ" +
      "est\022\013\n\003a00\030\001 \001(\005\022\013\n\003a01\030\002 \001(\005\022\013\n\003a10\030\003 \001" +
      "(\005\022\013\n\003a11\030\004 \001(\005\022\013\n\003b00\030\005 \001(\005\022\013\n\003b01\030\006 \001(" +
      "\005\022\013\n\003b10\030\007 \001(\005\022\013\n\003b11\030\010 \001(\005\"Q\n\017RequestMa" +
      "trices\022\036\n\001A\030\001 \001(\0132\023.matrixmult.MatrixN\022\036" +
      "\n\001B\030\002 \001(\0132\023.matrixmult.MatrixN\"(\n\007Matrix" +
      "N\022\035\n\004rows\030\001 \003(\0132\017.matrixmult.Row\"\023\n\003Row\022" +
      "\014\n\004cols\030\001 \003(\005\"-\n\013MatrixReply\022\036\n\001C\030\001 \001(\0132" +
      "\023.matrixmult.MatrixN2\234\001\n\rMatrixService\022G" +
      "\n\rMultiplyBlock\022\033.matrixmult.RequestMatr",
      "ices\032\027.matrixmult.MatrixReply\"\000\022B\n\010AddBl" +
      "ock\022\033.matrixmult.RequestMatrices\032\027.matri" +
      "xmult.MatrixReply\"\000B7\n\"com.example.grpc." +
      "server.grpcserverB\nMatrixMultP\001\242\002\002MMb\006pr" +
      "oto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_matrixmult_MatrixRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_matrixmult_MatrixRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_matrixmult_MatrixRequest_descriptor,
        new java.lang.String[] { "A00", "A01", "A10", "A11", "B00", "B01", "B10", "B11", });
    internal_static_matrixmult_RequestMatrices_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_matrixmult_RequestMatrices_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_matrixmult_RequestMatrices_descriptor,
        new java.lang.String[] { "A", "B", });
    internal_static_matrixmult_MatrixN_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_matrixmult_MatrixN_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_matrixmult_MatrixN_descriptor,
        new java.lang.String[] { "Rows", });
    internal_static_matrixmult_Row_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_matrixmult_Row_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_matrixmult_Row_descriptor,
        new java.lang.String[] { "Cols", });
    internal_static_matrixmult_MatrixReply_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_matrixmult_MatrixReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_matrixmult_MatrixReply_descriptor,
        new java.lang.String[] { "C", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
