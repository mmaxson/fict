package com.murun.fict.main;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

//import org.h2.server.web.WebServlet;

@EnableResourceServer
@EnableTransactionManagement
@ComponentScan(basePackages="com.murun.*")



@EnableWebSecurity
@EnableJpaRepositories("com.murun.fict.*")
@Configuration
@EnableSwagger2
@RefreshScope

@Import({ ResourceServerConfiguration.class })
public class ApplicationConfiguration  {


    private static final String PROPERTY_VALUE_ENTITY_MANAGER_PACKAGES_TO_SCAN = "com.murun.fict.*";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE = "hibernate.jdbc.fetch_size";
    private static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
    private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";
    private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS =  "hibernate.id.new_generator_mappings";


    @Resource
    private ApplicationProperties applicationProperties;

  /*  @Resource
    private JedisConnectionFactory jedisConnFactory;
*/

    /*@Profile("dev")
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
*/

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(applicationProperties.getJdbc().getDriverClassName());
        dataSource.setUrl(applicationProperties.getJdbc().getUrl());
        dataSource.setUsername(applicationProperties.getJdbc().getUsername());
        dataSource.setPassword(applicationProperties.getJdbc().getPassword());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_VALUE_ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setPersistenceUnitName("control");

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibProperties());


        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {

        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,	applicationProperties.getHibernate().getDialect());
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, applicationProperties.getHibernate().getShowSql());
        properties.put(PROPERTY_NAME_HIBERNATE_JDBC_FETCH_SIZE, applicationProperties.getHibernate().getJdbcFetchSize());
        properties.put(PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE, applicationProperties.getHibernate().getJdbcBatchSize());
        properties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_POOL_SIZE, applicationProperties.getHibernate().getConnectionPoolSize());
        properties.put(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA, applicationProperties.getHibernate().getDefaultSchema());
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, applicationProperties.getHibernate().getHbm2ddlAuto());
        properties.put(PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS, applicationProperties.getHibernate().getIdNewGeneratorMappings());
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Primary
    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder().modulesToInstall(Hibernate5Module.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(applicationProperties.getFict().getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(CorsConfigurationSource corsConfigurationSource) {
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(corsFilter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


    /*@Bean
    JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
      //  redisStandaloneConfiguration.setPassword(RedisPassword.of("yourRedisPasswordIfAny"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }*/

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "entity",
                "Entity API",
                "1.0",
                "Terms of service",
                "mmurun@gmail.com",
                "License of API",
                "API license URL");

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
