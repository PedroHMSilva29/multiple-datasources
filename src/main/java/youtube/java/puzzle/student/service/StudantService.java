package youtube.java.puzzle.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youtube.java.puzzle.student.entity.StudentEntity;
import youtube.java.puzzle.student.repository.StudentRepository;

import java.util.List;

@Service
public class StudantService {

    @Autowired
    private StudentRepository repository;

    @Transactional(transactionManager = "studentTransactionManager")
    public void salvar(List<? extends StudentEntity> list) {
        repository.saveAll(list);
    }

    @Transactional(transactionManager = "studentTransactionManager")
    public void salvar(StudentEntity studanteEntities) {
        repository.save(studanteEntities);
    }

}
