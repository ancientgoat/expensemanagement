package com.apptricity.repo;

import com.apptricity.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Simple Spring Repository for the Merchant associated with an ExpenseReport.
 *  'exported = false' is used to NOT export the default REST calls to the outside world.
 *  However, they can still be used internally by other classes, like a Service.
 */
@RepositoryRestResource(collectionResourceRel = "merchants", path = "merchant", exported = false)
public interface MerchantRepo extends MongoRepository<Merchant, String> {
}
