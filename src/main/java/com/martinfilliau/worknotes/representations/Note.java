package com.martinfilliau.worknotes.representations;

import java.util.Date;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author martinfilliau
 */
public class Note {
    
    private String activity;
    private String project;
    private String task;
    private String comments;
    private Date date;

    /**
     * Get this representation as a Solr document
     * @return SolrInputDocument containing this representation
     */
    public SolrInputDocument getSolrDocument() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("activity", activity);
        document.addField("project", project);
        document.addField("task", task);
        document.addField("comments", comments);
        document.addField("date", date);
        return document;
    }
    
    /* GETTERS and SETTERS */
    
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
     