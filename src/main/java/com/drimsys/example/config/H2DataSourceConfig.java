package com.drimsys.example.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Primary
@Configuration
@DependsOn("multiTxManager")
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = {"com.drimsys.example.domain.h2"} ,
        entityManagerFactoryRef = "h2EntityManagerFactory" ,
        transactionManagerRef = "multiTxManager"
)
@EntityScan("com.drimsys.example.domain.h2")
public class H2DataSourceConfig {
    @Value("${spring.jta.atomikos.datasource.h2.unique-resource-name}")
    private String uniqueResourceName;

    @Value("${spring.jta.atomikos.datasource.h2.xa-data-source-class-name}")
    private String dataSourceClassName;

    @Value("${spring.jta.atomikos.datasource.h2.xa-properties.user}")
    private String user;

    @Value("${spring.jta.atomikos.datasource.h2.xa-properties.password}")
    private String password;

    @Value("${spring.jta.atomikos.datasource.h2.xa-properties.url}")
    private String url;

    @Primary
    @Bean(name = "h2DataSource")
    public DataSource h2DataSource() {
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName(uniqueResourceName);
        dataSource.setXaDataSourceClassName(dataSourceClassName);
        dataSource.setXaProperties(properties);
        return dataSource;
    }

    @Primary
    @Bean(name = "h2EntityManagerFactory")
    @DependsOn("multiTxManager")
    public LocalContainerEntityManagerFactoryBean h2EntityManagerFactory() {
        Properties properties = new Properties();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(h2DataSource());
        entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManager.setPackagesToScan("com.drimsys.example.domain.h2");
        entityManager.setPersistenceUnitName("h2_write_unit");
        entityManager.setJpaProperties(properties);
        return entityManager;
    }
}
