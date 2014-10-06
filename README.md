Jena over Virtuoso examples
===========================

About
-----

This project aims to illustrate Jena + Virtuoso issues.

How to run the examples
-----------------------

This is a maven projet:

    mvn clean package

### Virtuoso connection pool deadlock

I observed deadlock when using the pool in an intensive multithreaded environment.

    java -jar target/virtuoso-jena-example-1.0-SNAPSHOT-jar-with-dependencies.jar \
        -e DEADLOCK_VIRT \
        --virt-host localhost \
        --virt-port 1111 \
        --virt-user dba \
        --virt-pass dba \
        --virt-max-connections 8 \
        --thread-pool-size 8

will run example class [VirtuosoConnectionPoolDeadLockExample](https://github.com/kops/jena-virtuoso-example/blob/master/src/main/java/fr/dudie/jena/example/VirtuosoConnectionPoolDeadLockExample.java). 

1. Initialize a `VirtuosoConnectionPoolDatasource`
2. Initialize a `ThreadPoolExecutor` to run parallel tasks
3. Submit 1 000 000 task to the `ThreadPoolExecutor`
   * Open a `VirtGraph`
   * Open a `VirtModel`
   * Add a single triple to the `VirtModel`
   * Close the `VirtModel`
   * Close the `VirtGraph`

