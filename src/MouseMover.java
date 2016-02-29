import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;


/*
 * This code could be improved by looking for EVENTS rather than mouse movements
 * for even if the mouse doesn't move, but a button is clicked, the screen will
 * not sleep.
 * However, as the code is written at the moment the mouse will still be jiggled
 * even if other buttons have been pressed but the mouse has not been moved.
*/
public class MouseMover {
	//most displays have a minimum of 60 seconds before it goes into sleep mode
	//this code then jiggles the mouse at a rate of once every 30 seconds, however
	//this can be altered due to your personal needs
	
	public static final int NUMBER_OF_MILLISECONDS = 30000;
	
	
	//the mouse jiggles to a random position no greater than 1/16th of the screen size (this allows
	//this code to be used effectively for screens of differing sizes)
	public static final double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int MAX_X = (int)(SCREEN_WIDTH/16);
	public static final int MAX_Y = (int)(SCREEN_HEIGHT/16);

	
	public static Point oldMouseLocation;
	public static Point newMouseLocation;
	
	public static void main(String[] args) throws Exception {
		
		oldMouseLocation = newMouseLocation = MouseInfo.getPointerInfo().getLocation();
		while(true)
		{
			oldMouseLocation = MouseInfo.getPointerInfo().getLocation();
			Thread.sleep(NUMBER_OF_MILLISECONDS);
			newMouseLocation = MouseInfo.getPointerInfo().getLocation();
			
			//if the mouse has moved there is no reason to jiggle it and cause undue disruption
			if(!hasMouseMoved(oldMouseLocation, newMouseLocation))
			{
				jiggleMouse(newMouseLocation);
			}
		}
	}
	
	public static boolean hasMouseMoved(Point oldLocation, Point newLocation)
	{
		if((oldLocation.getX() == newLocation.getX()) && (oldLocation.getY() == newLocation.getY()))
		{
			return false;
		}
		return true;
	}
	
	public static void jiggleMouse(Point mouseLocation) throws Exception {
		Robot robot = new Robot();
		int mouseX = (int)mouseLocation.getX();
		int mouseY = (int)mouseLocation.getY();
		//check to make sure subtracting X wouldn't put the mouse off the screen
		if(mouseX - MAX_X >= 0)
		{
			robot.mouseMove(mouseX - MAX_X, mouseY);
		}
		
		//if subtracting X puts it off the screen then simply add X instead
		else
		{
			robot.mouseMove(mouseX + MAX_X, mouseY);
		}
		
		//move back to the original position so as to not be as noticable
		robot.mouseMove(mouseX, mouseY);
	}
}
