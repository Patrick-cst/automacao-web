package utils;

import java.awt.Window;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import evidencia.TakeScreenshot;
import io.cucumber.datatable.DataTable;
import setup.Hooks;

public class Utils {
	private static WebDriver driver;
	public static List<Map<String, String>> listDataTable;
	private static WebDriver yourWebDriverInstance;
	public static String gerarNome;
	static TakeScreenshot takeScreenShot = new TakeScreenshot();
	
	
	
	public static String getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(new Date());
        return dateString;
    }
	
	public static void wait_element_clickable(WebElement elemento, int timeout) {
		WebDriverWait aguardar = new WebDriverWait(Hooks.get_driver(), timeout);
		aguardar.until(ExpectedConditions.elementToBeClickable(elemento));
	}
	
	public static void wait_element(WebElement elemento, int timeout) {
		WebDriverWait aguardar = new WebDriverWait(Hooks.get_driver(), timeout);
		aguardar.until(ExpectedConditions.visibilityOf(elemento));
	}

	public static boolean element_exists(WebElement elemento, int timeout) {
		try {
			wait_element(elemento, timeout);
			return true;

		} catch (Exception e) {
			System.out.println("O elemento nao foi encontrado. Veja excessão abaixo:");
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static void create_datatable(DataTable dataTable_) {
		listDataTable = dataTable_.asMaps(String.class, String.class);
	}

	public static void uploadFile(WebElement elemento, String caminhoDoArquivo) {
		elemento.sendKeys(caminhoDoArquivo);
	}
	

	public static void scrollToElement(WebElement elemento) {
		try {
			((JavascriptExecutor) Hooks.get_driver()).executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"center\"});", elemento);

		} catch (Exception e) {
			System.out.println("O elemento nao foi encontrado. Veja excessão abaixo:");
			System.out.println(e.getMessage());
		}
	}

	public static void scrollAndClick(WebElement elemento) {
		try {
			((JavascriptExecutor) Hooks.get_driver()).executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"center\"});", elemento);

			elemento.click();

		} catch (Exception e) {
			System.out.println("O elemento nao foi encontrado. Veja excessão abaixo:");
			System.out.println(e.getMessage());
		}
	}

	public static String gerarCPF() {

		String iniciais = "";// contém os 9 primeiros números do cpf
		int numero;// número gerado randomicamente
		int primDig, segDig;// recebem o primeiro e o segundo digitos calculados
		int soma = 0, peso = 10; // utilizados nos calculos dos digitos
		// verificadores
		String num; // receberá o valor contido em iniciais

		// gera randomicamente os 9 primeiros números do cpf
		for (int i = 0; i < 9; i++) {

			numero = (int) (Math.random() * 10);
			if (numero > 9)// pois o número deve ser de 0 a 9
				numero = 9;
			iniciais += Integer.toString(numero);
		}

		// armazena em num o valor de iniciais
		num = iniciais;

		for (int i = 0; i < iniciais.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}

		if (soma % 11 == 0 | soma % 11 == 1)
			primDig = 0;
		else
			primDig = 11 - (soma % 11);

		soma = 0;
		peso = 11;

		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}

		soma += primDig * 2;
		if (soma % 11 == 0 | soma % 11 == 1)
			segDig = 0;
		else
			segDig = 11 - (soma % 11);

		// retorna o cpf gerado
		return iniciais + primDig + segDig;
	}

	public static String gerarCNPJ() {
		String iniciais = "";// contém os 8 primeiros números do cnpj
		int numero;// número gerado randomicamente
		int primDig, segDig;// recebem o primeiro e o segundo digitos calculados
		String num; // receberá o valor contido em iniciais
		int intRestoDivisao; // recebe o resto de uma divisão

		// gera randomicamente os 8 primeiros números do cnpj
		for (int i = 0; i < 8; i++) {
			numero = (int) (Math.random() * 10);
			if (numero > 9)// pois o número deve ser de 0 a 9
				numero = 9;
			iniciais += Integer.toString(numero);
		}
		iniciais += "0001";
		// armazena em num o valor de iniciais
		num = iniciais;

		// 5 4 3 2 9 8 7 6 5 4 3 2
		// calculando o primeiro dígito:
		int soma = 0;
		soma += 5 * Integer.parseInt((num.substring(0, 1)));
		soma += 4 * Integer.parseInt((num.substring(1, 2)));
		soma += 3 * Integer.parseInt((num.substring(2, 3)));
		soma += 2 * Integer.parseInt((num.substring(3, 4)));
		soma += 9 * Integer.parseInt((num.substring(4, 5)));
		soma += 8 * Integer.parseInt((num.substring(5, 6)));
		soma += 7 * Integer.parseInt((num.substring(6, 7)));
		soma += 6 * Integer.parseInt((num.substring(7, 8)));
		soma += 5 * Integer.parseInt((num.substring(8, 9)));
		soma += 4 * Integer.parseInt((num.substring(9, 10)));
		soma += 3 * Integer.parseInt((num.substring(10, 11)));
		soma += 2 * Integer.parseInt((num.substring(11, 12)));

		intRestoDivisao = soma % 11;
		// Caso o resto da divisão seja menor que 2,
		// o nosso primeiro dígito verificador se torna 0 (zero),
		// caso contrário subtrai-se o valor obtido de 11
		if (intRestoDivisao < 2) {
			primDig = 0;
		} else {
			primDig = 11 - intRestoDivisao;
		}

		// 6 5 4 3 2 9 8 7 6 5 4 3 2
		// calculando o segundo dígito:
		soma = 0;
		soma += 6 * Integer.parseInt((num.substring(0, 1)));
		soma += 5 * Integer.parseInt((num.substring(1, 2)));
		soma += 4 * Integer.parseInt((num.substring(2, 3)));
		soma += 3 * Integer.parseInt((num.substring(3, 4)));
		soma += 2 * Integer.parseInt((num.substring(4, 5)));
		soma += 9 * Integer.parseInt((num.substring(5, 6)));
		soma += 8 * Integer.parseInt((num.substring(6, 7)));
		soma += 7 * Integer.parseInt((num.substring(7, 8)));
		soma += 6 * Integer.parseInt((num.substring(8, 9)));
		soma += 5 * Integer.parseInt((num.substring(9, 10)));
		soma += 4 * Integer.parseInt((num.substring(10, 11)));
		soma += 3 * Integer.parseInt((num.substring(11, 12)));
		soma += 2 * primDig;

		intRestoDivisao = soma % 11;
		// Caso o resto da divisão seja menor que 2,
		// o nosso primeiro dígito verificador se torna 0 (zero),
		// caso contrário subtrai-se o valor obtido de 11
		if (intRestoDivisao < 2) {
			segDig = 0;
		} else {
			segDig = 11 - intRestoDivisao;
		}

		// retorna o cnpj gerado
		return iniciais + primDig + segDig;

	}

	public static String geraStringAleatoria() {
		// Determia as letras que poderão estar presente nas chaves
		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ123456789";

		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves+"TESTE-AUT";
	}
	
	public static String gerarNomeSobrenome() {
		// Determia as letras que poderão estar presente nas chaves
		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";

		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves + " " +  armazenaChaves;
	}

	public static String geraEmailAleatoria() {
		// Determia as letras que poderão estar presente nas chaves
		String letras = "abcdefghijklmnopqrstuvxzy";

		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves + "@gmail.com";
	}

	public static String gerarRg() throws Exception {

		String nDigResult;
		String numerosContatenados;
		String numeroGerado;

		Random numeroAleatorio = new Random();

		// numeros gerados
		int n1 = numeroAleatorio.nextInt(10);
		int n2 = numeroAleatorio.nextInt(10);
		int n3 = numeroAleatorio.nextInt(10);
		int n4 = numeroAleatorio.nextInt(10);
		int n5 = numeroAleatorio.nextInt(10);
		int n6 = numeroAleatorio.nextInt(10);
		int n7 = numeroAleatorio.nextInt(10);
		int n8 = numeroAleatorio.nextInt(10);
		int n9 = numeroAleatorio.nextInt(10);

		// Conctenando os numeros
		numerosContatenados = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3) + String.valueOf(n4)
				+ String.valueOf(n5) + String.valueOf(n6) + String.valueOf(n7) + String.valueOf(n8)
				+ String.valueOf(n9);

		numeroGerado = numerosContatenados;

		return numeroGerado;
	}

	public static String mostraResultado() throws Exception {

		String resultadoRG = gerarRg();

		return resultadoRG;
	}
	
	//
	
	
	private static int randomiza(int n) {
        int ranNum = (int) (Math.random() * n);
		return ranNum;
	}

	private static int mod(int dividendo, int divisor) {
		return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
	}

	public static String cpf(boolean comPontos) {
		int n = 9;
		int n1 = randomiza(n);
		int n2 = randomiza(n);
		int n3 = randomiza(n);
		int n4 = randomiza(n);
		int n5 = randomiza(n);
		int n6 = randomiza(n);
		int n7 = randomiza(n);
		int n8 = randomiza(n);
		int n9 = randomiza(n);
		int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

		d2 = 11 - (mod(d2, 11));

		String retorno = null;

		if (d2 >= 10)
			d2 = 0;
		retorno = "";

		if (comPontos)
			retorno = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
		else
			retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

		return retorno;
	}
	
	public static String nome() {

		int i, nrAleatorioVogal, nrAleatorioConsoante;

		String vogal[] = { "a", "e", "i", "o", "u", }, vc = "", nome = "", consoante[] = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "w", "x", "y", "z" };

		Random random = new Random();

		for (i = 0; i <= 5; i++) {

			nrAleatorioVogal = 0 + random.nextInt(4);// escolhe uma pos de 0 a 4

			nrAleatorioConsoante = 0 + random.nextInt(19);// escolhe pos de 0 a 19

			vc = vogal[nrAleatorioVogal] + consoante[nrAleatorioConsoante];

			nome = nome + vc;

		}

		return nome;

	}
}
