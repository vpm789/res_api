package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByName(String name) {
        return facultyRepository.findFacultyByNameContainingIgnoreCase(name);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findFacultyByColorContainsIgnoreCase(color);
    }

    public Faculty findFacultyByStudent(Student student) {
        return facultyRepository.findFacultyByStudent(student);
    }

    public String getFacultyMaxName() {
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length)).toString();
    }


    public String calculateWithStreamParallel(int limit) {
        long start = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a + 1)
                .parallel()
                .limit(limit)
                .reduce(0, Integer::sum);
        long end = System.currentTimeMillis();
        return "Duration of calculation sum: " + sum + " is " + (end - start);
    }
}
