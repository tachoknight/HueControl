package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Swupdate
{
	@JsonProperty("text")
	private String	text;
	@JsonProperty("notify")
	private Boolean	notify;
	@JsonProperty("updatestate")
	private Integer	updatestate;
	@JsonProperty("url")
	private String	url;
}
