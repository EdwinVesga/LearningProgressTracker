package repository;

import dto.StudentDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository {

    private Map<Long, StudentDTO> studentStorage;

    public StudentRepository() {
        this.studentStorage = new HashMap<>();
    }

    public boolean addStudent(StudentDTO studentDTO) {
        boolean success = false;

        if (!studentStorage.values().stream().anyMatch(s -> s.getEmail().equals(studentDTO.getEmail())
                && !s.getId().equals(studentDTO.getId()))) {
            studentStorage.put(studentDTO.getId(), studentDTO);
            success = true;
        }

        return success;
    }

    public StudentDTO findById(Long id) {
        return this.studentStorage.get(id);
    }

    public List<StudentDTO> findAll() {
        return new ArrayList<>(this.studentStorage.values());
    }

}

