package com.apptricity.entity;

import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.CustomDateSerializer;
import com.apptricity.util.UpdateHelper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Entity
public class ExpenseReport {

  @Id
  private String id;
  private BigDecimal amount;
  private Date expenseDateTime;
  private List<String> comments = Lists.newArrayList();
  private ExpenseReportStatus status = ExpenseReportStatus.NEW;
  private Merchant merchant;
  private Date createdDate = new Date();


  public String getId() {
    return id;
  }

  @JsonSerialize(using = CustomDateSerializer.class)
  public Date getExpenseDateTime() {
    return expenseDateTime;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public List<String> getComments() {
    return Lists.newArrayList(comments);
  }

  public ExpenseReportStatus getStatus() {
    return status;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  // --------------------------------------
  public ExpenseReport setExpenseDateTime(Date expenseDateTime) {
    this.expenseDateTime = expenseDateTime;
    return this;
  }

  public ExpenseReport setAmount(final BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public ExpenseReport setComments(List<String> comments) {
    this.comments = comments;
    return this;
  }

  public ExpenseReport addComment(final String inComment) {
    this.comments.add(inComment);
    return this;
  }

  public ExpenseReport setStatus(final ExpenseReportStatus status) {
    this.status = status;
    return this;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public boolean updateExpenseDateTime(final Date inExpenseDateTime) {
    final UpdateHelper<Date> updateHelper = UpdateHelper.newInstance(this.expenseDateTime, inExpenseDateTime);
    this.expenseDateTime = updateHelper.getValue();
    return updateHelper.isChanged();
  }

  public boolean updateAmount(final BigDecimal inAmount) {
    final UpdateHelper<BigDecimal> updateHelper = UpdateHelper.newInstance(this.amount, inAmount);
    this.amount = updateHelper.getValue();
    return updateHelper.isChanged();
  }

  public boolean updateStatus(final ExpenseReportStatus inStatus) {
    final UpdateHelper<ExpenseReportStatus> updateHelper = UpdateHelper.newInstance(this.status, inStatus);
    this.status = updateHelper.getValue();
    return updateHelper.isChanged();
  }
}
