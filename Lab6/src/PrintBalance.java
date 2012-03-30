import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;


/**
 * TODO A simple class that needs to be localized
 *
 * @author mohan.
 *         Created Mar 27, 2011.
 */
public class PrintBalance{

	
	/**
	 * Simple Java Method that is crying out to be localized.
	 *
	 * @param args
	 */
	public static void main(String args[])
	{
		if (args == null || args.length != 2) {
			throw new IllegalArgumentException("Need both language and country code!");
		}
		
		String language = args[0];
		String country = args[1];
		
		Locale currentLocale = new Locale(language, country);
		ResourceBundle messages;
		
		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		
		Scanner scanInput = new Scanner(System.in);
		Date today = new Date();
		
		DateFormat date = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, currentLocale);
		NumberFormat number = NumberFormat.getCurrencyInstance(currentLocale);
		
		//Greeting
		System.out.println(messages.getString("greetings"));
		
		//Get User's Name
		System.out.println(messages.getString("inquiry"));
		String name = scanInput.nextLine();
		System.out.println(messages.getString("pleased") + name);
		
		//print today's date, balance and bid goodbye
		System.out.println(messages.getString("asof") + " : "+ date.format(today));
		System.out.println(messages.getString("youowe") + number.format(9876543.21));
		System.out.println(messages.getString("farewell"));
	}
}



