package com.aeq.semantic;

/**
 * Created by anand on 17/01/2017.
 */
public class TestMe {
    public static void main(String[] args){
        VirtuosoConnectionPoolDeadLock virtuosoConnectionPoolDeadLock = new
                VirtuosoConnectionPoolDeadLock("localhost", 1111, "dba", "dba", 4, 4);

        virtuosoConnectionPoolDeadLock.run();
    }
}
