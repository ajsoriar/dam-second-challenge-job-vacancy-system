package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
