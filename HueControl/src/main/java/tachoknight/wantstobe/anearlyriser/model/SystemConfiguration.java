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
}
