package io.reto.gestor_vacantes.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PerfilDTO {

    private Integer id;

    @Size(max = 100)
    private String nombre;

}
