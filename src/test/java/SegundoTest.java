import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SegundoTest {

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

    @Test(description = "Test para recuperar contraseña de Facebook")
    public void forgotAccountTest()  {
        Logger log = Logger.getLogger(GoogleTest.class.getName());
        log.setLevel(Level.DEBUG);

        WebDriver driver = getDriver("https://www.facebook.com");
        driver.findElement(By.xpath("//button[@title='Aceptar todas']")).click();

        String initialTitle = driver.getTitle();

        Assert.assertEquals(initialTitle, "Facebook - Entrar o registrarse");

        // Click en recuperar contraseña
        driver.findElement(By.xpath("//a[contains(@href,'recover')]")).click();

        String forgotTitle = driver.getTitle();

        Assert.assertEquals(forgotTitle, "¿Has olvidado la contraseña? | No puedo entrar | Facebook");

        Assert.assertNotEquals(forgotTitle, "Facebook - Entrar o registrarse");

        driver.close();
        driver.quit();
    }
}
