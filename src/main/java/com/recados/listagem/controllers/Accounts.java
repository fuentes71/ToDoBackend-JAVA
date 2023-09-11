package com.recados.listagem.controllers;

import com.recados.listagem.database.DataBase;
import com.recados.listagem.dtos.CreateProfile;
import com.recados.listagem.dtos.ErrorData;
import com.recados.listagem.dtos.LoggedProfile;
import com.recados.listagem.models.Profile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class Accounts {


    @GetMapping
    public ResponseEntity accounts(){
        var data = DataBase.getProfiles();
        return ResponseEntity.ok().body(data);
    }
    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody @Valid LoggedProfile data){
        var validationUser = DataBase.getProfiles().stream().filter(user->
                 user.getEmail().equalsIgnoreCase(data.email())
        ).findFirst();

        if (validationUser.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorData("E-mail invalido"));}

        if (!validationUser.get().getPassword().equals(data.password())){
            return ResponseEntity.badRequest().body(new ErrorData("E-mail ou senha invalidos"));}

        return ResponseEntity.ok().body(validationUser.get());

    }

    @PostMapping("/singup")
    public ResponseEntity singUp(@RequestBody @Valid CreateProfile data) {
        if (DataBase.userExistByEmail(data.email())){
            return ResponseEntity.badRequest().body(new ErrorData("Usuario já cadastrado."));}

        if (!data.password().equals(data.rePassword())){
            return ResponseEntity.badRequest().body(new ErrorData("As senhas precisam ser iguais"));}

        if (data.password().length() <= 3){
            return ResponseEntity.badRequest().body(new ErrorData("Senha fraca. precisa ser mais forte"));}

        if (data.password().length() <= 5){
            return ResponseEntity.badRequest().body(new ErrorData("Senha media. precisa ser mais forte"));}

        var user = new Profile(
                data.name(),
                data.email(),
                data.password()
        );

        DataBase.addUser(user);

        return ResponseEntity.ok().body(user);

    }
}
