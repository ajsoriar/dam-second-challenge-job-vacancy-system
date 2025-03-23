package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Categoria;
import io.reto.gestor_vacantes.domain.Empresa;
import io.reto.gestor_vacantes.domain.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacanteRepository extends JpaRepository<Vacante, Integer> {

    Vacante findFirstByEmpresa(Empresa empresa);

    Vacante findFirstByCategoria(Categoria categoria);

}
