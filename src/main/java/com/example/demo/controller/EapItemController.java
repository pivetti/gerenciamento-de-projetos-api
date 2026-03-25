package com.example.demo.controller;

import com.example.demo.dto.eap.EapItemRequestDto;
import com.example.demo.dto.eap.EapItemResponseDto;
import com.example.demo.service.EapItemService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eap-itens")
@RequiredArgsConstructor
public class EapItemController {

    private final EapItemService eapItemService;

    @GetMapping
    public ResponseEntity<List<EapItemResponseDto>> listarTodos() {
        return ResponseEntity.ok(eapItemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EapItemResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eapItemService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EapItemResponseDto> criar(@Valid @RequestBody EapItemRequestDto request) {
        EapItemResponseDto response = eapItemService.criar(request);
        return ResponseEntity.created(URI.create("/eap-itens/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EapItemResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody EapItemRequestDto request) {
        return ResponseEntity.ok(eapItemService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eapItemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
