package io.reto.gestor_vacantes.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    @Size(max = 45)
    @UsuarioUsernameValid
    private String username;

    @Size(max = 45)
    private String nombre;

    @Size(max = 100)
    private String apellidos;

    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String password;

    private Integer enabled;

    private LocalDate fechaRegistro;

    private List<Integer> perfiles;

}
