package br.com.mapped.SeaMI.controller;

import br.com.mapped.SeaMI.dto.login.CadastroLoginDto;
import br.com.mapped.SeaMI.dto.login.DetalhesLoginDto;
import br.com.mapped.SeaMI.dto.relatorio.AtualizacaoRelatorioDto;
import br.com.mapped.SeaMI.dto.relatorio.CadastroRelatorioDto;
import br.com.mapped.SeaMI.dto.relatorio.DetalhesRelatorioDto;
import br.com.mapped.SeaMI.model.Login;
import br.com.mapped.SeaMI.model.Relatorio;
import br.com.mapped.SeaMI.repository.RelatorioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    //GET
    @GetMapping
    public ResponseEntity<List<DetalhesRelatorioDto>> get(Pageable pageable){
        var relatorio = relatorioRepository.findAll(pageable)
                .stream().map(DetalhesRelatorioDto::new).toList();
        return ResponseEntity.ok(relatorio);
    }

    //GET BY ID
    @GetMapping("{id}")
    public ResponseEntity<DetalhesRelatorioDto> get(@PathVariable("id")Long id){
        var relatorio = relatorioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhesRelatorioDto(relatorio));
    }


    //DELETE
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id")Long id){
        relatorioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //PUT
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesRelatorioDto> put(@PathVariable("id")Long id,
                                                    @RequestBody @Valid AtualizacaoRelatorioDto dto){
        var relatorio = relatorioRepository.getReferenceById(id);
        relatorio.atualizarInformacoesRelatorio(dto);
        return ResponseEntity.ok(new DetalhesRelatorioDto(relatorio));
    }

    //POST
    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesRelatorioDto> post(@RequestBody @Valid CadastroRelatorioDto relatorioDto,
                                                 UriComponentsBuilder uriBuilder){
        var relatorio = new Relatorio(relatorioDto);
        relatorioRepository.save(relatorio);
        var uri = uriBuilder.path("relatorios/{id}").buildAndExpand(relatorio.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesRelatorioDto(relatorio));
    }



}