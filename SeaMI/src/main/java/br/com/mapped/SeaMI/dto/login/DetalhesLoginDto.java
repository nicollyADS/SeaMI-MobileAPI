package br.com.mapped.SeaMI.dto.login;

import br.com.mapped.SeaMI.model.Login;

public record DetalhesLoginDto (Long id,String email, String senha){

    public DetalhesLoginDto(Login login){
        this(
                login.getId(), login.getEmail(), login.getSenha()
        );

    }
}
