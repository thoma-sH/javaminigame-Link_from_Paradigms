// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Tree {
    private static BufferedImage treeImage;
    private int x, y, width, height;
    public static final int TREE_WIDTH = 64, TREE_HEIGHT = 80;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        width = TREE_WIDTH;
        height = TREE_HEIGHT;
        if (treeImage == null) {
            treeImage = View.loadImage("images/tree3.png");
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean treeExists(int mouseX, int mouseY, ArrayList<Tree> trees) // ArrayList sprite exists function
    {
        int treeCounter = 0;
        for (int i = trees.size() - 1; i >= 0; i--) { // sprite.size()
            Tree currentTree = trees.get(i);
            if (currentTree.getX() == Math.floorDiv(mouseX, currentTree.width) * currentTree.width &&
                    currentTree.getY() == Math.floorDiv(mouseY, currentTree.height) * currentTree.height) // sprite.getW(), sprite.getH()
                treeCounter++; // spriteCounter++
        }

        return treeCounter != 0;
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
    public Tree(Json ob)
    {
        this.x = (int)ob.getLong("x");
        this.y = (int)ob.getLong("y");
        width = TREE_WIDTH;
        height = TREE_HEIGHT;

        if(treeImage == null)
            treeImage = View.loadImage("images/tree3.png");
    }

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

