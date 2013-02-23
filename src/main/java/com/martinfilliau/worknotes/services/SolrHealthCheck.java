package com.martinfilliau.worknotes.services;

import com.yammer.metrics.core.HealthCheck;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.SolrPingResponse;

/**
 *
 * @author martinfilliau
 */
public class SolrHealthCheck extends HealthCheck {

    private final HttpSolrServer solr;
    
    public SolrHealthCheck(HttpSolrServer solr) {
        super("Solr");
        this.solr = solr;
    }
    
    @Override
    protected Result check() throws Exception {
        try {
            SolrPingResponse response = solr.ping();
            return Result.healthy();
        } catch(Exception ex) {
            return Result.unhealthy(ex);
        }
    }
    
}
