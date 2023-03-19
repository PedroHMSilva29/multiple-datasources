package youtube.java.puzzle.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "youtube.java.puzzle.college.repository",
        entityManagerFactoryRef = "collegeEntityManagerFactory",
        transactionManagerRef = "collegeTransactionManager")
public class CollegeDataSourceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.college")
    public DataSourceProperties collegeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "batch-datasource")
    @Primary
    @ConfigurationProperties("spring.datasource.college.configuration")
    public DataSource collegeDataSource() {
        return collegeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "collegeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean collegeEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.college.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.college.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.college.dialect"));
        return builder
                .dataSource(collegeDataSource())
                .packages("youtube.java.puzzle.college.entity")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "collegeTransactionManager")
    public PlatformTransactionManager collegeTransactionManager(
            final @Qualifier("collegeEntityManagerFactory") LocalContainerEntityManagerFactoryBean collegeEntityManagerFactory) {
        return new JpaTransactionManager(collegeEntityManagerFactory.getObject());
    }
}
