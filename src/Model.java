// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.util.ArrayList;
import java.io.*;

public class Model {
    private static ArrayList<Tree> trees;
    Link link;

    public Model()
    {
        trees = new ArrayList<>();
        link = new Link(100, 100);
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("trees", tmpList);
        for (Tree tree : trees) tmpList.add(tree.marshal());
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
                Tree.getW()) * Tree.getW(),
                Math.floorDiv(mouseY + View.getCurrentRoomY(),
                Tree.getH()) * Tree.getH());
        trees.add(t);
    }

    public void clearTrees() {
        trees.clear();
    }

    public void unmarshal(Json ob)
    {
        trees.clear();
        for(int i = 0; i < ob.get("trees").size(); i++)
        {
            trees.add(new Tree(ob.get("trees").get(i)));
        }

    }

    public static ArrayList<Tree> getTrees() {
        return trees;
    }

    public void tellLinkToMoveYoBody(String direction)
    {
        link.moveYoBody(direction);
    }

    public void removeTree(int x, int y) {
        int TestX = Math.floorDiv(x + View.getCurrentRoomX(),
                Tree.getW()) * Tree.getW(); // creating test coordinates to loop through
        int TestY = Math.floorDiv(y + View.getCurrentRoomY(),
                Tree.getH()) * Tree.getH(); // to find a match, indicating tree to be removed
        for (int i = 0; i < trees.size(); i++) {
            if (TestX == trees.get(i).getX() && TestY == trees.get(i).getY()) //noinspection SingleStatementInBlock
            {
                trees.remove(i);  // it will not iteratively remove trees, because trees cannot be placed within
            }                                           // the same 64x80 pixel area. therefore
        }                           // there are 64*80 possible pixels to click on in 1 area, and they all will
    }                            // result in painting the same one tree in the same 64x80 pixel area.

    private boolean isColliding(Tree tree)
    {
        return (Link.getLeftSide() < Tree.getRightSide(tree) &&
                Link.getRightSide() > Tree.getLeftSide(tree) &&
                Link.getTop() < Tree.getRoots(tree) &&
                Link.getRoots() > Tree.getTop(tree));
    }

    public void fixCollision(ArrayList<Tree> trees) {
        for (Tree tree : trees) {
            if (isColliding(tree)) {
                Link.setCoords(Link.getPx(), Link.getPy());
            }
        }
    }

    @Override
    public String toString()
    {
        return "Map contains: " + trees.size() + " trees. Link: " + link;
    }
}