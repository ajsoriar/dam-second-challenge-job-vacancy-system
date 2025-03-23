package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.PerfilDTO;
import io.reto.gestor_vacantes.service.PerfilService;
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
@RequestMapping(value = "/api/perfils", produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilResource {

    private final PerfilService perfilService;

    public PerfilResource(final PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getAllPerfils() {
        return ResponseEntity.ok(perfilService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(perfilService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createPerfil(@RequestBody @Valid final PerfilDTO perfilDTO) {
        final Integer createdId = perfilService.create(perfilDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePerfil(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final PerfilDTO perfilDTO) {
        perfilService.update(id, perfilDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable(name = "id") final Integer id) {
        perfilService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
