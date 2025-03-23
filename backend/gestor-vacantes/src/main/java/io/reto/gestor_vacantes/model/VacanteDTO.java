package io.reto.gestor_vacantes.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VacanteDTO {

    private Integer id;

    @Size(max = 200)
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    private LocalDate fecha;

    private Double salario;

    @Size(max = 255)
    private String estatus;

    private Integer destacado;

    @Size(max = 255)
    private String imagen;

    @Size(max = 255)
    private String detalles;

    private Integer empresa;

    private Integer categoria;

}
