package com.example.demo.service;

import com.example.demo.dto.eap.EapItemRequestDto;
import com.example.demo.dto.eap.EapItemResponseDto;
import com.example.demo.entity.EapItem;
import com.example.demo.repository.EapItemRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EapItemService {

    private final EapItemRepository eapItemRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

    public List<EapItemResponseDto> listarTodos() {
        return eapItemRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EapItemResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getEapItem(id));
    }

    public EapItemResponseDto criar(EapItemRequestDto request) {
        EapItem eapItem = EapItem.builder().build();
        preencherCampos(eapItem, request);
        return toResponse(eapItemRepository.save(eapItem));
    }

    public EapItemResponseDto atualizar(Long id, EapItemRequestDto request) {
        EapItem eapItem = lookupService.getEapItem(id);
        preencherCampos(eapItem, request);
        return toResponse(eapItemRepository.save(eapItem));
    }

    public EapItemResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        EapItem eapItem = lookupService.getEapItem(id);
        aplicarPatch(eapItem, updates);
        return toResponse(eapItemRepository.save(eapItem));
    }

    public void deletar(Long id) {
        eapItemRepository.delete(lookupService.getEapItem(id));
    }

    private void preencherCampos(EapItem eapItem, EapItemRequestDto request) {
        eapItem.setNome(request.getNome());
        eapItem.setDescricao(request.getDescricao());
        eapItem.setCodigo(request.getCodigo());
        eapItem.setNivel(request.getNivel());
        eapItem.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        eapItem.setItemPai(request.getItemPaiId() != null ? lookupService.getEapItem(request.getItemPaiId()) : null);
    }

    private void aplicarPatch(EapItem eapItem, Map<String, Object> updates) {
        if (updates.containsKey("nome")) {
            eapItem.setNome(patchFieldService.getString(updates, "nome"));
        }
        if (updates.containsKey("descricao")) {
            eapItem.setDescricao(patchFieldService.getString(updates, "descricao"));
        }
        if (updates.containsKey("codigo")) {
            eapItem.setCodigo(patchFieldService.getString(updates, "codigo"));
        }
        if (updates.containsKey("nivel")) {
            eapItem.setNivel(patchFieldService.getInteger(updates, "nivel"));
        }
        if (updates.containsKey("projetoId")) {
            eapItem.setProjeto(lookupService.getProjeto(patchFieldService.getLong(updates, "projetoId")));
        }
        if (updates.containsKey("itemPaiId")) {
            Long itemPaiId = patchFieldService.getLong(updates, "itemPaiId");
            eapItem.setItemPai(itemPaiId != null ? lookupService.getEapItem(itemPaiId) : null);
        }
    }

    private EapItemResponseDto toResponse(EapItem eapItem) {
        return EapItemResponseDto.builder()
                .id(eapItem.getId())
                .nome(eapItem.getNome())
                .descricao(eapItem.getDescricao())
                .codigo(eapItem.getCodigo())
                .nivel(eapItem.getNivel())
                .projetoId(eapItem.getProjeto().getId())
                .projetoNome(eapItem.getProjeto().getNome())
                .itemPaiId(eapItem.getItemPai() != null ? eapItem.getItemPai().getId() : null)
                .itemPaiNome(eapItem.getItemPai() != null ? eapItem.getItemPai().getNome() : null)
                .build();
    }
}
