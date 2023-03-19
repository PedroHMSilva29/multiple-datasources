package youtube.java.puzzle.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import youtube.java.puzzle.student.entity.StudentEntity;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "youtube.java.puzzle.student.repository",
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager")
public class StudentDataSourceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties("spring.datasource.student")
    public DataSourceProperties studentDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.student.configuration")
    public DataSource studentDataSource() {
        return studentDataSourceProperties().initializeDataSourceBuilder()
                .type( HikariDataSource.class).build();
    }

    @Bean(name = "studentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.student.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.student.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.student.dialect"));
        return builder
                .dataSource(studentDataSource())
                .packages( StudentEntity.class)
                .properties(properties)
                .build();
    }

    @Bean(name = "studentTransactionManager")
    public PlatformTransactionManager studentTransactionManager(
            final @Qualifier("studentEntityManagerFactory") LocalContainerEntityManagerFactoryBean studentEntityManagerFactory) {
        return new JpaTransactionManager(studentEntityManagerFactory.getObject());
    }
}
