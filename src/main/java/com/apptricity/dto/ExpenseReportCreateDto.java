package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.util.ErrorMessage;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class ExpenseReportCreateDto {

//  private String merchantName;
//  private BigDecimal amount;
//  private Date createdDate;
//  private String comment;
//  private List<String> comments = new ArrayList<String>();

  private final Merchant merchant;
  private final ExpenseReport expenseReport;
  private final ErrorMessage errorMessage;

  public ExpenseReportCreateDto(final Builder builder) {
    this.errorMessage = builder.errorMessage;
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

  public boolean hasErrors() {
    return this.errorMessage.haveErrors();
  }

  public ErrorMessage getErrorMessage(){
    return this.errorMessage;
  }

  /**
   *
   */
  public static class Builder {
    private String merchantName;
    private BigDecimal amount;
    private Date expenseDateTime;
    private String comment;
    private ErrorMessage errorMessage;

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

    private void validateInput() {

      final ErrorMessage.Builder builder = new ErrorMessage.Builder();

      if (null == this.amount) {
        builder.addError("'amount' is required, please add and try again.");
      }

      if (null == this.merchantName) {
        builder.addError("'merchantName' is required, please add and try again.");
      }

      if (null == this.expenseDateTime) {
        builder.addError("'expenseDateTime' is required, please add and try again.");
      }

      this.errorMessage = builder.build();
    }

    public ExpenseReportCreateDto build() {
      validateInput();
      return new ExpenseReportCreateDto(this);
    }
  }
}
