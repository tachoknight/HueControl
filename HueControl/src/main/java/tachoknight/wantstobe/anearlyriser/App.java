package tachoknight.wantstobe.anearlyriser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
import tachoknight.wantstobe.anearlyriser.model.SystemConfiguration;

/**
 * Hello world!
 * 
 */
public class App
{
	public static void main(String[] args)
	{
		// HueConfiguration hc = new HueConfiguration("10.0.1.28", "javaRemote",
		// "javaRemote");
		// BulbFactory bf = new BulbFactory(hc);

		HttpClient client = new DefaultHttpClient();

		HttpGet request = new HttpGet("http://" + "10.0.1.28" + "/api/" + "newdeveloper");

		try
		{
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
																					.getContent()));

			ObjectMapper mapper = new ObjectMapper();
			SystemConfiguration sc = mapper.readValue(rd, SystemConfiguration.class);

			System.out.println(sc.getConfig().getName());
			Map<String, LightsEntry> lights = sc.getLights();
			
			LightsEntry light1 = lights.get("1");
			System.out.println(light1.getName() + " - " + light1.getState().getBri());

		}
		catch (IOException e)
		{
			System.err.println("Drat, got " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally
		{
			request.releaseConnection();
			client.getConnectionManager().closeExpiredConnections();
		}
		
		System.out.println("Hello World!");
	}
}
