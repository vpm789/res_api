package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testCRUDFaculty() throws Exception {
        var faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("green");
        var savedFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Assertions
                .assertThat(savedFaculty).isNotNull();

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty?id=" + savedFaculty.getId(), String.class))
                .isNotEmpty();

        HttpEntity<Faculty> httpFaculty = new HttpEntity<>(savedFaculty);
        savedFaculty.setColor("blue");
        ResponseEntity<Faculty> facultyEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                httpFaculty,
                Faculty.class);

        Assertions
                .assertThat(facultyEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Faculty> facultyEntityDelete = restTemplate.exchange(
                "http://localhost:" + port + "/faculty?id=" + savedFaculty.getId(),
                HttpMethod.DELETE,
                httpFaculty,
                Faculty.class);

        Assertions
                .assertThat(facultyEntityDelete.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
