package io.reto.gestor_vacantes.service;

import io.reto.gestor_vacantes.domain.Empresa;
import io.reto.gestor_vacantes.domain.Vacante;
import io.reto.gestor_vacantes.model.EmpresaDTO;
import io.reto.gestor_vacantes.repos.EmpresaRepository;
import io.reto.gestor_vacantes.repos.VacanteRepository;
import io.reto.gestor_vacantes.util.NotFoundException;
import io.reto.gestor_vacantes.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final VacanteRepository vacanteRepository;

    public EmpresaService(final EmpresaRepository empresaRepository,
            final VacanteRepository vacanteRepository) {
        this.empresaRepository = empresaRepository;
        this.vacanteRepository = vacanteRepository;
    }

    public List<EmpresaDTO> findAll() {
        final List<Empresa> empresas = empresaRepository.findAll(Sort.by("id"));
        return empresas.stream()
                .map(empresa -> mapToDTO(empresa, new EmpresaDTO()))
                .toList();
    }

    public EmpresaDTO get(final Integer id) {
        return empresaRepository.findById(id)
                .map(empresa -> mapToDTO(empresa, new EmpresaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final EmpresaDTO empresaDTO) {
        final Empresa empresa = new Empresa();
        mapToEntity(empresaDTO, empresa);
        return empresaRepository.save(empresa).getId();
    }

    public void update(final Integer id, final EmpresaDTO empresaDTO) {
        final Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(empresaDTO, empresa);
        empresaRepository.save(empresa);
    }

    public void delete(final Integer id) {
        empresaRepository.deleteById(id);
    }

    private EmpresaDTO mapToDTO(final Empresa empresa, final EmpresaDTO empresaDTO) {
        empresaDTO.setId(empresa.getId());
        empresaDTO.setRazonSocial(empresa.getRazonSocial());
        empresaDTO.setDireccionFiscal(empresa.getDireccionFiscal());
        empresaDTO.setPais(empresa.getPais());
        return empresaDTO;
    }

    private Empresa mapToEntity(final EmpresaDTO empresaDTO, final Empresa empresa) {
        empresa.setRazonSocial(empresaDTO.getRazonSocial());
        empresa.setDireccionFiscal(empresaDTO.getDireccionFiscal());
        empresa.setPais(empresaDTO.getPais());
        return empresa;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Vacante empresaVacante = vacanteRepository.findFirstByEmpresa(empresa);
        if (empresaVacante != null) {
            referencedWarning.setKey("empresa.vacante.empresa.referenced");
            referencedWarning.addParam(empresaVacante.getId());
            return referencedWarning;
        }
        return null;
    }

}
