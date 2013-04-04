package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class WhitelistEntry
{
	@JsonProperty("last use date")
	private String	lastUseDate;
	@JsonProperty("create date")
	private String	createDate;
	@JsonProperty("name")
	private String	name;
}
