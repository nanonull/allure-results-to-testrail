package conversion7;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Status;
import com.codepine.api.testrail.model.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import conversion7.model.AllureResultJsonPojo;
import conversion7.model.AllureToTestRailPojo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushToTestRailRun {

  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static final Logger LOGGER = LoggerFactory.getLogger(PushToTestRailRun.class);

  public static void main(String[] args) throws IOException {
    TestRail testRail = TestRail.builder(System.getenv("TESTRAIL_URL"),
        System.getenv("LOGIN"), System.getenv("PASSWORD"))
        .applicationName("RailQ").build();

    int projectId = Integer.parseInt(System.getenv("PROJECT_ID"));
    int runId = Integer.parseInt(System.getenv("RUN_ID"));

    List<ResultField> customResultFields = testRail.resultFields().list().execute();
    List<Test> runTests = testRail.tests().list(runId).execute();

    List<Status> statuses = testRail.statuses().list().execute();
    Map<String, Status> allureToTestrailStatus = new HashMap<>();
    allureToTestrailStatus.put("passed", statuses.stream()
        .filter(status -> status.getLabel().equalsIgnoreCase("passed")).findFirst().get());
    allureToTestrailStatus.put("failed", statuses.stream()
        .filter(status -> status.getLabel().equalsIgnoreCase("failed")).findFirst().get());
    allureToTestrailStatus.put("broken", statuses.stream()
        .filter(status -> status.getLabel().equalsIgnoreCase("blocked")).findFirst().get());
    Status unknownAllureStatus = statuses.stream()
        .filter(status -> status.getLabel().equalsIgnoreCase("retest")).findFirst().get();

    String absolutePathToAllureResults = System.getenv("ABSOLUTE_PATH_ALLURE_RESULTS");
    Collection<File> inputResultFiles = FileUtils.listFiles(
        new File(absolutePathToAllureResults), null, false);
    Map<String, AllureResultJsonPojo> allureResults = new LinkedHashMap<>();
    inputResultFiles.stream()
        .filter(file -> file.getName().endsWith("-result.json"))
        .forEach(file -> {
          try {
            AllureResultJsonPojo allureResultJson = GSON
                .fromJson(FileUtils.readFileToString(file, Charset.defaultCharset()),
                    AllureResultJsonPojo.class);
            allureResults.put(allureResultJson.getFullName(), allureResultJson);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });


    String absolutePathToAllureMapping = System.getenv("ABSOLUTE_PATH_ALLURE_MAPPING");
    AllureToTestRailPojo allureToTestRail = GSON.fromJson(FileUtils.readFileToString(
            new File(absolutePathToAllureMapping), Charset.defaultCharset()),
            AllureToTestRailPojo.class);

    Map<Integer, Result> testResultsOutput = new LinkedHashMap<>();
    allureToTestRail.getAllureTestNamesToTestRailIds().entrySet().forEach(testMapping -> {
      String testName = testMapping.getKey();
      List<Integer> railIds = testMapping.getValue();
      AllureResultJsonPojo allureResult = allureResults.get(testName);
      Status railStatus = allureToTestrailStatus.get(allureResult.getStatus());
      if (railStatus == null) {
        railStatus = unknownAllureStatus;
      }

      List<Test> testsByIds = runTests.stream()
          .filter(test -> railIds.contains(test.getCaseId()))
          .collect(Collectors.toList());
      Status finalRailStatus = railStatus;
      testsByIds.forEach(test -> {
        if (testResultsOutput.containsKey(test.getCaseId())) {
          throw new RuntimeException("Test was mapped twice: " + GSON.toJson(test));
        }
        testResultsOutput.put(test.getCaseId(), new Result()
                .setCaseId(test.getCaseId())
                .setStatusId(finalRailStatus.getId()));
      });

      LOGGER.info("Results: \n" + GSON.toJson(testResultsOutput.values()));
      testRail.results().addForCases(runId, new ArrayList<>(testResultsOutput.values()),
          customResultFields).execute();

    });

  }

}
