package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class SchedulesEntry
{
	@JsonProperty("time")
	private String	time;
	@JsonProperty("description")
	private String	description;
	@JsonProperty("name")
	private String	name;
	@JsonProperty("command")
	private Command	command;
}
