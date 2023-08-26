package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Creating student: {}", student);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            var findedStudent = studentRepository.findById(id).get();
            logger.info("Student: {} found", findedStudent);
            return findedStudent;
        } else {
            throw new RuntimeException();
        }
    }

    public Student editStudent(Student student) {
        logger.info("The method editStudent has invoked");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("The method deleteStudent has invoked");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findStudentByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> findStudentByFaculty(Faculty faculty) {
        return studentRepository.findStudentByFaculty(faculty);
    }

    public Integer getCountStudents() {
        return studentRepository.getCountStudents();
    }

    public Integer getAvgAge() {
        var avgAge = studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average();
        if (avgAge.isPresent()) {
            return ((int) avgAge.getAsDouble());
        } else {
            return null;
        }
    }

    public List<Student> getLastStudents() {
        return studentRepository.getLastStudents();
    }

    public Collection<Student> getStudentsByName(String name) {
        return studentRepository.getStudentsByName(name);
    }

    public Collection<Student> getStudentsFirstLetter(String firstLetter) {
        return studentRepository.findAll()
                .stream()
                .filter(p -> p.getName().startsWith(firstLetter))
                .collect(Collectors.toList());
    }

    public void getStudentsUnsync() {
        List<String> studentNames = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();

        System.out.print(studentNames);
        System.out.println();
        System.out.println(studentNames.get(0));
        System.out.println(studentNames.get(1));

        new Thread(() ->{
            System.out.println(studentNames.get(2));
            System.out.println(studentNames.get(3));
        }).start();

        new Thread(() ->{
            System.out.println(studentNames.get(4));
            System.out.println(studentNames.get(5));
        }).start();
    }

    public void getStudentsSync() {
        var students = studentRepository.findAll();

        System.out.println(students.get(0));
        System.out.println(students.get(1));

        new Thread(() ->{
            print(students.get(2));
            print(students.get(3));
        }).start();

        new Thread(() ->{
            print(students.get(4));
            print(students.get(5));
        }).start();
    }

    public synchronized void print(Object o) {
        System.out.println(o.toString());
    }
}
