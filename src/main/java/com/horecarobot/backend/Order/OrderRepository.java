package com.horecarobot.backend.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.fontys.horecarobot.databaselibrary.models.Order;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
