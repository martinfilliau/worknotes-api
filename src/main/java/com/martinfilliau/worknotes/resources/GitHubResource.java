package com.martinfilliau.worknotes.resources;

import com.martinfilliau.worknotes.config.GitHubConfiguration;
import com.martinfilliau.worknotes.representations.Note;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 *
 * @author martinfilliau
 */
@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
public class GitHubResource {
 
    private final HttpSolrServer solr;
    private final GitHubConfiguration config;
    
    public GitHubResource(HttpSolrServer solr, GitHubConfiguration config) {
        this.solr = solr;
        this.config = config;
    }
    
    @GET
    public Response loadCommits() throws IOException, SolrServerException {
        GitHubClient client = new GitHubClient();
        client.setCredentials(config.getUsername(), config.getPassword());
        
        CommitService commits = new CommitService(client);
        RepositoryService service = new RepositoryService();
        List<Repository> repos = new ArrayList<Repository>();
        for(String path : config.getRepositories()) {
            String[] splitted = path.split("/");
            repos.add(service.getRepository(splitted[0], splitted[1]));
        }
        
        List<RepositoryCommit> repoCommits;
        for (Repository repo : repos) {
            repoCommits = commits.getCommits(repo);
            for(RepositoryCommit commit : repoCommits) {
                if(commit.getCommit().getCommitter().getName().equals("Martin Filliau")) {
                    Note note = new Note();
                    note.setActivity("Dev");
                    note.setProject(repo.getName());
                    note.setComments(commit.getCommit().getMessage());
                    note.setDate(commit.getCommit().getCommitter().getDate());
                    note.setTask("DEV");
                    SolrInputDocument sid = note.getSolrDocument();
                    sid.addField("id", commit.getSha());
                    solr.add(sid);
                }
            }
        }
        solr.commit();

        return null;
    }
}
