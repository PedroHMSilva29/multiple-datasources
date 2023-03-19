package youtube.java.puzzle.model;

import lombok.Data;
import youtube.java.puzzle.student.entity.StudentEntity;

import java.util.List;

@Data
public class Response {
    List<StudentEntity> studentEntities;

}
