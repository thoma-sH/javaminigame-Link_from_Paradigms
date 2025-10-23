// Thomas Hamilton
// 17 Oct. 2025
// Assignment 4 - Polymorphism
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Link extends Sprite
{
    private int px = 100, py = 100;// Since we only have one Link, I will make this static for easier access.
    private double speed = 8;
    public static final int LINK_WIDTH = 42, LINK_HEIGHT = 65;
    private static BufferedImage[][] linkImages;
    private int currentLinkFrame = 0;
    private int currentLinkDirection = 0;
    private final int LINK_NUM_DIRECTIONS = 4, LINK_MAX_IMAGES_PER_DIRECTION = 11;
    private int rupeesCollected;

    public Link(int x, int y)
    {
        super(x, y, LINK_WIDTH, LINK_HEIGHT);
        valid = true;
        int index = 1;
        linkImages = new BufferedImage[LINK_NUM_DIRECTIONS]
                [LINK_MAX_IMAGES_PER_DIRECTION];

        for(int i = 0; i < LINK_NUM_DIRECTIONS; i++)

            for(int j = 0; j < LINK_MAX_IMAGES_PER_DIRECTION; j++)
            {
                if(linkImages[i][j] == null)
                {
                    linkImages[i][j] = View.loadImage("images/link" +
                            (index++) + ".png");
                }
            }
    }

    @Override
    public boolean isLink() {
        return true;
    }

    public void moveYoBody(String direction)
    {
        if(Objects.equals(direction, "left"))
        {
            x -= (int) speed;
        }
        if(Objects.equals(direction, "right"))
        {
            x += (int) speed;
        }
        if(Objects.equals(direction, "up"))
        {
            y -= (int) speed;
        }
        if(Objects.equals(direction, "down"))
        {
            y += (int) speed;
        }

    }

    public int getPx() { return this.px; }

    public int getPy() {
        return this.py;
    }

    public void setPCoordinate(int x, int y)
    {
        this.px = x;
        this.py = y;
    }

    public void setPx(int x) { this.px = x; }

    public void setPy(int y) {
        this.py = y;
    }

    public void setCoords(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public void setCurrentLinkDirection(int newLinkDirection)
    {
        currentLinkDirection = newLinkDirection;
    }

    public int getCurrentLinkDirection()
    {
        return currentLinkDirection;
    }

    public void drawYourself(Graphics g, int scrollX, int scrollY)
    {
        g.drawImage(linkImages[currentLinkDirection][currentLinkFrame], x - scrollX,
                y - scrollY, width, height, null);
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);

        return ob;
    }

    public void getOutOfTree(Tree t)
    {
        if(getRightSide() >=  t.getLeftSide() && (px + LINK_WIDTH) <= t.getX()){
            x = t.getX() - LINK_WIDTH - 1;
        }
        if(getLeftSide()<= t.getRightSide() && (px >= (t.getX() + t.getWidth()))){
            x = t.getX() + t.getWidth() + 1;
        }
        if(getTop() <= t.getRoots() && (py >= t.getRoots())){
            y = t.getY() + t.getHeight() + 1;
        }
        if(getRoots() >= t.getTop() && (py + LINK_HEIGHT) <= t.getY()){
            y = t.getY() - getHeight() - 1;
        }
    }

    @Override
    public void fixCollision(Sprite b)
    {
        if(b.isTree())
        {
            getOutOfTree( ((Tree)b) );
        }
        if(b.isTreasureChest())
        {
            if(((TreasureChest)b).getFramesSinceOpen() < TreasureChest.RESUME_DURATION)
            {
                setCoords(px, py);
            }else{
                System.out.println(rupeesCollected++);
            }
        }
    }

    public int returnRupees()
    {
        return rupeesCollected;
    }

    public void updateCurrentLinkFrame()
    {
        if(++currentLinkFrame >= LINK_MAX_IMAGES_PER_DIRECTION)
            currentLinkFrame = 0;
    }


    public boolean update()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Link at (" + x + "," + y + ") Link pc: (" + px + "," + py + ")" +
                " Width: " + width + ", Height: " + height;
    }
}
