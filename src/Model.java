// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging

import java.util.ArrayList;
import java.util.Iterator;

public class Model {
    private Link link;
    private static ArrayList<Sprite> sprites;
    private static ArrayList<Sprite> toAdd;
    private static ArrayList<Sprite> toRemove;
    private int itemNum;

    public Model()
    {
        sprites = new ArrayList<Sprite>();
        toAdd = new ArrayList<Sprite>();
        toRemove = new ArrayList<Sprite>();
        link = new Link(100, 100);

    }

    public Json marshal()
    {
        Json ob = Json.newObject();
        Json tmpTreeList = Json.newList();
        Json tmpTreasureChestList = Json.newList();
        ob.add("trees", tmpTreeList);
        ob.add("treasureChests", tmpTreasureChestList);
        for(Iterator<Sprite> it = sprites.iterator();  it.hasNext();){
            Sprite sprite = it.next();
            if(sprite.isTree()){
                tmpTreeList.add( sprite.marshal() );
            }
            if(sprite.isTreasureChest()){
                tmpTreasureChestList.add( sprite.marshal() );
            }
        }
        return ob;
    }

    public Model(Json ob)
    {
        toAdd = new ArrayList<Sprite>();
        Json tmpTreeList = ob.get("trees");
        Json tmpTreasureChestList = tmpTreeList.get("treasureChests");
        for(int i = 0; i < tmpTreeList.size(); i++)
            {
                toAdd.add(new Tree(tmpTreeList.get(i)));
            }
        for(int i = 0; i < tmpTreasureChestList.size(); i++)
            {
                toAdd.add(new TreasureChest(tmpTreasureChestList.get(i)));
            }
    }

    public void addTree(int x, int y)
    {
        Sprite t = new Tree(x, y);
        toAdd.add(t);
    }

    public void addBoomerang(int x, int y, int dirEnum)
    {
        Sprite b = new Boomerang(x, y, dirEnum);
        toAdd.add(b);
    }

    public void addTreasureChest(int x, int y)
    {
        Sprite c = new TreasureChest(x, y);
        toAdd.add(c);
    }

    public void clearSprites() {
        sprites.clear();
        sprites.add(link);
    }

    public void update()
    {
        Iterator<Sprite> iter1 = sprites.iterator();
        while(iter1.hasNext())
        {
            Sprite s1 = iter1.next();
            if(!s1.update())
                {
                toRemove.add(s1);
                continue;
                }
            for (Sprite s2 : sprites) {
                if (s1 == s2)
                    continue;
                if ((Sprite.isSpriteColliding(s1, s2))) {
                    s2.fixCollision(s1);
                    s1.fixCollision(s2);
                }

            }
        }
        sprites.addAll(toAdd);
        toAdd.clear();
        sprites.removeAll(toRemove);
        toRemove.clear();
    }

    public void unmarshal(Json ob)
    {
        sprites.clear();
        toRemove.clear();
        toAdd.clear();
        sprites.add(link);
        Json tmpTreeList = ob.get("trees");
        Json tmpTreasureChestList = ob.get("treasureChests");
        for(int i = 0; i < tmpTreeList.size(); i++)
        {
            sprites.add(new Tree(tmpTreeList.get(i)));
        }
        for(int i = 0; i < tmpTreasureChestList.size(); i++)
        {
            sprites.add(new TreasureChest(tmpTreasureChestList.get(i)));
        }

    }

    public static ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public static ArrayList<Sprite> getToAdd() {
        return toAdd;
    }

    public void tellLinkToMoveYoBody(String direction)
    {
        getLink().moveYoBody(direction);
    }

    public void removeTree(Sprite sprite) {
        toRemove.add(sprite);
    }

    public void removeTreasureChest(Sprite sprite) {
        toRemove.add(sprite);
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