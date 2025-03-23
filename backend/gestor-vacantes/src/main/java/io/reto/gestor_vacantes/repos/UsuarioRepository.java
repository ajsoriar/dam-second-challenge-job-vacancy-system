package io.reto.gestor_vacantes.repos;

import io.reto.gestor_vacantes.domain.Perfil;
import io.reto.gestor_vacantes.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findFirstByPerfiles(Perfil perfil);

    List<Usuario> findAllByPerfiles(Perfil perfil);

    boolean existsByUsernameIgnoreCase(String username);

}
