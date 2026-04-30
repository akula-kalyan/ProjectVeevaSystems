package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            // Create folder path
            String reportDir = "test-output/ExtentReports/";

            // Generate timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Report file path
            String reportPath = reportDir + "Report_" + timestamp + ".html";

            // Create reporter
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            // Configure report
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Results");

            // Create ExtentReports instance
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System info (important for reporting)
            extent.setSystemInfo("Tester", "Aditya Kopparthi");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}