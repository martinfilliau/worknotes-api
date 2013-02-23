package com.martinfilliau.worknotes;

import com.martinfilliau.worknotes.config.MainConfiguration;
import com.martinfilliau.worknotes.resources.WorkNotesResource;
import com.martinfilliau.worknotes.services.SolrHealthCheck;
import com.martinfilliau.worknotes.services.SolrService;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 *
 * @author martinfilliau
 */
public class WorkNotesService extends Service<MainConfiguration> {

    @Override
    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.setName("WorkNotes");
    }

    @Override
    public void run(MainConfiguration configuration, Environment environment) throws Exception {
        final HttpSolrServer solr = new HttpSolrServer(configuration.getSolrUrl());
        
        environment.addHealthCheck(new SolrHealthCheck(solr));
        environment.manage(new SolrService(solr));
        environment.addResource(new WorkNotesResource(solr));
    }
    
    public static void main(String[] args) throws Exception {
        new WorkNotesService().run(args);
    }
}
