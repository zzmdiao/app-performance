package com.iqianjin.appperformance.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.io.IOException;

@Configuration
@MapperScan(value = "com.iqianjin.appperformance.getData.dao", sqlSessionFactoryRef = "activitySqlSessionFactory")
public class DataSourceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean("activityDataSource")
    public DataSource activityDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);

        return dataSource;
    }

    @Bean("activityTransactionManager")
    @Primary
    public PlatformTransactionManager activityTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(activityDataSource());
        return transactionManager;
    }

    @Bean("activitySqlSessionFactory")
    public SqlSessionFactory activitySqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(activityDataSource());
        factory.setConfiguration(configuration());
        factory.setTypeHandlersPackage("com.iqianjin.activity.core.configure");

        try {
            Resource[] mappers = new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*Mapper.xml");
            factory.setMapperLocations(mappers);
        } catch (IOException e) {
            logger.error("Activity MapperLocations not exist", e);
        }

        return factory.getObject();
    }

    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }
}
