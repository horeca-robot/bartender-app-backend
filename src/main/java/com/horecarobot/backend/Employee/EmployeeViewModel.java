package com.horecarobot.backend.Employee;

import java.util.UUID;

import lombok.Data;

@Data
public class EmployeeViewModel {
    private UUID id;
    private String username;
}
