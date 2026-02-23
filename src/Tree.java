// Thomas Hamilton
// 17 Oct. 2025
// Assignment 4 - Polymorphism
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tree extends Sprite
{
    private static BufferedImage treeImage;
    public static final int TREE_WIDTH = 64, TREE_HEIGHT = 80;

    public Tree(int x, int y)
    {
        super(x, y, TREE_WIDTH, TREE_HEIGHT);
        valid = true;

        if (treeImage == null)
            treeImage = View.loadImage("images/tree3.png");
    }

    public Tree(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), TREE_WIDTH, TREE_HEIGHT);

        if(treeImage == null)
            treeImage = View.loadImage("images/tree3.png");
    }

    public boolean isTree()
    {
        return true;
    }

    @Override
    public void fixCollision(Sprite b)
    {// Trees are immovable objects.
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

    public void drawYourself(Graphics g, int scrollX, int scrollY)
    {
        g.drawImage(treeImage, x - scrollX,
                y - scrollY, TREE_WIDTH, TREE_HEIGHT, null);
    }

    public static BufferedImage getTreeImage()
    {
        return treeImage;
    }

    @Override
    public boolean update()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "Tree at (" + x + "," + y + ")";
    }

}

