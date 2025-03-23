package io.reto.gestor_vacantes.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.reto.gestor_vacantes.domain")
@EnableJpaRepositories("io.reto.gestor_vacantes.repos")
@EnableTransactionManagement
public class DomainConfig {
}
