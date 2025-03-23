package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Categoria;
import io.reto.gestor_vacantes.domain.Vacante;
import io.reto.gestor_vacantes.model.CategoriaDTO;
import io.reto.gestor_vacantes.repos.CategoriaRepository;
import io.reto.gestor_vacantes.repos.VacanteRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import io.reto.gestor_vacantes.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final VacanteRepository vacanteRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository,
            final VacanteRepository vacanteRepository) {
        this.categoriaRepository = categoriaRepository;
        this.vacanteRepository = vacanteRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("id"));
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Integer id) {
        return categoriaRepository.findById(id)
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        mapToEntity(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getId();
    }

    public void update(final Integer id, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Integer id) {
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO mapToDTO(final Categoria categoria, final CategoriaDTO categoriaDTO) {
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        return categoriaDTO;
    }

    private Categoria mapToEntity(final CategoriaDTO categoriaDTO, final Categoria categoria) {
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        return categoria;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Vacante categoriaVacante = vacanteRepository.findFirstByCategoria(categoria);
        if (categoriaVacante != null) {
            referencedWarning.setKey("categoria.vacante.categoria.referenced");
            referencedWarning.addParam(categoriaVacante.getId());
            return referencedWarning;
        }
        return null;
    }

}
