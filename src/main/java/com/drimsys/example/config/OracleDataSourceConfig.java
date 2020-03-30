package com.drimsys.example.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@DependsOn("multiTxManager")
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = {"com.drimsys.example.domain.oracle"} ,
        entityManagerFactoryRef = "oracleEntityManagerFactory" ,
        transactionManagerRef = "multiTxManager"
)
@EntityScan("com.drimsys.example.domain.oracle")
public class OracleDataSourceConfig {
    @Value("${spring.jta.atomikos.datasource.oracle.unique-resource-name}")
    private String uniqueResourceName;

    @Value("${spring.jta.atomikos.datasource.oracle.xa-data-source-class-name}")
    private String dataSourceClassName;

    @Value("${spring.jta.atomikos.datasource.oracle.xa-properties.user}")
    private String user;

    @Value("${spring.jta.atomikos.datasource.oracle.xa-properties.password}")
    private String password;

    @Value("${spring.jta.atomikos.datasource.oracle.xa-properties.url}")
    private String url;

    @Bean(name = "oracleDataSource")
    public DataSource oracleDataSource() throws SQLException {
        final OracleXADataSource oracleXADataSource = new OracleXADataSource();
        oracleXADataSource.setUser(user);
        oracleXADataSource.setPassword(password);
        oracleXADataSource.setURL(url);

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setUniqueResourceName(uniqueResourceName);
        dataSource.setXaDataSourceClassName(dataSourceClassName);
        dataSource.setXaDataSource(oracleXADataSource);
        return dataSource;
    }

    @Bean(name = "oracleEntityManagerFactory")
    @DependsOn("multiTxManager")
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory() throws SQLException {
        Properties properties = new Properties();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.ORACLE);

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(oracleDataSource());
        entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManager.setPackagesToScan("com.drimsys.example.domain.oracle");
        entityManager.setPersistenceUnitName("oracle_write_unit");
        entityManager.setJpaProperties(properties);
        return entityManager;
    }
}
