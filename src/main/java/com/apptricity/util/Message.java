package com.apptricity.util;

/**
 * A simple class containing a Message string and a message type.
 */
public class Message {
  private final String content;
  private final MessageType messageType;

  private Message(final Builder builder) {
    this.content = builder.content;
    this.messageType = builder.messageType;
  }

  public String getContent() {
    return content;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  /**
   *
   */
  public static class Builder {

    private String content;
    private MessageType messageType = MessageType.INFO;

    public Builder () {
    }

    public Builder (final Message inMessage) {
      this.content = inMessage.getContent();
      this.messageType = inMessage.getMessageType();
    }

    public Builder setInfo(final String inMessage) {
      this.content = inMessage;
      this.messageType = MessageType.INFO;
      return this;
    }

    public Builder setWarn(final String inMessage) {
      this.content = inMessage;
      this.messageType = MessageType.WARN;
      return this;
    }

    public Builder setError(final String inMessage) {
      this.content = inMessage;
      this.messageType = MessageType.ERROR;
      return this;
    }

    public Message build() {
      return new Message(this);
    }
  }
}