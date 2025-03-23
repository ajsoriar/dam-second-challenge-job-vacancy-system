package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Solicitud;
import io.reto.gestor_vacantes.domain.Usuario;
import io.reto.gestor_vacantes.domain.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

    Solicitud findFirstByVacante(Vacante vacante);

    Solicitud findFirstByUsuario(Usuario usuario);

}
