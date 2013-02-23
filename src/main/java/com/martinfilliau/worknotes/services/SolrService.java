package com.martinfilliau.worknotes.services;

import com.yammer.dropwizard.lifecycle.Managed;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 *
 * @author martinfilliau
 */
public class SolrService implements Managed {

    private final HttpSolrServer solr;
    
    public SolrService(HttpSolrServer solr) {
        this.solr = solr;
    }
    
    public void start() throws Exception {

    }

    public void stop() throws Exception {
        this.solr.commit();
    }
    
}
