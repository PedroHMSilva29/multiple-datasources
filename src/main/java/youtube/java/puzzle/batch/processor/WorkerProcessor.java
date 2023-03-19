package youtube.java.puzzle.batch.processor;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import youtube.java.puzzle.batch.ResultCSV;
import youtube.java.puzzle.batch.Worker;

@Component
public class WorkerProcessor implements ItemProcessor<Worker, ResultCSV> {
    @Override
    public ResultCSV process(Worker worker) {

        return new ResultCSV(worker.getId(),
                worker.getFirstName().toUpperCase(),
                worker.getLastName().toUpperCase(),
                worker.getCnpj());
    }
}
