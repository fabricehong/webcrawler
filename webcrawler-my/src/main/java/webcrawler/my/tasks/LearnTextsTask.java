package webcrawler.my.tasks;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.core.exceptions.TechnicalException;
import webcrawler.core.parsing.PageDom;
import webcrawler.core.tasks.PageDomTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/3/15
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class LearnTextsTask implements PageDomTask {
    private final String logFilePath;
    private Logger logger = LoggerFactory.getLogger(LearnTextsTask.class);
    private final FileWriter fileWriter;

    public LearnTextsTask(String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        File file = new File(this.logFilePath);
        fileWriter = new FileWriter(file);
    }

    @Override
    public synchronized void doTask(PageDom pageDom) {
        Elements paragraphs = pageDom.getParagraphs();
        for (Element elem : paragraphs) {
            logText(elem.text());
        }
    }

    private void logText(String text) {
        logger.debug(String.format("text : %s", text));
        try {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
    }
}
