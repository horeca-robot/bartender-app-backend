package com.horecarobot.backend;

import com.horecarobot.backend.RestaurantTable.RestaurantTableService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantTableRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RestaurantTableTests {
    private final RestaurantTableService restaurantTableService;
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableTests(RestaurantTableService restaurantTableService, RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableService = restaurantTableService;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    @Test
    public void Should_Add_Restaurant_Table() throws Exception {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantTable restaurantTable2 = new RestaurantTable(null, 2, 200, 80);

        //Act
        restaurantTableService.addRestTable(restaurantTable2);
        restaurantTable.setId(restaurantTable2.getId());

        assertThat(restaurantTableRepository.findAll().get(0)).usingRecursiveComparison().isEqualTo(restaurantTable);
    }

    @Test
    public void Should_Get_All_RestaurantTables() throws Exception{
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(UUID.randomUUID(), 1, 100, 40);
        RestaurantTable restaurantTable2 = new RestaurantTable(UUID.randomUUID(), 2, 200, 80);

        restaurantTableRepository.save(restaurantTable);
        restaurantTableRepository.save(restaurantTable2);

        //Act
        List<RestaurantTable> restaurantTables = restaurantTableService.getRestaurantTables();

        //Assert
        assertThat(restaurantTables).hasSize(2);
    }
}
