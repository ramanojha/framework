package com.myproject.qa.testing.framework.bdd.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;


@CucumberOptions(features = "Features", 
				 glue = { "com/myproject/qa/sanity/bdd/stepdefs/" }, 
				 plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
							"json:target/cucumber-reports/CucumberTestReport.json", 
							"rerun:target/cucumber-reports/rerun.txt" 
						  },
				monochrome = true, 
				strict = true,
				dryRun = false)

public class BddTestFlowRunner {

	private TestNGCucumberRunner testNGCucumberRunner;
	private static List<String> featureFilesList;

	@SuppressWarnings("unchecked")
	@BeforeClass(alwaysRun = true)
	@Parameters("featureFiles")
	public void setUpClass(String featureFiles) {
		featureFilesList = new ArrayList<String>(Arrays.asList(featureFiles.split("\\|")));
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		
	}

	@Test(groups = "cucumber", description = "Runs cucmber Features", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		if(featureFilesList.contains(cucumberFeature.getCucumberFeature().getPath()))
			testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void testDownClass() {
		testNGCucumberRunner.finish();
	}

}
