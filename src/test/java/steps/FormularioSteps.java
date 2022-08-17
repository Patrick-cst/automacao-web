package steps;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;

import com.itextpdf.text.DocumentException;

import Pages.CriarUsuariosPage;
import Pages.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import evidencia.PdfGenerete;
import evidencia.TakeScreenshot;
import io.cucumber.datatable.DataTable;
import setup.Hooks;
import utils.Utils;



public class FormularioSteps {
	
	CriarUsuariosPage  criarUsuariosPage = new CriarUsuariosPage(Hooks.get_driver());
	TakeScreenshot takeScreenShot = new TakeScreenshot();
	PdfGenerete pdf = new PdfGenerete();
	HomePage homePage = new HomePage(Hooks.get_driver());
	
	@Given("que esteja na tela de formulario")
	public void que_esteja_na_tela_de_formulario() throws IOException, AWTException, InterruptedException {
		homePage.clicarBotaoFormulario();
	}
	
	@When("preencher o formulario")
	public void preencher_o_formulario() throws IOException, AWTException, InterruptedException {
		criarUsuariosPage.clicarBotaoCriarUsuario();
		criarUsuariosPage.inserirNome("TESTE");
		criarUsuariosPage.inserirUltimoNome("VALDO");
		criarUsuariosPage.inserirEmail(Utils.geraEmailAleatoria());
		criarUsuariosPage.clicarBotaoCriar();
	}

	@Then("validar formulario preenchido")
	public void validar_formulario_preenchido() throws IOException, AWTException, InterruptedException {
		assertEquals("Usuário Criado com sucesso", criarUsuariosPage.validarMensagemUsuarioCriadoComSucesso());
	}
	
}
