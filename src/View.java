// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class View extends JPanel
{
    private final Model model;
    private static int currentRoomX, currentRoomY;
    private int itemEnum;

    public View(Controller c, Model m)
	{
        model = m;
        c.setView(this);
	}

    public static BufferedImage loadImage(String filename)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(filename));
        } catch (Exception e){
            System.err.println("Error loading image: " + filename);
            e.printStackTrace(System.err);
            System.exit(1);
        }
        return image;
    }

    public void moveCameraLeft()
    {
        currentRoomX -= Game.getWindowWidth();
        System.out.println("Move Left. Current Room X: " + currentRoomX +
                " Current Room Y: " + currentRoomY);
    }

    public void moveCameraRight()
    {
        currentRoomX += Game.getWindowWidth();
        System.out.println("Move Right. Current Room X: " + currentRoomX +
                " Current Room Y: " + currentRoomY);
    }

    public void moveCameraUp()
    {
        currentRoomY -= Game.getWindowHeight();
        System.out.println("Move Up. Current Room X: " + currentRoomX +
                " Current Room Y: " + currentRoomY);
    }

    public void moveCameraDown()
    {
        currentRoomY += Game.getWindowHeight();
        System.out.println("Move Down. Current Room X: " + currentRoomX +
                " Current Room Y: " + currentRoomY);
    }

    public void updateRoomView()
    {
        if(model.getLink().getX() >= (currentRoomX + Game.getWindowWidth()))
            moveCameraRight();
        if(model.getLink().getX() <= currentRoomX)
            moveCameraLeft();
        if((model.getLink().getY() + model.getLink().getHeight()) < currentRoomY)
            moveCameraUp();
        if((model.getLink().getY() + model.getLink().getHeight()) >= (currentRoomY + Game.getWindowHeight()))
            moveCameraDown();
    }

    public void paintComponent(Graphics g)
    {
        updateRoomView();
        g.setColor(new Color(72, 152, 72));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for(int i = 0; i < Model.getSprites().size(); i++){
            Model.getSprites().get(i).drawYourself(g, currentRoomX, currentRoomY);
        }

        if(Controller.isEditMode() && Controller.isAddMapItem())
        {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 100, 100);
        }

        if(Controller.isEditMode() && !Controller.isAddMapItem())
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 100, 100);
        }
        if(Controller.isEditMode())
        {
            if(getItemEnum() == 0){
                Tree t = new Tree(currentRoomX, currentRoomY);
                t.setX(currentRoomX + 18);
                t.setY(currentRoomY + 10);
                t.setWidth(20);
                t.setHeight(20);
                t.drawYourself(g, currentRoomX, currentRoomY);
            }
            if(getItemEnum() == 1){
                TreasureChest  tc = new TreasureChest(currentRoomX, currentRoomY);
                tc.setX(currentRoomX + 18);
                tc.setY(currentRoomY + 20);
                tc.drawYourself(g, currentRoomX, currentRoomY);
            }
        }
    }

    public static int getCurrentRoomX() { return currentRoomX; }

    public static int getCurrentRoomY() { return currentRoomY; }

    public int getItemEnum() {
        return itemEnum % 2;
    }

    public void incrementItemEnum()
    {
        itemEnum++;
    }

    public void resetItemEnum()
    {
        itemEnum = 0;
    }


    @Override
    public String toString()
    {
        return "Current Room Coords: " +  currentRoomX + ", " + currentRoomY;
    }
}

