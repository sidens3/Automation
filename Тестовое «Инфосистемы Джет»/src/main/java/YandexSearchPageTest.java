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
        String output = getStringBySelector("body .mini-suggest__popup-content [data-index='0']");
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

    @Test // вкладка Картанки отображается на странице
    public void testTabImagesIsDisplayed() {
        driver.get(baseUrl);
        assert driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).isDisplayed();
    }

    @Test //вкладка Картинки располагается на нужном месте
    public void testCorrectLocationTabImages(){
        driver.get(baseUrl);
        Point tabImagesPoint = driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getLocation();
        System.out.println(tabImagesPoint.toString());
        assert (tabImagesPoint.x == 247) && (tabImagesPoint.y == 244);
    }

    @Test // вкладка Картанки имеет соответствующее название
    public void testCorrectNameTabImages() {
        driver.get(baseUrl);
        assert driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getText().equals("Картинки");
    }

    @Test  //вкладка Картанки имеет нужный размер
    public void testSizeTabImages() {
        driver.get(baseUrl);
        Integer imagesTabHeight =  driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getSize().getHeight();
        Integer imagesTabWidth =  driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getSize().getWidth();
        assert (imagesTabHeight == 17) && (imagesTabWidth == 64);
    }

    @Test //вкладка Картанки находится между вкладками видео и новости
    public void testTabImagesBetweenVideoAndNews() {
        driver.get(baseUrl);
        Integer tabVideoLocationX = driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"video\"]")).getLocation().getX();
        Integer tabImagesLocationX = driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getLocation().getX();
        Integer tabNewsLocationX = driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"news\"]")).getLocation().getX();
        assert (tabVideoLocationX < tabImagesLocationX) && (tabImagesLocationX < tabNewsLocationX);

    }

    @Test //вкладка Картанки находится над полем поиск
    public void testTabImagesUnderSearchField() {
        driver.get(baseUrl);
        Integer tabImagesLocationY = driver.findElement(By.cssSelector("body .home-arrow__tabs [data-id=\"images\"]")).getLocation().getY();
        Integer tabSearchFieldLocationY =   driver.findElement(By.id("text")).getLocation().getY();
        assert tabImagesLocationY < tabSearchFieldLocationY;
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
