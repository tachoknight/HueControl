package tachoknight.wantstobe.anearlyriser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
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
		light1State.setOn(true);
		light1State.setBri(87);
		light1State.setHue(15);
		light1State.setSat(200);

		if (lf.setLightState(1, light1State) == false)
		{
			logger.error("Hmm...didn't set the light");
		}

		System.out.println("Hello World!");
	}
}
