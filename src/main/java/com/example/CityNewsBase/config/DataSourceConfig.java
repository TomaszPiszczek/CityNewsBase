package com.example.CityNewsBase.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class DataSourceConfig {


    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://16.170.252.4:5432/CityNews")
                .username("postgres")
                .password(SecretsManagerService.getSecretValue("dbPassword"))
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
