package com.martinfilliau.worknotes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author martinfilliau
 */
public class GitHubConfiguration {
    
    @NotEmpty
    @JsonProperty
    private String username;
    
    @NotEmpty
    @JsonProperty
    private String password;
    
    @NotEmpty
    @JsonProperty
    private List<String> repositories;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRepositories() {
        return repositories;
    }
}
