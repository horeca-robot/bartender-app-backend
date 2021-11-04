package com.horecarobot.backend.Product;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/product")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(path ="/{id}")
    public ProductDTO getProduct(@PathVariable("id") UUID productID) throws NotFoundException {
        Product product = this.productService.getProduct(productID);
        return convertToDTO(product);

    }

    @PostMapping
    public void createProduct(@RequestBody ProductDTO productDto){
        Product product = convertToEntity(productDto);
        productService.saveProduct(product);
    }

    // Mappers
    private Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
