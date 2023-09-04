package com.recados.listagem.dtos;

import jakarta.validation.constraints.NotEmpty;



public record AddTasks(

        @NotEmpty
        String title,
        @NotEmpty
        String message

) {}
