// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Link
{
    private static int px = 100, py = 100;
    private static int x, y; // Since we only have one Link, I will make this static for easier access.
    private static final double SPEED = 8;
    private static BufferedImage[][] linkImages;
    private static final int LINK_WIDTH = 40;
    private static final int LINK_HEIGHT = 63;
    private static int currentLinkFrame = 0;
    private static int currentLinkDirection = 0;
    private static final int NUM_DIRECTIONS = 4;
    private static final int MAX_IMAGES_PER_DIRECTION = 11;

    public Link(int x, int y)
    {
        Link.x = x;
        Link.y = y;
        int index = 1;
        linkImages = new BufferedImage[NUM_DIRECTIONS]
                [MAX_IMAGES_PER_DIRECTION];
        for(int i = 0; i < NUM_DIRECTIONS; i++)
            for(int j = 0; j < MAX_IMAGES_PER_DIRECTION; j++) {
                if(linkImages[i][j] == null){
                    linkImages[i][j] = View.loadImage("images/link" +
                            (index++) + ".png");
                }
            }
    }

    public void moveYoBody(String direction)
    {
        if(Objects.equals(direction, "left")) {
            x -= (int) SPEED;
        }
        if(Objects.equals(direction, "right")) {
            x += (int) SPEED;
        }
        if(Objects.equals(direction, "up")) {
            System.out.println(this);
            y -= (int) SPEED;
        }
        if(Objects.equals(direction, "down")) {
            y += (int) SPEED;
        }

    }

    public static int getPx() {
        return px;
    }

    public static int getPy() {
        return py;
    }

    public static void setPx(int x) {
        Link.px = x;
    }

    public static void setPy(int y) {
        Link.py = y;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static int getW() {
        return LINK_WIDTH;
    }

    public static int getH() {
        return LINK_HEIGHT;
    }

    public static void setCoords(int x, int y)
    {
        Link.x = x;
        Link.y = y;
    }

    public static int getRightSide()
    {
        return Link.getX() + Link.getW();
    }

    public static int getRoots()
    {
        return Link.getY() + Link.getH();
    }

    public static int getLeftSide()
    {
        return Link.getX();
    }

    public static int getTop()
    {
        return Link.getY();
    }

    public static void setCurrentLinkDirection(int currentLinkDirection)
    {
        Link.currentLinkDirection = currentLinkDirection;
    }

    public static void updateLinkAnimationSequenceFrame()
    {
        if(++currentLinkFrame >= MAX_IMAGES_PER_DIRECTION)
            currentLinkFrame = 0;
    }

    public static void drawYourself(Graphics g)
    {
        g.drawImage(linkImages[currentLinkDirection][currentLinkFrame], Link.getX() - View.getCurrentRoomX(),
                Link.getY() - View.getCurrentRoomY(), LINK_WIDTH, LINK_HEIGHT, null);
    }

    @Override
    public String toString() {
        return "Link at (" + x + "," + y + ")";
    }
}
