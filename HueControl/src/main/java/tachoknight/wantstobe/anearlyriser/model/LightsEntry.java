package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class LightsEntry
{
	@JsonProperty("name")
	private String				name;
	@JsonProperty("state")
	private State				state;
	@JsonProperty("modelid")
	private String				modelid;
	@JsonProperty("swversion")
	private Integer				swversion;
	@JsonProperty("type")
	private String				type;
	@JsonProperty("pointsymbol")
	private Map<String, String>	pointsymbol;
}
