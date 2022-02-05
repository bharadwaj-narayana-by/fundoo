package org.by.bharadwaj.fundoo.service;

import org.by.bharadwaj.fundoo.dto.NoteDTO;
import org.by.bharadwaj.fundoo.entity.Note;
import org.by.bharadwaj.fundoo.entity.User;
import org.by.bharadwaj.fundoo.exception.FundooException;
import org.by.bharadwaj.fundoo.repository.NoteRepository;
import org.by.bharadwaj.fundoo.repository.UserRepository;
import org.by.bharadwaj.fundoo.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNote(NoteDTO noteDTO, String token) {
        Long userId = getIdFromToken(token);
        User user = getUserFromId(userId);
        Note note = new Note();
        BeanUtils.copyProperties(noteDTO,note);
        note = noteRepository.save(note);
        user.getNotes().add(note);
        userRepository.save(user);
        return note;
    }

    @Override
    public Note updateNote(NoteDTO noteDTO, String token, Long id) {
        Long userId = getIdFromToken(token);
        //to check if user is valid
        User user = getUserFromId(userId);
        //Note note = noteRepository.findById(id).orElseThrow(()->new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
        Note note = user.getNotes().stream().filter(note1 -> note1.getId().equals(id)).findFirst()
                .orElseThrow(()->new FundooException(HttpStatus.NOT_FOUND.value(), "Note not found"));
        BeanUtils.copyProperties(noteDTO,note);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNotes(String token) {
        Long userId = getIdFromToken(token);
        User user = getUserFromId(userId);
        return user.getNotes();
    }


    private Long getIdFromToken(String token) {
        return tokenUtil.decodeToken(token);
    }

    private User getUserFromId(Long userId) {
        return userRepository
                .findById(userId).orElseThrow(()->new FundooException(HttpStatus.NOT_FOUND.value(), "User not found"));

    }
}
