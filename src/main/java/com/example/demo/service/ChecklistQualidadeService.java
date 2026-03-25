package com.example.demo.service;

import com.example.demo.dto.checklist.ChecklistQualidadeRequestDto;
import com.example.demo.dto.checklist.ChecklistQualidadeResponseDto;
import com.example.demo.entity.ChecklistQualidade;
import com.example.demo.enums.StatusChecklist;
import com.example.demo.repository.ChecklistQualidadeRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistQualidadeService {

    private final ChecklistQualidadeRepository checklistQualidadeRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

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

    public ChecklistQualidadeResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        ChecklistQualidade checklistQualidade = lookupService.getChecklistQualidade(id);
        aplicarPatch(checklistQualidade, updates);
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

    private void aplicarPatch(ChecklistQualidade checklistQualidade, Map<String, Object> updates) {
        if (updates.containsKey("descricao")) {
            checklistQualidade.setDescricao(patchFieldService.getString(updates, "descricao"));
        }
        if (updates.containsKey("criterio")) {
            checklistQualidade.setCriterio(patchFieldService.getString(updates, "criterio"));
        }
        if (updates.containsKey("status")) {
            checklistQualidade.setStatus(patchFieldService.getEnum(updates, "status", StatusChecklist.class));
        }
        if (updates.containsKey("dataVerificacao")) {
            checklistQualidade.setDataVerificacao(patchFieldService.getLocalDate(updates, "dataVerificacao"));
        }
        if (updates.containsKey("observacoes")) {
            checklistQualidade.setObservacoes(patchFieldService.getString(updates, "observacoes"));
        }
        if (updates.containsKey("projetoId")) {
            checklistQualidade.setProjeto(lookupService.getProjeto(patchFieldService.getLong(updates, "projetoId")));
        }
        if (updates.containsKey("atividadeId")) {
            Long atividadeId = patchFieldService.getLong(updates, "atividadeId");
            checklistQualidade.setAtividade(atividadeId != null ? lookupService.getAtividade(atividadeId) : null);
        }
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
