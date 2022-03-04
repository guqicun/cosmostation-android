// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ethermint/feemarket/v1/feemarket.proto

package ethermint.feemarket.v1;

public final class Feemarket {
  private Feemarket() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ParamsOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ethermint.feemarket.v1.Params)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * no base fee forces the EIP-1559 base fee to 0 (needed for 0 price calls)
     * </pre>
     *
     * <code>bool no_base_fee = 1;</code>
     * @return The noBaseFee.
     */
    boolean getNoBaseFee();

    /**
     * <pre>
     * base fee change denominator bounds the amount the base fee can change
     * between blocks.
     * </pre>
     *
     * <code>uint32 base_fee_change_denominator = 2;</code>
     * @return The baseFeeChangeDenominator.
     */
    int getBaseFeeChangeDenominator();

    /**
     * <pre>
     * elasticity multiplier bounds the maximum gas limit an EIP-1559 block may
     * have.
     * </pre>
     *
     * <code>uint32 elasticity_multiplier = 3;</code>
     * @return The elasticityMultiplier.
     */
    int getElasticityMultiplier();

    /**
     * <pre>
     * initial base fee for EIP-1559 blocks.
     * </pre>
     *
     * <code>int64 initial_base_fee = 4;</code>
     * @return The initialBaseFee.
     */
    long getInitialBaseFee();

    /**
     * <pre>
     * height at which the base fee calculation is enabled.
     * </pre>
     *
     * <code>int64 enable_height = 5;</code>
     * @return The enableHeight.
     */
    long getEnableHeight();
  }
  /**
   * <pre>
   * Params defines the EVM module parameters
   * </pre>
   *
   * Protobuf type {@code ethermint.feemarket.v1.Params}
   */
  public static final class Params extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:ethermint.feemarket.v1.Params)
      ParamsOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Params.newBuilder() to construct.
    private Params(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Params() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Params();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Params(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              noBaseFee_ = input.readBool();
              break;
            }
            case 16: {

              baseFeeChangeDenominator_ = input.readUInt32();
              break;
            }
            case 24: {

              elasticityMultiplier_ = input.readUInt32();
              break;
            }
            case 32: {

              initialBaseFee_ = input.readInt64();
              break;
            }
            case 40: {

              enableHeight_ = input.readInt64();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
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
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ethermint.feemarket.v1.Feemarket.internal_static_ethermint_feemarket_v1_Params_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ethermint.feemarket.v1.Feemarket.internal_static_ethermint_feemarket_v1_Params_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ethermint.feemarket.v1.Feemarket.Params.class, ethermint.feemarket.v1.Feemarket.Params.Builder.class);
    }

    public static final int NO_BASE_FEE_FIELD_NUMBER = 1;
    private boolean noBaseFee_;
    /**
     * <pre>
     * no base fee forces the EIP-1559 base fee to 0 (needed for 0 price calls)
     * </pre>
     *
     * <code>bool no_base_fee = 1;</code>
     * @return The noBaseFee.
     */
    @java.lang.Override
    public boolean getNoBaseFee() {
      return noBaseFee_;
    }

    public static final int BASE_FEE_CHANGE_DENOMINATOR_FIELD_NUMBER = 2;
    private int baseFeeChangeDenominator_;
    /**
     * <pre>
     * base fee change denominator bounds the amount the base fee can change
     * between blocks.
     * </pre>
     *
     * <code>uint32 base_fee_change_denominator = 2;</code>
     * @return The baseFeeChangeDenominator.
     */
    @java.lang.Override
    public int getBaseFeeChangeDenominator() {
      return baseFeeChangeDenominator_;
    }

    public static final int ELASTICITY_MULTIPLIER_FIELD_NUMBER = 3;
    private int elasticityMultiplier_;
    /**
     * <pre>
     * elasticity multiplier bounds the maximum gas limit an EIP-1559 block may
     * have.
     * </pre>
     *
     * <code>uint32 elasticity_multiplier = 3;</code>
     * @return The elasticityMultiplier.
     */
    @java.lang.Override
    public int getElasticityMultiplier() {
      return elasticityMultiplier_;
    }

    public static final int INITIAL_BASE_FEE_FIELD_NUMBER = 4;
    private long initialBaseFee_;
    /**
     * <pre>
     * initial base fee for EIP-1559 blocks.
     * </pre>
     *
     * <code>int64 initial_base_fee = 4;</code>
     * @return The initialBaseFee.
     */
    @java.lang.Override
    public long getInitialBaseFee() {
      return initialBaseFee_;
    }

    public static final int ENABLE_HEIGHT_FIELD_NUMBER = 5;
    private long enableHeight_;
    /**
     * <pre>
     * height at which the base fee calculation is enabled.
     * </pre>
     *
     * <code>int64 enable_height = 5;</code>
     * @return The enableHeight.
     */
    @java.lang.Override
    public long getEnableHeight() {
      return enableHeight_;
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
      if (noBaseFee_ != false) {
        output.writeBool(1, noBaseFee_);
      }
      if (baseFeeChangeDenominator_ != 0) {
        output.writeUInt32(2, baseFeeChangeDenominator_);
      }
      if (elasticityMultiplier_ != 0) {
        output.writeUInt32(3, elasticityMultiplier_);
      }
      if (initialBaseFee_ != 0L) {
        output.writeInt64(4, initialBaseFee_);
      }
      if (enableHeight_ != 0L) {
        output.writeInt64(5, enableHeight_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (noBaseFee_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(1, noBaseFee_);
      }
      if (baseFeeChangeDenominator_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(2, baseFeeChangeDenominator_);
      }
      if (elasticityMultiplier_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(3, elasticityMultiplier_);
      }
      if (initialBaseFee_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, initialBaseFee_);
      }
      if (enableHeight_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, enableHeight_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ethermint.feemarket.v1.Feemarket.Params)) {
        return super.equals(obj);
      }
      ethermint.feemarket.v1.Feemarket.Params other = (ethermint.feemarket.v1.Feemarket.Params) obj;

      if (getNoBaseFee()
          != other.getNoBaseFee()) return false;
      if (getBaseFeeChangeDenominator()
          != other.getBaseFeeChangeDenominator()) return false;
      if (getElasticityMultiplier()
          != other.getElasticityMultiplier()) return false;
      if (getInitialBaseFee()
          != other.getInitialBaseFee()) return false;
      if (getEnableHeight()
          != other.getEnableHeight()) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + NO_BASE_FEE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getNoBaseFee());
      hash = (37 * hash) + BASE_FEE_CHANGE_DENOMINATOR_FIELD_NUMBER;
      hash = (53 * hash) + getBaseFeeChangeDenominator();
      hash = (37 * hash) + ELASTICITY_MULTIPLIER_FIELD_NUMBER;
      hash = (53 * hash) + getElasticityMultiplier();
      hash = (37 * hash) + INITIAL_BASE_FEE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getInitialBaseFee());
      hash = (37 * hash) + ENABLE_HEIGHT_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getEnableHeight());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ethermint.feemarket.v1.Feemarket.Params parseFrom(
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
    public static Builder newBuilder(ethermint.feemarket.v1.Feemarket.Params prototype) {
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
     * <pre>
     * Params defines the EVM module parameters
     * </pre>
     *
     * Protobuf type {@code ethermint.feemarket.v1.Params}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ethermint.feemarket.v1.Params)
        ethermint.feemarket.v1.Feemarket.ParamsOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ethermint.feemarket.v1.Feemarket.internal_static_ethermint_feemarket_v1_Params_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ethermint.feemarket.v1.Feemarket.internal_static_ethermint_feemarket_v1_Params_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ethermint.feemarket.v1.Feemarket.Params.class, ethermint.feemarket.v1.Feemarket.Params.Builder.class);
      }

      // Construct using ethermint.feemarket.v1.Feemarket.Params.newBuilder()
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
      @java.lang.Override
      public Builder clear() {
        super.clear();
        noBaseFee_ = false;

        baseFeeChangeDenominator_ = 0;

        elasticityMultiplier_ = 0;

        initialBaseFee_ = 0L;

        enableHeight_ = 0L;

        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ethermint.feemarket.v1.Feemarket.internal_static_ethermint_feemarket_v1_Params_descriptor;
      }

      @java.lang.Override
      public ethermint.feemarket.v1.Feemarket.Params getDefaultInstanceForType() {
        return ethermint.feemarket.v1.Feemarket.Params.getDefaultInstance();
      }

      @java.lang.Override
      public ethermint.feemarket.v1.Feemarket.Params build() {
        ethermint.feemarket.v1.Feemarket.Params result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public ethermint.feemarket.v1.Feemarket.Params buildPartial() {
        ethermint.feemarket.v1.Feemarket.Params result = new ethermint.feemarket.v1.Feemarket.Params(this);
        result.noBaseFee_ = noBaseFee_;
        result.baseFeeChangeDenominator_ = baseFeeChangeDenominator_;
        result.elasticityMultiplier_ = elasticityMultiplier_;
        result.initialBaseFee_ = initialBaseFee_;
        result.enableHeight_ = enableHeight_;
        onBuilt();
        return result;
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
        if (other instanceof ethermint.feemarket.v1.Feemarket.Params) {
          return mergeFrom((ethermint.feemarket.v1.Feemarket.Params)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ethermint.feemarket.v1.Feemarket.Params other) {
        if (other == ethermint.feemarket.v1.Feemarket.Params.getDefaultInstance()) return this;
        if (other.getNoBaseFee() != false) {
          setNoBaseFee(other.getNoBaseFee());
        }
        if (other.getBaseFeeChangeDenominator() != 0) {
          setBaseFeeChangeDenominator(other.getBaseFeeChangeDenominator());
        }
        if (other.getElasticityMultiplier() != 0) {
          setElasticityMultiplier(other.getElasticityMultiplier());
        }
        if (other.getInitialBaseFee() != 0L) {
          setInitialBaseFee(other.getInitialBaseFee());
        }
        if (other.getEnableHeight() != 0L) {
          setEnableHeight(other.getEnableHeight());
        }
        this.mergeUnknownFields(other.unknownFields);
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
        ethermint.feemarket.v1.Feemarket.Params parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ethermint.feemarket.v1.Feemarket.Params) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private boolean noBaseFee_ ;
      /**
       * <pre>
       * no base fee forces the EIP-1559 base fee to 0 (needed for 0 price calls)
       * </pre>
       *
       * <code>bool no_base_fee = 1;</code>
       * @return The noBaseFee.
       */
      @java.lang.Override
      public boolean getNoBaseFee() {
        return noBaseFee_;
      }
      /**
       * <pre>
       * no base fee forces the EIP-1559 base fee to 0 (needed for 0 price calls)
       * </pre>
       *
       * <code>bool no_base_fee = 1;</code>
       * @param value The noBaseFee to set.
       * @return This builder for chaining.
       */
      public Builder setNoBaseFee(boolean value) {
        
        noBaseFee_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * no base fee forces the EIP-1559 base fee to 0 (needed for 0 price calls)
       * </pre>
       *
       * <code>bool no_base_fee = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearNoBaseFee() {
        
        noBaseFee_ = false;
        onChanged();
        return this;
      }

      private int baseFeeChangeDenominator_ ;
      /**
       * <pre>
       * base fee change denominator bounds the amount the base fee can change
       * between blocks.
       * </pre>
       *
       * <code>uint32 base_fee_change_denominator = 2;</code>
       * @return The baseFeeChangeDenominator.
       */
      @java.lang.Override
      public int getBaseFeeChangeDenominator() {
        return baseFeeChangeDenominator_;
      }
      /**
       * <pre>
       * base fee change denominator bounds the amount the base fee can change
       * between blocks.
       * </pre>
       *
       * <code>uint32 base_fee_change_denominator = 2;</code>
       * @param value The baseFeeChangeDenominator to set.
       * @return This builder for chaining.
       */
      public Builder setBaseFeeChangeDenominator(int value) {
        
        baseFeeChangeDenominator_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * base fee change denominator bounds the amount the base fee can change
       * between blocks.
       * </pre>
       *
       * <code>uint32 base_fee_change_denominator = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearBaseFeeChangeDenominator() {
        
        baseFeeChangeDenominator_ = 0;
        onChanged();
        return this;
      }

      private int elasticityMultiplier_ ;
      /**
       * <pre>
       * elasticity multiplier bounds the maximum gas limit an EIP-1559 block may
       * have.
       * </pre>
       *
       * <code>uint32 elasticity_multiplier = 3;</code>
       * @return The elasticityMultiplier.
       */
      @java.lang.Override
      public int getElasticityMultiplier() {
        return elasticityMultiplier_;
      }
      /**
       * <pre>
       * elasticity multiplier bounds the maximum gas limit an EIP-1559 block may
       * have.
       * </pre>
       *
       * <code>uint32 elasticity_multiplier = 3;</code>
       * @param value The elasticityMultiplier to set.
       * @return This builder for chaining.
       */
      public Builder setElasticityMultiplier(int value) {
        
        elasticityMultiplier_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * elasticity multiplier bounds the maximum gas limit an EIP-1559 block may
       * have.
       * </pre>
       *
       * <code>uint32 elasticity_multiplier = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearElasticityMultiplier() {
        
        elasticityMultiplier_ = 0;
        onChanged();
        return this;
      }

      private long initialBaseFee_ ;
      /**
       * <pre>
       * initial base fee for EIP-1559 blocks.
       * </pre>
       *
       * <code>int64 initial_base_fee = 4;</code>
       * @return The initialBaseFee.
       */
      @java.lang.Override
      public long getInitialBaseFee() {
        return initialBaseFee_;
      }
      /**
       * <pre>
       * initial base fee for EIP-1559 blocks.
       * </pre>
       *
       * <code>int64 initial_base_fee = 4;</code>
       * @param value The initialBaseFee to set.
       * @return This builder for chaining.
       */
      public Builder setInitialBaseFee(long value) {
        
        initialBaseFee_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * initial base fee for EIP-1559 blocks.
       * </pre>
       *
       * <code>int64 initial_base_fee = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearInitialBaseFee() {
        
        initialBaseFee_ = 0L;
        onChanged();
        return this;
      }

      private long enableHeight_ ;
      /**
       * <pre>
       * height at which the base fee calculation is enabled.
       * </pre>
       *
       * <code>int64 enable_height = 5;</code>
       * @return The enableHeight.
       */
      @java.lang.Override
      public long getEnableHeight() {
        return enableHeight_;
      }
      /**
       * <pre>
       * height at which the base fee calculation is enabled.
       * </pre>
       *
       * <code>int64 enable_height = 5;</code>
       * @param value The enableHeight to set.
       * @return This builder for chaining.
       */
      public Builder setEnableHeight(long value) {
        
        enableHeight_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * height at which the base fee calculation is enabled.
       * </pre>
       *
       * <code>int64 enable_height = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearEnableHeight() {
        
        enableHeight_ = 0L;
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


      // @@protoc_insertion_point(builder_scope:ethermint.feemarket.v1.Params)
    }

    // @@protoc_insertion_point(class_scope:ethermint.feemarket.v1.Params)
    private static final ethermint.feemarket.v1.Feemarket.Params DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ethermint.feemarket.v1.Feemarket.Params();
    }

    public static ethermint.feemarket.v1.Feemarket.Params getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Params>
        PARSER = new com.google.protobuf.AbstractParser<Params>() {
      @java.lang.Override
      public Params parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Params(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Params> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Params> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public ethermint.feemarket.v1.Feemarket.Params getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ethermint_feemarket_v1_Params_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ethermint_feemarket_v1_Params_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&ethermint/feemarket/v1/feemarket.proto" +
      "\022\026ethermint.feemarket.v1\"\222\001\n\006Params\022\023\n\013n" +
      "o_base_fee\030\001 \001(\010\022#\n\033base_fee_change_deno" +
      "minator\030\002 \001(\r\022\035\n\025elasticity_multiplier\030\003" +
      " \001(\r\022\030\n\020initial_base_fee\030\004 \001(\003\022\025\n\renable" +
      "_height\030\005 \001(\003B0Z.github.com/tharsis/ethe" +
      "rmint/x/feemarket/typesb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ethermint_feemarket_v1_Params_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ethermint_feemarket_v1_Params_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ethermint_feemarket_v1_Params_descriptor,
        new java.lang.String[] { "NoBaseFee", "BaseFeeChangeDenominator", "ElasticityMultiplier", "InitialBaseFee", "EnableHeight", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}