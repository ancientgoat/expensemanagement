package com.apptricity.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 */
public class ErrorMessage {

  private final List<String> errors;

  public ErrorMessage(final Builder builder) {
    this.errors = builder.errors;
  }

  public List<String> getErrors() {
    return Lists.newArrayList(errors);
  }

  public boolean haveErrors() {
    return null != errors && 0 < errors.size();
  }

  /**
   *
   */
  public static class Builder {

    private List<String> errors = Lists.newArrayList();

    public Builder addError(final String inError) {
      this.errors.add(inError);
      return this;
    }

    public boolean haveErrors() {
      return 0 < this.errors.size();
    }

    public ErrorMessage build() {
      return new ErrorMessage(this);
    }
  }
}
