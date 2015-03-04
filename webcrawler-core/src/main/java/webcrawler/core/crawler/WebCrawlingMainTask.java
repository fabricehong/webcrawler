package webcrawler.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Fabrice Hong
 * @date 04.03.15
 */
public class WebCrawlingMainTask {
    private ExecutorService executorService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public WebCrawlingMainTask() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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
}
