package com.exemplo.pessoaapi.service;

import com.exemplo.pessoaapi.dto.PessoaDTO;
import com.exemplo.pessoaapi.exception.ResourceNotFoundException;
import com.exemplo.pessoaapi.model.Pessoa;
import com.exemplo.pessoaapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    private PessoaDTO toDto(Pessoa p) {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setDtNascimento(p.getDtNascimento());
        dto.setAtivo(p.isAtivo());
        return dto;
    }

    private Pessoa toEntity(PessoaDTO dto) {
        Pessoa p = new Pessoa();
        p.setId(dto.getId());
        p.setNome(dto.getNome());
        p.setDtNascimento(dto.getDtNascimento());
        p.setAtivo(dto.isAtivo());
        return p;
    }

    public Page<PessoaDTO> listar(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Pessoa> pageEnt = repository.findAllByAtivoTrue(pageable);
        return pageEnt.map(this::toDto);
    }

    public PessoaDTO buscarPorId(Long id) {
        Pessoa p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + id));
        if (!p.isAtivo()) {
            throw new ResourceNotFoundException("Pessoa não encontrada com id " + id);
        }
        return toDto(p);
    }

    public PessoaDTO criar(PessoaDTO dto) {
        Pessoa p = toEntity(dto);
        p.setId(null);
        Pessoa salvo = repository.save(p);
        return toDto(salvo);
    }

    public PessoaDTO atualizar(Long id, PessoaDTO dto) {
        Pessoa existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + id));
        existente.setNome(dto.getNome());
        existente.setDtNascimento(dto.getDtNascimento());
        existente.setAtivo(dto.isAtivo());
        Pessoa atualizado = repository.save(existente);
        return toDto(atualizado);
    }

    public void deletar(Long id) {
        Pessoa existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id " + id));
        repository.delete(existente);
    }
}
