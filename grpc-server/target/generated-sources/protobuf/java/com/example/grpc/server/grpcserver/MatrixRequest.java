// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: matrix.proto

package com.example.grpc.server.grpcserver;

/**
 * <pre>
 * The request message containing the two matrices
 * </pre>
 *
 * Protobuf type {@code matrixmult.MatrixRequest}
 */
public  final class MatrixRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:matrixmult.MatrixRequest)
    MatrixRequestOrBuilder {
  // Use MatrixRequest.newBuilder() to construct.
  private MatrixRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MatrixRequest() {
    a00_ = 0;
    a01_ = 0;
    a10_ = 0;
    a11_ = 0;
    b00_ = 0;
    b01_ = 0;
    b10_ = 0;
    b11_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MatrixRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            a00_ = input.readInt32();
            break;
          }
          case 16: {

            a01_ = input.readInt32();
            break;
          }
          case 24: {

            a10_ = input.readInt32();
            break;
          }
          case 32: {

            a11_ = input.readInt32();
            break;
          }
          case 40: {

            b00_ = input.readInt32();
            break;
          }
          case 48: {

            b01_ = input.readInt32();
            break;
          }
          case 56: {

            b10_ = input.readInt32();
            break;
          }
          case 64: {

            b11_ = input.readInt32();
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.example.grpc.server.grpcserver.MatrixMult.internal_static_matrixmult_MatrixRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.example.grpc.server.grpcserver.MatrixMult.internal_static_matrixmult_MatrixRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.example.grpc.server.grpcserver.MatrixRequest.class, com.example.grpc.server.grpcserver.MatrixRequest.Builder.class);
  }

  public static final int A00_FIELD_NUMBER = 1;
  private int a00_;
  /**
   * <code>int32 a00 = 1;</code>
   */
  public int getA00() {
    return a00_;
  }

  public static final int A01_FIELD_NUMBER = 2;
  private int a01_;
  /**
   * <code>int32 a01 = 2;</code>
   */
  public int getA01() {
    return a01_;
  }

  public static final int A10_FIELD_NUMBER = 3;
  private int a10_;
  /**
   * <code>int32 a10 = 3;</code>
   */
  public int getA10() {
    return a10_;
  }

  public static final int A11_FIELD_NUMBER = 4;
  private int a11_;
  /**
   * <code>int32 a11 = 4;</code>
   */
  public int getA11() {
    return a11_;
  }

  public static final int B00_FIELD_NUMBER = 5;
  private int b00_;
  /**
   * <code>int32 b00 = 5;</code>
   */
  public int getB00() {
    return b00_;
  }

  public static final int B01_FIELD_NUMBER = 6;
  private int b01_;
  /**
   * <code>int32 b01 = 6;</code>
   */
  public int getB01() {
    return b01_;
  }

  public static final int B10_FIELD_NUMBER = 7;
  private int b10_;
  /**
   * <code>int32 b10 = 7;</code>
   */
  public int getB10() {
    return b10_;
  }

  public static final int B11_FIELD_NUMBER = 8;
  private int b11_;
  /**
   * <code>int32 b11 = 8;</code>
   */
  public int getB11() {
    return b11_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (a00_ != 0) {
      output.writeInt32(1, a00_);
    }
    if (a01_ != 0) {
      output.writeInt32(2, a01_);
    }
    if (a10_ != 0) {
      output.writeInt32(3, a10_);
    }
    if (a11_ != 0) {
      output.writeInt32(4, a11_);
    }
    if (b00_ != 0) {
      output.writeInt32(5, b00_);
    }
    if (b01_ != 0) {
      output.writeInt32(6, b01_);
    }
    if (b10_ != 0) {
      output.writeInt32(7, b10_);
    }
    if (b11_ != 0) {
      output.writeInt32(8, b11_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (a00_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, a00_);
    }
    if (a01_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, a01_);
    }
    if (a10_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, a10_);
    }
    if (a11_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, a11_);
    }
    if (b00_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, b00_);
    }
    if (b01_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(6, b01_);
    }
    if (b10_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(7, b10_);
    }
    if (b11_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(8, b11_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.example.grpc.server.grpcserver.MatrixRequest)) {
      return super.equals(obj);
    }
    com.example.grpc.server.grpcserver.MatrixRequest other = (com.example.grpc.server.grpcserver.MatrixRequest) obj;

    boolean result = true;
    result = result && (getA00()
        == other.getA00());
    result = result && (getA01()
        == other.getA01());
    result = result && (getA10()
        == other.getA10());
    result = result && (getA11()
        == other.getA11());
    result = result && (getB00()
        == other.getB00());
    result = result && (getB01()
        == other.getB01());
    result = result && (getB10()
        == other.getB10());
    result = result && (getB11()
        == other.getB11());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + A00_FIELD_NUMBER;
    hash = (53 * hash) + getA00();
    hash = (37 * hash) + A01_FIELD_NUMBER;
    hash = (53 * hash) + getA01();
    hash = (37 * hash) + A10_FIELD_NUMBER;
    hash = (53 * hash) + getA10();
    hash = (37 * hash) + A11_FIELD_NUMBER;
    hash = (53 * hash) + getA11();
    hash = (37 * hash) + B00_FIELD_NUMBER;
    hash = (53 * hash) + getB00();
    hash = (37 * hash) + B01_FIELD_NUMBER;
    hash = (53 * hash) + getB01();
    hash = (37 * hash) + B10_FIELD_NUMBER;
    hash = (53 * hash) + getB10();
    hash = (37 * hash) + B11_FIELD_NUMBER;
    hash = (53 * hash) + getB11();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.grpc.server.grpcserver.MatrixRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.example.grpc.server.grpcserver.MatrixRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * The request message containing the two matrices
   * </pre>
   *
   * Protobuf type {@code matrixmult.MatrixRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:matrixmult.MatrixRequest)
      com.example.grpc.server.grpcserver.MatrixRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.example.grpc.server.grpcserver.MatrixMult.internal_static_matrixmult_MatrixRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.example.grpc.server.grpcserver.MatrixMult.internal_static_matrixmult_MatrixRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.example.grpc.server.grpcserver.MatrixRequest.class, com.example.grpc.server.grpcserver.MatrixRequest.Builder.class);
    }

    // Construct using com.example.grpc.server.grpcserver.MatrixRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      a00_ = 0;

      a01_ = 0;

      a10_ = 0;

      a11_ = 0;

      b00_ = 0;

      b01_ = 0;

      b10_ = 0;

      b11_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.example.grpc.server.grpcserver.MatrixMult.internal_static_matrixmult_MatrixRequest_descriptor;
    }

    public com.example.grpc.server.grpcserver.MatrixRequest getDefaultInstanceForType() {
      return com.example.grpc.server.grpcserver.MatrixRequest.getDefaultInstance();
    }

    public com.example.grpc.server.grpcserver.MatrixRequest build() {
      com.example.grpc.server.grpcserver.MatrixRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.example.grpc.server.grpcserver.MatrixRequest buildPartial() {
      com.example.grpc.server.grpcserver.MatrixRequest result = new com.example.grpc.server.grpcserver.MatrixRequest(this);
      result.a00_ = a00_;
      result.a01_ = a01_;
      result.a10_ = a10_;
      result.a11_ = a11_;
      result.b00_ = b00_;
      result.b01_ = b01_;
      result.b10_ = b10_;
      result.b11_ = b11_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.example.grpc.server.grpcserver.MatrixRequest) {
        return mergeFrom((com.example.grpc.server.grpcserver.MatrixRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.example.grpc.server.grpcserver.MatrixRequest other) {
      if (other == com.example.grpc.server.grpcserver.MatrixRequest.getDefaultInstance()) return this;
      if (other.getA00() != 0) {
        setA00(other.getA00());
      }
      if (other.getA01() != 0) {
        setA01(other.getA01());
      }
      if (other.getA10() != 0) {
        setA10(other.getA10());
      }
      if (other.getA11() != 0) {
        setA11(other.getA11());
      }
      if (other.getB00() != 0) {
        setB00(other.getB00());
      }
      if (other.getB01() != 0) {
        setB01(other.getB01());
      }
      if (other.getB10() != 0) {
        setB10(other.getB10());
      }
      if (other.getB11() != 0) {
        setB11(other.getB11());
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.example.grpc.server.grpcserver.MatrixRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.example.grpc.server.grpcserver.MatrixRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int a00_ ;
    /**
     * <code>int32 a00 = 1;</code>
     */
    public int getA00() {
      return a00_;
    }
    /**
     * <code>int32 a00 = 1;</code>
     */
    public Builder setA00(int value) {
      
      a00_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 a00 = 1;</code>
     */
    public Builder clearA00() {
      
      a00_ = 0;
      onChanged();
      return this;
    }

    private int a01_ ;
    /**
     * <code>int32 a01 = 2;</code>
     */
    public int getA01() {
      return a01_;
    }
    /**
     * <code>int32 a01 = 2;</code>
     */
    public Builder setA01(int value) {
      
      a01_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 a01 = 2;</code>
     */
    public Builder clearA01() {
      
      a01_ = 0;
      onChanged();
      return this;
    }

    private int a10_ ;
    /**
     * <code>int32 a10 = 3;</code>
     */
    public int getA10() {
      return a10_;
    }
    /**
     * <code>int32 a10 = 3;</code>
     */
    public Builder setA10(int value) {
      
      a10_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 a10 = 3;</code>
     */
    public Builder clearA10() {
      
      a10_ = 0;
      onChanged();
      return this;
    }

    private int a11_ ;
    /**
     * <code>int32 a11 = 4;</code>
     */
    public int getA11() {
      return a11_;
    }
    /**
     * <code>int32 a11 = 4;</code>
     */
    public Builder setA11(int value) {
      
      a11_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 a11 = 4;</code>
     */
    public Builder clearA11() {
      
      a11_ = 0;
      onChanged();
      return this;
    }

    private int b00_ ;
    /**
     * <code>int32 b00 = 5;</code>
     */
    public int getB00() {
      return b00_;
    }
    /**
     * <code>int32 b00 = 5;</code>
     */
    public Builder setB00(int value) {
      
      b00_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 b00 = 5;</code>
     */
    public Builder clearB00() {
      
      b00_ = 0;
      onChanged();
      return this;
    }

    private int b01_ ;
    /**
     * <code>int32 b01 = 6;</code>
     */
    public int getB01() {
      return b01_;
    }
    /**
     * <code>int32 b01 = 6;</code>
     */
    public Builder setB01(int value) {
      
      b01_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 b01 = 6;</code>
     */
    public Builder clearB01() {
      
      b01_ = 0;
      onChanged();
      return this;
    }

    private int b10_ ;
    /**
     * <code>int32 b10 = 7;</code>
     */
    public int getB10() {
      return b10_;
    }
    /**
     * <code>int32 b10 = 7;</code>
     */
    public Builder setB10(int value) {
      
      b10_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 b10 = 7;</code>
     */
    public Builder clearB10() {
      
      b10_ = 0;
      onChanged();
      return this;
    }

    private int b11_ ;
    /**
     * <code>int32 b11 = 8;</code>
     */
    public int getB11() {
      return b11_;
    }
    /**
     * <code>int32 b11 = 8;</code>
     */
    public Builder setB11(int value) {
      
      b11_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 b11 = 8;</code>
     */
    public Builder clearB11() {
      
      b11_ = 0;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:matrixmult.MatrixRequest)
  }

  // @@protoc_insertion_point(class_scope:matrixmult.MatrixRequest)
  private static final com.example.grpc.server.grpcserver.MatrixRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.example.grpc.server.grpcserver.MatrixRequest();
  }

  public static com.example.grpc.server.grpcserver.MatrixRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MatrixRequest>
      PARSER = new com.google.protobuf.AbstractParser<MatrixRequest>() {
    public MatrixRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MatrixRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MatrixRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MatrixRequest> getParserForType() {
    return PARSER;
  }

  public com.example.grpc.server.grpcserver.MatrixRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

