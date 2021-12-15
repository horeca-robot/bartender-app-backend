package com.horecarobot.backend.Employee;

import com.horecarobot.backend.Exceptions.ValueNotUniqueException;
import com.horecarobot.backend.Exceptions.ValuesDontMatchException;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeUserRepository employeeUserRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeUserRepository employeeUserRepository, PasswordEncoder passwordEncoder) {
        this.employeeUserRepository = employeeUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<EmployeeUser> getAll(int page, int size) {
        return this.employeeUserRepository.findAll(PageRequest.of(page, size));
    }

    public EmployeeUser getByID(UUID employeeUUID) throws NotFoundException {
        return this.employeeUserRepository.findById(employeeUUID).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public void add(EmployeeUser employee) throws ValueNotUniqueException {
        if(this.employeeWithUsernameAlreadyExists(employee.getUsername())) {
            throw new ValueNotUniqueException("Username is already in use by a different user.");
        }

        // Todo:: Implement pincode hashing
//        String hashedPincode = this.passwordEncoder.encode(String.valueOf(employee.getPincode()));
//
//        System.out.println("----");
//        System.out.println(hashedPincode);
//        System.out.println("----");
//
//        System.out.println(this.passwordEncoder.matches(String.valueOf(1111), hashedPincode));

        if(this.employeeWithPinCodeAlreadyExists(employee.getPincode())) {
            throw new ValueNotUniqueException("Pin code is already in use by a different user.");
        }

        this.employeeUserRepository.save(employee);
    }

    public boolean login(EmployeeUser requestedEmployee) throws NotFoundException, ValuesDontMatchException {
        EmployeeUser employee = this.getByID(requestedEmployee.getId());

        if(!(employee.getUsername().equals(requestedEmployee.getUsername()))) {
            throw new ValuesDontMatchException("Username and pincode combination is not correct.");
        }

        if(employee.getPincode() != requestedEmployee.getPincode()) {
            throw new ValuesDontMatchException("Username and pincode combination is not correct.");
        }

        return true;
    }

    private boolean employeeWithUsernameAlreadyExists(String username) {
        EmployeeUser employee = this.getEmployeeByUsername(username);
        return employee != null;
    }

    private boolean employeeWithPinCodeAlreadyExists(short pincode) {
        EmployeeUser employee = this.getEmployeeByPincode(pincode);
        return employee != null;
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
            if(employee.getUsername().equals(username)) {
                return employee;
            }
        }

        return null;
    }
}
