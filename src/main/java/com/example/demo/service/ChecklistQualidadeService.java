package com.example.demo.service;

import com.example.demo.dto.checklist.ChecklistQualidadeRequestDto;
import com.example.demo.dto.checklist.ChecklistQualidadeResponseDto;
import com.example.demo.entity.ChecklistQualidade;
import com.example.demo.repository.ChecklistQualidadeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistQualidadeService {

    private final ChecklistQualidadeRepository checklistQualidadeRepository;
    private final EntityLookupService lookupService;

    public List<ChecklistQualidadeResponseDto> listarTodos() {
        return checklistQualidadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ChecklistQualidadeResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getChecklistQualidade(id));
    }

    public ChecklistQualidadeResponseDto criar(ChecklistQualidadeRequestDto request) {
        ChecklistQualidade checklistQualidade = ChecklistQualidade.builder().build();
        preencherCampos(checklistQualidade, request);
        return toResponse(checklistQualidadeRepository.save(checklistQualidade));
    }

    public ChecklistQualidadeResponseDto atualizar(Long id, ChecklistQualidadeRequestDto request) {
        ChecklistQualidade checklistQualidade = lookupService.getChecklistQualidade(id);
        preencherCampos(checklistQualidade, request);
        return toResponse(checklistQualidadeRepository.save(checklistQualidade));
    }

    public void deletar(Long id) {
        checklistQualidadeRepository.delete(lookupService.getChecklistQualidade(id));
    }

    private void preencherCampos(ChecklistQualidade checklistQualidade, ChecklistQualidadeRequestDto request) {
        checklistQualidade.setDescricao(request.getDescricao());
        checklistQualidade.setCriterio(request.getCriterio());
        checklistQualidade.setStatus(request.getStatus());
        checklistQualidade.setDataVerificacao(request.getDataVerificacao());
        checklistQualidade.setObservacoes(request.getObservacoes());
        checklistQualidade.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        checklistQualidade.setAtividade(
                request.getAtividadeId() != null ? lookupService.getAtividade(request.getAtividadeId()) : null);
    }

    private ChecklistQualidadeResponseDto toResponse(ChecklistQualidade checklistQualidade) {
        return ChecklistQualidadeResponseDto.builder()
                .id(checklistQualidade.getId())
                .descricao(checklistQualidade.getDescricao())
                .criterio(checklistQualidade.getCriterio())
                .status(checklistQualidade.getStatus())
                .dataVerificacao(checklistQualidade.getDataVerificacao())
                .observacoes(checklistQualidade.getObservacoes())
                .projetoId(checklistQualidade.getProjeto().getId())
                .projetoNome(checklistQualidade.getProjeto().getNome())
                .atividadeId(checklistQualidade.getAtividade() != null ? checklistQualidade.getAtividade().getId() : null)
                .atividadeTitulo(
                        checklistQualidade.getAtividade() != null ? checklistQualidade.getAtividade().getTitulo() : null)
                .build();
    }
}
