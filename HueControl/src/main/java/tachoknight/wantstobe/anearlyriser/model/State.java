package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class State
{
	@JsonProperty("bri")
	private Integer			bri;
	@JsonProperty("effect")
	private String			effect;
	@JsonProperty("sat")
	private Integer			sat;
	@JsonProperty("reachable")
	private Boolean			reachable;
	@JsonProperty("alert")
	private String			alert;
	@JsonProperty("hue")
	private Integer			hue;
	@JsonProperty("colormode")
	private String			colormode;
	@JsonProperty("on")
	private Boolean			on;
	@JsonProperty("ct")
	private Integer			ct;
	@JsonProperty("xy")
	private List<Double>	xy;
}
