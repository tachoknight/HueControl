package tachoknight.wantstobe.anearlyriser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tachoknight.wantstobe.anearlyriser.model.LightsEntry;
import tachoknight.wantstobe.anearlyriser.model.SchedulesEntry;
import tachoknight.wantstobe.anearlyriser.model.State;
import tachoknight.wantstobe.anearlyriser.model.SystemConfiguration;

public class LightsFactory
{
	private static final Logger	logger	= LoggerFactory.getLogger(LightsFactory.class);

	private String				ipAddress;
	private String				userName;

	public LightsFactory(String ipAddress, String userName)
	{
		this.ipAddress = ipAddress;
		this.userName = userName;
	}

	private String getBaseUrl()
	{
		return "http://" + ipAddress + "/api/" + userName;
	}

	private boolean put(String url, String jsonToPut)
	{
		logger.debug("URL: " + url + "\nJSON:" + jsonToPut);

		HttpClient client = new DefaultHttpClient();
		HttpPut putObject = new HttpPut(url);

		try
		{
			StringEntity entity = new StringEntity(jsonToPut);
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(	HTTP.CONTENT_TYPE,
														"application/json;charset=UTF-8"));
			putObject.setEntity(entity);

			HttpResponse response = client.execute(putObject);

			logger.debug("Response in put got: " + response.getStatusLine());

			return true;
		}
		catch (IOException e)
		{
			logger.error("Hmm, got an error: " + e.getLocalizedMessage(), e);
		}
		finally
		{
			putObject.releaseConnection();
			client.getConnectionManager().closeExpiredConnections();
		}

		logger.warn("Returning false to caller!");
		return false;
	}

	private boolean post(String url, String jsonToPut)
	{
		logger.debug("URL: " + url + "\nJSON:" + jsonToPut);

		HttpClient client = new DefaultHttpClient();
		HttpPost postObject = new HttpPost(url);

		try
		{
			StringEntity entity = new StringEntity(jsonToPut);
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(	HTTP.CONTENT_TYPE,
														"application/json;charset=UTF-8"));
			postObject.setEntity(entity);

			HttpResponse response = client.execute(postObject);

			logger.debug("Response in put got: " + response.getStatusLine());

			return true;
		}
		catch (IOException e)
		{
			logger.error("Hmm, got an error: " + e.getLocalizedMessage(), e);
		}
		finally
		{
			postObject.releaseConnection();
			client.getConnectionManager().closeExpiredConnections();
		}

		logger.warn("Returning false to caller!");
		return false;
	}

	private String get(String url)
	{
		logger.debug("Will use " + url + " for requests to the Hue");
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		try
		{
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
																					.getContent()));

			StringBuilder responseJSON = new StringBuilder();

			String line = null;
			while ((line = rd.readLine()) != null)
			{
				responseJSON.append(line);
			}

			return responseJSON.toString();
		}
		catch (IOException e)
		{
			logger.error("Hmm, got an error: " + e.getLocalizedMessage(), e);
		}
		finally
		{
			request.releaseConnection();
			client.getConnectionManager().closeExpiredConnections();
		}

		logger.warn("Returning null to caller!");
		return null;
	}

	public boolean setLightState(Integer numOfLight, State state)
	{
		String lightURL = getBaseUrl() + "/lights/" + numOfLight + "/state";

		try
		{
			ObjectMapper stateMapper = new ObjectMapper();

			/*
			 * Certain properties are read-only and must be excluded from the
			 * json we send to the server
			 */
			FilterProvider filters = new SimpleFilterProvider().addFilter(	"stateFilter",
																			SimpleBeanPropertyFilter.serializeAllExcept("colormode",
																														"reachable"));
			String stateJSON = stateMapper.writer(filters).writeValueAsString(state);

			return put(lightURL, stateJSON);
		}
		catch (JsonGenerationException e)
		{
			logger.error("Got a JsonGenerationException: " + e.getLocalizedMessage(), e);
		}
		catch (JsonMappingException e)
		{
			logger.error("Got a JsonMappingException: " + e.getLocalizedMessage(), e);
		}
		catch (IOException e)
		{
			logger.error("Got an IOException: " + e.getLocalizedMessage(), e);
		}

		return false;
	}

	/**
	 * Gets a full dump of the Hue configuration, including all lights,
	 * authorized devices, etc.
	 * 
	 * @return A {@link SystemConfiguration} object
	 */
	public SystemConfiguration getSystemConfiguration()
	{
		try
		{
			String rd = get(getBaseUrl());

			if (rd != null)
			{
				ObjectMapper mapper = new ObjectMapper();

				return mapper.readValue(rd, SystemConfiguration.class);
			}
		}
		catch (JsonParseException e)
		{
			logger.error("Got a JsonParserException: " + e.getLocalizedMessage(), e);
		}
		catch (JsonMappingException e)
		{
			logger.error("Got a JsonMappingException: " + e.getLocalizedMessage(), e);
		}
		catch (IOException e)
		{
			logger.error("Got an IOException: " + e.getLocalizedMessage(), e);
		}

		logger.warn("Returning null to caller!");
		return null;
	}

	/**
	 * Gets a map of light names (e.g. 1, 2, 3) to the light objects (of type
	 * {@link LightsEntry}.
	 * 
	 * @return A map of Strings (Light Number) to {@link LightsEntry} or null
	 */
	public Map<String, LightsEntry> getAllLights()
	{
		return getSystemConfiguration().getLights();
	}

	/**
	 * Gets a {@link LightsEntry} object that contains all the information about
	 * that particular light
	 * 
	 * @param light
	 *            number
	 * @return A {@link LightsEntry} object or null
	 */
	public LightsEntry getLight(Integer numOfLight)
	{
		return getAllLights().get(String.valueOf(numOfLight));
	}

	public Map<String, SchedulesEntry> getSchedules()
	{
		return getSystemConfiguration().getSchedules();
	}

	public boolean setSchedule(SchedulesEntry se)
	{
		String scheduleURL = getBaseUrl() + "/schedules";
		try
		{
			ObjectMapper stateMapper = new ObjectMapper();

			String stateJSON = stateMapper.writeValueAsString(se);

			return post(scheduleURL, stateJSON);
		}
		catch (JsonGenerationException e)
		{
			logger.error("Got a JsonGenerationException: " + e.getLocalizedMessage(), e);
		}
		catch (JsonMappingException e)
		{
			logger.error("Got a JsonMappingException: " + e.getLocalizedMessage(), e);
		}
		catch (IOException e)
		{
			logger.error("Got an IOException: " + e.getLocalizedMessage(), e);
		}

		logger.warn("Returning false to caller");
		return false;
	}
}
