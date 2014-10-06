package fr.dudie.jena.example;

import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class VirtuosoSingleConnectionInsertExample implements Example {

    private static final String GRAPH_NAME = "g://fr.dudie.jena/example";
    private String host;
    private int port;
    private String user;
    private String pass;

    @Override
    public void setLauncher(final Launcher l) {
        host = l.virtHost;
        port = l.virtPort;
        user = l.virtUser;
        pass = l.virtPass;
    }

    public void run() {

        final String url = String.format("jdbc:virtuoso://%s:%d", host, port);
        final Model m = VirtModel.openDatabaseModel(GRAPH_NAME, url, user, pass);

        for (int i = 0; i < 10000000; i++) {
            final Resource s = m.createResource("s://fr.dudie.jena/single");
            final Property p = m.createProperty("p://fr.dudie.jena/" + i);
            m.add(s, p, "value");
        }
    }

}
