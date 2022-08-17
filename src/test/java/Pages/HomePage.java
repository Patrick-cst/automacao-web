package Pages;




import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import evidencia.TakeScreenshot;
import setup.Hooks;
import utils.Utils;



public class HomePage extends Hooks {
    
	
	
	TakeScreenshot takeScreenShot = new TakeScreenshot();
	
	public HomePage(WebDriver driver) { 
		PageFactory.initElements(driver, this);		
	}
	
	@FindBy(xpath = "//a[text()='Formulário']")
	private WebElement btnFormulario;
	
	
	public void clicarBotaoFormulario() throws InterruptedException, IOException {
		Utils.wait_element_clickable(btnFormulario, 3);
		takeScreenShot.takeScreenShot("DEU BOM", btnFormulario);
		btnFormulario.click();
	}
	
}
