// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: thorchain/v1/x/thorchain/types/msg_reserve_contributor.proto

package com.types;

public final class MsgReserveContributorProto {
  private MsgReserveContributorProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgReserveContributorOrBuilder extends
      // @@protoc_insertion_point(interface_extends:types.MsgReserveContributor)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return Whether the tx field is set.
     */
    boolean hasTx();
    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return The tx.
     */
    com.common.CommonProto.Tx getTx();
    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     */
    com.common.CommonProto.TxOrBuilder getTxOrBuilder();

    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     * @return Whether the contributor field is set.
     */
    boolean hasContributor();
    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     * @return The contributor.
     */
    com.types.TypeReserveContributorProto.ReserveContributor getContributor();
    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     */
    com.types.TypeReserveContributorProto.ReserveContributorOrBuilder getContributorOrBuilder();

    /**
     * <code>bytes signer = 3 [json_name = "signer", (.gogoproto.casttype) = "github.com/cosmos/cosmos-sdk/types.AccAddress"];</code>
     * @return The signer.
     */
    com.google.protobuf.ByteString getSigner();
  }
  /**
   * Protobuf type {@code types.MsgReserveContributor}
   */
  public static final class MsgReserveContributor extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:types.MsgReserveContributor)
      MsgReserveContributorOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MsgReserveContributor.newBuilder() to construct.
    private MsgReserveContributor(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MsgReserveContributor() {
      signer_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new MsgReserveContributor();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.types.MsgReserveContributorProto.internal_static_types_MsgReserveContributor_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.types.MsgReserveContributorProto.internal_static_types_MsgReserveContributor_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.types.MsgReserveContributorProto.MsgReserveContributor.class, com.types.MsgReserveContributorProto.MsgReserveContributor.Builder.class);
    }

    public static final int TX_FIELD_NUMBER = 1;
    private com.common.CommonProto.Tx tx_;
    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return Whether the tx field is set.
     */
    @java.lang.Override
    public boolean hasTx() {
      return tx_ != null;
    }
    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     * @return The tx.
     */
    @java.lang.Override
    public com.common.CommonProto.Tx getTx() {
      return tx_ == null ? com.common.CommonProto.Tx.getDefaultInstance() : tx_;
    }
    /**
     * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
     */
    @java.lang.Override
    public com.common.CommonProto.TxOrBuilder getTxOrBuilder() {
      return tx_ == null ? com.common.CommonProto.Tx.getDefaultInstance() : tx_;
    }

    public static final int CONTRIBUTOR_FIELD_NUMBER = 2;
    private com.types.TypeReserveContributorProto.ReserveContributor contributor_;
    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     * @return Whether the contributor field is set.
     */
    @java.lang.Override
    public boolean hasContributor() {
      return contributor_ != null;
    }
    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     * @return The contributor.
     */
    @java.lang.Override
    public com.types.TypeReserveContributorProto.ReserveContributor getContributor() {
      return contributor_ == null ? com.types.TypeReserveContributorProto.ReserveContributor.getDefaultInstance() : contributor_;
    }
    /**
     * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
     */
    @java.lang.Override
    public com.types.TypeReserveContributorProto.ReserveContributorOrBuilder getContributorOrBuilder() {
      return contributor_ == null ? com.types.TypeReserveContributorProto.ReserveContributor.getDefaultInstance() : contributor_;
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
      if (contributor_ != null) {
        output.writeMessage(2, getContributor());
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
      if (contributor_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, getContributor());
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
      if (!(obj instanceof com.types.MsgReserveContributorProto.MsgReserveContributor)) {
        return super.equals(obj);
      }
      com.types.MsgReserveContributorProto.MsgReserveContributor other = (com.types.MsgReserveContributorProto.MsgReserveContributor) obj;

      if (hasTx() != other.hasTx()) return false;
      if (hasTx()) {
        if (!getTx()
            .equals(other.getTx())) return false;
      }
      if (hasContributor() != other.hasContributor()) return false;
      if (hasContributor()) {
        if (!getContributor()
            .equals(other.getContributor())) return false;
      }
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
      if (hasContributor()) {
        hash = (37 * hash) + CONTRIBUTOR_FIELD_NUMBER;
        hash = (53 * hash) + getContributor().hashCode();
      }
      hash = (37 * hash) + SIGNER_FIELD_NUMBER;
      hash = (53 * hash) + getSigner().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.types.MsgReserveContributorProto.MsgReserveContributor parseFrom(
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
    public static Builder newBuilder(com.types.MsgReserveContributorProto.MsgReserveContributor prototype) {
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
     * Protobuf type {@code types.MsgReserveContributor}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:types.MsgReserveContributor)
        com.types.MsgReserveContributorProto.MsgReserveContributorOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.types.MsgReserveContributorProto.internal_static_types_MsgReserveContributor_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.types.MsgReserveContributorProto.internal_static_types_MsgReserveContributor_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.types.MsgReserveContributorProto.MsgReserveContributor.class, com.types.MsgReserveContributorProto.MsgReserveContributor.Builder.class);
      }

      // Construct using com.types.MsgReserveContributorProto.MsgReserveContributor.newBuilder()
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
        contributor_ = null;
        if (contributorBuilder_ != null) {
          contributorBuilder_.dispose();
          contributorBuilder_ = null;
        }
        signer_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.types.MsgReserveContributorProto.internal_static_types_MsgReserveContributor_descriptor;
      }

      @java.lang.Override
      public com.types.MsgReserveContributorProto.MsgReserveContributor getDefaultInstanceForType() {
        return com.types.MsgReserveContributorProto.MsgReserveContributor.getDefaultInstance();
      }

      @java.lang.Override
      public com.types.MsgReserveContributorProto.MsgReserveContributor build() {
        com.types.MsgReserveContributorProto.MsgReserveContributor result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.types.MsgReserveContributorProto.MsgReserveContributor buildPartial() {
        com.types.MsgReserveContributorProto.MsgReserveContributor result = new com.types.MsgReserveContributorProto.MsgReserveContributor(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(com.types.MsgReserveContributorProto.MsgReserveContributor result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.tx_ = txBuilder_ == null
              ? tx_
              : txBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.contributor_ = contributorBuilder_ == null
              ? contributor_
              : contributorBuilder_.build();
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
        if (other instanceof com.types.MsgReserveContributorProto.MsgReserveContributor) {
          return mergeFrom((com.types.MsgReserveContributorProto.MsgReserveContributor)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.types.MsgReserveContributorProto.MsgReserveContributor other) {
        if (other == com.types.MsgReserveContributorProto.MsgReserveContributor.getDefaultInstance()) return this;
        if (other.hasTx()) {
          mergeTx(other.getTx());
        }
        if (other.hasContributor()) {
          mergeContributor(other.getContributor());
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
                input.readMessage(
                    getContributorFieldBuilder().getBuilder(),
                    extensionRegistry);
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

      private com.common.CommonProto.Tx tx_;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.common.CommonProto.Tx, com.common.CommonProto.Tx.Builder, com.common.CommonProto.TxOrBuilder> txBuilder_;
      /**
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       * @return Whether the tx field is set.
       */
      public boolean hasTx() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       * @return The tx.
       */
      public com.common.CommonProto.Tx getTx() {
        if (txBuilder_ == null) {
          return tx_ == null ? com.common.CommonProto.Tx.getDefaultInstance() : tx_;
        } else {
          return txBuilder_.getMessage();
        }
      }
      /**
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder setTx(com.common.CommonProto.Tx value) {
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
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder setTx(
          com.common.CommonProto.Tx.Builder builderForValue) {
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
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public Builder mergeTx(com.common.CommonProto.Tx value) {
        if (txBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0) &&
            tx_ != null &&
            tx_ != com.common.CommonProto.Tx.getDefaultInstance()) {
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
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
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
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public com.common.CommonProto.Tx.Builder getTxBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getTxFieldBuilder().getBuilder();
      }
      /**
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      public com.common.CommonProto.TxOrBuilder getTxOrBuilder() {
        if (txBuilder_ != null) {
          return txBuilder_.getMessageOrBuilder();
        } else {
          return tx_ == null ?
              com.common.CommonProto.Tx.getDefaultInstance() : tx_;
        }
      }
      /**
       * <code>.common.Tx tx = 1 [json_name = "tx", (.gogoproto.nullable) = false];</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.common.CommonProto.Tx, com.common.CommonProto.Tx.Builder, com.common.CommonProto.TxOrBuilder> 
          getTxFieldBuilder() {
        if (txBuilder_ == null) {
          txBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.common.CommonProto.Tx, com.common.CommonProto.Tx.Builder, com.common.CommonProto.TxOrBuilder>(
                  getTx(),
                  getParentForChildren(),
                  isClean());
          tx_ = null;
        }
        return txBuilder_;
      }

      private com.types.TypeReserveContributorProto.ReserveContributor contributor_;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.types.TypeReserveContributorProto.ReserveContributor, com.types.TypeReserveContributorProto.ReserveContributor.Builder, com.types.TypeReserveContributorProto.ReserveContributorOrBuilder> contributorBuilder_;
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       * @return Whether the contributor field is set.
       */
      public boolean hasContributor() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       * @return The contributor.
       */
      public com.types.TypeReserveContributorProto.ReserveContributor getContributor() {
        if (contributorBuilder_ == null) {
          return contributor_ == null ? com.types.TypeReserveContributorProto.ReserveContributor.getDefaultInstance() : contributor_;
        } else {
          return contributorBuilder_.getMessage();
        }
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public Builder setContributor(com.types.TypeReserveContributorProto.ReserveContributor value) {
        if (contributorBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          contributor_ = value;
        } else {
          contributorBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public Builder setContributor(
          com.types.TypeReserveContributorProto.ReserveContributor.Builder builderForValue) {
        if (contributorBuilder_ == null) {
          contributor_ = builderForValue.build();
        } else {
          contributorBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public Builder mergeContributor(com.types.TypeReserveContributorProto.ReserveContributor value) {
        if (contributorBuilder_ == null) {
          if (((bitField0_ & 0x00000002) != 0) &&
            contributor_ != null &&
            contributor_ != com.types.TypeReserveContributorProto.ReserveContributor.getDefaultInstance()) {
            getContributorBuilder().mergeFrom(value);
          } else {
            contributor_ = value;
          }
        } else {
          contributorBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public Builder clearContributor() {
        bitField0_ = (bitField0_ & ~0x00000002);
        contributor_ = null;
        if (contributorBuilder_ != null) {
          contributorBuilder_.dispose();
          contributorBuilder_ = null;
        }
        onChanged();
        return this;
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public com.types.TypeReserveContributorProto.ReserveContributor.Builder getContributorBuilder() {
        bitField0_ |= 0x00000002;
        onChanged();
        return getContributorFieldBuilder().getBuilder();
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      public com.types.TypeReserveContributorProto.ReserveContributorOrBuilder getContributorOrBuilder() {
        if (contributorBuilder_ != null) {
          return contributorBuilder_.getMessageOrBuilder();
        } else {
          return contributor_ == null ?
              com.types.TypeReserveContributorProto.ReserveContributor.getDefaultInstance() : contributor_;
        }
      }
      /**
       * <code>.types.ReserveContributor contributor = 2 [json_name = "contributor", (.gogoproto.nullable) = false];</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.types.TypeReserveContributorProto.ReserveContributor, com.types.TypeReserveContributorProto.ReserveContributor.Builder, com.types.TypeReserveContributorProto.ReserveContributorOrBuilder> 
          getContributorFieldBuilder() {
        if (contributorBuilder_ == null) {
          contributorBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.types.TypeReserveContributorProto.ReserveContributor, com.types.TypeReserveContributorProto.ReserveContributor.Builder, com.types.TypeReserveContributorProto.ReserveContributorOrBuilder>(
                  getContributor(),
                  getParentForChildren(),
                  isClean());
          contributor_ = null;
        }
        return contributorBuilder_;
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


      // @@protoc_insertion_point(builder_scope:types.MsgReserveContributor)
    }

    // @@protoc_insertion_point(class_scope:types.MsgReserveContributor)
    private static final com.types.MsgReserveContributorProto.MsgReserveContributor DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.types.MsgReserveContributorProto.MsgReserveContributor();
    }

    public static com.types.MsgReserveContributorProto.MsgReserveContributor getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MsgReserveContributor>
        PARSER = new com.google.protobuf.AbstractParser<MsgReserveContributor>() {
      @java.lang.Override
      public MsgReserveContributor parsePartialFrom(
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

    public static com.google.protobuf.Parser<MsgReserveContributor> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MsgReserveContributor> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.types.MsgReserveContributorProto.MsgReserveContributor getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_types_MsgReserveContributor_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_types_MsgReserveContributor_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n<thorchain/v1/x/thorchain/types/msg_res" +
      "erve_contributor.proto\022\005types\032=thorchain" +
      "/v1/x/thorchain/types/type_reserve_contr" +
      "ibutor.proto\032 thorchain/v1/common/common" +
      ".proto\032\024gogoproto/gogo.proto\"\307\001\n\025MsgRese" +
      "rveContributor\022 \n\002tx\030\001 \001(\0132\n.common.TxB\004" +
      "\310\336\037\000R\002tx\022A\n\013contributor\030\002 \001(\0132\031.types.Re" +
      "serveContributorB\004\310\336\037\000R\013contributor\022I\n\006s" +
      "igner\030\003 \001(\014B1\372\336\037-github.com/cosmos/cosmo" +
      "s-sdk/types.AccAddressR\006signerB\212\001\n\tcom.t" +
      "ypesB\032MsgReserveContributorProtoZ/gitlab" +
      ".com/thorchain/thornode/x/thorchain/type" +
      "s\242\002\003TXX\252\002\005Types\312\002\005Types\342\002\021Types\\GPBMetad" +
      "ata\352\002\005Typesb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.types.TypeReserveContributorProto.getDescriptor(),
          com.common.CommonProto.getDescriptor(),
          com.gogoproto.GogoProto.getDescriptor(),
        });
    internal_static_types_MsgReserveContributor_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_types_MsgReserveContributor_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_types_MsgReserveContributor_descriptor,
        new java.lang.String[] { "Tx", "Contributor", "Signer", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.gogoproto.GogoProto.casttype);
    registry.add(com.gogoproto.GogoProto.nullable);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.types.TypeReserveContributorProto.getDescriptor();
    com.common.CommonProto.getDescriptor();
    com.gogoproto.GogoProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}