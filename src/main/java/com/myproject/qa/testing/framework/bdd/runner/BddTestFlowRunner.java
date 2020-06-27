package com.myproject.qa.testing.framework.bdd.runner;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@CucumberOptions(features = "Features", 
				 glue = { "com/myproject/qa/sanity/bdd/stepdefs/" }, 
				 plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
							"json:target/cucumber-reports/CucumberTestReport.json", 
							"rerun:target/cucumber-reports/rerun.txt" 
						  },
				monochrome = true, 
				strict = true,
				dryRun = false)

public class BddTestFlowRunner{
    private TestNGCucumberRunner testNGCucumberRunner;
    private static List<String> featureFilesList;
    
    @BeforeClass(alwaysRun = true)
	@Parameters("featureFiles")
    public void setUpClass(String featureFiles) {
    	featureFilesList = new ArrayList<String>(Arrays.asList(featureFiles.split("\\|")));
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

 
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
    	String[] files = pickleWrapper.getPickleEvent().uri.split("/");
    	if(featureFilesList.contains(files[files.length-1]))
    		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
    }
   
    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }
   
}
