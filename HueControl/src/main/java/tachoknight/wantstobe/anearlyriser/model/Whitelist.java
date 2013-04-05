package tachoknight.wantstobe.anearlyriser.model;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Whitelist
{
	@JsonProperty("whitelist")
	private Map<String, WhitelistEntry>	whitelist;

	public Map<String, WhitelistEntry> getWhitelist()
	{
		return whitelist;
	}

	public void setWhitelist(Map<String, WhitelistEntry> whitelist)
	{
		this.whitelist = whitelist;
	}

	/*
	 * The JSON we get back here uses a dynamic key to a value object, which at
	 * least Jackson doesn't like (or I didn't find a good way to handle thi
	 * automagically, so we do it manually here)
	 */
	@JsonAnySetter
	public void handleUnknown(String key, Object value)
	{
		/* Object is a KV Map */
		LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) value;
		WhitelistEntry entry = new WhitelistEntry();
		entry.setCreateDate((String) map.get("create date"));
		entry.setLastUseDate((String) map.get("last use date"));
		entry.setName((String) map.get("name"));
		
		/*
		 * And because we have to deal with all this manually, so we also have
		 * to instantiate the map
		 */
		whitelist = new HashMap<String, WhitelistEntry>();
		whitelist.put(key, entry);
	}
}
