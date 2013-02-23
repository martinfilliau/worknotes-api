package com.martinfilliau.worknotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import com.yammer.dropwizard.config.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author martinfilliau
 */
public class MainConfiguration extends Configuration {
    
    @NotEmpty
    @JsonProperty
    private String solrUrl = "http://localhost:8983/solr";
    
    @NotNull
    @JsonProperty
    @Valid
    private GitHubConfiguration github = new GitHubConfiguration();
    
    public String getSolrUrl() {
        return solrUrl;
    }
    
    public GitHubConfiguration getGithub() {
        return github;
    }

}
