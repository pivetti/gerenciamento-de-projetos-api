package com.example.demo.service;

import com.example.demo.dto.atividade.AtividadeRequestDto;
import com.example.demo.dto.atividade.AtividadeResponseDto;
import com.example.demo.entity.Atividade;
import com.example.demo.repository.AtividadeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final EntityLookupService lookupService;

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
