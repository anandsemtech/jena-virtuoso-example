package fr.dudie.jena.example;

public enum Example {
    
    SINGLE_INSERT(new SingleInsertExample());

    public final Runnable app;

    private Example(final Runnable example) {
        this.app = example;
    }
}
