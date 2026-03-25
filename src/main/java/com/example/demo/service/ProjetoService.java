package com.example.demo.service;

import com.example.demo.dto.projeto.ProjetoRequestDto;
import com.example.demo.dto.projeto.ProjetoResponseDto;
import com.example.demo.entity.Projeto;
import com.example.demo.repository.ProjetoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final EntityLookupService lookupService;

    public List<ProjetoResponseDto> listarTodos() {
        return projetoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProjetoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getProjeto(id));
    }

    public ProjetoResponseDto criar(ProjetoRequestDto request) {
        Projeto projeto = Projeto.builder().build();
        preencherCampos(projeto, request);
        return toResponse(projetoRepository.save(projeto));
    }

    public ProjetoResponseDto atualizar(Long id, ProjetoRequestDto request) {
        Projeto projeto = lookupService.getProjeto(id);
        preencherCampos(projeto, request);
        return toResponse(projetoRepository.save(projeto));
    }

    public void deletar(Long id) {
        projetoRepository.delete(lookupService.getProjeto(id));
    }

    private void preencherCampos(Projeto projeto, ProjetoRequestDto request) {
        projeto.setNome(request.getNome());
        projeto.setDescricao(request.getDescricao());
        projeto.setObjetivo(request.getObjetivo());
        projeto.setStatus(request.getStatus());
        projeto.setPrioridade(request.getPrioridade());
        projeto.setDataInicio(request.getDataInicio());
        projeto.setDataFim(request.getDataFim());
        projeto.setOrcamentoPrevisto(request.getOrcamentoPrevisto());
        projeto.setPercentualConcluido(request.getPercentualConcluido());
    }

    private ProjetoResponseDto toResponse(Projeto projeto) {
        return ProjetoResponseDto.builder()
                .id(projeto.getId())
                .nome(projeto.getNome())
                .descricao(projeto.getDescricao())
                .objetivo(projeto.getObjetivo())
                .status(projeto.getStatus())
                .prioridade(projeto.getPrioridade())
                .dataInicio(projeto.getDataInicio())
                .dataFim(projeto.getDataFim())
                .orcamentoPrevisto(projeto.getOrcamentoPrevisto())
                .percentualConcluido(projeto.getPercentualConcluido())
                .build();
    }
}
