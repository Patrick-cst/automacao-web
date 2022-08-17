package evidencia;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import setup.Hooks;
public class TakeScreenshot {
    private static TakeScreenshot instance;
    private static LinkedHashMap<String,BufferedImage> bufferImageMap = new LinkedHashMap<>();
    public  LinkedHashMap<String, BufferedImage> getBufferImageMap() {
        return bufferImageMap;
    }
    public  void setBufferImageMap(LinkedHashMap<String, BufferedImage> bufferImageMap) {
        TakeScreenshot.bufferImageMap = bufferImageMap;
    }
    public static synchronized TakeScreenshot getInstance() {
        if (instance == null) {
            instance = new TakeScreenshot();
        }
        return instance;
    }
    public void cleanBufferImageMap() {
        instance = null;
        bufferImageMap = new LinkedHashMap<String, BufferedImage>();
    }
    public  void takeScreenShot(String txt, WebElement element) throws IOException {
    	PdfGenerete.HighlightElement(element);
        File input = ((TakesScreenshot)Hooks.get_driver()).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(input);
        bufferImageMap.put(txt, image);
    }
}