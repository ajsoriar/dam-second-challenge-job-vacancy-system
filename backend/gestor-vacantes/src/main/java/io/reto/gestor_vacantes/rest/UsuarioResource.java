package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.UsuarioDTO;
import io.reto.gestor_vacantes.service.UsuarioService;
import io.reto.gestor_vacantes.util.ReferencedException;
import io.reto.gestor_vacantes.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "username") final String username) {
        return ResponseEntity.ok(usuarioService.get(username));
    }

    @PostMapping
    public ResponseEntity<String> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final String createdUsername = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>('"' + createdUsername + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUsuario(
            @PathVariable(name = "username") final String username,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(username, usuarioDTO);
        return ResponseEntity.ok('"' + username + '"');
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "username") final String username) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(username);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(username);
        return ResponseEntity.noContent().build();
    }

}
