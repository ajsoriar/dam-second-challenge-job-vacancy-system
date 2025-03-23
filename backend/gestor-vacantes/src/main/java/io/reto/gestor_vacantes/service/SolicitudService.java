package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Solicitud;
import io.reto.gestor_vacantes.domain.Usuario;
import io.reto.gestor_vacantes.domain.Vacante;
import io.reto.gestor_vacantes.model.SolicitudDTO;
import io.reto.gestor_vacantes.repos.SolicitudRepository;
import io.reto.gestor_vacantes.repos.UsuarioRepository;
import io.reto.gestor_vacantes.repos.VacanteRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final VacanteRepository vacanteRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitudService(final SolicitudRepository solicitudRepository,
            final VacanteRepository vacanteRepository, final UsuarioRepository usuarioRepository) {
        this.solicitudRepository = solicitudRepository;
        this.vacanteRepository = vacanteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SolicitudDTO> findAll() {
        final List<Solicitud> solicituds = solicitudRepository.findAll(Sort.by("id"));
        return solicituds.stream()
                .map(solicitud -> mapToDTO(solicitud, new SolicitudDTO()))
                .toList();
    }

    public SolicitudDTO get(final Integer id) {
        return solicitudRepository.findById(id)
                .map(solicitud -> mapToDTO(solicitud, new SolicitudDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SolicitudDTO solicitudDTO) {
        final Solicitud solicitud = new Solicitud();
        mapToEntity(solicitudDTO, solicitud);
        return solicitudRepository.save(solicitud).getId();
    }

    public void update(final Integer id, final SolicitudDTO solicitudDTO) {
        final Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solicitudDTO, solicitud);
        solicitudRepository.save(solicitud);
    }

    public void delete(final Integer id) {
        solicitudRepository.deleteById(id);
    }

    private SolicitudDTO mapToDTO(final Solicitud solicitud, final SolicitudDTO solicitudDTO) {
        solicitudDTO.setId(solicitud.getId());
        solicitudDTO.setFecha(solicitud.getFecha());
        solicitudDTO.setArchivo(solicitud.getArchivo());
        solicitudDTO.setComentarios(solicitud.getComentarios());
        solicitudDTO.setEstado(solicitud.getEstado());
        solicitudDTO.setVacante(solicitud.getVacante() == null ? null : solicitud.getVacante().getId());
        solicitudDTO.setUsuario(solicitud.getUsuario() == null ? null : solicitud.getUsuario().getUsername());
        return solicitudDTO;
    }

    private Solicitud mapToEntity(final SolicitudDTO solicitudDTO, final Solicitud solicitud) {
        solicitud.setFecha(solicitudDTO.getFecha());
        solicitud.setArchivo(solicitudDTO.getArchivo());
        solicitud.setComentarios(solicitudDTO.getComentarios());
        solicitud.setEstado(solicitudDTO.getEstado());
        final Vacante vacante = solicitudDTO.getVacante() == null ? null : vacanteRepository.findById(solicitudDTO.getVacante())
                .orElseThrow(() -> new NotFoundException("vacante not found"));
        solicitud.setVacante(vacante);
        final Usuario usuario = solicitudDTO.getUsuario() == null ? null : usuarioRepository.findById(solicitudDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        solicitud.setUsuario(usuario);
        return solicitud;
    }

}
