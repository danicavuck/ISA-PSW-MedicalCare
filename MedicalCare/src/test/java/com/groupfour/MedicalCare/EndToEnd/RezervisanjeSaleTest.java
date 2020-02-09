package com.groupfour.MedicalCare.EndToEnd;

import com.groupfour.MedicalCare.MedicalCareApplication;
import com.groupfour.MedicalCare.Repository.PacijentRepository;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MedicalCareApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class RezervisanjeSaleTest {
    @MockBean
    CustomEmailSender customEmailSenderMock;
    @Autowired
    PacijentRepository pacijentRepository;

    @Test
    @Transactional
    void end2End(){
        WebDriverManager.firefoxdriver().setup();
        final WebDriver webDriver = new FirefoxDriver();

        login(webDriver);
        navigacijaDoListePregledaNaCekanju(webDriver);
        navigacijaDoKalendaraSale(webDriver);
        navigacijaDoListePregledaNaCekanju(webDriver);
        odabirSaleZaPregled(webDriver);
        logout(webDriver);
        assertEquals("http://localhost:4200/login",webDriver.getCurrentUrl());
        webDriver.close();
    }

    void sleep(WebDriver webDriver, int milliseconds) {
        synchronized (webDriver){
            try {
                webDriver.wait(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void login(WebDriver webDriver) {
        webDriver.get("http://localhost:4200/login");
        sleep(webDriver, 3000);
        webDriver.findElement(By.id("email")).sendKeys("petar.kovacevic0088@gmail.com");
        webDriver.findElement(By.id("lozinka")).sendKeys("adminklinike");
        webDriver.findElement(By.id("prijava")).click();
        sleep(webDriver, 5000);
    }

    void navigacijaDoListePregledaNaCekanju(WebDriver webDriver) {
        webDriver.findElement(By.id("detaljnije")).click();
        sleep(webDriver, 2000);
        WebElement element = webDriver.findElement(By.id("preglediNaCekanju"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(webDriver, 1000);
        element.click();
        sleep(webDriver, 3000);
    }

    void navigacijaDoKalendaraSale(WebDriver webDriver) {
        webDriver.findElement(By.id("nazad")).click();
        sleep(webDriver, 1500);
        WebElement element = webDriver.findElement(By.id("Glavna sala"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(webDriver, 1000);
        element.click();
        sleep(webDriver, 5000);
        webDriver.get("http://localhost:4200/adminklinike");
        sleep(webDriver, 1000);
    }

    void odabirSaleZaPregled(WebDriver webDriver) {
        List<WebElement> elements = webDriver.findElements(By.id("saleComboBox"));
        elements.get(0).click();
        sleep(webDriver, 1000);
        webDriver.findElement(By.id("Glavna sala")).click();
        sleep(webDriver, 3000);
        webDriver.findElement(By.id("dodajSalu")).click();
    }

    void logout(WebDriver webDriver) {
        webDriver.findElement(By.id("nazad")).click();
        sleep(webDriver, 1500);
        WebElement element = webDriver.findElement(By.id("logout"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(webDriver, 1000);
        element.click();
        sleep(webDriver, 1000);
    }
}
