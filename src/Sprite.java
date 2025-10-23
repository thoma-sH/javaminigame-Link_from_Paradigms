// Thomas Hamilton
// 17 Oct. 2025
// Assignment 4 - Polymorphism
import java.awt.*;

public abstract class Sprite
{
    protected int x, y, width, height;
    protected boolean valid = true;

    protected Sprite(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected int getX()
    {
        return this.x;
    }

    protected int getY()
    {
        return this.y;
    }

    protected int getWidth()
    {
        return this.width;
    }

    protected int getHeight()
    {
        return this.height;
    }

    protected void  setX(int x)
    {
        this.x = x;
    }

    protected void  setY(int y)
    {
        this.y = y;
    }

    protected void  setWidth(int width)
    {
        this.width = width;
    }

    protected void  setHeight(int height)
    {
        this.height = height;
    }

    protected int getRightSide()
    {
        return this.getX() + this.getWidth();
    }

    protected int getRoots()
    {
        return this.getY() + this.getHeight();
    }

    protected int getLeftSide()
    {
        return this.getX();
    }

    protected int getTop()
    {
        return this.getY();
    }

    protected boolean isLink()
    {
        return false;
    }

    protected boolean isTree()
    {
        return false;
    }

    protected boolean isTreasureChest()
    {
        return false;
    }

    protected boolean isBoomerang()
    {
        return false;
    }

    protected boolean amIClickingOnYou(int mouseX, int mouseY) // ArrayList sprite exists function
    {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    protected boolean isSpriteColliding(Sprite a, Sprite b)
    {
        return (a.getLeftSide() < b.getRightSide() &&
                a.getRightSide() > b.getLeftSide() &&
                a.getTop() < b.getRoots() &&
                a.getRoots() > b.getTop());
    }

    protected abstract void fixCollision(Sprite b);

    protected abstract boolean update();

    protected abstract void drawYourself(Graphics g, int scrollX, int scrollY);

    protected abstract Json marshal();

    @Override
    public abstract String toString();

}
