package com.horecarobot.backend;

import com.horecarobot.backend.RestaurantTable.RestaurantTableService;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantTableRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    public void Should_Add_Restaurant_Table() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantTable restaurantTable2 = new RestaurantTable(null, 1, 100, 40);

        //Act
        restaurantTableService.addRestaurantTable(restaurantTable2);
        restaurantTable.setId(restaurantTable2.getId());

        assertThat(restaurantTableRepository.findAll().get(0)).usingRecursiveComparison().isEqualTo(restaurantTable);
    }

    @Test
    public void Should_Get_All_RestaurantTables() {
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

    @Test
    public void Should_Get_Chosen_RestaurantTable() throws NotFoundException {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantTable restaurantTable2 = new RestaurantTable(null, 2, 200, 80);

        restaurantTableRepository.save(restaurantTable);
        restaurantTableRepository.save(restaurantTable2);

        //Act
        RestaurantTable restaurantTableToCheck = restaurantTableService.getRestaurantTable(restaurantTable.getId());

        //Assert
        assertThat(restaurantTableToCheck).usingRecursiveComparison().isEqualTo(restaurantTable);
    }

    @Test()
    public void Should_Give_Not_Found_Exception_If_Restaurant_Table_Doesnt_Exist() {
        //Arrange
        UUID randomUUID = UUID.randomUUID();
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        restaurantTableRepository.save(restaurantTable);

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> restaurantTableService.getRestaurantTable(randomUUID));
        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }
}
