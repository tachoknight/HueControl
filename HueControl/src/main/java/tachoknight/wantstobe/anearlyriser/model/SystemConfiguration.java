package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class SystemConfiguration
{
	@JsonProperty("lights")
	private Map<String, LightsEntry>	lights;
	@JsonProperty("schedules")
	private Map<String, SchedulesEntry>	schedules;
	@JsonProperty("config")
	private Config						config;
	@JsonProperty("groups")
	private Map<String, Object>			groups;
	@JsonProperty("scenes")
	private Map<String, ScenesEntry>	scenes;
	
	
	public Map<String, ScenesEntry> getScenes()
	{
		return scenes;
	}

	public void setScenes(Map<String, ScenesEntry> scenes)
	{
		this.scenes = scenes;
	}

	public Map<String, LightsEntry> getLights()
	{
		return lights;
	}

	public void setLights(Map<String, LightsEntry> lights)
	{
		this.lights = lights;
	}

	public Map<String, SchedulesEntry> getSchedules()
	{
		return schedules;
	}

	public void setSchedules(Map<String, SchedulesEntry> schedules)
	{
		this.schedules = schedules;
	}

	public Config getConfig()
	{
		return config;
	}

	public void setConfig(Config config)
	{
		this.config = config;
	}

	public Map<String, Object> getGroups()
	{
		return groups;
	}

	public void setGroups(Map<String, Object> groups)
	{
		this.groups = groups;
	}

}
