package com.martinfilliau.worknotes.resources;

import com.martinfilliau.worknotes.representations.Note;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author martinfilliau
 */
@Path("/worknotes")
@Produces(MediaType.APPLICATION_JSON)
public class WorkNotesResource {
    
    public WorkNotesResource() {
        
    }
    
    @POST
    public void insertNote(@Valid Note note) {
        
    }
}
