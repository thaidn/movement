// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MixMessage.proto

package io.movement;

public final class MixMessageProtos {
  private MixMessageProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface MixMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:movement.MixMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional bytes pseudonym = 1;</code>
     */
    boolean hasPseudonym();
    /**
     * <code>optional bytes pseudonym = 1;</code>
     */
    com.google.protobuf.ByteString getPseudonym();

    /**
     * <code>optional bytes message = 2;</code>
     */
    boolean hasMessage();
    /**
     * <code>optional bytes message = 2;</code>
     */
    com.google.protobuf.ByteString getMessage();
  }
  /**
   * Protobuf type {@code movement.MixMessage}
   */
  public static final class MixMessage extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:movement.MixMessage)
      MixMessageOrBuilder {
    // Use MixMessage.newBuilder() to construct.
    private MixMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private MixMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final MixMessage defaultInstance;
    public static MixMessage getDefaultInstance() {
      return defaultInstance;
    }

    public MixMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private MixMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              pseudonym_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              message_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.movement.MixMessageProtos.internal_static_movement_MixMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.movement.MixMessageProtos.internal_static_movement_MixMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.movement.MixMessageProtos.MixMessage.class, io.movement.MixMessageProtos.MixMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<MixMessage> PARSER =
        new com.google.protobuf.AbstractParser<MixMessage>() {
      public MixMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MixMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<MixMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int PSEUDONYM_FIELD_NUMBER = 1;
    private com.google.protobuf.ByteString pseudonym_;
    /**
     * <code>optional bytes pseudonym = 1;</code>
     */
    public boolean hasPseudonym() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional bytes pseudonym = 1;</code>
     */
    public com.google.protobuf.ByteString getPseudonym() {
      return pseudonym_;
    }

    public static final int MESSAGE_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString message_;
    /**
     * <code>optional bytes message = 2;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional bytes message = 2;</code>
     */
    public com.google.protobuf.ByteString getMessage() {
      return message_;
    }

    private void initFields() {
      pseudonym_ = com.google.protobuf.ByteString.EMPTY;
      message_ = com.google.protobuf.ByteString.EMPTY;
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
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, pseudonym_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, message_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, pseudonym_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, message_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static io.movement.MixMessageProtos.MixMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static io.movement.MixMessageProtos.MixMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static io.movement.MixMessageProtos.MixMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(io.movement.MixMessageProtos.MixMessage prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code movement.MixMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:movement.MixMessage)
        io.movement.MixMessageProtos.MixMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return io.movement.MixMessageProtos.internal_static_movement_MixMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return io.movement.MixMessageProtos.internal_static_movement_MixMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                io.movement.MixMessageProtos.MixMessage.class, io.movement.MixMessageProtos.MixMessage.Builder.class);
      }

      // Construct using io.movement.MixMessageProtos.MixMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        pseudonym_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        message_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return io.movement.MixMessageProtos.internal_static_movement_MixMessage_descriptor;
      }

      public io.movement.MixMessageProtos.MixMessage getDefaultInstanceForType() {
        return io.movement.MixMessageProtos.MixMessage.getDefaultInstance();
      }

      public io.movement.MixMessageProtos.MixMessage build() {
        io.movement.MixMessageProtos.MixMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public io.movement.MixMessageProtos.MixMessage buildPartial() {
        io.movement.MixMessageProtos.MixMessage result = new io.movement.MixMessageProtos.MixMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.pseudonym_ = pseudonym_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.message_ = message_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof io.movement.MixMessageProtos.MixMessage) {
          return mergeFrom((io.movement.MixMessageProtos.MixMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(io.movement.MixMessageProtos.MixMessage other) {
        if (other == io.movement.MixMessageProtos.MixMessage.getDefaultInstance()) return this;
        if (other.hasPseudonym()) {
          setPseudonym(other.getPseudonym());
        }
        if (other.hasMessage()) {
          setMessage(other.getMessage());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        io.movement.MixMessageProtos.MixMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (io.movement.MixMessageProtos.MixMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.ByteString pseudonym_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes pseudonym = 1;</code>
       */
      public boolean hasPseudonym() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional bytes pseudonym = 1;</code>
       */
      public com.google.protobuf.ByteString getPseudonym() {
        return pseudonym_;
      }
      /**
       * <code>optional bytes pseudonym = 1;</code>
       */
      public Builder setPseudonym(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        pseudonym_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes pseudonym = 1;</code>
       */
      public Builder clearPseudonym() {
        bitField0_ = (bitField0_ & ~0x00000001);
        pseudonym_ = getDefaultInstance().getPseudonym();
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString message_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes message = 2;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional bytes message = 2;</code>
       */
      public com.google.protobuf.ByteString getMessage() {
        return message_;
      }
      /**
       * <code>optional bytes message = 2;</code>
       */
      public Builder setMessage(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        message_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes message = 2;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000002);
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:movement.MixMessage)
    }

    static {
      defaultInstance = new MixMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:movement.MixMessage)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_movement_MixMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_movement_MixMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020MixMessage.proto\022\010movement\"0\n\nMixMessa" +
      "ge\022\021\n\tpseudonym\030\001 \001(\014\022\017\n\007message\030\002 \001(\014B\037" +
      "\n\013io.movementB\020MixMessageProtos"
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
    internal_static_movement_MixMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_movement_MixMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_movement_MixMessage_descriptor,
        new java.lang.String[] { "Pseudonym", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}