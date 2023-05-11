package com.hendra.beexcersice.Controllers;

import com.hendra.beexcersice.Entity.Department;
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
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(departments);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            department.setName(updatedDepartment.getName());
            Department savedDepartment = departmentRepository.save(department);
            return ResponseEntity.ok(savedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
