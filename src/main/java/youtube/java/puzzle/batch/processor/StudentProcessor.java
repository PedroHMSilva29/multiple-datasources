package youtube.java.puzzle.batch.processor;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import youtube.java.puzzle.batch.ResultCSV;
import youtube.java.puzzle.batch.Student;

@Component
public class StudentProcessor implements ItemProcessor<Student, ResultCSV> {

    @Override
    public ResultCSV process(Student student) {

        return new ResultCSV(student.getId(),
                student.getFirstName().toUpperCase(),
                student.getLastName().toUpperCase(),
                student.getEmail());
    }
}
