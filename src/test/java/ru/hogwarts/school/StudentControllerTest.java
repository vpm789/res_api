package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testCRUDStudent() throws Exception {
        var student = new Student();
        student.setName("Bob");
        student.setAge(23);
        var savedStudent = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(savedStudent).isNotNull();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?id=" + savedStudent.getId(), String.class))
                .isNotEmpty();

        HttpEntity<Student> httpStudent = new HttpEntity<>(savedStudent);
        savedStudent.setAge(21);
        ResponseEntity<Student> studentEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                httpStudent,
                Student.class);

        Assertions
                .assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Student> studentEntityDelete = restTemplate.exchange(
                "http://localhost:" + port + "/student?id=" + savedStudent.getId(),
                HttpMethod.DELETE,
                httpStudent,
                Student.class);

        Assertions
                .assertThat(studentEntityDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
