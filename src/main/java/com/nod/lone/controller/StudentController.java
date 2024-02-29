package com.nod.lone.controller;

import com.nod.lone.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.nod.lone.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public List<Student> findAllStudents() {

        //todo
        return service.findAllStudents();
    }

    @PostMapping("save_student")
    public String saveStudent(@RequestBody Student student) {
        service.saveStudent(student);
        return "Student successfully saved";
    }

    @GetMapping("/{email}")
    public Student findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }


    @PutMapping("update_student")
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }


    @DeleteMapping("delete_mapping/{email}")
    public void deleteStudent(@PathVariable String email) {
        service.deleteStudent(email);
    }

}
