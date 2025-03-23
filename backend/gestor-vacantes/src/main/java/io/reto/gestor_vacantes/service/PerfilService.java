package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Perfil;
import io.reto.gestor_vacantes.model.PerfilDTO;
import io.reto.gestor_vacantes.repos.PerfilRepository;
import io.reto.gestor_vacantes.repos.UsuarioRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(final PerfilRepository perfilRepository,
            final UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PerfilDTO> findAll() {
        final List<Perfil> perfils = perfilRepository.findAll(Sort.by("id"));
        return perfils.stream()
                .map(perfil -> mapToDTO(perfil, new PerfilDTO()))
                .toList();
    }

    public PerfilDTO get(final Integer id) {
        return perfilRepository.findById(id)
                .map(perfil -> mapToDTO(perfil, new PerfilDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PerfilDTO perfilDTO) {
        final Perfil perfil = new Perfil();
        mapToEntity(perfilDTO, perfil);
        return perfilRepository.save(perfil).getId();
    }

    public void update(final Integer id, final PerfilDTO perfilDTO) {
        final Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(perfilDTO, perfil);
        perfilRepository.save(perfil);
    }

    public void delete(final Integer id) {
        final Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        usuarioRepository.findAllByPerfiles(perfil)
                .forEach(usuario -> usuario.getPerfiles().remove(perfil));
        perfilRepository.delete(perfil);
    }

    private PerfilDTO mapToDTO(final Perfil perfil, final PerfilDTO perfilDTO) {
        perfilDTO.setId(perfil.getId());
        perfilDTO.setNombre(perfil.getNombre());
        return perfilDTO;
    }

    private Perfil mapToEntity(final PerfilDTO perfilDTO, final Perfil perfil) {
        perfil.setNombre(perfilDTO.getNombre());
        return perfil;
    }

}
