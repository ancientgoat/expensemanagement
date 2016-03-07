package com.apptricity.entity;

import com.apptricity.util.UpdateHelper;
import com.google.common.collect.Lists;
import org.springframework.data.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Entity representation of a Merchant associated with an ExpenseReport.
 */
@Entity
public class Merchant {

  @Id
  private String id;
  private String name;

  // I don't think I need this OneToMany when using the Mongo database.
  // But, this is just an example as a test anyway.
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

  /**
   * @param inName
   * @return boolean True if there was a change, false if no change to the value.
   */
  public boolean updateName(final String inName) {
    final UpdateHelper<String> updateHelper = UpdateHelper.newInstance(this.name, inName);
    this.name = updateHelper.getValue();
    return updateHelper.isChanged();
  }
}
