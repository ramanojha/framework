package com.myproject.qa.testing.framework.bdd.runner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
				dryRun = false,
				tags = "~@Smoke"
				)
//tags = {"@Smoke", "@Regression"} // AND Condition
//tags = {"@Smoke, @Regression"}   // OR Condition
//tags = {"~@Smoke, @Regression"}  // OR Condition where @Smoke will be ignored

public class BddTestRunner {
	
	private TestNGCucumberRunner testNGCucumberRunner;
	   
	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs cucmber Features", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
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
