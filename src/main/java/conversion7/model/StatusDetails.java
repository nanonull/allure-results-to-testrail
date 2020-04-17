package conversion7.model;

import com.google.gson.annotations.SerializedName;

public class StatusDetails{

	@SerializedName("known")
	private boolean known;

	@SerializedName("flaky")
	private boolean flaky;

	@SerializedName("muted")
	private boolean muted;

	public boolean isKnown(){
		return known;
	}

	public boolean isFlaky(){
		return flaky;
	}

	public boolean isMuted(){
		return muted;
	}
}