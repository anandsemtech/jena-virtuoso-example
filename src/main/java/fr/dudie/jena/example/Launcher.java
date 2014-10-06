package fr.dudie.jena.example;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.spi.OptionHandler;

public class Launcher {

    @Option(name = "-e", required = true)
    private ExampleList example;

    @Option(name = "--virt-host", metaVar="<hostname>")
    public String virtHost = "localhost";

    @Option(name = "--virt-port", metaVar="<port>")
    public Integer virtPort = 1111;

    @Option(name = "--virt-user", metaVar="<user>")
    public String virtUser = "dba";

    @Option(name = "--virt-pass", metaVar="<pass>")
    public String virtPass = "dba";

    @Option(name = "--virt-max-connections", metaVar="<maxConnections>")
    public int maxConnections = 4;
    

    @Option(name = "--thread-pool-size", metaVar="<threads>")
    public int threadPoolSize = 4;

    public static void main(final String[] args) {

        final Launcher launcher = new Launcher();
        final CmdLineParser parser = new CmdLineParser(launcher);

        try {
            parser.parseArgument(args);
        } catch (final CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.print("java -jar virtuoso-jena-example-x.y.z-jar-with-dependencies.jar");
            parser.printSingleLineUsage(System.err);
            System.err.println();
            System.exit(1);
        }

        System.out.printf("Begin %s\n", launcher.example);
        launcher.example.app.setLauncher(launcher);
        launcher.example.app.run();
        System.out.printf("End %s\n", launcher.example);
    }
}
