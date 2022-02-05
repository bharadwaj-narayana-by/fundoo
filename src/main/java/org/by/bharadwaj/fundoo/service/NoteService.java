package org.by.bharadwaj.fundoo.service;

import org.by.bharadwaj.fundoo.dto.NoteDTO;
import org.by.bharadwaj.fundoo.entity.Note;

import java.util.List;

public interface NoteService {

    public Note createNote(NoteDTO noteDTO, String token);

    public Note updateNote(NoteDTO noteDTO, String token, Long id);

    public List<Note> getNotes(String token);
}
