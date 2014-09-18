package fr.dudie.jena.example;

import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;

public class SingleInsertVirtuosoExample implements Runnable {

    private static final String GRAPH_NAME = "g://fr.dudie.jena/example";
    private static final String HOST = "jdbc:virtuoso://localhost:49153";
    private static final String USER = "dba";
    private static final String PASS = "dba";
    
    public void run() {

        final Model m =VirtModel.openDatabaseModel(GRAPH_NAME, HOST, USER, PASS);

        for (int i = 0; i < 10000000; i++) {
            final Resource s = new ResourceImpl("s://fr.dudie.jena/single");
            final Property p = m.createProperty("p://fr.dudie.jena/" + i);
            m.add(s, p, "value");
        }
    }
}
