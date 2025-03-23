package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
