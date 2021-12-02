package com.horecarobot.backend.Employee;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmployeeViewModel {
    private UUID id;
    private String username;
}
