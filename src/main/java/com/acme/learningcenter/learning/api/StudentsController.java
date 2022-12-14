package com.acme.learningcenter.learning.api;

import com.acme.learningcenter.learning.domain.model.entity.Student;
import com.acme.learningcenter.learning.domain.service.StudentService;
import com.acme.learningcenter.learning.mapping.StudentMapper;
import com.acme.learningcenter.learning.resource.CreateStudentResource;
import com.acme.learningcenter.learning.resource.StudentResource;
import com.acme.learningcenter.learning.resource.UpdateStudentResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/students", produces = "application/json")
public class StudentsController {
    private final StudentService studentService;

    private final StudentMapper mapper;

    public StudentsController(StudentService studentService, StudentMapper mapper) {
        this.studentService = studentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<StudentResource> getAllStudents(Pageable pageable) {
        return mapper.modelListPage(studentService.getAll(), pageable);
    }

    @GetMapping("{studentId}")
    public StudentResource getStudentById(@PathVariable Long studentId) {
        return mapper.toResource(studentService.getById(studentId));
    }

    @PostMapping
    public ResponseEntity<StudentResource> createStudent(@RequestBody CreateStudentResource resource){
        return new ResponseEntity<>(
                mapper.toResource(
                        studentService.create(
                                mapper.toModel(resource))), HttpStatus.CREATED);
    }

    @PutMapping("{studentId}")
    public StudentResource updateStudent(@PathVariable Long studentId,
                                         @RequestBody UpdateStudentResource resource){
        return mapper.toResource(studentService.update(studentId, mapper.toModel(resource)));
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
        return studentService.delete(studentId);
    }
}