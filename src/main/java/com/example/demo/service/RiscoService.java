package com.example.demo.service;

import com.example.demo.dto.risco.RiscoRequestDto;
import com.example.demo.dto.risco.RiscoResponseDto;
import com.example.demo.entity.Risco;
import com.example.demo.repository.RiscoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiscoService {

    private final RiscoRepository riscoRepository;
    private final EntityLookupService lookupService;

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
