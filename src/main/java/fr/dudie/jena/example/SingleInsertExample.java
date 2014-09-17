package fr.dudie.jena.example;

import java.io.File;
import java.io.IOException;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.tdb.TDBFactory;

public class SingleInsertExample implements Runnable {

    private static final String GRAPH_NAME = "g://fr.dudie.jena/example";
    private static final File workDir;
    static {
        try {
            workDir = File.createTempFile("jena-", ".tdb");
            workDir.delete();
            workDir.mkdir();
        } catch (IOException e) {
            throw new RuntimeException("can't create jena TDB working directory", e);
        }
    }

    public void run() {

        final Dataset dataset = TDBFactory.createDataset(workDir.getAbsolutePath());
        final Model m = dataset.getDefaultModel();

        dataset.begin(ReadWrite.WRITE);
        try {
            for (int i = 0; i < 100; i++) {
                final Resource s = new ResourceImpl("s://fr.dudie.jena/single");
                final Property p = m.createProperty("p://fr.dudie.jena/" + i);
                m.add(s, p, "value");
            }
            dataset.commit();
        } finally {
            dataset.end();
        }

        dataset.begin(ReadWrite.READ);
        final Query q = QueryFactory.create(String.format("select * from <%s> where { ?s ?p ?o }", GRAPH_NAME));
        try (final QueryExecution qexec = QueryExecutionFactory.create(q, m)) {
            final ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                final QuerySolution result = results.nextSolution();
                for (final String var : results.getResultVars()) {
                    System.out.printf("%s: %s,", var, result.get(var));
                }
                System.out.println();
            }
        }
    }
}
