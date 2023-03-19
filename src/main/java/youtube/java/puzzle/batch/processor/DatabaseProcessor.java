package youtube.java.puzzle.batch.processor;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import youtube.java.puzzle.batch.ResultCSV;
import youtube.java.puzzle.student.entity.StudentEntity;

@Component
public class DatabaseProcessor implements ItemProcessor<ResultCSV, StudentEntity> {
    @Override
    public StudentEntity process(ResultCSV resultCSV) {
        return resultCSV.mapToEntity();
    }
}
