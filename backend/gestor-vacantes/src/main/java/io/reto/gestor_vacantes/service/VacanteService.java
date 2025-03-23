package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Categoria;
import io.reto.gestor_vacantes.domain.Empresa;
import io.reto.gestor_vacantes.domain.Solicitud;
import io.reto.gestor_vacantes.domain.Vacante;
import io.reto.gestor_vacantes.model.VacanteDTO;
import io.reto.gestor_vacantes.repos.CategoriaRepository;
import io.reto.gestor_vacantes.repos.EmpresaRepository;
import io.reto.gestor_vacantes.repos.SolicitudRepository;
import io.reto.gestor_vacantes.repos.VacanteRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import io.reto.gestor_vacantes.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VacanteService {

    private final VacanteRepository vacanteRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaRepository categoriaRepository;
    private final SolicitudRepository solicitudRepository;

    public VacanteService(final VacanteRepository vacanteRepository,
            final EmpresaRepository empresaRepository,
            final CategoriaRepository categoriaRepository,
            final SolicitudRepository solicitudRepository) {
        this.vacanteRepository = vacanteRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.solicitudRepository = solicitudRepository;
    }

    public List<VacanteDTO> findAll() {
        final List<Vacante> vacantes = vacanteRepository.findAll(Sort.by("id"));
        return vacantes.stream()
                .map(vacante -> mapToDTO(vacante, new VacanteDTO()))
                .toList();
    }

    public VacanteDTO get(final Integer id) {
        return vacanteRepository.findById(id)
                .map(vacante -> mapToDTO(vacante, new VacanteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final VacanteDTO vacanteDTO) {
        final Vacante vacante = new Vacante();
        mapToEntity(vacanteDTO, vacante);
        return vacanteRepository.save(vacante).getId();
    }

    public void update(final Integer id, final VacanteDTO vacanteDTO) {
        final Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vacanteDTO, vacante);
        vacanteRepository.save(vacante);
    }

    public void delete(final Integer id) {
        vacanteRepository.deleteById(id);
    }

    private VacanteDTO mapToDTO(final Vacante vacante, final VacanteDTO vacanteDTO) {
        vacanteDTO.setId(vacante.getId());
        vacanteDTO.setNombre(vacante.getNombre());
        vacanteDTO.setDescripcion(vacante.getDescripcion());
        vacanteDTO.setFecha(vacante.getFecha());
        vacanteDTO.setSalario(vacante.getSalario());
        vacanteDTO.setEstatus(vacante.getEstatus());
        vacanteDTO.setDestacado(vacante.getDestacado());
        vacanteDTO.setImagen(vacante.getImagen());
        vacanteDTO.setDetalles(vacante.getDetalles());
        vacanteDTO.setEmpresa(vacante.getEmpresa() == null ? null : vacante.getEmpresa().getId());
        vacanteDTO.setCategoria(vacante.getCategoria() == null ? null : vacante.getCategoria().getId());
        return vacanteDTO;
    }

    private Vacante mapToEntity(final VacanteDTO vacanteDTO, final Vacante vacante) {
        vacante.setNombre(vacanteDTO.getNombre());
        vacante.setDescripcion(vacanteDTO.getDescripcion());
        vacante.setFecha(vacanteDTO.getFecha());
        vacante.setSalario(vacanteDTO.getSalario());
        vacante.setEstatus(vacanteDTO.getEstatus());
        vacante.setDestacado(vacanteDTO.getDestacado());
        vacante.setImagen(vacanteDTO.getImagen());
        vacante.setDetalles(vacanteDTO.getDetalles());
        final Empresa empresa = vacanteDTO.getEmpresa() == null ? null : empresaRepository.findById(vacanteDTO.getEmpresa())
                .orElseThrow(() -> new NotFoundException("empresa not found"));
        vacante.setEmpresa(empresa);
        final Categoria categoria = vacanteDTO.getCategoria() == null ? null : categoriaRepository.findById(vacanteDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        vacante.setCategoria(categoria);
        return vacante;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Solicitud vacanteSolicitud = solicitudRepository.findFirstByVacante(vacante);
        if (vacanteSolicitud != null) {
            referencedWarning.setKey("vacante.solicitud.vacante.referenced");
            referencedWarning.addParam(vacanteSolicitud.getId());
            return referencedWarning;
        }
        return null;
    }

}
