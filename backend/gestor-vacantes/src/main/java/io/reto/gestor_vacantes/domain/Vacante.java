package io.reto.gestor_vacantes.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table (name = "vacantes")
public class Vacante {

    @Id
    @Column(name = "id_vacante",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private LocalDate fecha;

    @Column
    private Double salario;

    @Column
    private String estatus;

    @Column
    private Integer destacado;

    @Column
    private String imagen;

    @Column
    private String detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "vacante")
    private Set<Solicitud> solicitudes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
