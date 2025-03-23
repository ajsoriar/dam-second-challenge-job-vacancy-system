package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.SolicitudDTO;
import io.reto.gestor_vacantes.service.SolicitudService;
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
@RequestMapping(value = "/api/solicituds", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudResource {

    private final SolicitudService solicitudService;

    public SolicitudResource(final SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudDTO>> getAllSolicituds() {
        return ResponseEntity.ok(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> getSolicitud(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(solicitudService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createSolicitud(
            @RequestBody @Valid final SolicitudDTO solicitudDTO) {
        final Integer createdId = solicitudService.create(solicitudDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSolicitud(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SolicitudDTO solicitudDTO) {
        solicitudService.update(id, solicitudDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable(name = "id") final Integer id) {
        solicitudService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
