package io.reto.gestor_vacantes.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table (name = "usuarios")
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false, length = 45)
    private String username;

    @Column(length = 45)
    private String nombre;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    @Column
    private Integer enabled;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "usuario")
    private Set<Solicitud> solicitudes;

    @ManyToMany
    @JoinTable(
            name = "Usuarioperfil",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Perfil> perfiles;

}
