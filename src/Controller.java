// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import java.awt.*;
import java.awt.event.*;


public class Controller implements ActionListener, MouseListener, KeyListener
{
    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private View view;
	private boolean keepGoing;
    private final Model model;
    private static boolean editMode;
    private static boolean addMapItem;
    private static final int RIGHT_ENUM = 2, LEFT_ENUM = 1, UP_ENUM = 3, DOWN_ENUM = 0;
	public Controller(Model m)
	{
        model = m;
		keepGoing = true;
        editMode = false;
        addMapItem = true;
	}


    public void setView(View v)
    {
        view = v;
    }

    public void mousePressed(MouseEvent e) {
        if (editMode && addMapItem) {
            if (!model.getTree().treeExists(e.getX() + View.getCurrentRoomX(), // check if a tree exists at the
                    e.getY()+View.getCurrentRoomY(), Model.getTrees())) {                     // specified spot
                model.addTree(e.getX(), e.getY());// if not, add a tree to our arraylist and
            }
        }
        if(editMode && !addMapItem && model.getTree().treeExists(e.getX() + View.getCurrentRoomX(),
                e.getY() + View.getCurrentRoomY(), Model.getTrees()))
        {
            model.removeTree(e.getX() + View.getCurrentRoomX(), e.getY() + View.getCurrentRoomY()); // removes tree where mouse clicks.
        }

    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_E:
                editMode = !editMode;
                addMapItem = true;
                break;
            case KeyEvent.VK_R:
                addMapItem = false;
                break;
            case KeyEvent.VK_A:
                addMapItem = true;
                break;
            case KeyEvent.VK_C:
                if(editMode)
                    model.clearTrees();
                break;
            case KeyEvent.VK_RIGHT:
                keyRight = true;
                break;
            case KeyEvent.VK_LEFT:
                keyLeft = true;
                break;
            case KeyEvent.VK_UP:
                keyUp = true;
                break;
            case KeyEvent.VK_DOWN:
                keyDown = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT:
                keyRight = false;
                break;
            case KeyEvent.VK_LEFT:
                keyLeft = false;
                break;
            case KeyEvent.VK_UP:
                keyUp = false;
                break;
            case KeyEvent.VK_DOWN:
                keyDown = false;
                break;
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_Q:
                System.exit(0);
        }

        char c = Character.toLowerCase(e.getKeyChar());
        switch(c)
        {
            case 'q':
                keepGoing = false;
                break;

            case 's':
                Json saveObject = model.marshal();
                saveObject.save("map.json");
                System.out.println("save map.json file!");
                break;

            case 'l':
                Json loadObject = Json.load("map.json");
                model.unmarshal(loadObject);
                System.out.println("load map.json file!");
                break;
        }
    }

    public boolean update()
    {

        if(keyRight)
        {
            model.tellLinkToMoveYoBody("right");
            model.getLink().updateLinkAnimationSequenceFrame();
            model.getLink().setCurrentLinkDirection(RIGHT_ENUM);

        }

        if(keyLeft)
        {
            model.tellLinkToMoveYoBody("left");
            model.getLink().updateLinkAnimationSequenceFrame();
            model.getLink().setCurrentLinkDirection(LEFT_ENUM);
        }

        if(keyUp)
        {
            model.tellLinkToMoveYoBody("up");
            model.getLink().updateLinkAnimationSequenceFrame();
            model.getLink().setCurrentLinkDirection(UP_ENUM);
        }

        if(keyDown)
        {
            model.tellLinkToMoveYoBody("down");
            model.getLink().updateLinkAnimationSequenceFrame();
            model.getLink().setCurrentLinkDirection(DOWN_ENUM);
        }

        return keepGoing;
    }

    public static boolean isAddMapItem()
    {
        return addMapItem;
    }

    public static boolean isEditMode()
    {
        return editMode;
    }

    @Override
    public String toString()
    {
        return "Kep states: UP " + keyUp + ", DOWN " + keyDown + ", LEFT " + keyLeft + ", RIGHT " + keyRight +
                "Edit mode: " +  editMode + " Add map item: " + addMapItem;
    }

    public void keyTyped(KeyEvent e)
    {    }

    public void actionPerformed(ActionEvent e) {

    }
}
