package com.horecarobot.backend.Product;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
}
