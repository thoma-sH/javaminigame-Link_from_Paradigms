// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.util.ArrayList;

public class Model {
    private static ArrayList<Tree> trees;
    private boolean collisionDetected;
    Link link;

    public Model()
    {
        int firstTreeX = 0;
        int firstTreeY = 0;
        trees = new ArrayList<>();
        link = new Link(100, 100);
        trees.add(new Tree(firstTreeX, firstTreeY)); // Hasty clean up for NoSuchElementException when getTree is called
    }                                                // on JSON trees.

    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpTreeList = Json.newList();
        ob.add("trees", tmpTreeList);
        for (Tree tree : trees) tmpTreeList.add(tree.marshal());
        return ob;
    }

    public Model(Json ob)
    {
        trees = new ArrayList<>();
        Json tmpList = ob.get("trees");
        for(int i = 0; i < tmpList.size(); i++)
            {
            trees.add(new Tree(tmpList.get(i)));
            }
    }

    public void addTree(int mouseX, int mouseY) {
        Tree t = new Tree(Math.floorDiv(mouseX + View.getCurrentRoomX(), // create new tree with x and y,
                getTreeWidth()) * getTreeWidth(),
                Math.floorDiv(mouseY + View.getCurrentRoomY(),
                getTreeHeight()) * getTreeHeight());
        trees.add(t);
    }

    public void clearTrees() {
        trees.clear();
    }

    public void update()
    {
        fixLinkCollision();
        link.setPCoordinate(link.getX(), link.getY());
    }

    public void unmarshal(Json ob)
    {
        trees.clear();
        Json tmpTreeList = ob.get("trees");
        for(int i = 0; i < tmpTreeList.size(); i++)
        {
            trees.add(new Tree(tmpTreeList.get(i)));
        }

    }

    public static ArrayList<Tree> getTrees() {
        return trees;
    }

    public void tellLinkToMoveYoBody(String direction)
    {
        getLink().moveYoBody(direction);
    }

    public void removeTree(int x, int y) {
        int TestX = Math.floorDiv(x, getTreeWidth()) * getTreeWidth(); // creating test coordinates to loop through
        int TestY = Math.floorDiv(y, getTreeHeight()) * getTreeHeight(); // to find a match, indicating tree to be removed
        for (int i = 0; i < trees.size(); i++) {
            if (TestX == trees.get(i).getX() && TestY == trees.get(i).getY()) //noinspection SingleStatementInBlock
            {
                trees.remove(i);  // it will not iteratively remove trees, because trees cannot be placed within
            }                                           // the same 64x80 pixel area. therefore
        }                           // there are 64*80 possible pixels to click on in 1 area, and they all will
    }                            // result in painting the same one tree in the same 64x80 pixel area.

    private boolean isSpriteColliding(/*sprite a, */ Tree tree)
    {
        return (getLink().getLeftSide() < tree.getRightSide() &&
                getLink().getRightSide() > tree.getLeftSide() &&
                getLink().getTop() < tree.getRoots() &&
                getLink().getRoots() > tree.getTop());
    }

    public void fixLinkCollision() {
        for (Tree tree : trees) {
            if (isSpriteColliding(tree)) {
                link.setCoords(link.getPx(), link.getPy());
            }
        }
    }
    public Link getLink() {
        return link;
    }

    public int getTreeHeight()
    {
        return trees.getFirst().getHeight(); // allows for access of private variables when no tree is instantiated yet.
    }

    public int getTreeWidth()
    {
        return trees.getFirst().getWidth();
    }

    public Tree  getTree()
    {
        return trees.getFirst(); // allows for drawYourself method to call drawYourself to all trees.
    }

    public boolean collisionDetected() {
        return collisionDetected;
    }

    @Override
    public String toString()
    {
        return "Map contains: " + trees.size() + " trees. Link: " + link;
    }
}