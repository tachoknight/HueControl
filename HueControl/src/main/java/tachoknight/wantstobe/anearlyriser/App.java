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
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
import tachoknight.wantstobe.anearlyriser.model.State;
import tachoknight.wantstobe.anearlyriser.model.SystemConfiguration;

/**
 * Hello world!
 * 
 */
public class App
{
	public static void main(String[] args)
	{
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

			//State light1State = light1.getState();
			State light1State = new State();
			light1State.setOn(true);
			light1State.setBri(87);
			light1State.setHue(15);
			light1State.setSat(200);

			HttpPut statePut = new HttpPut("http://" + "10.0.1.28"
											+ "/api/"
											+ "newdeveloper/lights/1/state");

			ObjectMapper lightMapper = new ObjectMapper();

			FilterProvider filters = new SimpleFilterProvider().addFilter(	"stateFilter",
																			SimpleBeanPropertyFilter.serializeAllExcept("colormode","reachable"));

			String light1JSON = lightMapper.writer(filters).writeValueAsString(light1State);
			
			System.out.println("--> " + light1JSON);
			StringEntity entity = new StringEntity(light1JSON);
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(	HTTP.CONTENT_TYPE,
														"application/json;charset=UTF-8"));
			statePut.setEntity(entity);

			HttpResponse light1Response = client.execute(statePut);
			HttpEntity entity1 = light1Response.getEntity();
			System.out.println(light1Response.getStatusLine());

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
