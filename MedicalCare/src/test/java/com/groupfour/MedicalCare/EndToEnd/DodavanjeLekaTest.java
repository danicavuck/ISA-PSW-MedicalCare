package com.groupfour.MedicalCare.EndToEnd;

import com.groupfour.MedicalCare.MedicalCareApplication;
import com.groupfour.MedicalCare.Utill.CustomEmailSender;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MedicalCareApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class DodavanjeLekaTest {
    @MockBean
    CustomEmailSender customEmailSenderMock;

    @Test
    @Transactional
    void end2end(){
        WebDriverManager.firefoxdriver().setup();
        final WebDriver webDriver = new FirefoxDriver();

        login(webDriver);
        dodajLek(webDriver);

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
        webDriver.findElement(By.id("email")).sendKeys("adminkc@gmail.com");
        webDriver.findElement(By.id("lozinka")).sendKeys("adminadmin");
        webDriver.findElement(By.id("prijava")).click();
        sleep(webDriver, 5000);
    }

    void dodajLek(WebDriver webDriver){
        webDriver.findElement(By.id("sifarnikLekova")).click();
        sleep(webDriver, 2000);
        webDriver.findElement(By.id("dLek")).click();
        sleep(webDriver, 2000);
        webDriver.findElement(By.id("nazivLeka")).sendKeys("Bensedin");
        webDriver.findElement(By.id("kodLeka")).sendKeys("111");
        webDriver.findElement(By.id("dodaj")).click();
        webDriver.findElement(By.id("odjavi")).click();
    }


}
