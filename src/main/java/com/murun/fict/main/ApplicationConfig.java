package com.murun.fict.main;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;





@EnableTransactionManagement

@ComponentScan(basePackages="com.murun.*")


@PropertySource(value = "classpath:application.properties")
//@PropertySource(value = "classpath:properties/aws.properties")
@EnableWebSecurity
@EnableJpaRepositories("com.murun.fict.*")
@EnableCaching
@Configuration
public class ApplicationConfig {

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "jdbc.driverClassName";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "jdbc.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "jdbc.username";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
    private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";

    private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";

    private static final String PROPERTY_NAME_HIBERNATE_IMPLICIT_NAMING_STRATEGY = "hibernate.implicit_naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy";


    private static final String PROPERTY_NAME_KMS_ENDPOINT = "kms.endpoint";
    private static final String PROPERTY_NAME_KMS_KEYID = "kms.keyId";

    private static final String PROPERTY_VALUE_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.murun.fict.*";




    @Resource
    private Environment env;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword((env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD)));
        return dataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_VALUE_ENTITYMANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setPersistenceUnitName("control");

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibProperties());


        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,	env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE));
        properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
        properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE));
        properties.put(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA));
      //  properties.put(PROPERTY_NAME_HIBERNATE_IMPLICIT_NAMING_STRATEGY, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_IMPLICIT_NAMING_STRATEGY));
     //   properties.put(PROPERTY_NAME_HIBERNATE_PHYSICAL_NAMING_STRATEGY, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_PHYSICAL_NAMING_STRATEGY));


        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(Hibernate5Module.class);
    }

  /*  @Bean
    public String kmsEndPoint() {
        return env.getProperty(PROPERTY_NAME_KMS_ENDPOINT);
    }


     @Bean
     public String kmsKeyId() {
         return env.getProperty(PROPERTY_NAME__KMS_KEYID);
     }
*/




}
