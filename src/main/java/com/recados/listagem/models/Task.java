package com.recados.listagem.models;

import com.recados.listagem.Enums.EDone;
import com.recados.listagem.Enums.EFile;
import com.recados.listagem.dtos.UpdateTask;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@AllArgsConstructor
@Getter
public class Task {

    private UUID id;

    private String title;

    private String message;

    private EFile file;

    private EDone done;
    public Task (String title ,String message) {
        this.message = message;
        this.title = title;
        id = UUID.randomUUID();
        file = EFile.NOTFILED;
        done = EDone.PRODUCTION;
    }



    public EFile file() {
        if (file.equals(EFile.NOTFILED)) return file = EFile.FILED;
        return file = EFile.NOTFILED;
    }

    public EDone done() {
        if (done.equals(EDone.PRODUCTION)) return done = EDone.DONE;
        return done = EDone.PRODUCTION;
    }

    public void updateTask(UpdateTask task){
        if (task.message() != null)
            message = task.message();


        if (task.title() != null)
            title = task.title();
    }
}
