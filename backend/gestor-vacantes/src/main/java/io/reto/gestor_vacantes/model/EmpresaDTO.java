package io.reto.gestor_vacantes.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmpresaDTO {

    private Integer id;

    @Size(max = 45)
    private String razonSocial;

    @Size(max = 45)
    private String direccionFiscal;

    @Size(max = 45)
    private String pais;

}
