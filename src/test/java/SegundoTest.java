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

    @Test(description = "EJERCICIO 1, CLASE 2")
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

    @Test(description = "EJERCICIO 2, CLASE 2")
    public void forgotAccountPartialLinkTest() {
        Logger log = Logger.getLogger(GoogleTest.class.getName());
        log.setLevel(Level.DEBUG);

        WebDriver driver = getDriver("https://www.facebook.com");

        driver.findElement(By.xpath("//button[@title='Aceptar todas']")).click();

        driver.findElement(By.partialLinkText("¿Has olvidado la contraseña?")).click();
        String forgotTitle = driver.getTitle();

        Assert.assertNotEquals(forgotTitle, "Facebook - Entrar o registrarse");

        driver.close();
        driver.quit();
    }

    @Test(description = "EJERCICIO 3, CLASE 2")
    public void customSalesforceLink() throws InterruptedException {
        Logger log = Logger.getLogger(GoogleTest.class.getName());
        log.setLevel(Level.DEBUG);

        WebDriver driver = getDriver("https://login.salesforce.com/");

        // Click en Access my domain
        driver.findElement(By.xpath("//a[@id='mydomainLink']")).click();

        // Introducir "as" en dominio
        driver.findElement(By.xpath("//input[@id='mydomain']")).sendKeys("as");

        String customDomain = driver.findElement(By.xpath("//div[@id='mydomain_preview']")).getText();

        Assert.assertEquals(customDomain, "https://as.my.salesforce.com");

        // Click en continue
        driver.findElement(By.xpath("//button[@name='Continue']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("test");

        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("test");

        driver.findElement(By.xpath("//input[@value='Iniciar sesión']")).click();
        Thread.sleep(2000);

        String loginMessage = driver.findElement(By.xpath("//div[@role='alert']//p")).getText();

        Assert.assertEquals(loginMessage, "No se puede iniciar sesión");

        driver.close();
        driver.quit();
    }
}
