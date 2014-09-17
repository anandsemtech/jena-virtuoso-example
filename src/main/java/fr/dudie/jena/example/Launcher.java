package fr.dudie.jena.example;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Launcher {

    @Option(name = "-e", required = true)
    private Example example;

    public static void main(final String[] args) {

        final Launcher launcher = new Launcher();
        final CmdLineParser parser = new CmdLineParser(launcher);

        try {
            parser.parseArgument(args);
        } catch (final CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }

        launcher.example.app.run();
    }
}
