import java.awt.*;
import java.awt.image.BufferedImage;

public class TreasureChest extends Sprite {
    private static BufferedImage closedTreasureChest;
    private static BufferedImage openedTreasureChest;
    public static final int TREASURE_CHEST_WIDTH = 45, TREASURE_CHEST_HEIGHT = 45;
    public static final int RUPEE_WIDTH = 30, RUPEE_HEIGHT = 30;
    private boolean closed;
    private boolean isPaused = false;
    private int pauseFrameCounter = 0;
    public static final int PAUSE_DURATION = 80;
    public static final int RESUME_DURATION = 5;

    public TreasureChest(int x, int y){
        super(x, y, TREASURE_CHEST_WIDTH, TREASURE_CHEST_HEIGHT);
        valid = true;
        closed = true;
        if (closedTreasureChest == null) {
            closedTreasureChest = View.loadImage("images/treasurechest.png");
        }
        if (openedTreasureChest == null) {
            openedTreasureChest = View.loadImage("images/rupee.png");
        }
    }

    public TreasureChest(Json ob)
    {
        super((int)ob.getLong("x"), (int)ob.getLong("y"),
                TREASURE_CHEST_WIDTH, TREASURE_CHEST_HEIGHT);

        if (closedTreasureChest == null) {
            closedTreasureChest = View.loadImage("images/treasurechest.png");
        }
        if (openedTreasureChest == null) {
            openedTreasureChest = View.loadImage("images/rupee.png");
        }
    }

    @Override
    public boolean update()
    {
        closed = valid;
        if(!closed){
            framesSinceOpen++;
        }
        if(framesSinceOpen == PAUSE_DURATION) {
            Model.getSprites().remove(this);
        }
        return valid;
    }

    @Override
    public boolean isTreasureChest() {
        return true;
    }

    @Override
    public void drawYourself(Graphics g) {
        if (closed) {
            g.drawImage(closedTreasureChest, x - View.getCurrentRoomX(),
                    y - View.getCurrentRoomY(), TREASURE_CHEST_WIDTH,
                    TREASURE_CHEST_HEIGHT, null);
            System.out.println("Placed chest!");
        }else{
            g.drawImage(openedTreasureChest, x - View.getCurrentRoomX(),
                    y - View.getCurrentRoomY(), RUPEE_WIDTH,
                    RUPEE_HEIGHT, null);
        }
    }

    @Override
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);
        ob.add("closed", closed);

        return ob;
    }

    @Override
    public String toString() {
        return "";
    }
}
