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
    public static final int RIGHT_ENUM = 2, LEFT_ENUM = 1, UP_ENUM = 3, DOWN_ENUM = 0;

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
        int spriteCounter = 0;
        if(editMode && (e.getX() >= 0 && e.getX() <= 100 &&  e.getY() >= 0 && e.getY() <= 100)){
           view.incrementItemEnum();
        }
        if (editMode && addMapItem &&
                !(e.getX() >= 0 && e.getX() <= 100 &&  e.getY() >= 0 && e.getY() <= 100)) {
            for(int i = 0; i < Model.getSprites().size(); i++){
                Sprite sprite = Model.getSprites().get(i);
                if(sprite.amIClickingOnYou(e.getX() + View.getCurrentRoomX(),
                        e.getY() + View.getCurrentRoomY())){
                    spriteCounter++;
                }
            }
            if(spriteCounter == 0 && view.getItemEnum() == 0){
                model.addTree(Math.floorDiv(e.getX()+ View.getCurrentRoomX(), // create new tree with x and y,
                                Tree.TREE_WIDTH) * Tree.TREE_WIDTH,                                    // according to current coordinates.
                        Math.floorDiv(e.getY()+View.getCurrentRoomY(),
                                Tree.TREE_HEIGHT) * Tree.TREE_HEIGHT);
            }
            if(spriteCounter == 0 && view.getItemEnum() == 1){
                model.addTreasureChest(e.getX() + View.getCurrentRoomX(), // create new chest
                        e.getY() + View.getCurrentRoomY());
            }
        }

        if (editMode && !addMapItem &&
                !(e.getX() >= 0 && e.getX() <= 100 &&  e.getY() >= 0 && e.getY() <= 100)) {
            for(int i = 0; i < Model.getSprites().size(); i++) {
                Sprite currentSprite = Model.getSprites().get(i);
                if(view.getItemEnum() == 0 && currentSprite.isTree() &&
                        currentSprite.amIClickingOnYou(e.getX() + View.getCurrentRoomX(),
                        e.getY() + View.getCurrentRoomY())) {
                    model.removeTree(currentSprite);
                }
                if(view.getItemEnum() == 1 && currentSprite.isTreasureChest() &&
                        currentSprite.amIClickingOnYou(e.getX() + View.getCurrentRoomX(),
                        e.getY() + View.getCurrentRoomY())) {
                    model.removeTreasureChest(currentSprite);
                }
            }
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
                view.resetItemEnum();
                break;
            case KeyEvent.VK_R:
                addMapItem = false;
                break;
            case KeyEvent.VK_A:
                addMapItem = true;
                break;
            case KeyEvent.VK_C:
                if(editMode) {
                    model.clearSprites();
                }
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
            case KeyEvent.VK_SPACE:
                model.addBoomerang(model.getLink().getX(), model.getLink().getY(),
                        model.getLink().getCurrentLinkDirection());
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
        model.getLink().setPCoordinate(model.getLink().getX(), model.getLink().getY());
        System.out.println(Model.getSprites().size());
        if(keyRight)
        {
            model.tellLinkToMoveYoBody("right");
            model.getLink().updateCurrentLinkFrame();
            model.getLink().setCurrentLinkDirection(RIGHT_ENUM);

        }

        if(keyLeft)
        {
            model.tellLinkToMoveYoBody("left");
            model.getLink().updateCurrentLinkFrame();
            model.getLink().setCurrentLinkDirection(LEFT_ENUM);
        }

        if(keyUp)
        {
            model.tellLinkToMoveYoBody("up");
            model.getLink().updateCurrentLinkFrame();
            model.getLink().setCurrentLinkDirection(UP_ENUM);
        }

        if(keyDown)
        {
            model.tellLinkToMoveYoBody("down");
            model.getLink().updateCurrentLinkFrame();
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
                "Edit mode: " +  editMode + " Add map item: " + addMapItem + ", currentRoomX " +View.getCurrentRoomX()
                + ", currentRoomY " + View.getCurrentRoomY();
    }

    public void keyTyped(KeyEvent e)
    {    }

    public void actionPerformed(ActionEvent e) {

    }
}
