package com.example.demo.service;

import com.example.demo.dto.atividade.AtividadeRequestDto;
import com.example.demo.dto.atividade.AtividadeResponseDto;
import com.example.demo.entity.Atividade;
import com.example.demo.enums.Prioridade;
import com.example.demo.enums.StatusAtividade;
import com.example.demo.repository.AtividadeRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

    public List<AtividadeResponseDto> listarTodos() {
        return atividadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AtividadeResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getAtividade(id));
    }

    public AtividadeResponseDto criar(AtividadeRequestDto request) {
        Atividade atividade = Atividade.builder().build();
        preencherCampos(atividade, request);
        return toResponse(atividadeRepository.save(atividade));
    }

    public AtividadeResponseDto atualizar(Long id, AtividadeRequestDto request) {
        Atividade atividade = lookupService.getAtividade(id);
        preencherCampos(atividade, request);
        return toResponse(atividadeRepository.save(atividade));
    }

    public AtividadeResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        Atividade atividade = lookupService.getAtividade(id);
        aplicarPatch(atividade, updates);
        return toResponse(atividadeRepository.save(atividade));
    }

    public void deletar(Long id) {
        atividadeRepository.delete(lookupService.getAtividade(id));
    }

    private void preencherCampos(Atividade atividade, AtividadeRequestDto request) {
        atividade.setTitulo(request.getTitulo());
        atividade.setDescricao(request.getDescricao());
        atividade.setStatus(request.getStatus());
        atividade.setPrioridade(request.getPrioridade());
        atividade.setDataInicio(request.getDataInicio());
        atividade.setPrazo(request.getPrazo());
        atividade.setDataConclusao(request.getDataConclusao());
        atividade.setPercentualConclusao(request.getPercentualConclusao());
        atividade.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        atividade.setEapItem(request.getEapItemId() != null ? lookupService.getEapItem(request.getEapItemId()) : null);
        atividade.setResponsavel(
                request.getResponsavelId() != null ? lookupService.getParticipante(request.getResponsavelId()) : null);
    }

    private void aplicarPatch(Atividade atividade, Map<String, Object> updates) {
        if (updates.containsKey("titulo")) {
            atividade.setTitulo(patchFieldService.getString(updates, "titulo"));
        }
        if (updates.containsKey("descricao")) {
            atividade.setDescricao(patchFieldService.getString(updates, "descricao"));
        }
        if (updates.containsKey("status")) {
            atividade.setStatus(patchFieldService.getEnum(updates, "status", StatusAtividade.class));
        }
        if (updates.containsKey("prioridade")) {
            atividade.setPrioridade(patchFieldService.getEnum(updates, "prioridade", Prioridade.class));
        }
        if (updates.containsKey("dataInicio")) {
            atividade.setDataInicio(patchFieldService.getLocalDate(updates, "dataInicio"));
        }
        if (updates.containsKey("prazo")) {
            atividade.setPrazo(patchFieldService.getLocalDate(updates, "prazo"));
        }
        if (updates.containsKey("dataConclusao")) {
            atividade.setDataConclusao(patchFieldService.getLocalDate(updates, "dataConclusao"));
        }
        if (updates.containsKey("percentualConclusao")) {
            atividade.setPercentualConclusao(patchFieldService.getInteger(updates, "percentualConclusao"));
        }
        if (updates.containsKey("projetoId")) {
            atividade.setProjeto(lookupService.getProjeto(patchFieldService.getLong(updates, "projetoId")));
        }
        if (updates.containsKey("eapItemId")) {
            Long eapItemId = patchFieldService.getLong(updates, "eapItemId");
            atividade.setEapItem(eapItemId != null ? lookupService.getEapItem(eapItemId) : null);
        }
        if (updates.containsKey("responsavelId")) {
            Long responsavelId = patchFieldService.getLong(updates, "responsavelId");
            atividade.setResponsavel(responsavelId != null ? lookupService.getParticipante(responsavelId) : null);
        }
    }

    private AtividadeResponseDto toResponse(Atividade atividade) {
        return AtividadeResponseDto.builder()
                .id(atividade.getId())
                .titulo(atividade.getTitulo())
                .descricao(atividade.getDescricao())
                .status(atividade.getStatus())
                .prioridade(atividade.getPrioridade())
                .dataInicio(atividade.getDataInicio())
                .prazo(atividade.getPrazo())
                .dataConclusao(atividade.getDataConclusao())
                .percentualConclusao(atividade.getPercentualConclusao())
                .projetoId(atividade.getProjeto().getId())
                .projetoNome(atividade.getProjeto().getNome())
                .eapItemId(atividade.getEapItem() != null ? atividade.getEapItem().getId() : null)
                .eapItemNome(atividade.getEapItem() != null ? atividade.getEapItem().getNome() : null)
                .responsavelId(atividade.getResponsavel() != null ? atividade.getResponsavel().getId() : null)
                .responsavelNome(atividade.getResponsavel() != null ? atividade.getResponsavel().getUsuario().getNome() : null)
                .build();
    }
}
