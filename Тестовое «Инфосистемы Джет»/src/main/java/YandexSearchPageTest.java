import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class YandexSearchPageTest {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\drivers\\WinChromeDriver80\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://yandex.ru/";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test //выводится значение погоды
    public void testGetWeatherInfo() {
        String searchWord = "погода";
        setWordOnYandexSearchField(searchWord);
        String output = getStringBySelector("body .mini-suggest__item_type_fact[data-type='fact']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    @Test //выводится первая строчка из всплывающего окна поиска по слову погода
    public void testGetWeatherFirstLine() {
        String searchWord = "погода";
        setWordOnYandexSearchField("погода");
        String output = getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    @Test //выводится первая строчка из всплывающего окна поиска по слову липецк
    public void testGetLipetskFirstLine() {
        String searchWord = "липецк";
        setWordOnYandexSearchField("липецк");
        String output =getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    //todo написать яндексу про бажку
    @Test //выводится первая строчка из всплывающего окна поиска по слову Липецк
    @Ignore
    public void testGetCapitalLipetskFirstLine() {
        String searchWord = "Липецк";
        setWordOnYandexSearchField("Липецк");
        String output = getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    @Test //выводится первая строчка из всплывающего окна поиска по слову лото
    public void testGetLotoFirstLine() {
        String searchWord = "лото";
        setWordOnYandexSearchField("лото");
        String output = getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    //todo написать яндексу про бажку
    @Test //выводится первая строчка из всплывающего окна поиска по слову Лото
    @Ignore
    public void testGetCapitalLotoFirstLine() {
        String searchWord = "Лото";
        setWordOnYandexSearchField("Лото");
        String output = getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
        System.out.println(output);
        assert output.contains(searchWord);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void setWordOnYandexSearchField(String str){
        driver.get(baseUrl);
        driver.findElement(By.id("text")).click();
        driver.findElement(By.id("text")).clear();
        driver.findElement(By.id("text")).sendKeys(str);
    }

    private String getStringBySelector(String selector){
        if (isElementPresent(By.cssSelector(selector))){
            return driver.findElement(By.cssSelector(selector)).getText();
        } else {
            throw new NoSuchElementException("No such element by current css selector");
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
