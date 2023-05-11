package com.hendra.beexcersice.Controllers;

import com.hendra.beexcersice.Entity.CustomErrorResponse;
import com.hendra.beexcersice.Entity.Employee;
import com.hendra.beexcersice.Repository.DepartmentRepository;
import com.hendra.beexcersice.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            String errorMessage = "Employee with ID " + id + " not found.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setDepartment(updatedEmployee.getDepartment());
            Employee savedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(savedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

