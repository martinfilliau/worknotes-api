package com.martinfilliau.worknotes.views;

import com.martinfilliau.worknotes.representations.Note;
import com.yammer.dropwizard.views.View;

/**
 *
 * @author martinfilliau
 */
public class NoteView extends View {
    private final Note note;
    
    public NoteView(Note note) {
        super("note.mustache");
        this.note = note;
    }
    
    public Note getNote() {
        return note;
    }
}
