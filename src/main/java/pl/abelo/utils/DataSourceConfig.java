package pl.abelo.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.hikari.username}")
    private String username;

    @Value("${spring.datasource.hikari.password}")
    private String password;

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String databaseURL;

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private String poolSize;

    @Bean
    public DataSource dataSource() {
        final HikariDataSource ds = new HikariDataSource();

        ds.setDataSourceClassName(driverName);
        ds.setMaximumPoolSize(Integer.parseInt(poolSize));

        ds.addDataSourceProperty("url", databaseURL);
        ds.addDataSourceProperty("user", username);
        ds.addDataSourceProperty("password", password);

        return ds;
    }

    private Properties additionalJPAProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");

        return properties;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reportEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalJPAProperties());
        em.setPersistenceUnitName("banco");
        em.setPackagesToScan("pl.abelo.model");

        return em;
    }
}

