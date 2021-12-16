package com.horecarobot.backend.Employee;

import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeUserDTO {
    private UUID id;
    private String username;
    private short pincode;
}
