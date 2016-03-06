package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.util.Messages;

/**
 *
 */
public class ExpenseReportResponseDto {

  private final Merchant merchant;
  private final ExpenseReport expenseReport;
  private final Messages messages;

  public ExpenseReportResponseDto(final Builder builder) {
    this.messages = builder.messages;
    this.merchant = builder.merchant;
    this.expenseReport = builder.expenseReport;
  }

  public Merchant getMerchant() {
    return this.merchant;
  }

  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  public boolean hasMessage() {
    return null != this.messages && this.messages.hasMessage();
  }

  public Messages getMessages() {
    return this.messages;
  }

  /**
   *
   */
  public static class Builder {

    private Merchant merchant;
    private ExpenseReport expenseReport;
    private Messages messages;
    private Messages.Builder messagesBuilder = new Messages.Builder();

    public Builder addMessages(final Messages inMessages) {
      messagesBuilder.addMessages(inMessages);
      return this;
    }

    public Builder addInfo(final String inErrorMessage) {
      this.messagesBuilder.addInfo(inErrorMessage);
      return this;
    }

    public Builder addWarn(final String inErrorMessage) {
      this.messagesBuilder.addWarn(inErrorMessage);
      return this;
    }

    public Builder addError(final String inErrorMessage) {
      this.messagesBuilder.addError(inErrorMessage);
      return this;
    }

    public Builder setExpenseReport(final ExpenseReport inExpenseReport) {
      this.expenseReport = inExpenseReport;
      return this;
    }

    public Builder setMerchant(final Merchant inMerchant) {
      this.merchant = inMerchant;
      return this;
    }

    public ExpenseReportResponseDto build() {
      this.messages = messagesBuilder.build();
      return new ExpenseReportResponseDto(this);
    }
  }
}
