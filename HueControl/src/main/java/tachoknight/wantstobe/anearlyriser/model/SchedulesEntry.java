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

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Command getCommand()
	{
		return command;
	}

	public void setCommand(Command command)
	{
		this.command = command;
	}

}
