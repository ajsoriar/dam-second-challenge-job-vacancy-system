package io.reto.gestor_vacantes.domain;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

@Table (name = "categorias")
public class Categoria {

    @Id
    @Column(name = "id_categoria",nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 2000)
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private Set<Vacante> vacantes;

}
