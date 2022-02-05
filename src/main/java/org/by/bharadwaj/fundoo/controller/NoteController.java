package org.by.bharadwaj.fundoo.controller;

import org.by.bharadwaj.fundoo.dto.NoteDTO;
import org.by.bharadwaj.fundoo.entity.Note;
import org.by.bharadwaj.fundoo.exception.FundooException;
import org.by.bharadwaj.fundoo.response.Response;
import org.by.bharadwaj.fundoo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @PostMapping
    public ResponseEntity<Response> crateNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader String token,
                                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new FundooException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Note note =  noteService.createNote(noteDTO,token);
        return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(),"Note Created successfully",note),
                HttpStatus.CREATED);
    }



    @PutMapping(path = "/{id}")
    public ResponseEntity<Response> updateNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader String token,@PathVariable Long id,
                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new FundooException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Note note =  noteService.updateNote(noteDTO,token, id);
        return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(),"Note updated successfully",note),
                HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Response> getNotes(@RequestHeader String token) {
        return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "User Retrived Successfully"
                ,noteService.getNotes(token)),HttpStatus.OK);
    }
}
