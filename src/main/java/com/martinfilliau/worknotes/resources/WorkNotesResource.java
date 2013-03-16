package com.martinfilliau.worknotes.resources;

import com.martinfilliau.worknotes.representations.Note;
import com.martinfilliau.worknotes.views.NoteView;
import com.sun.jersey.api.NotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author martinfilliau
 */
@Path("/worknotes")
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

    @GET
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("*:*");
            QueryResponse response = solr.query(query);
            Iterator<SolrDocument> iter = response.getResults().iterator();

            SolrDocument doc;
            Note note;
            while (iter.hasNext()) {
                doc = iter.next();
                note = new Note();
                // TODO move this to Note, constructor from SolrDocument
                note.setActivity((String) doc.getFieldValue("activity"));
                note.setProject((String) doc.getFieldValue("project"));
                note.setTask((String) doc.getFieldValue("task"));
                note.setDate((Date) doc.getFieldValue("date"));
                note.setComments((String) doc.getFieldValue("comments"));
                notes.add(note);
            }
        } catch (SolrServerException ex) {
            Logger.getLogger(WorkNotesResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notes;
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public NoteView getById(@PathParam("id") String id) {
        try {
            SolrQuery query = new SolrQuery();
            query.setQuery("id:"+id);
            QueryResponse response = solr.query(query);
            Iterator<SolrDocument> iter = response.getResults().iterator();

            SolrDocument doc;
            Note note;
            while (iter.hasNext()) {
                doc = iter.next();
                note = new Note();
                // TODO move this to Note, constructor from SolrDocument
                note.setActivity((String) doc.getFieldValue("activity"));
                note.setProject((String) doc.getFieldValue("project"));
                note.setTask((String) doc.getFieldValue("task"));
                note.setDate((Date) doc.getFieldValue("date"));
                note.setComments((String) doc.getFieldValue("comments"));
                return new NoteView(note);
            }
        } catch (SolrServerException ex) {
            Logger.getLogger(WorkNotesResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new NotFoundException(id);
    }
}