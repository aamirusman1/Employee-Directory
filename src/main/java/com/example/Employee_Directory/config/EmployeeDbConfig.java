package com.example.Employee_Directory.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.Employee_Directory.repository.employee",
                "com.example.Employee_Directory.repository.user",
                "com.example.Employee_Directory.repository.role"},
        entityManagerFactoryRef = "employeeEntityManager",
        transactionManagerRef = "employeeTransactionManager"
)
public class EmployeeDbConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource employeeDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean employeeEntityManager(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(employeeDataSource())
                .packages("com.example.Employee_Directory.model")
                .persistenceUnit("employee")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager employeeTransactionManager(
            @Qualifier("employeeEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
