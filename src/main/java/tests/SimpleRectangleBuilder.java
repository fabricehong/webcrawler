package tests;

import java.awt.*;

public class SimpleRectangleBuilder {
    private int width;
    private int height;
    private int x = 0;
    private int y = 0;
    private int borderThickness = 1;
    private Color fillColor = Color.RED;
    private Color bodyColor = Color.BLUE;

    public SimpleRectangleBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public SimpleRectangleBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public SimpleRectangleBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public SimpleRectangleBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public SimpleRectangleBuilder setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
        return this;
    }

    public SimpleRectangleBuilder setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public SimpleRectangleBuilder setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
        return this;
    }

    public SimpleRectangle createSimpleRectangle() {
        return new SimpleRectangle(x, y, width, height, borderThickness, fillColor, bodyColor);
    }
}