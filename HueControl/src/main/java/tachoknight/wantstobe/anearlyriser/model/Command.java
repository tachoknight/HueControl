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

	public Body getBody()
	{
		return body;
	}

	public void setBody(Body body)
	{
		this.body = body;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

}
