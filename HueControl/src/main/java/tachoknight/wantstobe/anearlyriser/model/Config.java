package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Config
{
	@JsonProperty("portalservices")
	private Boolean		portalservices;
	@JsonProperty("gateway")
	private String		gateway;
	@JsonProperty("mac")
	private String		mac;
	@JsonProperty("swversion")
	private Integer		swversion;
	@JsonProperty("linkbutton")
	private Boolean		linkbutton;
	@JsonProperty("ipaddress")
	private String		ipaddress;
	@JsonProperty("proxyport")
	private Integer		proxyport;
	@JsonProperty("swupdate")
	private Swupdate	swupdate;
	@JsonProperty("netmask")
	private String		netmask;
	@JsonProperty("name")
	private String		name;
	@JsonProperty("dhcp")
	private Boolean		dhcp;
	@JsonProperty("UTC")
	private String		UTC;
	@JsonProperty("proxyaddress")
	private String		proxyaddress;
	@JsonProperty("whitelist")
	private Whitelist	whitelist;
}
