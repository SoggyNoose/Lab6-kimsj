import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class PrintBalanceTest {

	private static final String MY_NAME = "Sam";
	
	private String separator;
	private Date today;
	
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private ByteArrayInputStream in = new ByteArrayInputStream(MY_NAME.getBytes());
	
	private InputStream originalIn;
	private PrintStream originalOut;
	
	private static final String[] englishArgs = { "en", "US" };
	private static final String[] frenchArgs = { "fr", "FR" };
	private static final String[] germanArgs = { "de", "DE" };
	
	@Before
	public void setUp() {
		originalIn = System.in;
		originalOut = System.out;
		
		separator = System.getProperty("line.separator");
		today = new Date();
		
		System.setIn(in);
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void tearDown() {
		System.setIn(originalIn);
		System.setOut(originalOut);
	}
	
	@Test
	public void testEnglish() {
		PrintBalance.main(englishArgs);
		
		DateFormat date = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, new Locale(englishArgs[0], englishArgs[1]));
		
		// On a scale from 1-10 how wrong am I doing this?
		assertEquals("Hello World" + separator +
					 "Please enter your name" + separator +
					 "I am pleased to meet you " + MY_NAME + separator +
					 "As of : " + date.format(today) + separator +
					 "You owe the school $9,876,543.21" + separator +
					 "Good Bye" + separator, 
					 
					 out.toString());
	}
	
	@Test
	public void testFrench() {
		PrintBalance.main(frenchArgs);
		
		DateFormat date = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, new Locale(frenchArgs[0], frenchArgs[1]));
		
		assertEquals("Bonjour tout le monde" + separator +
				     "S'il vous plaît entrer votre nom" + separator +
				     "Je suis heureux de vous rencontrer " + MY_NAME + separator +
				     "comme d' : " + date.format(today) + separator +
				     "Vous devez l'école 9 876 543,21 €" + separator +
				     "au revoir" + separator, 
				 
				     out.toString());
	}
	
	@Test
	public void testGerman() {
		PrintBalance.main(germanArgs);
		
		DateFormat date = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, new Locale(germanArgs[0], germanArgs[1]));
		
		assertEquals("Hallo Welt" + separator +
				     "Bitte geben Sie Ihren Namen" + separator +
				     "Ich freue mich, Sie kennen zu lernen " + MY_NAME + separator +
				     "ab : " + date.format(today) + separator + // ...ab?
				     "Sie schulden die Schule 9.876.543,21 €" + separator +
				     "Auf Wiedersehen" + separator, 
				 
				     out.toString());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThrowsOnNoArguments() {
		String[] args = {  };
		PrintBalance.main(args);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThrowsOnOneArguments() {
		String[] args = { "en" };
		PrintBalance.main(args);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testThrowsOnThreeArguments() {
		String[] args = { "en", "EN", "eN" };
		PrintBalance.main(args);
	}
}
