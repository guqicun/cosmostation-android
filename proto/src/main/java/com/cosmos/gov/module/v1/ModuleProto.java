// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cosmos/gov/module/v1/module.proto

package com.cosmos.gov.module.v1;

public final class ModuleProto {
  private ModuleProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ModuleOrBuilder extends
      // @@protoc_insertion_point(interface_extends:cosmos.gov.module.v1.Module)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * max_metadata_len defines the maximum proposal metadata length.
     * Defaults to 255 if not explicitly set.
     * </pre>
     *
     * <code>uint64 max_metadata_len = 1 [json_name = "maxMetadataLen"];</code>
     * @return The maxMetadataLen.
     */
    long getMaxMetadataLen();

    /**
     * <pre>
     * authority defines the custom module authority. If not set, defaults to the governance module.
     * </pre>
     *
     * <code>string authority = 2 [json_name = "authority"];</code>
     * @return The authority.
     */
    java.lang.String getAuthority();
    /**
     * <pre>
     * authority defines the custom module authority. If not set, defaults to the governance module.
     * </pre>
     *
     * <code>string authority = 2 [json_name = "authority"];</code>
     * @return The bytes for authority.
     */
    com.google.protobuf.ByteString
        getAuthorityBytes();
  }
  /**
   * <pre>
   * Module is the config object of the gov module.
   * </pre>
   *
   * Protobuf type {@code cosmos.gov.module.v1.Module}
   */
  public static final class Module extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:cosmos.gov.module.v1.Module)
      ModuleOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Module.newBuilder() to construct.
    private Module(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Module() {
      authority_ = "";
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new Module();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.cosmos.gov.module.v1.ModuleProto.internal_static_cosmos_gov_module_v1_Module_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.cosmos.gov.module.v1.ModuleProto.internal_static_cosmos_gov_module_v1_Module_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.cosmos.gov.module.v1.ModuleProto.Module.class, com.cosmos.gov.module.v1.ModuleProto.Module.Builder.class);
    }

    public static final int MAX_METADATA_LEN_FIELD_NUMBER = 1;
    private long maxMetadataLen_ = 0L;
    /**
     * <pre>
     * max_metadata_len defines the maximum proposal metadata length.
     * Defaults to 255 if not explicitly set.
     * </pre>
     *
     * <code>uint64 max_metadata_len = 1 [json_name = "maxMetadataLen"];</code>
     * @return The maxMetadataLen.
     */
    @java.lang.Override
    public long getMaxMetadataLen() {
      return maxMetadataLen_;
    }

    public static final int AUTHORITY_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile java.lang.Object authority_ = "";
    /**
     * <pre>
     * authority defines the custom module authority. If not set, defaults to the governance module.
     * </pre>
     *
     * <code>string authority = 2 [json_name = "authority"];</code>
     * @return The authority.
     */
    @java.lang.Override
    public java.lang.String getAuthority() {
      java.lang.Object ref = authority_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        authority_ = s;
        return s;
      }
    }
    /**
     * <pre>
     * authority defines the custom module authority. If not set, defaults to the governance module.
     * </pre>
     *
     * <code>string authority = 2 [json_name = "authority"];</code>
     * @return The bytes for authority.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getAuthorityBytes() {
      java.lang.Object ref = authority_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        authority_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
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
      if (maxMetadataLen_ != 0L) {
        output.writeUInt64(1, maxMetadataLen_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(authority_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, authority_);
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (maxMetadataLen_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(1, maxMetadataLen_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(authority_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, authority_);
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
      if (!(obj instanceof com.cosmos.gov.module.v1.ModuleProto.Module)) {
        return super.equals(obj);
      }
      com.cosmos.gov.module.v1.ModuleProto.Module other = (com.cosmos.gov.module.v1.ModuleProto.Module) obj;

      if (getMaxMetadataLen()
          != other.getMaxMetadataLen()) return false;
      if (!getAuthority()
          .equals(other.getAuthority())) return false;
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
      hash = (37 * hash) + MAX_METADATA_LEN_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getMaxMetadataLen());
      hash = (37 * hash) + AUTHORITY_FIELD_NUMBER;
      hash = (53 * hash) + getAuthority().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.cosmos.gov.module.v1.ModuleProto.Module parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.cosmos.gov.module.v1.ModuleProto.Module parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.cosmos.gov.module.v1.ModuleProto.Module parseFrom(
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
    public static Builder newBuilder(com.cosmos.gov.module.v1.ModuleProto.Module prototype) {
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
     * Module is the config object of the gov module.
     * </pre>
     *
     * Protobuf type {@code cosmos.gov.module.v1.Module}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:cosmos.gov.module.v1.Module)
        com.cosmos.gov.module.v1.ModuleProto.ModuleOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.cosmos.gov.module.v1.ModuleProto.internal_static_cosmos_gov_module_v1_Module_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.cosmos.gov.module.v1.ModuleProto.internal_static_cosmos_gov_module_v1_Module_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.cosmos.gov.module.v1.ModuleProto.Module.class, com.cosmos.gov.module.v1.ModuleProto.Module.Builder.class);
      }

      // Construct using com.cosmos.gov.module.v1.ModuleProto.Module.newBuilder()
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
        maxMetadataLen_ = 0L;
        authority_ = "";
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.cosmos.gov.module.v1.ModuleProto.internal_static_cosmos_gov_module_v1_Module_descriptor;
      }

      @java.lang.Override
      public com.cosmos.gov.module.v1.ModuleProto.Module getDefaultInstanceForType() {
        return com.cosmos.gov.module.v1.ModuleProto.Module.getDefaultInstance();
      }

      @java.lang.Override
      public com.cosmos.gov.module.v1.ModuleProto.Module build() {
        com.cosmos.gov.module.v1.ModuleProto.Module result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.cosmos.gov.module.v1.ModuleProto.Module buildPartial() {
        com.cosmos.gov.module.v1.ModuleProto.Module result = new com.cosmos.gov.module.v1.ModuleProto.Module(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(com.cosmos.gov.module.v1.ModuleProto.Module result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.maxMetadataLen_ = maxMetadataLen_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.authority_ = authority_;
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
        if (other instanceof com.cosmos.gov.module.v1.ModuleProto.Module) {
          return mergeFrom((com.cosmos.gov.module.v1.ModuleProto.Module)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.cosmos.gov.module.v1.ModuleProto.Module other) {
        if (other == com.cosmos.gov.module.v1.ModuleProto.Module.getDefaultInstance()) return this;
        if (other.getMaxMetadataLen() != 0L) {
          setMaxMetadataLen(other.getMaxMetadataLen());
        }
        if (!other.getAuthority().isEmpty()) {
          authority_ = other.authority_;
          bitField0_ |= 0x00000002;
          onChanged();
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
              case 8: {
                maxMetadataLen_ = input.readUInt64();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              case 18: {
                authority_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
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

      private long maxMetadataLen_ ;
      /**
       * <pre>
       * max_metadata_len defines the maximum proposal metadata length.
       * Defaults to 255 if not explicitly set.
       * </pre>
       *
       * <code>uint64 max_metadata_len = 1 [json_name = "maxMetadataLen"];</code>
       * @return The maxMetadataLen.
       */
      @java.lang.Override
      public long getMaxMetadataLen() {
        return maxMetadataLen_;
      }
      /**
       * <pre>
       * max_metadata_len defines the maximum proposal metadata length.
       * Defaults to 255 if not explicitly set.
       * </pre>
       *
       * <code>uint64 max_metadata_len = 1 [json_name = "maxMetadataLen"];</code>
       * @param value The maxMetadataLen to set.
       * @return This builder for chaining.
       */
      public Builder setMaxMetadataLen(long value) {

        maxMetadataLen_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * max_metadata_len defines the maximum proposal metadata length.
       * Defaults to 255 if not explicitly set.
       * </pre>
       *
       * <code>uint64 max_metadata_len = 1 [json_name = "maxMetadataLen"];</code>
       * @return This builder for chaining.
       */
      public Builder clearMaxMetadataLen() {
        bitField0_ = (bitField0_ & ~0x00000001);
        maxMetadataLen_ = 0L;
        onChanged();
        return this;
      }

      private java.lang.Object authority_ = "";
      /**
       * <pre>
       * authority defines the custom module authority. If not set, defaults to the governance module.
       * </pre>
       *
       * <code>string authority = 2 [json_name = "authority"];</code>
       * @return The authority.
       */
      public java.lang.String getAuthority() {
        java.lang.Object ref = authority_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          authority_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <pre>
       * authority defines the custom module authority. If not set, defaults to the governance module.
       * </pre>
       *
       * <code>string authority = 2 [json_name = "authority"];</code>
       * @return The bytes for authority.
       */
      public com.google.protobuf.ByteString
          getAuthorityBytes() {
        java.lang.Object ref = authority_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          authority_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       * authority defines the custom module authority. If not set, defaults to the governance module.
       * </pre>
       *
       * <code>string authority = 2 [json_name = "authority"];</code>
       * @param value The authority to set.
       * @return This builder for chaining.
       */
      public Builder setAuthority(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        authority_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <pre>
       * authority defines the custom module authority. If not set, defaults to the governance module.
       * </pre>
       *
       * <code>string authority = 2 [json_name = "authority"];</code>
       * @return This builder for chaining.
       */
      public Builder clearAuthority() {
        authority_ = getDefaultInstance().getAuthority();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <pre>
       * authority defines the custom module authority. If not set, defaults to the governance module.
       * </pre>
       *
       * <code>string authority = 2 [json_name = "authority"];</code>
       * @param value The bytes for authority to set.
       * @return This builder for chaining.
       */
      public Builder setAuthorityBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        authority_ = value;
        bitField0_ |= 0x00000002;
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


      // @@protoc_insertion_point(builder_scope:cosmos.gov.module.v1.Module)
    }

    // @@protoc_insertion_point(class_scope:cosmos.gov.module.v1.Module)
    private static final com.cosmos.gov.module.v1.ModuleProto.Module DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.cosmos.gov.module.v1.ModuleProto.Module();
    }

    public static com.cosmos.gov.module.v1.ModuleProto.Module getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Module>
        PARSER = new com.google.protobuf.AbstractParser<Module>() {
      @java.lang.Override
      public Module parsePartialFrom(
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

    public static com.google.protobuf.Parser<Module> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Module> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.cosmos.gov.module.v1.ModuleProto.Module getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cosmos_gov_module_v1_Module_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cosmos_gov_module_v1_Module_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!cosmos/gov/module/v1/module.proto\022\024cos" +
      "mos.gov.module.v1\032 cosmos/app/v1alpha1/m" +
      "odule.proto\"l\n\006Module\022(\n\020max_metadata_le" +
      "n\030\001 \001(\004R\016maxMetadataLen\022\034\n\tauthority\030\002 \001" +
      "(\tR\tauthority:\032\272\300\226\332\001\024\n\022cosmossdk.io/x/go" +
      "vB\230\001\n\030com.cosmos.gov.module.v1B\013ModulePr" +
      "oto\242\002\003CGM\252\002\024Cosmos.Gov.Module.V1\312\002\024Cosmo" +
      "s\\Gov\\Module\\V1\342\002 Cosmos\\Gov\\Module\\V1\\G" +
      "PBMetadata\352\002\027Cosmos::Gov::Module::V1b\006pr" +
      "oto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.cosmos.app.v1alpha1.ModuleProto.getDescriptor(),
        });
    internal_static_cosmos_gov_module_v1_Module_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cosmos_gov_module_v1_Module_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cosmos_gov_module_v1_Module_descriptor,
        new java.lang.String[] { "MaxMetadataLen", "Authority", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.cosmos.app.v1alpha1.ModuleProto.module);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.cosmos.app.v1alpha1.ModuleProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}