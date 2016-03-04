package com.apptricity.dto;

import com.apptricity.entity.ExpenseReport;
import com.apptricity.entity.Merchant;
import com.apptricity.util.ErrorMessage;

/**
 *
 */
public class ExpenseReportResponseDto {

//  private String merchantName;
//  private BigDecimal amount;
//  private Date createdDate;
//  private String comment;
//  private List<String> comments = new ArrayList<String>();

  private final Merchant merchant;
  private final ExpenseReport expenseReport;
  private final ErrorMessage errorMessage;

  public ExpenseReportResponseDto(final Builder builder) {
    this.errorMessage = builder.errorMessage;
    this.merchant = builder.merchant;
    this.expenseReport = builder.expenseReport;
  }

  public Merchant getMerchant() {
    return this.merchant;
  }

  public ExpenseReport getExpenseReport() {
    return this.expenseReport;
  }

  public boolean hasErrors() {
    return null != this.errorMessage && this.errorMessage.haveErrors();
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  /**
   *
   */
  public static class Builder {

    private Merchant merchant;
    private ExpenseReport expenseReport;
    private ErrorMessage.Builder errorMessageBuilder = new ErrorMessage.Builder();
    private ErrorMessage errorMessage;

    public Builder addErrorMessage(final String inErrorMessage) {
      this.errorMessageBuilder.addError(inErrorMessage);
      return this;
    }

    public Builder setErrorMessage(final ErrorMessage inErrorMessage) {
      this.errorMessage = inErrorMessage;
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
      this.errorMessage = errorMessageBuilder.build();
      return new ExpenseReportResponseDto(this);
    }
  }
}
