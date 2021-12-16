package com.horecarobot.backend.RestaurantTable;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/restaurantTable")
@CrossOrigin(origins = {"http://localhost:8081", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444"})
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
    public RestaurantTableDTO getRestaurantTable(@PathVariable("restaurantTableID") UUID id) throws NotFoundException {
        return convertToDTO(this.restaurantTableService.getRestaurantTable(id));
    }

    @PostMapping
    public void createRestaurantTable(@RequestBody RestaurantTableDTO restaurantTableDTO) {
        restaurantTableService.addRestaurantTable(convertToEntity(restaurantTableDTO));
    }

    // Mappers
    private RestaurantTable convertToEntity(RestaurantTableDTO restaurantTableDTO) {
        return modelMapper.map(restaurantTableDTO, RestaurantTable.class);
    }

    private RestaurantTableDTO convertToDTO(RestaurantTable restaurantTable) {
        return modelMapper.map(restaurantTable, RestaurantTableDTO.class);
    }
}
