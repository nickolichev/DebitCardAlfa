
package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {

  private WebDriver driver;

  @BeforeAll
  public static void setupAll() {
    WebDriverManager.chromedriver().setup();
//      System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
  }

  @BeforeEach
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--no-sandbox");
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    driver.get("http://localhost:9999/");
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
    driver = null;
  }

  @Test
  public void testSuccessful() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Андрей Иванович Кузнецов");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("+71234567890");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();

    assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
  }

  @Test
  public void testInvalidNameLatin() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Ivan Belov");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.cssSelector("span[data-test-id= 'name'] .input__sub")).getText();

    assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
  }

  @Test
  public void testInvalidNameUnderline() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Иван_Белов");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.cssSelector("span[data-test-id= 'name'] .input__sub")).getText();

    assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
  }

  @Test
  public void testInvalidNameLettersWithUmlaut() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Николай Ёлкин");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.cssSelector("span[data-test-id= 'name'] .input__sub")).getText();

    assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
  }

  @Test
  public void testEmptyName() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.cssSelector("span[data-test-id= 'name'] .input__sub")).getText();

    assertEquals("Поле обязательно для заполнения", text.trim());
  }

  @Test
  public void testInvalidPhoneWithoutPlus() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Петров Иван Сидорович");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("71234567890");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text1 = driver.findElement(By.cssSelector("span[data-test-id= 'phone'] .input__sub")).getText();

    assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text1.trim());
  }

  @Test
  public void testInvalidPhoneMoreNumbers() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Петров Иван Сидорович");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("+712345678909");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text1 = driver.findElement(By.cssSelector("span[data-test-id= 'phone'] .input__sub")).getText();

    assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text1.trim());
  }

  @Test
  public void testInvalidPhoneLessNumbers() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Петров Иван Сидорович");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("+7123456789");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text1 = driver.findElement(By.cssSelector("span[data-test-id= 'phone'] .input__sub")).getText();

    assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text1.trim());
  }

  @Test
  public void testInvalidPhoneEightAndWithoutPlus() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Петров Иван Сидорович");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("812345678909");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text1 = driver.findElement(By.cssSelector("span[data-test-id= 'phone'] .input__sub")).getText();

    assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text1.trim());
  }

  @Test
  public void testEmptyPhone() {
    driver.findElement(By.cssSelector("span[data-test-id= 'name'] input")).sendKeys("Петров Иван Сидорович");
    driver.findElement(By.cssSelector("span[data-test-id= 'phone'] input")).sendKeys("");
    driver.findElement(By.className("checkbox__box")).click();
    driver.findElement(By.className("button_view_extra")).click();
    String text = driver.findElement(By.cssSelector("span[data-test-id= 'phone'] .input__sub")).getText();

    assertEquals("Поле обязательно для заполнения", text.trim());
  }
}

