package com.parse.starter.util;

import java.util.HashMap;


public class ParseErros {
    //Mensagens de erro

    private HashMap<Integer, String> erros;

    public ParseErros() {
        //Configurar mensagens de erro
        this.erros = new HashMap<>();
        this.erros.put(202, "Este nome de usuário já esta sendo utilizado! ");
        this.erros.put(201, "Não preencheu a senha! ");
        this.erros.put(203, "Este email já esta sendo utilizado ");

    }

    public String getErro (int codErro) {
        return this.erros.get(codErro);
    }
}