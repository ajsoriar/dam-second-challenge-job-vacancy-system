package io.reto.gestor_vacantes.rest;

import io.reto.gestor_vacantes.model.CategoriaDTO;
import io.reto.gestor_vacantes.service.CategoriaService;
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
@RequestMapping(value = "/api/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping("/test-db-connection")
    public ResponseEntity<String> testDbConnection() {
        try {
            if (categoriaService.findAll().isEmpty()) {
                return ResponseEntity.ok("Conexión a la base de datos exitosa, pero no hay datos.");
            } else {
                return ResponseEntity.ok("Conexión a la base de datos exitosa.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(categoriaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createCategoria(
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        final Integer createdId = categoriaService.create(categoriaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateCategoria(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        categoriaService.update(id, categoriaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = categoriaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
