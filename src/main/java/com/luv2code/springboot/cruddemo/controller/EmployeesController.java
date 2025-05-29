package com.luv2code.springboot.cruddemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeesController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    public EmployeesController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
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
        employee.setId(null);
        Employee createdEmployee = employeeService.save(employee);
        return new ResponseEntity<>(createdEmployee,HttpStatus.CREATED);
    }

    @PutMapping("employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.save(employee);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }


    @DeleteMapping("employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable int id){
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("employees/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable int id , @RequestBody Map<String,Object> patchPayload ){

        Employee employee = employeeService.findById(id);

        if(employee == null)
            throw new RuntimeException("employee not found.");

        if (patchPayload.containsKey("id"))
            throw new RuntimeException("Employee id not allowed in the request body");

        Employee patchedEmployee = apply(patchPayload,employee);
        Employee updatedEmployee = employeeService.save(patchedEmployee);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }


    private Employee apply(Map<String,Object> patchedPayload,Employee tempEmployee){
        ObjectNode employeeNode = objectMapper.convertValue(tempEmployee,ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(patchedPayload,ObjectNode.class);

        employeeNode.setAll(patchNode);
        return objectMapper.convertValue(employeeNode,Employee.class);
    }






}
