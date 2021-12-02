package com.horecarobot.backend.Employee;

import com.horecarobot.backend.Exceptions.ValueNotUniqueException;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    EmployeeUserRepository employeeUserRepository;

    public EmployeeService(EmployeeUserRepository employeeUserRepository) {
        this.employeeUserRepository = employeeUserRepository;
    }

    public Page<EmployeeUser> getAll(int page, int size) {
        return this.employeeUserRepository.findAll(PageRequest.of(page, size));
    }

    public EmployeeUser getByID(UUID employeeUUID) throws NotFoundException {
        return this.employeeUserRepository.findById(employeeUUID).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public void add(EmployeeUser employee) throws ValueNotUniqueException {
        EmployeeUser existingEmployee = this.getEmployeeByPincode(employee.getPincode());

        if(existingEmployee != null) {
            throw new ValueNotUniqueException("Pin code already exists on a different user.");
        }

        this.employeeUserRepository.save(employee);
    }

    // TODO:: This is just to make it work. I'm waiting until I can get employees by their pin code directly from the repository.
    private EmployeeUser getEmployeeByPincode(short pincode) {
        List<EmployeeUser> employees = this.employeeUserRepository.findAll();

        for(EmployeeUser employee : employees) {
            if(employee.getPincode() == pincode) {
                return employee;
            }
        }

        return null;
    }

    // TODO:: This is just to make it work. I'm waiting until I can get employees by their username directly from the repository.
    private EmployeeUser getEmployeeByUsername(String username) {
        List<EmployeeUser> employees = this.employeeUserRepository.findAll();

        for(EmployeeUser employee : employees) {
            if(employee.getUsername() == username) {
                return employee;
            }
        }

        return null;
    }
}
