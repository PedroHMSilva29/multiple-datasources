package youtube.java.puzzle.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import youtube.java.puzzle.batch.listener.JobListener;
import youtube.java.puzzle.batch.processor.DatabaseProcessor;
import youtube.java.puzzle.batch.processor.StudentProcessor;
import youtube.java.puzzle.batch.processor.WorkerProcessor;
import youtube.java.puzzle.batch.reader.FileRequestStudantReader;
import youtube.java.puzzle.batch.reader.FileRequestWorkerReader;
import youtube.java.puzzle.batch.reader.ResultCSVRequestReader;
import youtube.java.puzzle.batch.writer.DatabaseRequestWriter;
import youtube.java.puzzle.batch.writer.FileRequestWriter;
import youtube.java.puzzle.student.entity.StudentEntity;

@Configuration
@EnableBatchProcessing
//@EnableJpaRepositories("br.com.pehenmo.postgres.repository")
public class JobConfiguration {

    @Autowired
    JobListener jobListener;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    FileRequestStudantReader fileStudantReader;

    @Autowired
    FileRequestWorkerReader fileWorkerReader;

    @Autowired
    ResultCSVRequestReader resultCSVRequestReader;

    @Autowired
    FileRequestWriter fileWriter;

    @Autowired
    DatabaseRequestWriter databaseWriter;

    @Bean
    @StepScope
    public FlatFileItemReader<Student> fileReaderStudent() {
        return fileStudantReader.reader();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Worker> fileReaderWorker() {
        return fileWorkerReader.reader();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ResultCSV> fileReaderResultCSV() {
        return resultCSVRequestReader.reader();
    }

    @Bean
    @StepScope
    public StudentProcessor processorStudent() {
        return new StudentProcessor();
    }

    @Bean
    @StepScope
    public DatabaseProcessor processorDatabase() {
        return new DatabaseProcessor();
    }

    @Bean
    @StepScope
    public WorkerProcessor processorWorker() {
        return new WorkerProcessor();
    }


    @Bean
    @StepScope
    public FlatFileItemWriter<ResultCSV> fileWriter() {
        return fileWriter.write();
    }

    @Bean
    @StepScope
    public ItemWriter<StudentEntity> databaseWriter() {
        return databaseWriter;
    }

    @Bean
    public Step fileWorkerStep() {
        return stepBuilderFactory
                .get("file-worker-step").<Worker, ResultCSV>chunk(2)
                .reader(fileReaderWorker())
                .processor(processorWorker())
                .writer(fileWriter())
                .build();
    }

    @Bean
    public Step fileDatabaseStep() {
        return stepBuilderFactory
                .get("file-database-step").<ResultCSV, StudentEntity>chunk(2)
                .reader(fileReaderResultCSV())
                .processor(processorDatabase())
                .writer(databaseWriter())
                .build();
    }

    @Bean
    public Step fileStudentStep() {
        return stepBuilderFactory
                .get("file-student-step").<Student, ResultCSV>chunk(2)
                .reader(fileReaderStudent())
                .processor(processorStudent())
                .writer(fileWriter())
                .build();
    }


    @Bean
    public Job mainJob() {
        return jobBuilderFactory
                .get("main-job")
                .incrementer(new RunIdIncrementer())
                .start(fileStudentStep())
                .next(fileWorkerStep())
                .next(fileDatabaseStep())
                .listener(jobListener)
                .build();
    }

}
