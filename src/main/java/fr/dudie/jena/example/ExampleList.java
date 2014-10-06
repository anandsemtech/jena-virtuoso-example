package fr.dudie.jena.example;

public enum ExampleList {
    
    SINGLE_INSERT(new TDBSimpleInsertExample()), 
    SINGLE_INSERT_VIRT(new VirtuosoSingleConnectionInsertExample()),
    DEADLOCK_VIRT(new VirtuosoConnectionPoolDeadLockExample());

    public final Example app;

    private ExampleList(final Example example) {
        this.app = example;
    }
}
