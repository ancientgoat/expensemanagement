package com.apptricity.entity;

import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Entity
public class Merchant {

  @Id
  private String id;
  private String name;

  @OneToMany(mappedBy = "merchant", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private List<ExpenseReport> expenseReportList = Lists.newArrayList();

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Merchant setName(final String inName) {
    this.name = inName;
    return this;
  }
}
