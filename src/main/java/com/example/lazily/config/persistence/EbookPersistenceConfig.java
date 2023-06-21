package com.example.lazily.config.persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    entityManagerFactoryRef = "ebookEntityManagerFactory",
    transactionManagerRef = "ebookTransactionManager",
    basePackages = {"com.example.lazily.ebook"}
)
public class EbookPersistenceConfig {

    @Primary
    @Bean(name="ebookDataSourceProperties")
    @ConfigurationProperties("spring.datasource-ebook")
    public DataSourceProperties ebookDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "ebookDataSource")
    @ConfigurationProperties("spring.datasource-ebook.configuration")
    public DataSource ebookDataSource(
        @Qualifier("ebookDataSourceProperties") DataSourceProperties ebookDataSourceProperties
    ) {
        return ebookDataSourceProperties
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }

    @Primary
    @Bean(name = "ebookEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ebookEntityManagerFactory(
        EntityManagerFactoryBuilder ebookEntityManagerFactoryBuilder,
        @Qualifier("ebookDataSource") DataSource ebookDataSource) {

        Map<String, String> ebookJpaProperties = new HashMap<>();
        ebookJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");

        return ebookEntityManagerFactoryBuilder
            .dataSource(ebookDataSource)
            .packages("com.example.lazily.ebook")
            .persistenceUnit("ebookDataSource")
            .properties(ebookJpaProperties)
            .build();
    }

    @Primary
    @Bean(name = "ebookTransactionManager")
    public PlatformTransactionManager ebookTransactionManager(
        @Qualifier("ebookEntityManagerFactory") EntityManagerFactory ebookEntityManagerFactory) {

        return new JpaTransactionManager(ebookEntityManagerFactory);
    }
}
