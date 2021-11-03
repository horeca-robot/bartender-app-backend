package com.horecarobot.backend.RestaurantTable;

import com.horecarobot.backend.Order.RestaurantOrderDTO;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/restaurantTable")
@CrossOrigin(origins = "*")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantTableController(RestaurantTableService restaurantTableService, ModelMapper modelMapper) {
        this.restaurantTableService = restaurantTableService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<RestaurantTableDTO> getRestaurantTables() {
        List<RestaurantTable> restaurantTables = restaurantTableService.getRestaurantTables();
        return restaurantTables.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "/{restaurantTableID}")
    public RestaurantTableDTO getRestaurantTable(@PathVariable("restaurantTableID") UUID id){
        return convertToDTO(this.restaurantTableService.getTable(id));
    }

    @PostMapping
    public void createRestaurantTable(@RequestBody RestaurantTableDTO restaurantTableDTO) {
        restaurantTableService.addRestTable(convertToEntity(restaurantTableDTO));
    }

    // Mappers
    private RestaurantTable convertToEntity(RestaurantTableDTO restaurantTableDTO) {
        return modelMapper.map(restaurantTableDTO, RestaurantTable.class);
    }

    private RestaurantTableDTO convertToDTO(RestaurantTable restaurantTable) {
        return modelMapper.map(restaurantTable, RestaurantTableDTO.class);
    }
}
