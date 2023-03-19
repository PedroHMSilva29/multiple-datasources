package youtube.java.puzzle.batch;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SpringBatchConfig {

    @Autowired
    @Qualifier(value = "batch-datasource")
    public DataSource datasource;

    @Bean
    public BatchConfigurer configurer() {
        return new DefaultBatchConfigurer(datasource);
    }

    @Bean(name = "batch-repositorio")
    public JobRepository getJobRepository() {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(this.datasource);
        factoryBean.setTransactionManager(new DataSourceTransactionManager(datasource));
        factoryBean.setTablePrefix("BATCH_");
        factoryBean.setIsolationLevelForCreate("PROPAGATION_REQUIRED");
        try {
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }

}
