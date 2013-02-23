package com.martinfilliau.worknotes;

import com.martinfilliau.worknotes.config.MainConfiguration;
import com.martinfilliau.worknotes.resources.WorkNotesResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

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
        environment.addResource(new WorkNotesResource());
    }
    
    public static void main(String[] args) throws Exception {
        new WorkNotesService().run(args);
    }
}
