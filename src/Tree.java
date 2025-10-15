// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import java.awt.*;
import java.awt.image.BufferedImage;

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

    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);

        return ob;
    }

    public void drawYourself(Graphics g)
    {
        g.drawImage(treeImage, x - View.getCurrentRoomX(),
                y - View.getCurrentRoomY(), TREE_WIDTH, TREE_HEIGHT, null);

        if(Controller.isEditMode() && Controller.isAddMapItem())
        {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10,  TREE_WIDTH, TREE_HEIGHT, null);
        }

        if(Controller.isEditMode() && !Controller.isAddMapItem())
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 100, 100);
            g.drawImage(treeImage, 18, 10, TREE_WIDTH, TREE_HEIGHT, null);
        }
    }

    // Unmarshalling constructor

    @Override
    public String toString() {
        return "Tree at (" + x + "," + y + ")";
    }

}

