package com.apptricity.entity;

import com.apptricity.enums.ExpenseReportStatus;
import com.apptricity.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Entity
public class ExpenseReport {

  @Id
  String Id;

  private BigDecimal amount;
  private Date expenseDateTime;
  private List<String> comments = Lists.newArrayList();
  private ExpenseReportStatus status = ExpenseReportStatus.NEW;

  private Date createdDate = new Date();

  @ManyToOne(cascade = CascadeType.MERGE)
  private Merchant merchant;

  public String getId() {
    return Id;
  }

  @JsonSerialize(using = CustomDateSerializer.class)
  public Date getExpenseDateTime() {
    return expenseDateTime;
  }

  public ExpenseReport setExpenseDateTime(Date expenseDateTime) {
    this.expenseDateTime = expenseDateTime;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public ExpenseReport setAmount(final BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public ExpenseReport setCreatedDate(final Date createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public List<String> getComments() {
    return Lists.newArrayList(comments);
  }

  public ExpenseReport setComments(List<String> comments) {
    this.comments = comments;
    return this;
  }

  public ExpenseReport addComment(final String inComment) {
    this.comments.add(inComment);
    return this;
  }

  public ExpenseReportStatus getStatus() {
    return status;
  }

  public ExpenseReport setStatus(final ExpenseReportStatus status) {
    this.status = status;
    return this;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }
}
