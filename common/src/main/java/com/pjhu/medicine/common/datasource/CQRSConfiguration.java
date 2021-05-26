package com.pjhu.medicine.common.datasource;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
public class CQRSConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.config")
    public Properties dataSourceProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public Properties masterProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public Properties slaveProperties() {
        return new Properties();
    }

    @Bean(name = "masterDataSource")
    public DataSource master() {
        Properties properties = dataSourceProperties();
        HikariConfig configuration = new HikariConfig(masterProperties());
        configuration.setDataSourceProperties(properties);
        return new HikariDataSource(configuration);
    }

    @Bean(name = "slaveDataSource")
    public DataSource slave() {
        Properties properties = dataSourceProperties();
        HikariConfig configuration = new HikariConfig(slaveProperties());
        configuration.setDataSourceProperties(properties);
        return new HikariDataSource(configuration);
    }

    @DependsOn({"masterDataSource", "slaveDataSource"})
    @Bean(name = "lazyDataSource")
    @Primary
    public DataSource dataSource() {
        CRQSRoutingDataSource rds = new CRQSRoutingDataSource();
        rds.setTargetDataSources(
                ImmutableMap.of(
                        DataSourceType.SLAVE, slave(),
                        DataSourceType.MASTER, master()));
        rds.setDefaultTargetDataSource(master());
        rds.afterPropertiesSet();
        return new LazyConnectionDataSourceProxy(rds);
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager() {
        log.debug("datasource={}", dataSource());
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
