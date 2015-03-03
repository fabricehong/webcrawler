package webcrawler.models.tasks;

import org.htmlparser.Node;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webcrawler.models.parsing.PageDom;

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
        Class<ParagraphTag> tagType = ParagraphTag.class;
        try {
            NodeList tags = pageDom.getTags(tagType);
            SimpleNodeIterator elements = tags.elements();
            int para = 0;
            while (elements.hasMoreNodes()) {
                Node node = elements.nextNode();

                logger.debug(String.format("paragraph %s", para++));
                logParagraph(node, para);
            }
        } catch (ParserException e) {
            logger.error(String.format("Error Searching '%s' tags", tagType), e);
        } catch (IOException e) {
            logger.error(String.format("Error reading file '%s'", logFilePath), e);
        }
        try {
            fileWriter.flush();
        } catch (IOException e) {
            logger.error(String.format("error while flushing file '%s'", this.logFilePath), e);
        }
    }

    private void logParagraph(Node node, int para) throws IOException {
        NodeList children = node.getChildren();
        if (children==null) {
            return;
        }
        SimpleNodeIterator elements = children.elements();
        int noo = 0;
        while(elements.hasMoreNodes()) {
            logger.debug(String.format("node %s", noo++));
            Node n = elements.nextNode();
            String text = n.getText();
            logText(text);
        }
    }

    private void logText(String text) throws IOException {
        logger.debug(String.format("text : %s", text));
        fileWriter.write(text);
    }
}
