package com.murun.fict.main;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties
@Validated
public class ApplicationProperties {


    public static class Fict {

        private String allowedOrigins;

        public String getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(String allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }
    }

    public static class Jdbc {
        @NotBlank
        private String driverClassName;

        private String password;
        @NotBlank
        private String url;
        @NotBlank
        private String username;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class Hibernate {
        @NotBlank
        private String dialect;
        @NotBlank
        private String ShowSql;
        @NotBlank
        private String JdbcFetchSize;
        @NotBlank
        private String JdbcBatchSize;
        @NotBlank
        private String ConnectionPoolSize;

        private String defaultSchema;

        @NotBlank
        private String hbm2ddlAuto;
        @NotBlank
        private String idNewGeneratorMappings;

        public String getDialect() {
            return dialect;
        }

        public void setDialect(String dialect) {
            this.dialect = dialect;
        }

        public String getShowSql() {
            return ShowSql;
        }

        public void setShowSql(String showSql) {
            ShowSql = showSql;
        }

        public String getJdbcFetchSize() {
            return JdbcFetchSize;
        }

        public void setJdbcFetchSize(String jdbcFetchSize) {
            JdbcFetchSize = jdbcFetchSize;
        }

        public String getJdbcBatchSize() {
            return JdbcBatchSize;
        }

        public void setJdbcBatchSize(String jdbcBatchSize) {
            JdbcBatchSize = jdbcBatchSize;
        }

        public String getConnectionPoolSize() {
            return ConnectionPoolSize;
        }

        public void setConnectionPoolSize(String connectionPoolSize) {
            ConnectionPoolSize = connectionPoolSize;
        }

        public String getDefaultSchema() {
            return defaultSchema;
        }

        public void setDefaultSchema(String defaultSchema) {
            this.defaultSchema = defaultSchema;
        }

        public String getHbm2ddlAuto() {
            return hbm2ddlAuto;
        }

        public void setHbm2ddlAuto(String hbm2ddlAuto) {
            this.hbm2ddlAuto = hbm2ddlAuto;
        }

        public String getIdNewGeneratorMappings() {
            return idNewGeneratorMappings;
        }

        public void setIdNewGeneratorMappings(String idNewGeneratorMappings) {
            this.idNewGeneratorMappings = idNewGeneratorMappings;
        }
    }


    private Fict fict;
    private Jdbc jdbc;
    private Hibernate hibernate;


    public Fict getFict() {
        return fict;
    }

    public void setFict(Fict fict) {
        this.fict = fict;
    }

    public Jdbc getJdbc() {
        return jdbc;
    }

    public void setJdbc(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    public Hibernate getHibernate() {
        return hibernate;
    }

    public void setHibernate(Hibernate hibernate) {
        this.hibernate = hibernate;
    }
}
