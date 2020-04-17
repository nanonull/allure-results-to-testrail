package conversion7.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class AllureToTestRailPojo {

  @SerializedName("allureTestNamesToTestRailIds")
  private Map<String, List<Integer>> allureTestNamesToTestRailIds;


  public Map<String, List<Integer>> getAllureTestNamesToTestRailIds() {
    return allureTestNamesToTestRailIds;
  }
}