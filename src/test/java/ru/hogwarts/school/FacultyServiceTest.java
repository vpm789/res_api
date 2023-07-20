package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FacultyServiceTest {
    private final FacultyService out = new FacultyService();
    @Test
    public void facultyServiceTest() {
        Faculty facultyJava = out.createFaculty(new Faculty(0L, "Java", "green"));
        assertEquals(facultyJava, out.findFaculty(1L));
        Faculty facultyMath = new Faculty(1L, "Math", "brown");
        assertEquals("Math", out.editFaculty(facultyMath).getName());
        out.deleteFaculty(1L);
        assertNull(out.findFaculty(1L));
    }

}
