// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Tree extends Sprite{
    private static BufferedImage treeImage;
    public static final int TREE_WIDTH = 64, TREE_HEIGHT = 80;

    public Tree(int x, int y) {
        super(x, y, TREE_WIDTH, TREE_HEIGHT);
        if (treeImage == null) {
            treeImage = View.loadImage("images/tree3.png");
        }
    }

    public Tree(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"), TREE_WIDTH, TREE_HEIGHT);

        if(treeImage == null)
            treeImage = View.loadImage("images/tree3.png");
    }

    public boolean treeExists(int mouseX, int mouseY) // ArrayList sprite exists function
    {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public int getRightSide()
    {
        return this.x + this.width;
    }

    public int getRoots() { return this.y + this.height; }

    public int getLeftSide() { return this.x; }

    public int getTop() { return this.y; }

    public void drawYourself(Graphics g, ArrayList<Tree> trees)
    {
        for(Iterator<Tree> it = trees.iterator(); it.hasNext();) {
            Tree t = it.next();
            g.drawImage(treeImage, t.getX() - View.getCurrentRoomX(),
                t.getY() - View.getCurrentRoomY(), t.width, t.height, null);
        }

        if(Controller.isEditMode() && Controller.isAddMapItem())
        {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10, this.width, this.height, null);
        }

        if(Controller.isEditMode() && !Controller.isAddMapItem())
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10, this.width, this.height, null);
        }
    }

    // Unmarshalling constructor

    @Override
    public String toString() {
        return "Tree at (" + x + "," + y + ")";
    }

    // Marshalling constructor
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);

        return ob;
    }
}

