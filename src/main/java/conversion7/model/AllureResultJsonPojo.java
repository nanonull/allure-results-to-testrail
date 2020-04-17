package conversion7.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AllureResultJsonPojo {

  @SerializedName("attachments")
  private List<Object> attachments;

  @SerializedName("start")
  private long start;

  @SerializedName("fullName")
  private String fullName;

  @SerializedName("steps")
  private List<Object> steps;

  @SerializedName("uuid")
  private String uuid;

  @SerializedName("labels")
  private List<LabelsItem> labels;

  @SerializedName("stage")
  private String stage;

  @SerializedName("stop")
  private long stop;

  @SerializedName("historyId")
  private String historyId;

  @SerializedName("name")
  private String name;

  @SerializedName("statusDetails")
  private StatusDetails statusDetails;

  @SerializedName("links")
  private List<Object> links;

  @SerializedName("parameters")
  private List<ParametersItem> parameters;

  @SerializedName("status")
  private String status;

  public List<Object> getAttachments() {
    return attachments;
  }

  public long getStart() {
    return start;
  }

  public String getFullName() {
    return fullName;
  }

  public List<Object> getSteps() {
    return steps;
  }

  public String getUuid() {
    return uuid;
  }

  public List<LabelsItem> getLabels() {
    return labels;
  }

  public String getStage() {
    return stage;
  }

  public long getStop() {
    return stop;
  }

  public String getHistoryId() {
    return historyId;
  }

  public String getName() {
    return name;
  }

  public StatusDetails getStatusDetails() {
    return statusDetails;
  }

  public List<Object> getLinks() {
    return links;
  }

  public List<ParametersItem> getParameters() {
    return parameters;
  }

  public String getStatus() {
    return status;
  }
}