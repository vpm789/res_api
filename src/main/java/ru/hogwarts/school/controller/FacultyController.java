package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties(){
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
    @GetMapping("/get-by-name-color")
    public ResponseEntity getFacultyByNameOrColor(@RequestParam(required = false, name = "name") String name,
                                                         @RequestParam(required = false, name = "color") String color){
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByName(name));
        }
        if(color != null && !color.isEmpty() && !color.isBlank()){
            return ResponseEntity.ok(facultyService.getFacultyByColor(color));
        }
        return getAllFaculties();
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/find-faculty-by-student")
    public Faculty findFacultyByStudent (@RequestBody Student student){
        return facultyService.findFacultyByStudent(student);
    }

    @GetMapping("/max-faculty-name")
    public ResponseEntity<String> getFacultyMaxName(){
        return ResponseEntity.ok(facultyService.getFacultyMaxName());
    }

    @GetMapping("/stream-parallel/{limit}")
    public ResponseEntity<String> getInteger(@PathVariable int limit){
        return ResponseEntity.ok(facultyService.calculateWithStreamParallel(limit));
    }

}
