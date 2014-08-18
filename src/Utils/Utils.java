package Utils;

public class Utils {

	public static boolean sleep( long milliseconds )
	{
		try
		{
			Thread.sleep( milliseconds );
			
			return true;
		}
		catch ( InterruptedException e )
		{
			return false;
		}
	}
	
	
}
