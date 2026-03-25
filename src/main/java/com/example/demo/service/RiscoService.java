package com.example.demo.service;

import com.example.demo.dto.risco.RiscoRequestDto;
import com.example.demo.dto.risco.RiscoResponseDto;
import com.example.demo.entity.Risco;
import com.example.demo.enums.CategoriaRisco;
import com.example.demo.enums.StatusRisco;
import com.example.demo.repository.RiscoRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiscoService {

    private final RiscoRepository riscoRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

    public List<RiscoResponseDto> listarTodos() {
        return riscoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RiscoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getRisco(id));
    }

    public RiscoResponseDto criar(RiscoRequestDto request) {
        Risco risco = Risco.builder().build();
        preencherCampos(risco, request);
        return toResponse(riscoRepository.save(risco));
    }

    public RiscoResponseDto atualizar(Long id, RiscoRequestDto request) {
        Risco risco = lookupService.getRisco(id);
        preencherCampos(risco, request);
        return toResponse(riscoRepository.save(risco));
    }

    public RiscoResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        Risco risco = lookupService.getRisco(id);
        aplicarPatch(risco, updates);
        return toResponse(riscoRepository.save(risco));
    }

    public void deletar(Long id) {
        riscoRepository.delete(lookupService.getRisco(id));
    }

    private void preencherCampos(Risco risco, RiscoRequestDto request) {
        risco.setTitulo(request.getTitulo());
        risco.setDescricao(request.getDescricao());
        risco.setCategoria(request.getCategoria());
        risco.setProbabilidade(request.getProbabilidade());
        risco.setImpacto(request.getImpacto());
        risco.setCriticidade(request.getCriticidade());
        risco.setStatus(request.getStatus());
        risco.setEstrategiaResposta(request.getEstrategiaResposta());
        risco.setPlanoMitigacao(request.getPlanoMitigacao());
        risco.setProjeto(lookupService.getProjeto(request.getProjetoId()));
    }

    private void aplicarPatch(Risco risco, Map<String, Object> updates) {
        if (updates.containsKey("titulo")) {
            risco.setTitulo(patchFieldService.getString(updates, "titulo"));
        }
        if (updates.containsKey("descricao")) {
            risco.setDescricao(patchFieldService.getString(updates, "descricao"));
        }
        if (updates.containsKey("categoria")) {
            risco.setCategoria(patchFieldService.getEnum(updates, "categoria", CategoriaRisco.class));
        }
        if (updates.containsKey("probabilidade")) {
            risco.setProbabilidade(patchFieldService.getInteger(updates, "probabilidade"));
        }
        if (updates.containsKey("impacto")) {
            risco.setImpacto(patchFieldService.getInteger(updates, "impacto"));
        }
        if (updates.containsKey("criticidade")) {
            risco.setCriticidade(patchFieldService.getInteger(updates, "criticidade"));
        }
        if (updates.containsKey("status")) {
            risco.setStatus(patchFieldService.getEnum(updates, "status", StatusRisco.class));
        }
        if (updates.containsKey("estrategiaResposta")) {
            risco.setEstrategiaResposta(patchFieldService.getString(updates, "estrategiaResposta"));
        }
        if (updates.containsKey("planoMitigacao")) {
            risco.setPlanoMitigacao(patchFieldService.getString(updates, "planoMitigacao"));
        }
        if (updates.containsKey("projetoId")) {
            risco.setProjeto(lookupService.getProjeto(patchFieldService.getLong(updates, "projetoId")));
        }
    }

    private RiscoResponseDto toResponse(Risco risco) {
        return RiscoResponseDto.builder()
                .id(risco.getId())
                .titulo(risco.getTitulo())
                .descricao(risco.getDescricao())
                .categoria(risco.getCategoria())
                .probabilidade(risco.getProbabilidade())
                .impacto(risco.getImpacto())
                .criticidade(risco.getCriticidade())
                .status(risco.getStatus())
                .estrategiaResposta(risco.getEstrategiaResposta())
                .planoMitigacao(risco.getPlanoMitigacao())
                .projetoId(risco.getProjeto().getId())
                .projetoNome(risco.getProjeto().getNome())
                .build();
    }
}
