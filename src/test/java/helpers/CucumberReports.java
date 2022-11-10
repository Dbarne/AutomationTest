package helpers;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CucumberReports {

    public static void generateReports() {
        File dir = new File("target/cucumber-parallel");
        File[] fileList = dir.listFiles((dir1, name) -> name.endsWith(".json"));
        File reportDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        for (File file : Objects.requireNonNull(fileList)) {
            String absolutePath = file.getAbsolutePath();
            jsonFiles.add(absolutePath);
            System.out.print(jsonFiles);
        }
        String buildNumber = "1";
        String projectName = "TechnicalTest";

        Configuration configuration = new Configuration(reportDirectory, projectName);
        // optional configuration
        //configuration.setParallelTesting(parallelTesting);
        configuration.setBuildNumber(buildNumber);
        // additional metadata presented on main page
        configuration.addClassifications("User Name", System.getProperty("user.name"));
        configuration.addClassifications("Time Zone", System.getProperty("user.timezone"));
        configuration.addClassifications("OS", System.getProperty("os.name"));
        //configuration.addClassifications("Device", "Android");
        configuration.addClassifications("Device", System.getProperty("profile"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
