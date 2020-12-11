import io.qameta.allure.Allure;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class PrimerTest {

    /**
     * Metodo que devuelve driver pasandole la url por parametro
     * @param s url de la pagina
     * @return driver
     */
    private WebDriver getDriver(String s) {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get(s);
        driver.manage().window().maximize();

        return driver;
    }

    @Test
    public void testing(){
        // Propiedades del driver
        WebDriver driver = getDriver("https://www.facebook.com");
        // Actualizar
        driver.navigate().refresh();
        // Volver atras
//        driver.navigate().back();
        // Volver adelante
//        driver.navigate().forward();
        // Maximizar pantalla
//        driver.manage().window().maximize();
        System.out.println("El titulo del sitio es: "+driver.getTitle());
        System.out.println("La url del sitio es: "+driver.getCurrentUrl());

        driver.close();
        driver.quit();
    }

    @Test
    public void spotifyTest(){
        WebDriver driver = getDriver("https://www.spotify.com");

        System.out.println("El titulo del sitio es: "+driver.getTitle());
        System.out.println("La url del sitio es: "+driver.getCurrentUrl());

        // Lista de h1 de la página
        List<WebElement> listaH1 = driver.findElements(By.tagName("h1"));

        System.out.println("Se encontraron "+listaH1.size()+" elementos de tipo h1");

        for (WebElement element :
             listaH1) {
            System.out.println("Elemento h1: "+element.getText());
        }

        // Lista de h2 de la página
        List<WebElement> listaH2 = driver.findElements(By.tagName("h2"));

        System.out.println("Se encontraron "+listaH2.size()+" elementos de tipo h2");

        for (WebElement element :
                listaH2) {
            System.out.println("Elemento h2: "+element.getText());
        }

        driver.close();
        driver.quit();
    }

    @Test
    public void bbcMundo(){
        WebDriver driver = getDriver("https://www.bbc.com/mundo");

        System.out.println("El titulo del sitio es: "+driver.getTitle());
        System.out.println("La url del sitio es: "+driver.getCurrentUrl());

        // Lista de parrafos de la página
        List<WebElement> listaParrafos = driver.findElements(By.tagName("p"));

        System.out.println("Se encontraron "+listaParrafos.size()+" elementos de tipo parrafo");

        for (WebElement element :
                listaParrafos) {
            // Evitar parrafos vacios
            if (!element.getText().equals("") || !element.getText().isEmpty()){
                System.out.println("Parrafo: "+element.getText());
            }
        }
        System.out.println("====================================================");

        // Lista de links de la página
        List<WebElement> listaLinks = driver.findElements(By.tagName("a"));

        System.out.println("Se encontraron "+listaLinks.size()+" elementos de tipo link");

        for (WebElement element :
                listaLinks) {
            // Evitar links vacios
            if (!element.getText().equals("") || !element.getText().isEmpty()){
                System.out.println("Link: "+element.getText());
            }
        }

        driver.close();
        driver.quit();
    }

    @Test(description = "Test de obtencion de medidas de la pantalla de testeo")
    public void getWindowSizeTest(){
        Logger log = Logger.getLogger(GoogleTest.class.getName());
        log.setLevel(Level.DEBUG);

        WebDriver driver = getDriver("https://www.google.es");

        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();

        log.info("Window height: "+height);
        log.info("Window width: "+width);

        Allure.addAttachment("Window height",""+height);
        Allure.addAttachment("Window width",""+width);

        // Setear nuevas dimensiones (720x480)
        driver.manage().window().setSize(new Dimension(720, 480));

        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();

        log.info("New window height: "+height);
        log.info("New window width: "+width);

        Allure.addAttachment("New window height",""+height);
        Allure.addAttachment("New window width",""+width);

        Assert.assertEquals(height, 480);
        Assert.assertEquals(width, 720);

        driver.close();
        driver.quit();
    }
}
