package com.example.demo.service;

import com.example.demo.dto.custo.CustoRequestDto;
import com.example.demo.dto.custo.CustoResponseDto;
import com.example.demo.entity.Custo;
import com.example.demo.enums.TipoCusto;
import com.example.demo.repository.CustoRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustoService {

    private final CustoRepository custoRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

    public List<CustoResponseDto> listarTodos() {
        return custoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CustoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getCusto(id));
    }

    public CustoResponseDto criar(CustoRequestDto request) {
        Custo custo = Custo.builder().build();
        preencherCampos(custo, request);
        return toResponse(custoRepository.save(custo));
    }

    public CustoResponseDto atualizar(Long id, CustoRequestDto request) {
        Custo custo = lookupService.getCusto(id);
        preencherCampos(custo, request);
        return toResponse(custoRepository.save(custo));
    }

    public CustoResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        Custo custo = lookupService.getCusto(id);
        aplicarPatch(custo, updates);
        return toResponse(custoRepository.save(custo));
    }

    public void deletar(Long id) {
        custoRepository.delete(lookupService.getCusto(id));
    }

    private void preencherCampos(Custo custo, CustoRequestDto request) {
        custo.setDescricao(request.getDescricao());
        custo.setTipo(request.getTipo());
        custo.setValorPrevisto(request.getValorPrevisto());
        custo.setValorReal(request.getValorReal());
        custo.setDataLancamento(request.getDataLancamento());
        custo.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        custo.setAtividade(request.getAtividadeId() != null ? lookupService.getAtividade(request.getAtividadeId()) : null);
        custo.setRecurso(request.getRecursoId() != null ? lookupService.getRecurso(request.getRecursoId()) : null);
    }

    private void aplicarPatch(Custo custo, Map<String, Object> updates) {
        if (updates.containsKey("descricao")) {
            custo.setDescricao(patchFieldService.getString(updates, "descricao"));
        }
        if (updates.containsKey("tipo")) {
            custo.setTipo(patchFieldService.getEnum(updates, "tipo", TipoCusto.class));
        }
        if (updates.containsKey("valorPrevisto")) {
            custo.setValorPrevisto(patchFieldService.getBigDecimal(updates, "valorPrevisto"));
        }
        if (updates.containsKey("valorReal")) {
            custo.setValorReal(patchFieldService.getBigDecimal(updates, "valorReal"));
        }
        if (updates.containsKey("dataLancamento")) {
            custo.setDataLancamento(patchFieldService.getLocalDate(updates, "dataLancamento"));
        }
        if (updates.containsKey("projetoId")) {
            custo.setProjeto(lookupService.getProjeto(patchFieldService.getLong(updates, "projetoId")));
        }
        if (updates.containsKey("atividadeId")) {
            Long atividadeId = patchFieldService.getLong(updates, "atividadeId");
            custo.setAtividade(atividadeId != null ? lookupService.getAtividade(atividadeId) : null);
        }
        if (updates.containsKey("recursoId")) {
            Long recursoId = patchFieldService.getLong(updates, "recursoId");
            custo.setRecurso(recursoId != null ? lookupService.getRecurso(recursoId) : null);
        }
    }

    private CustoResponseDto toResponse(Custo custo) {
        return CustoResponseDto.builder()
                .id(custo.getId())
                .descricao(custo.getDescricao())
                .tipo(custo.getTipo())
                .valorPrevisto(custo.getValorPrevisto())
                .valorReal(custo.getValorReal())
                .dataLancamento(custo.getDataLancamento())
                .projetoId(custo.getProjeto().getId())
                .projetoNome(custo.getProjeto().getNome())
                .atividadeId(custo.getAtividade() != null ? custo.getAtividade().getId() : null)
                .atividadeTitulo(custo.getAtividade() != null ? custo.getAtividade().getTitulo() : null)
                .recursoId(custo.getRecurso() != null ? custo.getRecurso().getId() : null)
                .recursoNome(custo.getRecurso() != null ? custo.getRecurso().getNome() : null)
                .build();
    }
}
