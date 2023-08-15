package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findStudentByAgeBetween(int minAge, int maxAge){
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> findStudentByFaculty(Faculty faculty){
        return studentRepository.findStudentByFaculty(faculty);
    }

    public Integer getCountStudents() {
        return studentRepository.getCountStudents();
    }

    public Integer getAvgAge() {
        return studentRepository.getAvgAge();
    }

    public List<Student> getLastStudents() {
        return studentRepository.getLastStudents();
    }

    public Collection<Student> getStudentsByName(String name) {
        return studentRepository.getStudentsByName(name);
    }
}
