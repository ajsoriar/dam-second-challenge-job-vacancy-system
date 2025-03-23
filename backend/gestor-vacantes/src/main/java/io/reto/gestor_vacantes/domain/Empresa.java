package io.reto.gestor_vacantes.domain;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

@Table(name = "empresas")
public class Empresa {

    @Id
    @Column(name="id_empresa", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="razon_social",length = 45)
    private String razonSocial;

    @Column(name ="direccion_fiscal",length = 45)
    private String direccionFiscal;

    @Column(length = 45)
    private String pais;

    @OneToMany(mappedBy = "empresa")
    private Set<Vacante> vacantes;

}
