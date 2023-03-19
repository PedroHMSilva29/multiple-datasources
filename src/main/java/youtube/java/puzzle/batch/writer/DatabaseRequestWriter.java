package youtube.java.puzzle.batch.writer;


import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import youtube.java.puzzle.student.entity.StudentEntity;
import youtube.java.puzzle.student.service.StudantService;

import java.util.List;

@Component
public class DatabaseRequestWriter implements ItemWriter<StudentEntity> {

    @Autowired
    StudantService service;

    @Override
    public void write(List<? extends StudentEntity> list) {

        service.salvar(list);
    }
}
