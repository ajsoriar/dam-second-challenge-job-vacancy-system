package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.EmpresaDTO;
import io.reto.gestor_vacantes.service.EmpresaService;
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
@RequestMapping(value = "/api/empresas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpresaResource {

    private final EmpresaService empresaService;

    public EmpresaResource(final EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(empresaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createEmpresa(@RequestBody @Valid final EmpresaDTO empresaDTO) {
        final Integer createdId = empresaService.create(empresaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateEmpresa(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final EmpresaDTO empresaDTO) {
        empresaService.update(id, empresaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = empresaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        empresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
