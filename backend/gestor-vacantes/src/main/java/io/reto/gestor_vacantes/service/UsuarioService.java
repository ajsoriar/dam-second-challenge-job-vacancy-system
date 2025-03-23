package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Perfil;
import io.reto.gestor_vacantes.domain.Solicitud;
import io.reto.gestor_vacantes.domain.Usuario;
import io.reto.gestor_vacantes.model.UsuarioDTO;
import io.reto.gestor_vacantes.repos.PerfilRepository;
import io.reto.gestor_vacantes.repos.SolicitudRepository;
import io.reto.gestor_vacantes.repos.UsuarioRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import io.reto.gestor_vacantes.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final SolicitudRepository solicitudRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final PerfilRepository perfilRepository,
            final SolicitudRepository solicitudRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.solicitudRepository = solicitudRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("username"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final String username) {
        return usuarioRepository.findById(username)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        usuario.setUsername(usuarioDTO.getUsername());
        return usuarioRepository.save(usuario).getUsername();
    }

    public void update(final String username, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final String username) {
        usuarioRepository.deleteById(username);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setEnabled(usuario.getEnabled());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setPerfiles(usuario.getPerfiles().stream()
                .map(perfil -> perfil.getId())
                .toList());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setEnabled(usuarioDTO.getEnabled());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        final List<Perfil> perfiles = perfilRepository.findAllById(
                usuarioDTO.getPerfiles() == null ? Collections.emptyList() : usuarioDTO.getPerfiles());
        if (perfiles.size() != (usuarioDTO.getPerfiles() == null ? 0 : usuarioDTO.getPerfiles().size())) {
            throw new NotFoundException("one of perfiles not found");
        }
        usuario.setPerfiles(new HashSet<>(perfiles));
        return usuario;
    }

    public boolean usernameExists(final String username) {
        return usuarioRepository.existsByUsernameIgnoreCase(username);
    }

    public ReferencedWarning getReferencedWarning(final String username) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(NotFoundException::new);
        final Solicitud usuarioSolicitud = solicitudRepository.findFirstByUsuario(usuario);
        if (usuarioSolicitud != null) {
            referencedWarning.setKey("usuario.solicitud.usuario.referenced");
            referencedWarning.addParam(usuarioSolicitud.getId());
            return referencedWarning;
        }
        return null;
    }

}
