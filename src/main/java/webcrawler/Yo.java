package webcrawler;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 11.04.13
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class Yo {

    private static int anInt;
    public static final int THE_CONSTANT = 0;

    public static void main(String[] args) {
        int i = 4;
        anInt = 4 + i;
        int z = THE_CONSTANT;
        z = z + anInt;
    }
}
