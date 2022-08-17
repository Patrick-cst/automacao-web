package setup;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.itextpdf.text.DocumentException;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import evidencia.PdfGenerete;
import evidencia.TakeScreenshot;


public class Hooks {

	PdfGenerete pdf = new PdfGenerete();
	TakeScreenshot takeScreenshot = new TakeScreenshot();
	public static WebDriver driver;
	//public static ResourceBundle bundle = ResourceBundle.getBundle("project");
		
	@Before
	public void start_test(Scenario scenario) {          
		
//		System.setProperty("webdriver.chrome.driver", "E:\\Projetos-cbyk\\QA\\aut-start\\src\\test\\resources\\driver\\chromedriver.exe"); 
			
		System.setProperty("webdriver.chrome.driver", "src"+ File.separator +
				"test" + File.separator + "resources" + 	File.separator + 
				"driver" + File.separator + "chromedriver.exe"); 
		driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//driver.get(bundle.getString("env.url"));
		driver.get("https://automacaocombatista.herokuapp.com/treinamento/home");
		driver.manage().window().maximize();
	}	
	
	@After
	public void tear_down(Scenario scenario) throws InterruptedException, DocumentException, IOException, ParseException { 	
		takeScreenshot.takeScreenShot(""+scenario.getStatus()+"", null);
		pdf.createPdf(scenario.getName(), scenario.getStatus());
		driver.quit();
	}
		
	public static WebDriver get_driver() { 
		return driver;
	}
	
}


