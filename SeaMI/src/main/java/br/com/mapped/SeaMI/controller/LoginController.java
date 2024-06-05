package br.com.mapped.SeaMI.controller;

import br.com.mapped.SeaMI.dto.amostraAgua.AtualizacaoAmostraAguaDto;
import br.com.mapped.SeaMI.dto.amostraAgua.CadastroAmostraAguaDto;
import br.com.mapped.SeaMI.dto.amostraAgua.DetalhesAmostraAguaDto;
import br.com.mapped.SeaMI.dto.login.AtualizacaoLoginDto;
import br.com.mapped.SeaMI.dto.login.CadastroLoginDto;
import br.com.mapped.SeaMI.dto.login.DetalhesLoginDto;
import br.com.mapped.SeaMI.model.AmostraAgua;
import br.com.mapped.SeaMI.model.Login;
import br.com.mapped.SeaMI.repository.AmostraAguaRepository;
import br.com.mapped.SeaMI.repository.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("logins")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    //GET
    @GetMapping
    public ResponseEntity<List<DetalhesLoginDto>> get(Pageable pageable){
        var login = loginRepository.findAll(pageable)
                .stream().map(DetalhesLoginDto::new).toList();
        return ResponseEntity.ok(login);
    }

    //GET BY ID
    @GetMapping("{id}")
    public ResponseEntity<DetalhesLoginDto> get(@PathVariable("id")Long id){
        var login = loginRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhesLoginDto(login));
    }


    //DELETE
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable("id")Long id){
        loginRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //PUT
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesLoginDto> put(@PathVariable("id")Long id,
                                                      @RequestBody @Valid AtualizacaoLoginDto dto){
        var login = loginRepository.getReferenceById(id);
        login.atualizarInformacoesLogin(dto);
        return ResponseEntity.ok(new DetalhesLoginDto(login));
    }

    //POST
    @PostMapping
    @Transactional
    public ResponseEntity<DetalhesLoginDto> post(@RequestBody @Valid CadastroLoginDto loginDto,
                                                       UriComponentsBuilder uriBuilder){
        var login = new Login(loginDto);
        loginRepository.save(login);
        var uri = uriBuilder.path("logins/{id}").buildAndExpand(login.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesLoginDto(login));
    }


}