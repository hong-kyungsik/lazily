package com.example.lazily.config.persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(
    entityManagerFactoryRef = "paperEntityManagerFactory",
    transactionManagerRef = "paperTransactionManager",
    basePackages = {"com.example.lazily.paper"}
)
public class PaperPersistenceConfig {

    @Bean(name="paperDataSourceProperties")
    @ConfigurationProperties("spring.datasource-paper")
    public DataSourceProperties paperDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "paperDataSource")
    @ConfigurationProperties("spring.datasource-paper.configuration")
    public DataSource paperDataSource(
        @Qualifier("paperDataSourceProperties") DataSourceProperties paperDataSourceProperties
    ) {
        return paperDataSourceProperties
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }
    
    @Bean(name = "paperEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean paperEntityManagerFactory(
        EntityManagerFactoryBuilder paperEntityManagerFactoryBuilder, 
        @Qualifier("paperDataSource") DataSource paperDataSource) {

        Map<String, String> paperJpaProperties = new HashMap<>();
        paperJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

        return paperEntityManagerFactoryBuilder
            .dataSource(paperDataSource)
            .packages("com.example.lazily.paper")
            .persistenceUnit("paperDataSource")
            .properties(paperJpaProperties)
            .build();
    }

    @Bean(name = "paperTransactionManager")
    public PlatformTransactionManager paperTransactionManager(
        @Qualifier("paperEntityManagerFactory") EntityManagerFactory paperEntityManagerFactory) {

        return new JpaTransactionManager(paperEntityManagerFactory);
    }
}
