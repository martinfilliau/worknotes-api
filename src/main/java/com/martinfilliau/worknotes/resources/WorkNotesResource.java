package com.martinfilliau.worknotes.resources;

import com.martinfilliau.worknotes.representations.Note;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author martinfilliau
 */
@Path("/worknotes")
@Produces(MediaType.APPLICATION_JSON)
public class WorkNotesResource {
    
    private final HttpSolrServer solr;
    
    public WorkNotesResource(HttpSolrServer solr) {
        this.solr = solr;
    }
    
    @POST
    public Note insertNote(@Valid Note note) {
        try {
            String uuid = UUID.randomUUID().toString();
            SolrInputDocument doc = note.getSolrDocument();
            doc.addField("id", uuid);
            solr.add(doc);
            solr.commit();
        } catch (SolrServerException ex) {
            Logger.getLogger(WorkNotesResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WorkNotesResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return note;
    }
}
