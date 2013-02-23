package com.martinfilliau.worknotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import com.yammer.dropwizard.config.Configuration;

/**
 *
 * @author martinfilliau
 */
public class MainConfiguration extends Configuration {
    
    @NotEmpty
    @JsonProperty
    private String solrUrl = "localhost:8983";
    
    public String getSolrUrl() {
        return solrUrl;
    }

}
