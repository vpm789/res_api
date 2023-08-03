package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarService {
    private final String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final boolean saveOnDisk;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         @Value("${path.to.avatars.folder}") String avatarsDir,
                         @Value("${save-avatars-on-disk}") boolean saveOnDisk) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
        this.saveOnDisk = saveOnDisk;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        var student = studentRepository.findById(studentId).orElseThrow();
        var filePath = saveOnDisk ? saveOnDisk(avatarFile, student) : null;

        var avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setFilePath(filePath);
        avatar.setData(avatarFile.getBytes());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setStudent(student);
        avatarRepository.save(avatar);
    }

    private String saveOnDisk(MultipartFile avatarFile, Student student) throws IOException {
        var path = Path.of(avatarsDir);
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
        }

        var dotIndex = avatarFile.getOriginalFilename().lastIndexOf('.');
        var ext = avatarFile.getOriginalFilename().substring(dotIndex + 1);
        var filePath = avatarsDir + "/" + student.getId() + "_" + student.getName() + "." + ext;

        try (var in = avatarFile.getInputStream();
             var out = new FileOutputStream(filePath)) {
            in.transferTo(out);
        }
        return filePath;
    }

    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
}
