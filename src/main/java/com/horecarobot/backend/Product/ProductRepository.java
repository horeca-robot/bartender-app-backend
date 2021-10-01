package com.horecarobot.backend.Product;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
