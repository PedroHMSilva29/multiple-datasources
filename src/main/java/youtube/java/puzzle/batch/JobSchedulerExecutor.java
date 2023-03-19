package youtube.java.puzzle.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobSchedulerExecutor {


    @Autowired
    @Qualifier("batch-repositorio")
    private JobRepository repository;


    @Autowired
    Job job;

    /**
     * second, minute, hour, day of month, month, day(s) of week
     * 0 1 1 * * ? - every day 1:01AM
     * *///40 * * * * * - every 40 seconds
    /*
     * @throws Exception
     **/
    @Scheduled(cron = "*/10 * * * * *")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(repository);
        jobLauncher.setTaskExecutor(new SyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        jobLauncher.run(job, params);
    }
}
