package com.apptricity.repo;

import com.apptricity.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 *
 */
@RepositoryRestResource(collectionResourceRel = "merchants", path = "merchant", exported = false)
public interface MerchantRepo extends MongoRepository<Merchant, String> {

  List<Merchant> findByName(@Param("name") String name);

}
