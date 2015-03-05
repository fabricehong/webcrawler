package webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Fabrice Hong
 * @date 04.03.15
 */
public class WebCrawlingMainTask {
    private ThreadPoolExecutor executorService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public WebCrawlingMainTask() {
        int nThreads = Runtime.getRuntime().availableProcessors()*10;
        logger.info(String.format("Crawler task setup with %s parallel threads", nThreads));
        this.executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(nThreads);
//        this.executorService = Executors.newFixedThreadPool(1);

    }

    public void submitTask(VisitUrlTask task) {
        this.executorService.submit(task);
    }

    public void submitAllTasks(Collection<VisitUrlTask> tasks) {
        for (VisitUrlTask task : tasks) {
            this.executorService.submit(task);
        }
    }

    public void shutdown() {
        this.executorService.shutdown();
    }

    public int getWorkingThreads() {
        return this.executorService.getActiveCount();
    }

    public long getTaskExecuted() {
        return this.executorService.getTaskCount();
    }
}
