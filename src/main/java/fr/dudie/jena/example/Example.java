package fr.dudie.jena.example;

public enum Example {
    
    SINGLE_INSERT(new SingleInsertExample()), 
    SINGLE_INSERT_VIRT(new SingleInsertVirtuosoExample());

    public final Runnable app;

    private Example(final Runnable example) {
        this.app = example;
    }
}
