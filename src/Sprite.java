import java.awt.*;

public abstract class Sprite
{
    protected int x, y, height, width;

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

    public abstract void drawYourself(Graphics g);

    @Override
    public abstract String toString();

}
