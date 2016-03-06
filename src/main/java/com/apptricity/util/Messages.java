package com.apptricity.util;

import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.service.ExpenseReportService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class Messages {

  private final List<Message> messages;
  private final Map<MessageType, Integer> countMap = Maps.newHashMap();

  private Messages(final Builder builder) {
    this.messages = Lists.newArrayList(builder.messages);

    for (final Message msg : this.messages) {
      Integer count = countMap.get(msg.getMessageType());
      if (null == count) {
        count = 0;
      }
      count++;
      countMap.put(msg.getMessageType(), count);
    }
  }

  public List<Message> getMessages() {
    return Lists.newArrayList(this.messages);
  }

  public int size() {
    return this.messages.size();
  }

  public boolean hasMessage() {
    return 0 < this.messages.size();
  }

  public boolean hasWarning(){
    final Integer count = countMap.get(MessageType.WARN);
    return null != count && 0 < count;
  }

  public boolean hasError(){
    final Integer count = countMap.get(MessageType.ERROR);
    return null != count && 0 < count;
  }

  public boolean hasWarningOrError(){
    return this.hasWarning() || this.hasError();
  }


  /**
   *
   */
  public static class Builder {

    private List<Message> messages = Lists.newArrayList();

    public Builder addMessages(final Messages inMessages) {
      if (null != inMessages && 0 < inMessages.size()) {
        for (final Message msg : inMessages.getMessages()) {
          messages.add(new Message.Builder(msg).build());
        }
      }
      return this;
    }

    public Builder addInfo(final String inMessage) {
      messages.add(new Message.Builder().setInfo(inMessage).build());
      return this;
    }

    public Builder addWarn(final String inMessage) {
      messages.add(new Message.Builder().setWarn(inMessage).build());
      return this;
    }

    public Builder addError(final String inMessage) {
      messages.add(new Message.Builder().setError(inMessage).build());
      return this;
    }

    public Messages build() {
      return new Messages(this);
    }

  }
}