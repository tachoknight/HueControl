package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Whitelist
{
	@JsonProperty("whitelist")
	private Map<String, WhitelistEntry>	whitelist;
}
