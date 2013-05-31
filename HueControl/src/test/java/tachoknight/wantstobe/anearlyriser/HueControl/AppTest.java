package tachoknight.wantstobe.anearlyriser.HueControl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import tachoknight.wantstobe.anearlyriser.LightsFactory;
import tachoknight.wantstobe.anearlyriser.model.Body;
import tachoknight.wantstobe.anearlyriser.model.Command;
import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
import tachoknight.wantstobe.anearlyriser.model.SchedulesEntry;
import tachoknight.wantstobe.anearlyriser.model.State;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
	private LightsFactory	lf	= new LightsFactory("10.0.1.28", "newdeveloper");

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName)
	{
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(AppTest.class);
	}

	/*
	 * Turn on light #1
	 */
	public void testTurnLight1On()
	{
		/* Set a light */
		State light1State = new State();
		light1State.setOn(true);

		/* Should return true if we can turn light 1 on */
		assertTrue(lf.setLightState(1, light1State));

		/* And verify that the system thinks light 1 is on */
		assertTrue(lf.getLight(1).getState().getOn());
	}

	public void setScheduleForLight3()
	{
		final Integer LIGHT3 = 3;

		SchedulesEntry se = new SchedulesEntry();
		se.setName("Morning Rising");
		se.setDescription("Rise and shine");

		/* Hue only works with times in UTC */
		DateTime currentDate = new DateTime().plusDays(1);
		
		DateTime dt = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth(), 6, 0, 0);
		DateTime newDT = dt.withZone(DateTimeZone.UTC);

		/* And set the date and time in the format Hue wants */
		se.setTime(newDT.toString("YYYY-MM-dd'T'hh:mm:ss"));

		
		Command command = new Command();
		command.setAddress("/api/newdeveloper/lights/" + LIGHT3 + "/state");
		command.setMethod("PUT");
		Body body = new Body();
		body.setOn(true);
		body.setBri(255);

		/*
		 * According to the documentation, 10 = 1 second, so let's set it to go
		 * for an hour
		 */
		body.setTransitiontime(10 * 60 * 60);

		/* And set the color in CIE */
		List<Double> cieXY = new ArrayList<Double>();
		cieXY.add(.651);
		cieXY.add(.390);
		body.setXy(cieXY);

		command.setBody(body);
		se.setCommand(command);

		/* And now actually set the schedule */
		assertTrue(lf.setSchedule(se));

		/* And turn light 3 off */

		/* Set a light */
		State light3State = new State();
		light3State.setOn(false);

		assertTrue(lf.setLightState(LIGHT3, light3State));
	}
	

}
