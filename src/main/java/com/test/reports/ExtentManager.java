package com.test.reports;


//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html


import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
//import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports extent;
	public static String screenShotFolderPath;

	public static ExtentReports getInstance(String reportPath) {
		if (extent == null) {
			String fileName= "\\Report.html";
			String scr= "\\screenshots\\";
			
			Date d=new Date();
			String folderName=d.toString().replace(":", "_").replace(" ", "_");
			new File (reportPath+folderName+scr).mkdirs();
			System.out.println(reportPath+folderName+fileName);
			screenShotFolderPath=reportPath+folderName+scr;
			createInstance(reportPath+folderName+fileName);
			
			
			
			//String fileName= "\\Report.html";
			//System.out.println(reportPath+"Report_"+folderName+".html");
		}
		return extent;
	}
	
	public static ExtentReports createInstance(String fileName)
	{
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("RMD Web Services Testing");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Test Summary Report");
		htmlReporter.containsStatus(Status.PASS);
		htmlReporter.containsStatus(Status.FAIL);
		htmlReporter.containsStatus(Status.SKIP);
		
		
		
		extent = new ExtentReports();

		

		extent.attachReporter(htmlReporter);
		return extent;
		
	}
}
