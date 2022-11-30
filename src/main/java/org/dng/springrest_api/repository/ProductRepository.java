package org.dng.springrest_api.repository;

import org.dng.springrest_api.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
