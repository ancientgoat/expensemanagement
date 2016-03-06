package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.Messages;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class ExpenseReportCreateDto {

  private final Merchant merchant;
  private final ExpenseReport expenseReport;
  private final Messages messages;

  public ExpenseReportCreateDto(final Builder builder) {
    this.messages = builder.messages;
    this.merchant = new Merchant().setName(builder.merchantName);
    this.expenseReport =
        new ExpenseReport()
            .setAmount(builder.amount)
            .addComment(builder.comment)
            .setExpenseDateTime(builder.expenseDateTime)
    ;
  }

  public Merchant getMerchant() {
    return this.merchant;
  }

  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  public boolean hasMessage() {
    return this.messages.hasMessage();
  }

  public Messages getMessages(){
    return this.messages;
  }

  /**
   *
   */
  public static class Builder {
    private String merchantName;
    private BigDecimal amount;
    private Date expenseDateTime;
    private String comment;
    private ExpenseReportStatus status = ExpenseReportStatus.NEW;
    private Messages messages;
    private Messages.Builder messagesBuilder = new Messages.Builder();

    public void setStatus(ExpenseReportStatus status) {

      this.status = status;
    }

    public Builder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder setExpenseDateTime(Date expenseDateTime) {
      this.expenseDateTime = expenseDateTime;
      return this;
    }

    public Builder setMerchantName(String merchantName) {
      this.merchantName = merchantName;
      return this;
    }

    private void validateForInsert() {
      if (null == this.amount) {
        this.messagesBuilder.addError("'amount' is required, please add and try again.");
      }
      if (null == this.merchantName) {
        this.messagesBuilder.addError("'merchantName' is required, please add and try again.");
      }
      if (null == this.expenseDateTime) {
        this.messagesBuilder.addError("'expenseDateTime' is required, please add and try again.");
      }
    }

    private void validateForUpdate() {
    }

    public ExpenseReportCreateDto buildForUpdate() {
      validateForUpdate();
      return _build();
    }

    public ExpenseReportCreateDto buildForInsert() {
      validateForInsert();
      return _build();
    }

    private ExpenseReportCreateDto _build() {
      this.messages = messagesBuilder.build();
      return new ExpenseReportCreateDto(this);
    }
  }
}
