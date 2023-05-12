package com.hendra.beexcersice.Controllers;

import com.hendra.beexcersice.Entity.CustomErrorResponse;
import com.hendra.beexcersice.Entity.Department;
import com.hendra.beexcersice.Entity.Employee;
import com.hendra.beexcersice.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        if(departments.size() != 0){
            return ResponseEntity.ok(departments);
        }else {
            String errorMessage = "No Data Found";
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            String errorMessage = "Department with ID " + id + " not found.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            department.setName(updatedDepartment.getName());
            Department savedDepartment = departmentRepository.save(department);
            return ResponseEntity.ok(savedDepartment);
        } else {
            String errorMessage = "Department with ID " + id + " not found.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            departmentRepository.deleteById(id);
            String errorMessage = "Department with ID " + id + " deleted successfully.";
            CustomErrorResponse res = new CustomErrorResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            String errorMessage = "Department with ID " + id + " not found.";
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
