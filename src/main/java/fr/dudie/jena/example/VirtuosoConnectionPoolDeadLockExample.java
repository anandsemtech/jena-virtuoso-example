package fr.dudie.jena.example;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sql.ConnectionPoolDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import virtuoso.jdbc4.VirtuosoConnectionPoolDataSource;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import fr.dudie.jena.example.concurrent.WaitAndAddTaskLaterPolicy;

public class VirtuosoConnectionPoolDeadLockExample implements Example {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtuosoConnectionPoolDeadLockExample.class);

    private static final String GRAPH_NAME = "g://fr.dudie.jena/example";
    private String host;
    private int port;
    private String user;
    private String pass;
    private int maxConnections;
    private int threads;

    private VirtuosoConnectionPoolDataSource ds;

    private ThreadPoolExecutor executor;

    @Override
    public void setLauncher(final Launcher l) {
        host = l.virtHost;
        port = l.virtPort;
        user = l.virtUser;
        pass = l.virtPass;
        threads = l.threadPoolSize;
    }

    @Override
    public void run() {

        // initialize virtuoso connection pool
        ds = new VirtuosoConnectionPoolDataSource();
        ds.setUser(user);
        ds.setPwdClear(pass);
        ds.setPortNumber(port);
        ds.setServerName(host);
        try {
            ds.setMaxPoolSize(maxConnections);
        } catch (final SQLException e) {
            throw new RuntimeException("Unable to configure virtuoso connection pool size", e);
        }

        // initialize the thread pool
        // We use a bounded ArrayBlockingQueue with a custom RejectedExecutionHandler to avoid memory overflow creating
        // the tasks.
        executor = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(500), new WaitAndAddTaskLaterPolicy());

        for (int i = 0; i < 1_000_000; i++) {
            executor.execute(new TripleInsertion(i, ds));
        }

        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TripleInsertion implements Runnable {

        private final int id;
        private final ConnectionPoolDataSource ds;

        public TripleInsertion(final int id, final VirtuosoConnectionPoolDataSource ds) {
            this.id = id;
            this.ds = ds;
        }

        @Override
        public void run() {
            LOGGER.info("task#{} START", id);
            VirtGraph g = null;
            try {
                g = new VirtGraph(GRAPH_NAME, ds);
                Model m = null;
                try {
                    m = new VirtModel(g);
                    final Resource s = m.createResource("s://fr.dudie.jena/pool_" + id);
                    final Property p = m.createProperty("p://fr.dudie.jena/id");
                    m.add(s, p, String.valueOf(id));
                } finally {
                    if (null != m) {
                        m.close();
                    }
                }
            } finally {
                if (null != g) {
                    g.close();
                }
            }
            LOGGER.info("task#{} END", id);
        }
    }
}
