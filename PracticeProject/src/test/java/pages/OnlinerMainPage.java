package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import config.AppConfig;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OnlinerMainPage extends BasePage {
    private final SelenideElement searchField = $(By.className("fast-search__input"));
    private final SelenideElement searchFieldInFrame = $(By.className("search__input"));
    private final SelenideElement searchFrame = $(By.className("modal-iframe"));
    private final SelenideElement firstProductTitle = $(".product__title > a");
    private final SelenideElement cartIcon = $("a.auth-bar__item");
    private final SelenideElement vkLink = $(".footer-style__social-button_vk");
    private final String END_POINT = "/";

    @Step("Open page by url: " + AppConfig.baseUrl + END_POINT)
    public OnlinerMainPage open(){
        Selenide.open(AppConfig.baseUrl + END_POINT);
        assertTrue(pageIsOpened());

        return this;
    }

    public boolean pageIsOpened(){return searchField.isDisplayed();}

    @Override
    protected WebElement getPageIdentifier() {
        return searchField;
    }

    @Step("Click cart icon")
    public OnlinerCartPage clickCartIcon(){
        cartIcon.click();

        return new OnlinerCartPage();
    }

    @Step("Click search field")
    public OnlinerMainPage clickSearchField(){
        searchField.click();

        return this;
    }

    @Step("Input {0} into field")
    public OnlinerMainPage inputTextSearchField(String productName){
        searchField.sendKeys(productName);

        switchTo().frame(searchFrame);

        assertEquals(productName, searchFieldInFrame.getValue(), "The field value does not match the entered data!");
        return this;
    }

    @Step("Checking that the name of the first product in the search result matches the search data")
    public OnlinerMainPage checkFirstProductTitle(String productName){
        firstProductTitle.should(visible, Duration.ofSeconds(5));
        takeScreenshot();
        firstProductTitle.shouldHave(text(productName));

        return this;
    }

    @Step("Click VK icon")
    public VkOnlinerPage clickVkIcon(){
        vkLink.click();

        return new VkOnlinerPage();
    }
}
