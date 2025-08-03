package com.example.Employee_Directory.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.Employee_Directory.repository.student",
        entityManagerFactoryRef = "studentEntityManager",
        transactionManagerRef = "studentTransactionManager"
)
public class StudentDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "student.datasource")
    public DataSource studentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean studentEntityManager(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(studentDataSource())
                .packages("com.example.Employee_Directory.model")
                .persistenceUnit("student")
                .build();
    }

    @Bean
    public PlatformTransactionManager studentTransactionManager(
            @Qualifier("studentEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
