package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/filter_by_age/{age}")
    public Collection<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/find_age_between")
    public Collection<Student> findStudentByAgeBetween(@RequestParam int minAge,
                                                       @RequestParam int maxAge) {
        return studentService.findStudentByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/find_student_by_faculty")
    public Collection<Student> findStudentByFaculty(@RequestBody Faculty faculty) {
        return studentService.findStudentByFaculty(faculty);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public Integer getCountStudents() {
        return studentService.getCountStudents();
    }

    @GetMapping("/avg_age")
    public Integer getAvgAge() {
        return studentService.getAvgAge();
    }

    @GetMapping("/last_students")
    public List<Student> getLastStudents() {
        return studentService.getLastStudents();
    }

    @GetMapping("/name/{name}")
    public Collection<Student> getStudentsByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

}
