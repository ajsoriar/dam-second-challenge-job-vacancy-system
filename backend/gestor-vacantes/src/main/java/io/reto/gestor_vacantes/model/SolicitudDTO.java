package io.reto.gestor_vacantes.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SolicitudDTO {

    private Integer id;

    private LocalDate fecha;

    @Size(max = 255)
    private String archivo;

    @Size(max = 255)
    private String comentarios;

    private Integer estado;

    private Integer vacante;

    @Size(max = 45)
    private String usuario;

}
