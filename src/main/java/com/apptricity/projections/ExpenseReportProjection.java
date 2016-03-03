package com.apptricity.projections;

import com.apptricity.entity.ExpenseReport;
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

  @Value("#{target.amount}")
  BigDecimal getAmount();

  @Value("#{target.createdDate}")
  Date getCreatedDate();

  @Value("#{target.comments}")
  List<String> getComments();
}
