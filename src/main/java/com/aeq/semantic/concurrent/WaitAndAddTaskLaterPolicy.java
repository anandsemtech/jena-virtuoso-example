package com.aeq.semantic.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitAndAddTaskLaterPolicy implements RejectedExecutionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaitAndAddTaskLaterPolicy.class);

    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            LOGGER.error("unable to add task {} to the queue", r, e);
        }
    }

}
