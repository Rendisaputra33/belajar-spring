package com.bhiteshop.bhiteshop.controller;

import com.bhiteshop.bhiteshop.domain.entity.Student;
import com.bhiteshop.bhiteshop.domain.request.CreateStudentRequest;
import com.bhiteshop.bhiteshop.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/siswa")
public class MainController {
    private final StudentRepository studentRepository;

    @Autowired
    public MainController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<Student> index() {
        return studentRepository.findAll();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Student create(@RequestBody CreateStudentRequest request) {
        var student = new Student();
        student.setNim(request.getNim());
        student.setName(request.getName());
        return studentRepository.save(student);
    }

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Student update(@RequestBody CreateStudentRequest request, @PathVariable Long id) {
        var find = studentRepository.findById(id);

        if (find.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var student = find.get();
        student.setName(request.getName());
        student.setNim(request.getNim());

        studentRepository.save(student);

        return student;
    }

    @DeleteMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    String delete(@PathVariable Long id) {
        var find = studentRepository.findById(id);

        if (find.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        studentRepository.delete(find.get());

        return "Sukses";
    }
}
