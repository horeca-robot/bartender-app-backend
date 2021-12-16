package com.horecarobot.backend.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.horecarobot.backend.Auth.AuthService;
import com.horecarobot.backend.Exceptions.FieldNotFoundException;
import com.horecarobot.backend.Exceptions.InvalidTokenException;
import com.horecarobot.backend.Exceptions.ValueNotUniqueException;
import com.horecarobot.backend.Exceptions.ValuesDontMatchException;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/employee")
@CrossOrigin(origins = {"http://localhost:8080", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444"})
public class EmployeeController {
    private final EmployeeService employeeService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AuthService authService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "2") int size
    ) {
        Page<EmployeeUser> employees = this.employeeService.getAll(page, size);
        List<EmployeeViewModel> employeeViewModels = employees.stream().map(this::convertToViewModel).collect(Collectors.toList());

        if (employeeViewModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();

        response.put("current", employees.getNumber());
        response.put("total", employees.getTotalPages());
        response.put("totalItems", employees.getTotalElements());
        response.put("employees", employeeViewModels);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{employeeUUID}")
    public EmployeeViewModel getByID(@PathVariable("employeeUUID") UUID employeeUUID) throws NotFoundException {
        return this.convertToViewModel(this.employeeService.getByID(employeeUUID));
    }

    @PostMapping
    public void create(@RequestBody EmployeeUserDTO employeeDTO) throws ValueNotUniqueException {
        this.employeeService.add(this.convertToEntity(employeeDTO));
    }

    @PostMapping(path = "/{employeeUUID}/login")
    public String login(
            @RequestBody EmployeeUserDTO employeeUserDTO,
            @PathVariable("employeeUUID") UUID employeeUUID
    ) throws NotFoundException, ValuesDontMatchException {
        employeeUserDTO.setId(employeeUUID);

        if(this.employeeService.login(this.convertToEntity(employeeUserDTO))) {
            Map<String, String> tokenClaims = new HashMap<String, String>();
            tokenClaims.put("uid", employeeUserDTO.getId().toString());

            return this.authService.createJWT(tokenClaims);
        }

        return null;
    }

    @PostMapping(path = "/{employeeUUID}/validate")
    public String validate(
            @RequestBody Map<String, String> requestBody,
            @PathVariable("employeeUUID") UUID employeeUUID
    ) throws JWTVerificationException, FieldNotFoundException, InvalidTokenException {
        if(!requestBody.containsKey("token")) {
            throw new FieldNotFoundException("Missing field: 'token'");
        }

        String jwt = requestBody.get("token");

        if(!this.authService.tokenIsValid(jwt, employeeUUID)) {
            throw new InvalidTokenException("Token is invalid");
        }

        return "Token is valid";
    }

    @PostMapping(path = "/{employeeUUID}/refresh")
    public String refresh(
            @RequestBody Map<String, String> requestBody,
            @PathVariable("employeeUUID") UUID employeeUUID
    ) throws JWTVerificationException, FieldNotFoundException, InvalidTokenException {
        if(!requestBody.containsKey("token")) {
            throw new FieldNotFoundException("Missing field: token");
        }

        String jwt = requestBody.get("token");

        return this.authService.refreshToken(jwt, employeeUUID);
    }

    // Mappers
    private EmployeeUser convertToEntity(EmployeeUserDTO employeeUserDTO) {
        return modelMapper.map(employeeUserDTO, EmployeeUser.class);
    }

    private EmployeeViewModel convertToViewModel(EmployeeUser employeeUser) {
        return modelMapper.map(employeeUser, EmployeeViewModel.class);
    }
}
