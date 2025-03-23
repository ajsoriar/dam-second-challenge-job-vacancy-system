package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.VacanteDTO;
import io.reto.gestor_vacantes.service.VacanteService;
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
@RequestMapping(value = "/api/vacantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VacanteResource {

    private final VacanteService vacanteService;

    public VacanteResource(final VacanteService vacanteService) {
        this.vacanteService = vacanteService;
    }

    @GetMapping
    public ResponseEntity<List<VacanteDTO>> getAllVacantes() {
        return ResponseEntity.ok(vacanteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacanteDTO> getVacante(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(vacanteService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createVacante(@RequestBody @Valid final VacanteDTO vacanteDTO) {
        final Integer createdId = vacanteService.create(vacanteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateVacante(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final VacanteDTO vacanteDTO) {
        vacanteService.update(id, vacanteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacante(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = vacanteService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        vacanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
