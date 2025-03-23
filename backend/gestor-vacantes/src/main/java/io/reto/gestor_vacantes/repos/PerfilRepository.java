package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
}
