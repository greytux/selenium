import io.qameta.allure.Allure;
import org.apache.log4j.Level;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.log4j.Logger;

import java.util.List;

public class GoogleTest {

    /**
     * Metodo que devuelve driver pasandole la url por parametro
     * @param s url de la pagina
     * @return driver
     */
    private WebDriver getDriver(String s) {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("test-type");

        WebDriver driver = new ChromeDriver(options);

        driver.get(s);
        driver.manage().window().maximize();

        return driver;
    }

    @Test(description = "Busqueda en Google, acceso a bandcamp y al album demos")
    public void getTitleTest(){
        Logger log = Logger.getLogger(GoogleTest.class.getName());
        log.setLevel(Level.DEBUG);

        WebDriver driver = getDriver("https://www.google.com");
        Allure.addAttachment("Console log: ", "Acceso a la url");

        String title = driver.getTitle();

        // Cerrar el popup de inicio de Google
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'consent.google.com')]")));
        driver.findElement(By.xpath("//*[@id=\"introAgreeButton\"]/span/span")).click();

        // Escribir en la barra de busqueda
        WebElement barraBuscar = driver.findElement(By.xpath("//input[@title='Buscar']"));
        barraBuscar.sendKeys("King Gizzard an the lizzard wizard");
        barraBuscar.sendKeys(Keys.ENTER);

        // Acceder a bandcamp
        driver.findElement(By.xpath("//h3//span[contains(text(),': Music')]")).click();

        // Acceder al album demos
        driver.findElement(By.xpath("//li//a[contains(@href,'demos')]")).click();

        driver.navigate().back();

        // Lista de albums
        List<WebElement> albums = driver.findElements(By.xpath("//li[contains(@class,'music-grid-item')]//p"));
        Allure.addAttachment("Numero de albums: ", ("")+albums.size());
        Assert.assertEquals(albums.size(), 28);

        for (WebElement album:
                albums) {
            Allure.addAttachment("Album: ", album.getText());
        }

        if(title.equals("Music | King Gizzard & The Lizard Wizard")){
            System.out.println("TEST PASSED");
            log.info("TEST PASSED");
            Allure.addAttachment("Console log: ", "TEST PASSED");
        }else{
            System.out.println("TEST FAILED");
            log.warn("TEST FAILED");
        }

        driver.close();
        driver.quit();
    }
}
