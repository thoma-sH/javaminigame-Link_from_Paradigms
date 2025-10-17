import java.awt.*;
import java.awt.image.BufferedImage;

public class TreasureChest extends Sprite {
    private static BufferedImage closedTreasureChest;
    private static BufferedImage openedTreasureChest;
    public static final int TREASURE_CHEST_WIDTH = 45, TREASURE_CHEST_HEIGHT = 45;
    public static final int RUPEE_WIDTH = 30, RUPEE_HEIGHT = 30;
    private int framesSinceOpen;
    public static final int PAUSE_DURATION = 80;
    public static final int RESUME_DURATION = 5;
    private boolean closed;

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
        this.closed = ob.getBool("closed");
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
        if(!closed){
            framesSinceOpen++;
        }
        if(framesSinceOpen == PAUSE_DURATION) {
            valid = false;
        }
        return valid;
    }

    public int getFramesSinceOpen() {
        return framesSinceOpen;
    }

    @Override
    public void fixCollision(Sprite b)
    {
        if(b.isLink() || b.isBoomerang()){
            closed = false;
            setWidth(RUPEE_WIDTH);
            setHeight(RUPEE_HEIGHT);
            setX(x);
            setY(y);
        }
        if(b.isLink() && framesSinceOpen >= RESUME_DURATION)
            valid = false;
        if(b.isBoomerang() && !closed && framesSinceOpen >= RESUME_DURATION){
            valid = false;
        }
    }

    @Override
    public boolean isTreasureChest() {
        return true;
    }

    @Override
    public void drawYourself(Graphics g, int scrollX, int scrollY) {
        if (closed) {
            g.drawImage(closedTreasureChest, x - scrollX,
                    y - scrollY, TREASURE_CHEST_WIDTH,
                    TREASURE_CHEST_HEIGHT, null);
            System.out.println("Placed chest!");
        }else{
            g.drawImage(openedTreasureChest, x - scrollX,
                    y - scrollY, RUPEE_WIDTH,
                    RUPEE_HEIGHT, null);
        }
        System.out.println("Drew");
    }

    @Override
    public Json marshal() {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", width);
        ob.add("h", height);
        ob.add("closed", closed);
        ob.add("valid", valid);

        return ob;
    }

    @Override
    public String toString() {
        return "";
    }
}
