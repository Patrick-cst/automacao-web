package evidencia;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cucumber.api.Result.Type;
import setup.Hooks;
import cucumber.api.Scenario;
public class PdfGenerete {
    private static PdfGenerete instace;
    private Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
    private TakeScreenshot take = new TakeScreenshot();
    private static Font CAT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    public static synchronized PdfGenerete getInstance() {
        if (instace == null) {
            instace = new PdfGenerete();
        }
        return instace;
    }
    public void createDocument(Type status, String nameScenario) throws DocumentException, IOException, ParseException {
        Path path = Paths.get("src\\test\\resources\\target\\" + getDate());
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PdfWriter.getInstance(document, new FileOutputStream(
                    path + "\\"+status+"_Cenário_" + nameScenario + "_Data_" + getDate() + "_Hora_" + getHour() + ".pdf"));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        document.open();
        document.addTitle(nameScenario);
    }
    
    public Paragraph addImagesInPdf() throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph("", CAT_FONT);
        PdfPTable table = new PdfPTable(1);
        for (Entry<String, BufferedImage> textAdnImage : take.getBufferImageMap().entrySet()) {
            table.addCell(getTextsInBuffer(textAdnImage.getKey()));
            table.addCell(getImageInBuffer(textAdnImage.getValue()));
        }
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        paragraph.add(table);
        paragraph.setLeading(1);
        paragraph.setSpacingAfter(0);
        paragraph.setSpacingBefore(0);
        return paragraph;
    }
    public void addExternalImage(String path, String string, int align, float ySize, float xSize) throws MalformedURLException, IOException, DocumentException, ParseException  {
            Image image = Image.getInstance(path);
            image.setAlignment(align);
            image.scaleAbsolute(ySize, xSize);
            document.add(image);

//          document.newPage();
    }
    
    public void addCenarioExecutado(String scenario) throws DocumentException {
    	Paragraph paragraph = new Paragraph("CENÁRIO EXECUTADO: "+scenario.toUpperCase(), CAT_FONT);
        paragraph.setLeading(90f);
        document.add(paragraph);
    }
    
    public void addData() throws DocumentException, ParseException {
    	Paragraph paragraphData = new Paragraph("DIA EXECUTADO: "+ getDate(), CAT_FONT);
        paragraphData.setLeading(60f);
        document.add(paragraphData);
    }
    
    public void addStatus(Type type) throws DocumentException {
    	Paragraph paragraphStatus = new Paragraph("Status: "+ type, CAT_FONT);
        paragraphStatus.setLeading(60f);
        document.add(paragraphStatus);
    }
    
    public PdfPCell getImageInBuffer(BufferedImage bufferedImage)
            throws BadElementException, IOException {
        Image image = Image.getInstance(bufferedImage, null);
        image.scaleAbsolute(800,450);
        PdfPCell imageCell = new PdfPCell(image);
        document.newPage();
        return imageCell;
    }
    public PdfPCell getTextsInBuffer(String description) throws BadElementException, IOException {
        PdfPCell text = new PdfPCell(new Paragraph("Passo executado: "+description.toUpperCase()));
        text.setBorderWidthBottom(100);
        text.setBorder(1);
        text.setPadding(40);
        text.setHorizontalAlignment(Element.TITLE);
        return text;
    }
    
    public static PdfPCell addNameScenario(String cenario) throws BadElementException, IOException {
        PdfPCell text = new PdfPCell(new Paragraph("Cenário executado: "+cenario));
        text.setBorderWidthBottom(100);
        text.setBorder(1);
        text.setPadding(50);
        text.setHorizontalAlignment(Element.TITLE);
        return text;
    }
    
    public static boolean HighlightElement(WebElement element) {
		try {
			if (Hooks.get_driver() instanceof JavascriptExecutor) {
				JavascriptExecutor js = (JavascriptExecutor) Hooks.get_driver();
				js.executeScript("arguments[0].style.border='3px solid red'", element);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
    
    
    public void createPdf(String name, Type type) throws DocumentException, IOException, ParseException {
        createDocument(type, name);
        try {
//        	addExternalImage("", name, Element.ALIGN_JUSTIFIED, 450, 150); 
        	addCenarioExecutado(name);
        	addData();
        	addStatus(type);
            document.add(addImagesInPdf());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
            take.cleanBufferImageMap(); 
        }
    }
    
    public static String getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = format.format(new Date());
        return dateString;
    }
    public String getHour() {
        String timeStamp = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
        return timeStamp;
    }
}