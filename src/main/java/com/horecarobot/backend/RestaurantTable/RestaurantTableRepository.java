package com.horecarobot.backend.RestaurantTable;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, UUID> {
}
