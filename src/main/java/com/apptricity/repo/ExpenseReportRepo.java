package com.apptricity.repo;

import com.apptricity.entity.ExpenseReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 */
// Notice : exported = false
@RepositoryRestResource(collectionResourceRel = "expensereports", path = "expensereport", exported = false)
public interface ExpenseReportRepo extends MongoRepository<ExpenseReport, String> {
}
