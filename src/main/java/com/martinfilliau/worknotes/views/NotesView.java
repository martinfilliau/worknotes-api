package com.martinfilliau.worknotes.views;

import com.martinfilliau.worknotes.representations.Note;
import com.yammer.dropwizard.views.View;
import java.util.List;

/**
 *
 * @author martinfilliau
 */
public class NotesView extends View {
    
    private final List<Note> notes;
    
    public NotesView(List<Note> notes) {
        super("notes.mustache");
        this.notes = notes;
    }
    
    public List<Note> getNotes() {
        return notes;
    }
}
