// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: thorchain/v1/x/thorchain/types/msg_refund.proto

package com.types;

public final class MsgRefundProto {
  private MsgRefundProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgRefundTxOrBuilder extends
      // @@protoc_insertion_point(interface_extends:types.MsgRefundTx)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return Whether the tx field is set.
     */
    boolean hasTx();
    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return The tx.
     */
    com.types.TypeObservedTxProto.ObservedTx getTx();
    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     */
    com.types.TypeObservedTxProto.ObservedTxOrBuilder getTxOrBuilder();

    /**
     * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
     * @return The inTxId.
     */
    java.lang.String getInTxId();
    /**
     * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
     * @return The bytes for inTxId.
     */
    com.google.protobuf.ByteString
        getInTxIdBytes();

    /**
     * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
     * @return The signer.
     */
    com.google.protobuf.ByteString getSigner();
  }
  /**
   * Protobuf type {@code types.MsgRefundTx}
   */
  public static final class MsgRefundTx extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:types.MsgRefundTx)
      MsgRefundTxOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MsgRefundTx.newBuilder() to construct.
    private MsgRefundTx(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgRefundTx() {
      inTxId_ = "";
      signer_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MsgRefundTx();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.types.MsgRefundProto.internal_static_types_MsgRefundTx_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.types.MsgRefundProto.internal_static_types_MsgRefundTx_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.types.MsgRefundProto.MsgRefundTx.class, com.types.MsgRefundProto.MsgRefundTx.Builder.class);
    }

    public static final int TX_FIELD_NUMBER = 1;
    private com.types.TypeObservedTxProto.ObservedTx tx_;
    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return Whether the tx field is set.
     */
    @java.lang.Override
    public boolean hasTx() {
      return tx_ != null;
    }
    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return The tx.
     */
    @java.lang.Override
    public com.types.TypeObservedTxProto.ObservedTx getTx() {
      return tx_ == null ? com.types.TypeObservedTxProto.ObservedTx.getDefaultInstance() : tx_;
    }
    /**
     * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     */
    @java.lang.Override
    public com.types.TypeObservedTxProto.ObservedTxOrBuilder getTxOrBuilder() {
      return tx_ == null ? com.types.TypeObservedTxProto.ObservedTx.getDefaultInstance() : tx_;
    }

    public static final int IN_TX_ID_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile java.lang.Object inTxId_ = "";
    /**
     * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
     * @return The inTxId.
     */
    @java.lang.Override
    public java.lang.String getInTxId() {
      java.lang.Object ref = inTxId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        inTxId_ = s;
        return s;
      }
    }
    /**
     * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
     * @return The bytes for inTxId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getInTxIdBytes() {
      java.lang.Object ref = inTxId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        inTxId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SIGNER_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString signer_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
     * @return The signer.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSigner() {
      return signer_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (tx_ != null) {
        output.writeMessage(1, getTx());
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(inTxId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, inTxId_);
      }
      if (!signer_.isEmpty()) {
        output.writeBytes(3, signer_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (tx_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getTx());
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(inTxId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, inTxId_);
      }
      if (!signer_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, signer_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.types.MsgRefundProto.MsgRefundTx)) {
        return super.equals(obj);
      }
      com.types.MsgRefundProto.MsgRefundTx other = (com.types.MsgRefundProto.MsgRefundTx) obj;

      if (hasTx() != other.hasTx()) return false;
      if (hasTx()) {
        if (!getTx()
            .equals(other.getTx())) return false;
      }
      if (!getInTxId()
          .equals(other.getInTxId())) return false;
      if (!getSigner()
          .equals(other.getSigner())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasTx()) {
        hash = (37 * hash) + TX_FIELD_NUMBER;
        hash = (53 * hash) + getTx().hashCode();
      }
      hash = (37 * hash) + IN_TX_ID_FIELD_NUMBER;
      hash = (53 * hash) + getInTxId().hashCode();
      hash = (37 * hash) + SIGNER_FIELD_NUMBER;
      hash = (53 * hash) + getSigner().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.types.MsgRefundProto.MsgRefundTx parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.types.MsgRefundProto.MsgRefundTx parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.types.MsgRefundProto.MsgRefundTx parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.types.MsgRefundProto.MsgRefundTx prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
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
     * Protobuf type {@code types.MsgRefundTx}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:types.MsgRefundTx)
        com.types.MsgRefundProto.MsgRefundTxOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.types.MsgRefundProto.internal_static_types_MsgRefundTx_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.types.MsgRefundProto.internal_static_types_MsgRefundTx_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.types.MsgRefundProto.MsgRefundTx.class, com.types.MsgRefundProto.MsgRefundTx.Builder.class);
      }

      // Construct using com.types.MsgRefundProto.MsgRefundTx.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        tx_ = null;
        if (txBuilder_ != null) {
          txBuilder_.dispose();
          txBuilder_ = null;
        }
        inTxId_ = "";
        signer_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.types.MsgRefundProto.internal_static_types_MsgRefundTx_descriptor;
      }

      @java.lang.Override
      public com.types.MsgRefundProto.MsgRefundTx getDefaultInstanceForType() {
        return com.types.MsgRefundProto.MsgRefundTx.getDefaultInstance();
      }

      @java.lang.Override
      public com.types.MsgRefundProto.MsgRefundTx build() {
        com.types.MsgRefundProto.MsgRefundTx result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.types.MsgRefundProto.MsgRefundTx buildPartial() {
        com.types.MsgRefundProto.MsgRefundTx result = new com.types.MsgRefundProto.MsgRefundTx(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(com.types.MsgRefundProto.MsgRefundTx result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.tx_ = txBuilder_ == null
              ? tx_
              : txBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.inTxId_ = inTxId_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.signer_ = signer_;
        }
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.types.MsgRefundProto.MsgRefundTx) {
          return mergeFrom((com.types.MsgRefundProto.MsgRefundTx)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.types.MsgRefundProto.MsgRefundTx other) {
        if (other == com.types.MsgRefundProto.MsgRefundTx.getDefaultInstance()) return this;
        if (other.hasTx()) {
          mergeTx(other.getTx());
        }
        if (!other.getInTxId().isEmpty()) {
          inTxId_ = other.inTxId_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        if (other.getSigner() != com.google.protobuf.ByteString.EMPTY) {
          setSigner(other.getSigner());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                input.readMessage(
                    getTxFieldBuilder().getBuilder(),
                    extensionRegistry);
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 18: {
                inTxId_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              case 26: {
                signer_ = input.readBytes();
                bitField0_ |= 0x00000004;
                break;
              } // case 26
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private com.types.TypeObservedTxProto.ObservedTx tx_;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.types.TypeObservedTxProto.ObservedTx, com.types.TypeObservedTxProto.ObservedTx.Builder, com.types.TypeObservedTxProto.ObservedTxOrBuilder> txBuilder_;
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       * @return Whether the tx field is set.
       */
      public boolean hasTx() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       * @return The tx.
       */
      public com.types.TypeObservedTxProto.ObservedTx getTx() {
        if (txBuilder_ == null) {
          return tx_ == null ? com.types.TypeObservedTxProto.ObservedTx.getDefaultInstance() : tx_;
        } else {
          return txBuilder_.getMessage();
        }
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder setTx(com.types.TypeObservedTxProto.ObservedTx value) {
        if (txBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          tx_ = value;
        } else {
          txBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder setTx(
          com.types.TypeObservedTxProto.ObservedTx.Builder builderForValue) {
        if (txBuilder_ == null) {
          tx_ = builderForValue.build();
        } else {
          txBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder mergeTx(com.types.TypeObservedTxProto.ObservedTx value) {
        if (txBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0) &&
            tx_ != null &&
            tx_ != com.types.TypeObservedTxProto.ObservedTx.getDefaultInstance()) {
            getTxBuilder().mergeFrom(value);
          } else {
            tx_ = value;
          }
        } else {
          txBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder clearTx() {
        bitField0_ = (bitField0_ & ~0x00000001);
        tx_ = null;
        if (txBuilder_ != null) {
          txBuilder_.dispose();
          txBuilder_ = null;
        }
        onChanged();
        return this;
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public com.types.TypeObservedTxProto.ObservedTx.Builder getTxBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getTxFieldBuilder().getBuilder();
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public com.types.TypeObservedTxProto.ObservedTxOrBuilder getTxOrBuilder() {
        if (txBuilder_ != null) {
          return txBuilder_.getMessageOrBuilder();
        } else {
          return tx_ == null ?
              com.types.TypeObservedTxProto.ObservedTx.getDefaultInstance() : tx_;
        }
      }
      /**
       * <code>.types.ObservedTx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.types.TypeObservedTxProto.ObservedTx, com.types.TypeObservedTxProto.ObservedTx.Builder, com.types.TypeObservedTxProto.ObservedTxOrBuilder> 
          getTxFieldBuilder() {
        if (txBuilder_ == null) {
          txBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.types.TypeObservedTxProto.ObservedTx, com.types.TypeObservedTxProto.ObservedTx.Builder, com.types.TypeObservedTxProto.ObservedTxOrBuilder>(
                  getTx(),
                  getParentForChildren(),
                  isClean());
          tx_ = null;
        }
        return txBuilder_;
      }

      private java.lang.Object inTxId_ = "";
      /**
       * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
       * @return The inTxId.
       */
      public java.lang.String getInTxId() {
        java.lang.Object ref = inTxId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          inTxId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
       * @return The bytes for inTxId.
       */
      public com.google.protobuf.ByteString
          getInTxIdBytes() {
        java.lang.Object ref = inTxId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          inTxId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
       * @param value The inTxId to set.
       * @return This builder for chaining.
       */
      public Builder setInTxId(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        inTxId_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
       * @return This builder for chaining.
       */
      public Builder clearInTxId() {
        inTxId_ = getDefaultInstance().getInTxId();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>string in_tx_id = 2 [json_name = "inTxId", (.gogoproto.customname) = "InTxID", (.gogoproto.casttype) = "gitlab.com/thorchain/thornode/common.TxID"];</code>
       * @param value The bytes for inTxId to set.
       * @return This builder for chaining.
       */
      public Builder setInTxIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        inTxId_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString signer_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
       * @return The signer.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString getSigner() {
        return signer_;
      }
      /**
       * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
       * @param value The signer to set.
       * @return This builder for chaining.
       */
      public Builder setSigner(com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        signer_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
       * @return This builder for chaining.
       */
      public Builder clearSigner() {
        bitField0_ = (bitField0_ & ~0x00000004);
        signer_ = getDefaultInstance().getSigner();
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:types.MsgRefundTx)
    }

    // @@protoc_insertion_point(class_scope:types.MsgRefundTx)
    private static final com.types.MsgRefundProto.MsgRefundTx DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.types.MsgRefundProto.MsgRefundTx();
    }

    public static com.types.MsgRefundProto.MsgRefundTx getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MsgRefundTx>
        PARSER = new com.google.protobuf.AbstractParser<MsgRefundTx>() {
      @java.lang.Override
      public MsgRefundTx parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<MsgRefundTx> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgRefundTx> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.types.MsgRefundProto.MsgRefundTx getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_types_MsgRefundTx_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_types_MsgRefundTx_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n/thorchain/v1/x/thorchain/types/msg_ref" +
      "und.proto\022\005types\0325thorchain/v1/x/thorcha" +
      "in/types/type_observed_tx.proto\032\024gogopro" +
      "to/gogo.proto\"\324\001\n\013MsgRefundTx\022\'\n\002tx\030\001 \001(" +
      "\0132\021.types.ObservedTxB\004\310\336\037\000R\002tx\022Q\n\010in_tx_" +
      "id\030\002 \001(\tB7\342\336\037\006InTxID\372\336\037)gitlab.com/thorc" +
      "hain/thornode/common.TxIDR\006inTxId\022I\n\006sig" +
      "ner\030\003 \001(\014B1\372\336\037-github.com/cosmos/cosmos-" +
      "sdk/types.AccAddressR\006signerB~\n\tcom.type" +
      "sB\016MsgRefundProtoZ/gitlab.com/thorchain/" +
      "thornode/x/thorchain/types\242\002\003TXX\252\002\005Types" +
      "\312\002\005Types\342\002\021Types\\GPBMetadata\352\002\005Typesb\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.types.TypeObservedTxProto.getDescriptor(),
          com.gogoproto.GogoProto.getDescriptor(),
        });
    internal_static_types_MsgRefundTx_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_types_MsgRefundTx_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_types_MsgRefundTx_descriptor,
        new java.lang.String[] { "Tx", "InTxId", "Signer", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.gogoproto.GogoProto.casttype);
    registry.add(com.gogoproto.GogoProto.customname);
    registry.add(com.gogoproto.GogoProto.nullable);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.types.TypeObservedTxProto.getDescriptor();
    com.gogoproto.GogoProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
