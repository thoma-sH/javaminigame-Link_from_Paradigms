// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Tree {
    private static BufferedImage treeImage;
    private final int x;
    private final int y;
    private static int w = 64, h = 80;

    public Tree(int newX, int newY) {
        x = newX;
        y = newY;
        if (treeImage == null) {
            treeImage = View.loadImage("images/tree3.png");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getW() {
        return w;
    }

    public static int getH() {
        return h;
    }

    public static boolean treeExists(int mouseX, int mouseY, ArrayList<Tree> trees) // ArrayList sprite exists function
    {
        int treeCounter = 0;
        for (int i = trees.size() - 1; i >= 0; i--) { // sprite.size()
            Tree currentTree = trees.get(i);
            if (currentTree.getX() == Math.floorDiv(mouseX + View.getCurrentRoomX(), Tree.getW()) * Tree.getW() &&
                    currentTree.getY() == Math.floorDiv(mouseY + View.getCurrentRoomY(), Tree.getH()) * Tree.getH()) // sprite.getW(), sprite.getH()
                treeCounter++; // spriteCounter++
        }

        return treeCounter != 0;
    }

    public static int getRightSide(Tree tree)
    {
        return tree.getX() + Tree.getW();
    }

    public static int getRoots(Tree tree) { return tree.getY() + Tree.getH(); }

    public static int getLeftSide(Tree tree) { return tree.getX(); }

    public static int getTop(Tree tree)
    {
        return tree.getY();
    }

    public static void drawYourself(Graphics g, ArrayList<Tree> trees)
    {
        for(Iterator<Tree> it = trees.iterator(); it.hasNext();) {
            Tree t = it.next();
            g.drawImage(treeImage, t.getX() - View.getCurrentRoomX(),
                t.getY() - View.getCurrentRoomY(), Tree.getW(), Tree.getH(), null);
        }

        if(Controller.isEditMode() && Controller.isAddMapItem())
        {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10, Tree.getW(), Tree.getH(), null);
        }

        if(Controller.isEditMode() && !Controller.isAddMapItem())
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10, Tree.getW(), Tree.getH(), null);
        }
    }

    // Unmarshalling constructor
    public Tree(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = 64;
        h = 80;

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
        ob.add("w", w);
        ob.add("h", h);

        return ob;
    }
}

