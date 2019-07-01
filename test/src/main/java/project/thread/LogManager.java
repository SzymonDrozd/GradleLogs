package project.thread;

import project.log.task.LogTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogManager extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(LogManager.class);

    public static BlockingQueue<LogTask> queue = new LinkedBlockingDeque<>();
    public static int threadCounter = 5;
    public static long waitTime = 10;

    public LogManager() {

    }

    @Override
    public void run() {
        LOG.debug("Starting Log Manager");
        while (ProjectConfiguration.IS_LOG_MANAGER_RUNNING) {
            if (threadCounter > 0 ) {
                if( !queue.isEmpty()){
                    LogTask task;
                    try {
                        task = queue.take();
                        LOG.debug("Task is found: " + task.getClass().getName());
                        threadCounter--;
                        task.run();
                    } catch (Throwable e){
                        LOG.warn("Exception in LogManager: " + e);
                        try {
                            this.wait(waitTime);
                        } catch (InterruptedException ex) {
                            LOG.warn("Exception in LogManager where task is not founded: " + ex);
                        }
                    }
                }
            } else {
                LOG.debug("Limit of threads is reached");
            }
        }
    }

}
