package com.horecarobot.backend;

import com.horecarobot.backend.RestaurantTable.RestaurantTableService;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantTableRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class RestaurantTableTests {
    @InjectMocks
    private RestaurantTableService restaurantTableService;

    @Mock
    private RestaurantTableRepository restaurantTableRepository;

    @BeforeEach
    public void setUp() {
        restaurantTableService = new RestaurantTableService(restaurantTableRepository);
        openMocks(restaurantTableService);
    }

    @Test
    public void Should_Add_Restaurant_Table() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantTable restaurantTable2 = new RestaurantTable(null, 1, 100, 40);

        //Act
        restaurantTableService.addRestaurantTable(restaurantTable2);

        //Arrange
        assertThat(restaurantTable2).usingRecursiveComparison().isEqualTo(restaurantTable);
        verify(restaurantTableRepository, times(1)).save(restaurantTable);
    }

    @Test
    public void Should_Get_All_RestaurantTables() {
        //Arrange
        List<RestaurantTable> restaurantTableList = new ArrayList<>();
        restaurantTableList.add(new RestaurantTable(UUID.randomUUID(), 1, 100, 40));
        restaurantTableList.add(new RestaurantTable(UUID.randomUUID(), 2, 200, 340));

        when(restaurantTableRepository.findAll()).thenReturn(restaurantTableList);

        //Act
        List<RestaurantTable> listWithRestaurantTables = restaurantTableService.getRestaurantTables();

        //Assert
        assertEquals(2, listWithRestaurantTables.size());
        verify(restaurantTableRepository, times(1)).findAll();
    }

    @Test
    public void Should_Get_Chosen_RestaurantTable() throws NotFoundException {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        when(restaurantTableRepository.findById(restaurantTable.getId())).thenReturn(Optional.of(restaurantTable));

        //Act
        RestaurantTable restaurantToCheck = restaurantTableService.getRestaurantTable(restaurantTable.getId());

        //Assert
        assertThat(restaurantToCheck).usingRecursiveComparison().isEqualTo(restaurantTable);
        verify(restaurantTableRepository, times(1)).findById(restaurantTable.getId());
    }

    @Test()
    public void Should_Give_Not_Found_Exception_If_Restaurant_Table_Doesnt_Exist() {
        //Arrange
        UUID randomUUID = UUID.randomUUID();
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        when(restaurantTableRepository.findById(restaurantTable.getId())).thenReturn(Optional.of(restaurantTable));

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> restaurantTableService.getRestaurantTable(randomUUID));
        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }
}
