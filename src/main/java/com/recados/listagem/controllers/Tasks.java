package com.recados.listagem.controllers;

import com.recados.listagem.Enums.EDone;
import com.recados.listagem.Enums.EFile;
import com.recados.listagem.database.DataBase;
import com.recados.listagem.dtos.AddTasks;
import com.recados.listagem.dtos.ErrorData;
import com.recados.listagem.dtos.UpdateTask;
import com.recados.listagem.models.Task;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("tasks")
public class Tasks {


    @GetMapping("/{id}")
    public ResponseEntity Tasks(@RequestParam(required = false) String title, @RequestParam(required = false) EFile file, @RequestParam(required = false) EDone done , @PathVariable UUID id){
        var userOptinal = DataBase.getUserById(id);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        var filtered = userOptinal.getTasks();

        if (title != null){
            filtered = filtered.stream().filter(task -> task.getTitle().contains(title)).toList();
        }
        if (file != null){
            filtered = filtered.stream().filter(task -> task.getFile().equals(file)).toList();
        }
        if (done != null){
            filtered = filtered.stream().filter(task -> task.getDone().equals(done)).toList();
        }


        return ResponseEntity.ok().body(filtered);

    }
    @PostMapping("/{id}")
    public ResponseEntity createTask(@PathVariable UUID id,@RequestBody @Valid AddTasks newTask){
        var userOptinal = DataBase.getUserById(id);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        if (newTask.message().length() < 5) return ResponseEntity.badRequest().body(new ErrorData("Titulo precisa conter 5 ou mais caracteres"));

        var task = new Task(newTask.title(), newTask.message());

        userOptinal.getTasks().add(task);
        return ResponseEntity.ok().body(userOptinal);
    }

    @DeleteMapping("/{idUser}/{idTask}")
    public ResponseEntity deteletTask(@PathVariable UUID idUser, @PathVariable UUID idTask){
        var userOptinal = DataBase.getUserById(idUser);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        var taskOptinal = userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findFirst();

        if (taskOptinal.isEmpty()) return ResponseEntity.badRequest().body(new ErrorData("Recado não encontrado"));

        userOptinal.getTasks().removeIf(task -> task.getId().equals(idTask));
        return ResponseEntity.ok().body(userOptinal);    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAllTasks(@PathVariable UUID id){
        var userOptinal = DataBase.getUserById(id);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        if (userOptinal.getTasks().stream().filter(task -> task.getId().equals(id)).findFirst().isEmpty())
            return  ResponseEntity.badRequest().body(new ErrorData("tarefa não encontrada"));

        userOptinal.getTasks().clear();
        return ResponseEntity.ok().body(userOptinal);    }


    @PutMapping("/{idUser}/{idTask}")
    public ResponseEntity updateTask(@RequestBody UpdateTask data, @PathVariable UUID idUser, @PathVariable UUID idTask){
        var userOptinal = DataBase.getUserById(idUser);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        if (userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findFirst().isEmpty())
            return  ResponseEntity.badRequest().body(new ErrorData("tarefa não encontrada"));

        userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findFirst().get().updateTask(data);
        return ResponseEntity.ok().body(userOptinal);    }

    @PutMapping("/file/{idUser}/{idTask}")
    public ResponseEntity fileTask(@PathVariable UUID idUser,@PathVariable UUID idTask){
        var userOptinal = DataBase.getUserById(idUser);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        if (userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findFirst().isEmpty())
            return  ResponseEntity.badRequest().body(new ErrorData("tarefa não encontrada"));

        userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findAny().get().file();
        return ResponseEntity.ok().body(userOptinal);
    }

    @PutMapping("/done/{idUser}/{idTask}")
    public ResponseEntity doneTask(@PathVariable UUID idUser,@PathVariable UUID idTask){
        var userOptinal = DataBase.getUserById(idUser);

        if(userOptinal == null) return ResponseEntity.badRequest().body(new ErrorData("Usuario invalido"));

        userOptinal.getTasks().stream().filter(task -> task.getId().equals(idTask)).findAny().get().done();
        return ResponseEntity.ok().body(userOptinal);
    }
}
