import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestHappyPath() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров Егор");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();

        String textMainPage = driver.findElement(By.cssSelector("[id='root']")).getText();
        assertEquals("Заявка на дебетовую карту\n" +
                "Альфа-Карта\n" +
                "До 2% на все покупки\n" +
                "До 6% годовых на остаток\n" +
                "Персональные данные\n" +
                "  Мы гарантируем безопасность и сохранность ваших данных\n" +
                "Фамилия и имя\n" +
                "Укажите точно как в паспорте\n" +
                "Мобильный телефон\n" +
                "На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.\n" +
                "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй\n" +
                "Продолжить", textMainPage.trim());
        assertTrue(driver.findElement(By.cssSelector("[id='root']")).isDisplayed());

        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        assertTrue(driver.findElement(By.className("icon")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=order-success]")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[id='root']")).isDisplayed());

    }

    @Test
    void shouldTestNameDash() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Калинин-Малинин Эдуард ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        assertTrue(driver.findElement(By.className("icon")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=order-success]")).isDisplayed());

    }

    @Test
    void shouldTestNameЁ() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Семенов Пётр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
        assertTrue(driver.findElement(By.className("icon")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=order-success]")).isDisplayed());

    }

    @Test
    void shouldTestInvalidNameLatin() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Boris Jonson");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidNameHieroglyphs() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("康熙字典體 康熙字典");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidNameNumbers() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("123 4567");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidNamesSpecialCharacters() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов.");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestEmptyNames() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhone10Number() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999123456");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhone9Number() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhone12Number() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+799912345789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhone1Number() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhoneLetters() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+gfhfjnhjfgv");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestInvalidPhoneSpecialCharacters() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7(900)1234752");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestEmptyPhone() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
        assertTrue(driver.findElement(By.className("input__sub")).isDisplayed());

    }

    @Test
    void shouldTestEmptyCheckbox() {

        //поиск элемента
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Олег Кабанов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79004785147");
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
        assertTrue(driver.findElement(By.className("checkbox__text")).isDisplayed());

    }
}