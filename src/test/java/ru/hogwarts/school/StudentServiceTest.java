package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private final StudentService out = new StudentService();
    @Test
    public void studentServiceTest() {
        Student studentPetya = out.createStudent(new Student(0L, "Petya", 19));
        assertEquals(studentPetya, out.findStudent(1L));
        Student studentVasya = new Student(1L, "Vasya", 20);
        assertEquals("Vasya", out.editStudent(studentVasya).getName());
        out.deleteStudent(1L);
        assertNull(out.findStudent(1L));
    }
}
