package tachoknight.wantstobe.anearlyriser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tachoknight.wantstobe.anearlyriser.model.Body;
import tachoknight.wantstobe.anearlyriser.model.Command;
import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
import tachoknight.wantstobe.anearlyriser.model.SchedulesEntry;
import tachoknight.wantstobe.anearlyriser.model.State;
import tachoknight.wantstobe.anearlyriser.model.SystemConfiguration;

/**
 * Hello world!
 * 
 */
public class App
{
	private static final Logger	logger	= LoggerFactory.getLogger(App.class);

	public static void main(String[] args)
	{
		/*
		 * First we set up our lights factory
		 */
		LightsFactory lf = new LightsFactory("10.0.1.28", "newdeveloper");

		/* Get a dump of the configuration */
		SystemConfiguration sc = lf.getSystemConfiguration();
		logger.info(sc.getConfig().getName());

		/* Get a map of the available lights */
		Map<String, LightsEntry> lights = lf.getAllLights();
		/* And loop through 'em for yucks */
		for (Map.Entry<String, LightsEntry> entry : lights.entrySet())
		{
			String key = entry.getKey();
			LightsEntry value = entry.getValue();

			logger.info("Light " + key + " - " + value.getName());
		}

		/* Set a light */
		State light1State = new State();
		light1State.setOn(false);
		light1State.setBri(124);
		light1State.setHue(150);
		light1State.setSat(200);

		if (lf.setLightState(1, light1State) == false)
		{
			logger.error("Hmm...didn't set the light");
		}

		/* Scheduling demonstration */
		playWithSchedules(lf);

		System.out.println("Hello World!");
	}

	private static void playWithSchedules(LightsFactory lf)
	{
		{
			logger.info("Get the current schedule...");
			
			/* Get the current schedule */
			Map<String, SchedulesEntry> schedules = lf.getSchedules();

			for (Map.Entry<String, SchedulesEntry> entry : schedules.entrySet())
			{
				String key = entry.getKey();
				SchedulesEntry value = entry.getValue();

				logger.info("Schedule " + key
							+ " - \n\tName:"
							+ value.getName()
							+ "\n\tDescription:"
							+ value.getDescription()
							+ "\n\tDate and Time:"
							+ value.getTime()
							+ "\n\tCommand: "
							+ value.getCommand());
			}
		}

		/* Now let's schedule light #1 to turn on 1 minute from now */

		/* First let's turn off light #1 */
		State light1State = new State();
		light1State.setOn(false);

		if (lf.setLightState(1, light1State) == false)
		{
			logger.error("Hmm...didn't set the light for demonstrating scheduling");
		}

		/*
		 * And now we begin putting together the schedule object
		 */

		/* First the easy stuff */
		SchedulesEntry se = new SchedulesEntry();
		se.setName("Demo schedule whee");
		se.setDescription("Demonstrating a scheduled task");

		/*
		 * And configure the time to go a minute from now...
		 */

		/* Hue only works with times in UTC */
		DateTime dt = new DateTime(DateTimeZone.UTC);
		/* Now add a minute */
		DateTime newDT = dt.plusMinutes(1);
		
		/* And set the date and time in the format Hue wants */
		se.setTime(newDT.toString("YYYY-MM-dd'T'hh:mm:ss"));

		/*
		 * And now set the command we're going to execute, which is going to be
		 * to turn light #1 on to a bright level of 124 and a transition time of
		 * 200
		 */
		Command command = new Command();
		command.setAddress("/api/newdeveloper/lights/1/state");
		command.setMethod("PUT");
		Body body = new Body();
		body.setOn(true);
		body.setBri(124);
		/* According to the documentation, 10 = 1 second, so let's set it to go over a minute */
		body.setTransitiontime(10 * 60);
		
		/* And set the color in CIE */
		List<Double> cieXY = new ArrayList<Double>();
		cieXY.add(.470);
		cieXY.add(.450);
		body.setXy(cieXY);
		
		
		command.setBody(body);
		se.setCommand(command);

		/* And now actually set the schedule */
		lf.setSchedule(se);

		/*
		 * And for yucks, let's run through the schedule list again
		 */

		{
			logger.info("Now getting the new schedule...");
			
			/* Get the current schedule */
			Map<String, SchedulesEntry> schedules = lf.getSchedules();

			for (Map.Entry<String, SchedulesEntry> entry : schedules.entrySet())
			{
				String key = entry.getKey();
				SchedulesEntry value = entry.getValue();

				logger.info("Schedule " + key
							+ " - \n\tName:"
							+ value.getName()
							+ "\n\tDescription:"
							+ value.getDescription()
							+ "\n\tDate and Time:"
							+ value.getTime()
							+ "\n\tCommand: "
							+ value.getCommand().getBody());
			}
		}

	}
}
