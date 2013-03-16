package com.martinfilliau.worknotes.cli;

import com.martinfilliau.worknotes.config.GitHubConfiguration;
import com.martinfilliau.worknotes.config.MainConfiguration;
import com.martinfilliau.worknotes.representations.Note;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author martinfilliau
 */
public class ImportGithubCommand extends ConfiguredCommand<MainConfiguration> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportGithubCommand.class);

    public ImportGithubCommand() {
        super("github", "Import GitHub activities");
    }
    
    @Override
    protected void run(Bootstrap<MainConfiguration> bootstrap,
                        Namespace namespace, 
                        MainConfiguration configuration) throws Exception {
        HttpSolrServer solr = new HttpSolrServer(configuration.getSolrUrl());
        GitHubClient client = new GitHubClient();
        client.setCredentials(configuration.getGithub().getUsername(),
                configuration.getGithub().getPassword());
        
        CommitService commits = new CommitService(client);
        RepositoryService service = new RepositoryService();
        List<Repository> repos = new ArrayList<Repository>();
        for(String path : configuration.getGithub().getRepositories()) {
            String[] splitted = path.split("/");
            repos.add(service.getRepository(splitted[0], splitted[1]));
            LOGGER.info("Repo: " + splitted[0] + " / " + splitted[1]);
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
        
    }
    
}
