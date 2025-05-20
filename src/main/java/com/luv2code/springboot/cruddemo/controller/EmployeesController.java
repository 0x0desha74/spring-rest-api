package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeesController {

    private final EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees(){
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }


    @PostMapping("employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee createdEmployee = employeeService.save(employee);
        return new ResponseEntity<>(employee,HttpStatus.CREATED);
    }

    @PutMapping("employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee createdEmployee = employeeService.save(employee);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }


    @DeleteMapping("employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable int id){
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }









}
