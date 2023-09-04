package com.recados.listagem.database;

import com.recados.listagem.dtos.ErrorData;
import com.recados.listagem.models.Profile;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

public abstract class DataBase {

    @Getter
    private static ArrayList<Profile> profiles = new ArrayList<>();

    public static void addUser(@NotNull Profile Profile){
        if(Profile.getId() == null) throw new RuntimeException("Usuario invalido");

        profiles.add(Profile);
    }
    public static boolean userExistByEmail(String email){
        var userFiltered = profiles.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
        return userFiltered.isPresent();
    }

    public static Profile getUserById(UUID id) {
        var userFiltered = profiles.stream().filter(user -> user.getId().equals(id)).findFirst();

        return userFiltered.orElse(null);
    }


}
