// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.util.ArrayList;

public class Model {
    private static ArrayList<Sprite> sprites;
    private Link link;

    public Model()
    {
        sprites = new ArrayList<>();
        link = new Link(100, 100);
        sprites.add(link);
    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpTreeList = Json.newList();
        ob.add("tree", tmpTreeList);

        for(int i = 0; i < sprites.size(); i++){
            if(sprites.get(i).isTree()){
                tmpTreeList.add(((Tree)sprites.get(i)).marshal());
            }
        }
        return ob;
    }

    public Model(Json ob)
    {
        sprites = new ArrayList<>();
        Json tmpList = ob.get("sprites");
        for(int i = 0; i < tmpList.size(); i++)
            {
            sprites.add(new Tree(tmpList.get(i)));
            }
    }

    public void addTree(int mouseX, int mouseY) {
        Tree t = new Tree(Math.floorDiv(mouseX + View.getCurrentRoomX(), // create new tree with x and y,
                getTreeWidth()) * getTreeWidth(),
                Math.floorDiv(mouseY + View.getCurrentRoomY(),
                getTreeHeight()) * getTreeHeight());
        sprites.add(t);
    }

    public void clearSprites() {
        sprites.clear();
    }

    public void update()
    {
        fixLinkCollision();
        link.setPCoordinate(link.getX(), link.getY());
    }

    public void unmarshal(Json ob)
    {
        sprites.clear();
        sprites.add(link);
        Json tmpTreeList = ob.get("trees");
        for(int i = 0; i < tmpTreeList.size(); i++)
        {
            sprites.add(new Tree(tmpTreeList.get(i)));
        }

    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public void tellLinkToMoveYoBody(String direction)
    {
        getLink().moveYoBody(direction);
    }

    public void removeTree(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void fixLinkCollision() {
        for (int i = 1; i < sprites.size(); i++ ) {
            if (link.isSpriteColliding(link, sprites.get(i))) {
                link.setCoords(link.getPx(), link.getPy());
            }
        }
    }

    public Link getLink() {
        return link;
    }

    public int getTreeHeight()
    {
        return sprites.getFirst().getHeight(); // allows for access of private variables when no tree is instantiated yet.
    }

    public int getTreeWidth()
    {
        return sprites.getFirst().getWidth();
    }

    @Override
    public String toString()
    {
        return "Map contains: " + sprites.size() + " sprites. Link: " + link;
    }
}