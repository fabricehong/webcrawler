package webcrawler.gui.graph.node.colorizer;

import webcrawler.core.parsing.PageDom;
import webcrawler.core.url.UrlUtils;

import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 3/4/15
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeColorizer {
    public static final int FIRST_CHAR = 97;
    public static final int LAST_CHAR = 122;
    public static final int CHAR_RANGE = LAST_CHAR-FIRST_CHAR;
    Map<String, Color> idToColor;

    public NodeColorizer() {
        this.idToColor = new HashMap<>();
    }

    public Color getNodeColor(String url, PageDom pageDom) {
        String colorKey = null;
        try {
            colorKey = UrlUtils.getHost(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Color nodeColor = this.idToColor.get(colorKey);
        if (nodeColor == null) {
            nodeColor = generateNewColor(colorKey);
            this.idToColor.put(colorKey, nodeColor);
        }
        return nodeColor;
    }

    public String generateTable() {
        String fabrice = "Fabrice";
        Color colorFabrice = generateNewColor(fabrice);
        String fabien = "Fadrdbiesn";
        Color colorFabien = generateNewColor(fabien);
        System.out.println(String.format("fabrice : %s", colorFabrice));
        System.out.println(String.format("fabien : %s", colorFabien));
        String nicolas = "Nicolas";
        Color colorNicolas = generateNewColor(nicolas);
        String nicolier = "Nicolier";
        Color colorNicolier = generateNewColor(nicolier);
        String s = "<table>" +
                "<tr><td style='background-color: rgb(%s,%s,%s);'>%s</td></tr>" +
                "<tr><td style='background-color: rgb(%s,%s,%s);'>%s</td></tr>" +
                "<tr><td style='background-color: rgb(%s,%s,%s);'>%s</td></tr>" +
                "<tr><td style='background-color: rgb(%s,%s,%s);'>%s</td></tr>" +
                "</table>";
        s = String.format(s,
                colorFabrice.getRed(), colorFabrice.getGreen(), colorFabrice.getBlue(), fabrice,
                colorFabien.getRed(), colorFabien.getGreen(), colorFabien.getBlue(), fabien,
                colorNicolas.getRed(), colorNicolas.getGreen(), colorNicolas.getBlue(), nicolas,
                colorNicolier.getRed(), colorNicolier.getGreen(), colorNicolier.getBlue(), nicolier
                );
        return s;
    }

    public static void main(String[] args) {
        System.out.println(new NodeColorizer().generateTable());

    }

    private Color generateNewColor(String colorKey) {
        int mean = castStringToInt(colorKey, 255);
        int offset = 255 / 3;
        int r = mean;
        int g = offset(mean, offset, 255);
        int b = offset(mean, 2*offset, 255);
        return new Color(r, g, b);
    }

    private int offset(int n, int offset, int max) {
        return (n + offset) % max;
    }

    private static int castStringToInt(String str, int upperLimit) {
        String lowerStr = str.toLowerCase();
        int sum = 0;
        int count = 0;
        for (int i = 0; i < lowerStr.length(); i++) {
            char c = lowerStr.charAt(i);
            if (c >= FIRST_CHAR && c <= LAST_CHAR) {
                count++;
                sum += c-FIRST_CHAR;
            }
        }
        float coef = ((float)sum)/ (CHAR_RANGE*count);
        return (int)(coef * upperLimit );
    }
}
