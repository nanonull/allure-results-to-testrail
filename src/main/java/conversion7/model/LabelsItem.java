package conversion7.model;

import com.google.gson.annotations.SerializedName;

public class LabelsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;

	public String getName(){
		return name;
	}

	public String getValue(){
		return value;
	}
}