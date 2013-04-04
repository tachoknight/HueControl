package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Body
{
	@JsonProperty("bri")
	private Integer			bri;
	@JsonProperty("transitiontime")
	private Integer			transitiontime;
	@JsonProperty("on")
	private Boolean			on;
	@JsonProperty("xy")
	private List<Double>	xy;
}
