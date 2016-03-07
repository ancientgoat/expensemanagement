package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.Messages;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A Data Transfer Object that is created by input JSON during an HTTP call.
 * Used in the Controllers.
 */
public class ExpenseReportCreateDto {

  private final ExpenseReport expenseReport;
  private final Messages messages;

  public ExpenseReportCreateDto(final Builder builder) {
    this.messages = builder.messages;
    this.expenseReport =
        new ExpenseReport()
            .setAmount(builder.amount)
            .addComment(builder.comment)
            .setExpenseDateTime(builder.expenseDateTime)
            .setStatus(builder.status)
            .setMerchant(builder.merchant)
    ;
  }

  public boolean haveMerchantName() {
    boolean haveName = false;
    if (null != this.expenseReport) {
      Merchant merchant = this.expenseReport.getMerchant();
      if (null != merchant) {
        if (null != merchant.getName()) {
          haveName = true;
        }
      }
    }
    return haveName;
  }

  public String getMerchantName() {
    if (haveMerchantName()) {
      return this.expenseReport.getMerchant().getName();
    }
    return null;
  }

  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  public boolean hasMessage() {
    return this.messages.hasMessage();
  }

  public Messages getMessages() {
    return this.messages;
  }

  public String getFirstComment() {
    return this.expenseReport.getFirstComment();
  }

  public Merchant getMerchant() {
    if (null != this.expenseReport) {
      return this.expenseReport.getMerchant();
    }
    return null;
  }

  /**
   *
   */
  public static class Builder {
    private BigDecimal amount;
    private Date expenseDateTime;
    private String comment;
    private ExpenseReportStatus status = ExpenseReportStatus.NEW;
    private Messages messages;
    private Messages.Builder messagesBuilder = new Messages.Builder();
    private Merchant merchant = new Merchant();

    public void setStatus(ExpenseReportStatus status) {
      this.status = status;
    }

    public Builder setComment(final String inComment) {
      this.comment = inComment;
      return this;
    }

    public Builder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder setExpenseDateTime(final Date expenseDateTime) {
      this.expenseDateTime = expenseDateTime;
      return this;
    }

    public Builder setMerchant(final Merchant inMerchant) {
      this.merchant = inMerchant;
      return this;
    }

    private void validateForInsert() {
      if (null == this.amount) {
        this.messagesBuilder.addError("'amount' is required, please add and try again.");
      }
      if (null == this.merchant || null == this.merchant.getName() || 0 == this.merchant.getName().length()) {
        this.messagesBuilder.addError("'merchant.name' must have a size, I know nothing, I see nothing, I KNOW NOTHING.");
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
