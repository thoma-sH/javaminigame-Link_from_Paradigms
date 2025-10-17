import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Boomerang extends Sprite
{
    private int speed = 14;
    private static BufferedImage[] boomerangImages;
    private int currentBoomerangFrame = 0;
    public static final int BOOMERANG_WIDTH = 20, BOOMERANG_HEIGHT = 20;
    public static final int AMOUNT_OF_BOOMERANG_IMAGES = 4;
    private int xDirection, yDirection;
    public static final int RIGHT_ENUM = 2, LEFT_ENUM = 1, UP_ENUM = 3, DOWN_ENUM = 0;

    public Boomerang(int x, int y, int dirEnum)
    {
        super(x, y, BOOMERANG_WIDTH, BOOMERANG_HEIGHT);
        valid = true;
        int index = 1;
        boomerangImages = new BufferedImage[AMOUNT_OF_BOOMERANG_IMAGES];
        for(int i = 0; i < AMOUNT_OF_BOOMERANG_IMAGES; i++){
            if(boomerangImages[i] == null){
                boomerangImages[i] = View.loadImage("images/boomerang" +
                        (index++) + ".png");
            }
        }
        if(dirEnum == RIGHT_ENUM){
            xDirection = 1;
            yDirection = 0;
        }
        if(dirEnum == LEFT_ENUM){
            xDirection = -1;
            yDirection = 0;
        }
        if(dirEnum == DOWN_ENUM){
            yDirection = 1;
            xDirection = 0;
        }
        if(dirEnum == UP_ENUM){
            yDirection = -1;
            xDirection = 0;
        }
    }

    @Override
    public boolean isBoomerang() { return true; }

    public void setCoords(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    @Override
    public void fixCollision(Sprite b)
    {
        if(b.isTreasureChest())
        {
            valid = false;
        }
        if(b.isTree())
        {
            valid = false;
        }
    }

    public void updateCurrentBoomerangFrame(){
        if(++currentBoomerangFrame >= AMOUNT_OF_BOOMERANG_IMAGES)
            currentBoomerangFrame = 0;
    }

    public boolean update()
    {
        updateCurrentBoomerangFrame();
        x+= speed * xDirection;
        y+= speed * yDirection;
        return valid;
    }

    @Override
    public void drawYourself(Graphics g, int scrollX, int scrollY)
    {
        g.drawImage(boomerangImages[currentBoomerangFrame], x - scrollX,
                y - scrollY, width, height,null );
    }

    @Override
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);

        return ob;
    }

    @Override
    public String toString() {
        return "";
    }
}
