// Thomas Hamilton
// 2 Oct. 2025
// Assignment 3 - Collision detection and debugging
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
    private final Model model;
    private final View view;
    private final Controller controller;
    private boolean keepGoing;

    private final static int windowWidth = 700;
    private final static int windowHeight = 500;
	public Game()
	{
        keepGoing = true;
        model = new Model();
        controller = new Controller(model);
        view = new View(controller, model);
        this.addKeyListener(controller);
        view.addMouseListener(controller);
		this.setTitle("A3 - Link vs The World");
		this.setSize(700, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args)
    {
		Game g = new Game();
        g.run();
	}

    public static int getWindowWidth() { return windowWidth; }

    public static int getWindowHeight() { return windowHeight; }

    @Override
    public String toString()
    {
        return "Game state: " + keepGoing + ", Game window width and height: "
                + getWindowWidth() + ", " + getWindowHeight();
    }
    public void run()
    {
        Json loadObject = Json.load("map.json");
        model.unmarshal(loadObject);
        System.out.println("load map.json file!");
        do
        {
            keepGoing = controller.update();
            view.repaint();
            model.fixCollision(Model.getTrees());
            Toolkit.getDefaultToolkit().sync(); // Updates screen

            // Go to sleep for 50 milliseconds
            try
            {
                //noinspection BusyWait
                Thread.sleep(50);
            } catch(Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
                System.exit(1);
            }
        }
        while(keepGoing);
    }
}