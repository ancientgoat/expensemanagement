package com.apptricity.entity;

import com.apptricity.enums.ExpenseReportStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Projection(name = "expenseReportProj", types = {ExpenseReport.class})
public interface ExpenseReportProjection {

  @Value("#{target.merchant.name}")
  String getMerchantName();

  @Value("#{target.merchant.id}")
  String getMerchantId();

  @Value("#{target.amount}")
  BigDecimal getAmount();

  @Value("#{target.expenseDateTime}")
  Date getExpenseDateTime();

  @Value("#{target.status}")
  ExpenseReportStatus getSTatus();

  @Value("#{target.comments}")
  List<String> getComments();

  @Value("#{target.createdDate}")
  Date getCreatedDate();

}
