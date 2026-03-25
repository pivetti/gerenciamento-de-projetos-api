package com.example.demo.service;

import com.example.demo.dto.usuario.UsuarioRequestDto;
import com.example.demo.dto.usuario.UsuarioResponseDto;
import com.example.demo.entity.Usuario;
import com.example.demo.enums.PerfilUsuario;
import com.example.demo.repository.UsuarioRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getUsuario(id));
    }

    public UsuarioResponseDto criar(UsuarioRequestDto request) {
        Usuario usuario = Usuario.builder().build();
        preencherCampos(usuario, request);
        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto request) {
        Usuario usuario = lookupService.getUsuario(id);
        preencherCampos(usuario, request);
        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        Usuario usuario = lookupService.getUsuario(id);
        aplicarPatch(usuario, updates);
        return toResponse(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        usuarioRepository.delete(lookupService.getUsuario(id));
    }

    private void preencherCampos(Usuario usuario, UsuarioRequestDto request) {
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setTelefone(request.getTelefone());
        usuario.setPerfil(request.getPerfil());
    }

    private void aplicarPatch(Usuario usuario, Map<String, Object> updates) {
        if (updates.containsKey("nome")) {
            usuario.setNome(patchFieldService.getString(updates, "nome"));
        }
        if (updates.containsKey("email")) {
            usuario.setEmail(patchFieldService.getString(updates, "email"));
        }
        if (updates.containsKey("senha")) {
            usuario.setSenha(patchFieldService.getString(updates, "senha"));
        }
        if (updates.containsKey("telefone")) {
            usuario.setTelefone(patchFieldService.getString(updates, "telefone"));
        }
        if (updates.containsKey("perfil")) {
            usuario.setPerfil(patchFieldService.getEnum(updates, "perfil", PerfilUsuario.class));
        }
    }

    private UsuarioResponseDto toResponse(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .perfil(usuario.getPerfil())
                .build();
    }
}
