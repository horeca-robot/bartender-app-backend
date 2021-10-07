package com.horecarobot.backend.Category;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.fontys.horecarobot.databaselibrary.models.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}
