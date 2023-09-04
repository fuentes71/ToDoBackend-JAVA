package com.recados.listagem.models;

import com.recados.listagem.dtos.UpdateTask;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class Profile {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private List<Task> tasks;


    public Profile(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        tasks = new ArrayList<>();
        id = UUID.randomUUID();
    }


}
