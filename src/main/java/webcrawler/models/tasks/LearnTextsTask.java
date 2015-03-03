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
    public void doTask(PageDom pageDom) {
        Class<ParagraphTag> tagType = ParagraphTag.class;
        try {
            NodeList tags = pageDom.getTags(tagType);
            SimpleNodeIterator elements = tags.elements();
            while (elements.hasMoreNodes()) {
                Node node = elements.nextNode();
                String text = node.getText();
                logText(text);
            }
        } catch (ParserException e) {
            logger.error(String.format("Error Searching '%s' tags", tagType), e);
        } catch (IOException e) {
            logger.error(String.format("Error reading file '%s'", logFilePath), e);
        }
    }

    private void logText(String text) throws IOException {
        fileWriter.write(text);
    }
}
