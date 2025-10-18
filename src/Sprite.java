import java.awt.*;

public abstract class Sprite
{
    protected int x, y, width, height;
    protected boolean valid = true;

    public Sprite(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void  setX(int x)
    {
        this.x = x;
    }

    public void  setY(int y)
    {
        this.y = y;
    }

    public void  setWidth(int width)
    {
        this.width = width;
    }

    public void  setHeight(int height)
    {
        this.height = height;
    }

    public int getRightSide()
    {
        return this.getX() + this.getWidth();
    }

    public int getRoots()
    {
        return this.getY() + this.getHeight();
    }

    public int getLeftSide()
    {
        return this.getX();
    }

    public int getTop()
    {
        return this.getY();
    }

    public boolean isLink()
    {
        return false;
    }

    public boolean isTree()
    {
        return false;
    }

    public boolean isTreasureChest()
    {
        return false;
    }

    public boolean isBoomerang()
    {
        return false;
    }

    public boolean amIClickingOnYou(int mouseX, int mouseY) // ArrayList sprite exists function
    {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public boolean isSpriteColliding(Sprite a, Sprite b)
    {
        return (a.getLeftSide() < b.getRightSide() &&
                a.getRightSide() > b.getLeftSide() &&
                a.getTop() < b.getRoots() &&
                a.getRoots() > b.getTop());
    }

    public abstract void fixCollision(Sprite b);

    public abstract boolean update();

    public abstract void drawYourself(Graphics g, int scrollX, int scrollY);

    public abstract Json marshal();

    @Override
    public abstract String toString();

}
