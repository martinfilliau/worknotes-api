package com.martinfilliau.worknotes.representations;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author martinfilliau
 */
public class NoteTest {
    
    
    @Test
    public void serializesToJSON() throws Exception {
        final Note note = new Note();
        note.setActivity("activity");
        note.setProject("project");
        note.setTask("task");
        assertThat("a Note can be serialized to JSON",
                asJson(note),
                is(equalTo(jsonFixture("fixtures/note.json"))));
    }

}
