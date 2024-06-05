package br.com.mapped.SeaMI.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroLoginDto (

        @NotBlank(message = "O Email não pode estar em branco")
        @Size(max = 100, message = "O Email deve ter no máximo 100 caracteres")
        @Email(message = "O Email deve ser válido")
        String email,

        @NotBlank(message = "A Senha não pode estar em branco")
        @Size(max = 15, message = "A Senha deve ter no máximo 15 caracteres")
        String senha
){
}
