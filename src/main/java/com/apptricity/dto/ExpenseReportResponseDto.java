package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.util.Messages;

/**
 * A Data Transfer Object that is used to return information back to an API caller.
 */
public class ExpenseReportResponseDto {

  /**
   * ExpenseReport returned to the caller.
   */
  private final ExpenseReport expenseReport;

  /**
   * Messages returned to the caller
   */
  private final Messages messages;

  /**
   * @param builder This class follows the builder pattern.
   */
  private ExpenseReportResponseDto(final Builder builder) {
    this.messages = builder.messages;
    this.expenseReport = builder.expenseReport;
  }

  /**
   * @return ExpenseReport
   */
  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  /**
   * @return boolean True if we have any associated Messages
   */
  public boolean hasMessage() {
    return null != this.messages && this.messages.hasMessage();
  }

  /**
   * @return Messages
   */
  public Messages getMessages() {
    return this.messages;
  }

  /**
   * @return boolean True if we have any associated Messages that are of the type ERROR or WRAN.
   */
  public boolean hasWarningOrErrors(){
    return this.messages.hasWarningOrError();
  }

  /**
   * Simple builder pattern.
   */
  public static class Builder {

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

    public ExpenseReportResponseDto build() {
      this.messages = messagesBuilder.build();
      return new ExpenseReportResponseDto(this);
    }
  }
}
