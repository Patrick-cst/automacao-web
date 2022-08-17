package Pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import evidencia.TakeScreenshot;
import utils.Utils;

public class CriarUsuariosPage {

	
TakeScreenshot takeScreenShot = new TakeScreenshot();
	
	public CriarUsuariosPage(WebDriver driver) { 
		PageFactory.initElements(driver, this);		
	}
	
	@FindBy(xpath = "//a[text()='Criar Usuários']")
	private WebElement btnCriarUsuarios;
	
	@FindBy(id = "user_name")
	private WebElement cmpNome;
	
	@FindBy(id = "user_lastname")
	private WebElement cmpUltimoNome;
	
	@FindBy(name = "commit")
	private WebElement btnCriar;
	
	@FindBy(id = "user_email")
	private WebElement cmpEmail;
	
	@FindBy(id = "notice")
	private WebElement txtUsuarioCriadoComSucesso;
	
	public void clicarBotaoCriarUsuario() throws InterruptedException, IOException {
		Utils.wait_element_clickable(btnCriarUsuarios, 3);
		takeScreenShot.takeScreenShot("clicar no botão 'CRIAR USUARIOS'", btnCriarUsuarios);
		btnCriarUsuarios.click();
	}
	
	public void inserirNome(String nome) throws IOException {
		Utils.wait_element_clickable(cmpNome, 4);
		cmpNome.sendKeys(nome);
		takeScreenShot.takeScreenShot("Inserir 'Nome'", cmpNome);
	}
	
	public void inserirUltimoNome(String ultimoNome) throws IOException {
		Utils.wait_element_clickable(cmpUltimoNome, 4);
		cmpUltimoNome.sendKeys(ultimoNome);
		takeScreenShot.takeScreenShot("Inserir 'Ultimo Nome'", cmpUltimoNome);
	}
	
	public void inserirEmail(String email) {
		Utils.wait_element_clickable(cmpEmail, 0);
		cmpEmail.sendKeys(email);
	}
	
	public void clicarBotaoCriar() {
		Utils.wait_element_clickable(btnCriar, 3);
		btnCriar.click();
	}
	
	public String validarMensagemUsuarioCriadoComSucesso() {
		return txtUsuarioCriadoComSucesso.getText().trim();	
	}
}
