package tests;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class SimpleRectangle {

    public SimpleRectangle(int width, int height) {
        this(0,0,width,height);
    }

    public SimpleRectangle(int x, int y, int width, int height ) {
        this(0,0,width,height,1);
    }

    public SimpleRectangle(int x, int y, int width, int height, int borderThickness) {
        this(0,0,width,height,1, Color.RED, Color.BLUE);
    }

    public SimpleRectangle(int x, int y, int width, int height, int borderThickness, Color fillColor, Color bodyColor) {
        //do smth with all these params
    }



    public void show() {

    }

    public static void main(String[] args) {
        //usages can be scattered across the code
        final SimpleRectangle simpleRectangle = new SimpleRectangleBuilder().setX(1).setY(1).setWidth(10).setHeight(10).setBorderThickness(2).setBodyColor(Color.BLACK).createSimpleRectangle();
        simpleRectangle.show();


        final SimpleRectangle simpleRectangle1 = new SimpleRectangleBuilder().setX(1).setY(1).setWidth(10).setHeight(10).setBorderThickness(2).createSimpleRectangle();
        simpleRectangle1.show();


        final SimpleRectangle simpleRectangle2 = new SimpleRectangleBuilder().setX(1).setY(1).setWidth(10).setHeight(10).createSimpleRectangle();
        simpleRectangle2.show();


        final SimpleRectangle simpleRectangle3 = new SimpleRectangleBuilder().setWidth(1).setHeight(1).createSimpleRectangle();
        simpleRectangle3.show();


    }
}
