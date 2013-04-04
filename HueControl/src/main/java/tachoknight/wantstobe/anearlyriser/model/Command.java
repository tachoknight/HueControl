package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Command
{
	@JsonProperty("body")
	private Body	body;
	@JsonProperty("address")
	private String	address;
	@JsonProperty("method")
	private String	method;
}
