package org.by.bharadwaj.fundoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String remainder;

    private String color;

    private Boolean isPinned;

    private Boolean isArchived;

    private Boolean isTrashed;
}
