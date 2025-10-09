// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Link
{
    private int px = 100, py = 100;
    private int x, y, width, height; // Since we only have one Link, I will make this static for easier access.
    private double speed = 8;
    public static final int LINK_WIDTH = 40, LINK_HEIGHT = 63;
    private static BufferedImage[][] linkImages;
    private int currentLinkFrame = 0;
    private int currentLinkDirection = 0;
    private final int LINK_NUM_DIRECTIONS = 4, LINK_MAX_IMAGES_PER_DIRECTION = 11;

    public Link(int x, int y)
    {
        this.x = x;
        this.y = y;
        width = LINK_WIDTH;
        height = LINK_HEIGHT;
        int index = 1;
        linkImages = new BufferedImage[LINK_NUM_DIRECTIONS]
                [LINK_MAX_IMAGES_PER_DIRECTION];
        for(int i = 0; i < LINK_NUM_DIRECTIONS; i++)
            for(int j = 0; j < LINK_MAX_IMAGES_PER_DIRECTION; j++) {
                if(linkImages[i][j] == null){
                    linkImages[i][j] = View.loadImage("images/link" +
                            (index++) + ".png");
                }
            }
    }

    public void moveYoBody(String direction)
    {
        if(Objects.equals(direction, "left")) {
            x -= (int) speed;
        }
        if(Objects.equals(direction, "right")) {
            x += (int) speed;
        }
        if(Objects.equals(direction, "up")) {
            System.out.println(this);
            y -= (int) speed;
        }
        if(Objects.equals(direction, "down")) {
            y += (int) speed;
        }

    }

    public int getPx() { return this.px; }

    public int getPy() {
        return this.py;
    }

    public void setPx(int x) { this.px = x; }

    public void setPy(int y) {
        this.py = y;
    }

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    public int getW() { return width; }

    public int getH() { return height; }

    public void setCoords(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getRightSide()
    {
        return this.getX() + this.getW();
    }

    public int getRoots()
    {
        return this.getY() + this.getH();
    }

    public int getLeftSide()
    {
        return this.getX();
    }

    public int getTop()
    {
        return this.getY();
    }

    public void setCurrentLinkDirection(int newLinkDirection)
    {
        currentLinkDirection = newLinkDirection;
    }

    public void updateLinkAnimationSequenceFrame()
    {
        if(++currentLinkFrame >= LINK_MAX_IMAGES_PER_DIRECTION)
            currentLinkFrame = 0;
    }

    public void drawYourself(Graphics g)
    {
        g.drawImage(linkImages[currentLinkDirection][currentLinkFrame], this.getX() - View.getCurrentRoomX(),
                this.getY() - View.getCurrentRoomY(), width, height, null);
    }

    @Override
    public String toString() {
        return "Link at (" + x + "," + y + ")";
    }
}
