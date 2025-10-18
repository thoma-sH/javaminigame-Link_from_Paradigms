// Thomas Hamilton
// 17 Oct. 2025
// Assignment 4 - Polymorphism
import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
    private boolean keepGoing;
    private Model model;
    private View view;
    private Controller controller;
    public final static int WINDOW_WIDTH = 700;
    public final static int WINDOW_HEIGHT = 500;

	public Game()
	{
        keepGoing = true;
        model = new Model();
        controller = new Controller(model);
        view = new View(controller, model);
        this.addKeyListener(controller);
        view.addMouseListener(controller);
		this.setTitle("A4 - Polymorphism");
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

    @Override
    public String toString()
    {
        return "Game state: " + keepGoing + ", Game window width and height: "
                + WINDOW_WIDTH + ", " + WINDOW_HEIGHT;
    }
    public void run()
    {
        Json loadObject = Json.load("map.json");
        model.unmarshal(loadObject);
        System.out.println("loaded map.json file!");
        do
        {
            keepGoing = controller.update();
            view.repaint();
            model.update();
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