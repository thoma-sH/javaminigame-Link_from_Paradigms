// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.util.ArrayList;

public class Model {
    private static ArrayList<Sprite> sprites;
    private Link link;
    private Sprite treasureChest;
    private Sprite tree;
    private static ArrayList<Sprite> itemsICanAdd;
    private int itemNum;

    public Model()
    {
        sprites = new ArrayList<Sprite>();
        itemsICanAdd = new ArrayList<Sprite>();
        link = new Link(100, 100);
        treasureChest = new TreasureChest(View.getCurrentRoomX(), View.getCurrentRoomY());
        tree = new Tree(View.getCurrentRoomX(), View.getCurrentRoomY());
        itemsICanAdd.add(tree);
        itemsICanAdd.add(treasureChest);

    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpTreeList = Json.newList();
        ob.add("trees", tmpTreeList);

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

    public void addTree(int x, int y)
    {
        Sprite t = new Tree(x, y);
        sprites.add(t);
    }

    public void addTreasureChest(int x, int y)
    {
        Sprite c = new TreasureChest(x, y);
        sprites.add(c);
    }

    public void clearSprites() {
        sprites.clear();
        sprites.add(link);
    }

    public void update()
    {
        itemsICanAdd.get(itemNum % 2).setX(View.getCurrentRoomX());
        itemsICanAdd.get(itemNum % 2).setY(View.getCurrentRoomY());
        fixCollision();
        link.setPCoordinate(link.getX(), link.getY());
    }

    public void unmarshal(Json ob)
    {
        sprites.clear();
        sprites.add(link);
        sprites.add(treasureChest);
        Json tmpTreeList = ob.get("trees");
        for(int i = 0; i < tmpTreeList.size(); i++)
        {
            sprites.add(new Tree(tmpTreeList.get(i)));
        }

    }

    public static ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public static ArrayList<Sprite> getItemsICanAdd() {
        return itemsICanAdd;
    }

    public void tellLinkToMoveYoBody(String direction)
    {
        getLink().moveYoBody(direction);
    }

    public void removeTree(Sprite sprite) {
        sprites.remove(sprite);
    }

    public void fixCollision() {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite currentSprite = sprites.get(i);
            if (currentSprite.isTree() && Sprite.isSpriteColliding(link, currentSprite)) {
                link.setCoords(link.getPx(), link.getPy());
            }
            if (currentSprite.isTreasureChest() && Sprite.isSpriteColliding(link, currentSprite)) {
                currentSprite.valid = false;
                currentSprite.setWidth(TreasureChest.RUPEE_WIDTH);
                currentSprite.setHeight(TreasureChest.RUPEE_HEIGHT);
                currentSprite.setX(currentSprite.getX());
                currentSprite.setY(currentSprite.getY());
                if (Sprite.isSpriteColliding(link, currentSprite) &&
                        currentSprite.getFramesSinceOpen() < TreasureChest.RESUME_DURATION) {
                    link.setCoords(link.getPx(), link.getPy());
                }
                if (Sprite.isSpriteColliding(link, currentSprite)) {
                    sprites.remove(currentSprite);
                }
            }
        }
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public Link getLink() {
        return link;
    }

    @Override
    public String toString()
    {
        return "Map contains: " + sprites.size() + " sprites. Link: " + link;
    }
}