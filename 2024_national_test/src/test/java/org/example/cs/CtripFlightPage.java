package org.example.cs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;

import java.time.Duration;

public class CtripFlightPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    // 机票菜单相关元素
    @FindBy(xpath = "//button[contains(text(),'机票')]")
    private WebElement flightMenu;

    @FindBy(xpath = "//a[contains(text(),'国内/国际/中国港澳台')]")
    private WebElement domesticFlightLink;

    // 单程/往返/多程按钮
    @FindBy(xpath = "//span[contains(text(),'单程')]/ancestor::li")
    private WebElement singleTripBtn;

    @FindBy(xpath = "//span[contains(text(),'往返')]/ancestor::li")
    private WebElement roundTripBtn;

    @FindBy(xpath = "//span[contains(text(),'多程')]/ancestor::li")
    private WebElement multiTripBtn;

    @FindBy(xpath = "//span[contains(text(),'添加返程')]")
    private WebElement addReturnBtn;

    // 城市输入框
    @FindBy(xpath = "//input[@placeholder='可输入城市或机场']")
    private WebElement departureInput;

    @FindBy(xpath = "(//input[@name='owACity'])[1]")
    private WebElement arrivalInput;

    // 日期相关元素
    @FindBy(xpath = "//input[@placeholder='yyyy-mm-dd']")
    private WebElement dateInput;

    @FindBy(css = "div[class='calendar-modal date-custom date-custom-v2 calendar-active-animate calendar-fade-animate'] div:nth-child(1) div:nth-child(2) div:nth-child(1) div:nth-child(2) div:nth-child(4)")
    private WebElement sendDay;

    @FindBy(xpath = "//input[@placeholder='请选择日期'][2]")
    private WebElement returnDateInput;

    // 乘客类型和舱等
    @FindBy(xpath = "//span[contains(text(),'带儿童')]/ancestor::label")
    private WebElement childCheckbox;

    @FindBy(xpath = "//span[contains(text(),'不限舱等')]/ancestor::div")
    private WebElement cabinTypeDropdown;

    @FindBy(xpath = "//span[contains(text(),'经济舱')]")
    private WebElement economyClass;

    @FindBy(xpath = "//span[contains(text(),'乘客类型')]/ancestor::div")
    private WebElement passengerTypeDropdown;

    // 搜索按钮
    @FindBy(xpath = "//button[contains(@class,'search')]")
    private WebElement searchButton;

    // 搜索结果页面元素
    @FindBy(xpath = "//span[contains(text(),'12-08')]")
    private WebElement dateElement12_08;

    @FindBy(xpath = "//span[contains(text(),'直飞/经停')]/ancestor::label")
    private WebElement directFlightCheckbox;

    @FindBy(xpath = "//span[contains(text(),'航空公司')]/ancestor::div[contains(@class,'filter')]")
    private WebElement airlineDropdown;

    @FindBy(xpath = "//span[contains(text(),'更多')]/ancestor::div[contains(@class,'filter')]")
    private WebElement moreDropdown;

    @FindBy(xpath = "//span[contains(text(),'大型机')]/ancestor::label")
    private WebElement largeAircraftCheckbox;

    @FindBy(xpath = "//span[contains(text(),'更多日期')]")
    private WebElement moreDateBtn;

    @FindBy(xpath = "//span[contains(text(),'更多排序')]/ancestor::div")
    private WebElement moreSortDropdown;

    @FindBy(xpath = "//span[contains(text(),'价格高-低')]")
    private WebElement priceHighLowOption;

    @FindBy(xpath = "//span[contains(text(),'航司比较表')]")
    private WebElement airlineCompareBtn;

    @FindBy(xpath = "//button[contains(@class,'next')]")
    private WebElement nextBtn;

    @FindBy(xpath = "//span[contains(@class,'swap')]")
    private WebElement swapBtn;

    @FindBy(xpath = "//span[contains(text(),'邻近航线')]")
    private WebElement nearbyRoute;

    @FindBy(xpath = "//span[contains(text(),'缺口程')]")
    private WebElement gapRoute;

    @FindBy(xpath = "//span[contains(text(),'再加一程')]/ancestor::button")
    private WebElement addLegBtn;

    @FindBy(xpath = "//span[contains(text(),'退改签说明')]")
    private WebElement refundInfo;

    @FindBy(xpath = "//span[contains(text(),'返程')]")
    private WebElement returnTab;

    @FindBy(xpath = "//button[contains(text(),'预订')]")
    private WebElement bookBtn;

    @FindBy(xpath = "//span[contains(text(),'多种报销凭证')]")
    private WebElement reimbursement;

    @FindBy(xpath = "//span[contains(text(),'行程单或电子发票')]")
    private WebElement invoice;

    public CtripFlightPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // 导航到机票查询页面
    public void navigateToFlightPage() throws InterruptedException {
        try {
            actions.moveToElement(flightMenu).perform();
            Thread.sleep(500);
            domesticFlightLink.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            if (!driver.getCurrentUrl().contains("flights.ctrip.com")) {
                driver.get("https://flights.ctrip.com/online/channel/domestic");
                Thread.sleep(2000);
            }
        }
    }

    // 选择单程
    public void selectSingleTrip() throws InterruptedException {
        singleTripBtn.click();
        Thread.sleep(1000);
    }

    // 选择往返
    public void selectRoundTrip() throws InterruptedException {
        roundTripBtn.click();
        Thread.sleep(1000);
    }

    // 选择多程
    public void selectMultiTrip() throws InterruptedException {
        multiTripBtn.click();
        Thread.sleep(1000);
    }

    // 输入出发城市
    public void inputDepartureCity(String cityName) throws InterruptedException {
        try {
            departureInput.click();
            Thread.sleep(1000);
            actions.sendKeys(Keys.BACK_SPACE).perform();
            Thread.sleep(2000);
            departureInput.clear();
            Thread.sleep(300);
            departureInput.sendKeys(cityName);
            Thread.sleep(1000);
            actions.sendKeys(Keys.ENTER).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 输入到达城市
    public void inputArrivalCity(String cityName) throws InterruptedException {
        try {

            arrivalInput.click();
            Thread.sleep(1000);
            actions.sendKeys(Keys.BACK_SPACE).perform();
            Thread.sleep(2000);
            arrivalInput.sendKeys(cityName);
            Thread.sleep(1000);
            actions.sendKeys(Keys.ENTER).perform();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 选择日期
    public void selectDate(String dateStr) throws InterruptedException {
            dateInput.click();
            Thread.sleep(1000);
            sendDay.click();
            Thread.sleep(1000);
    }

    // 选择返回日期
    public void selectReturnDate(String dateStr) throws InterruptedException {
        try {
            returnDateInput.click();
            Thread.sleep(500);
            selectDate(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 勾选带儿童
    public void checkChildCheckbox() throws InterruptedException {
        childCheckbox.click();
        Thread.sleep(500);
    }

    // 选择经济舱
    public void selectEconomyClass() throws InterruptedException {
        cabinTypeDropdown.click();
        Thread.sleep(500);
        economyClass.click();
        Thread.sleep(500);
    }

    // 点击搜索
    public void clickSearch() throws InterruptedException {
        searchButton.click();
        Thread.sleep(5000);
    }

    // Getters for elements that might be needed directly
    public WebElement getDepartureInput() {
        return departureInput;
    }

    public WebElement getArrivalInput() {
        return arrivalInput;
    }

    public WebElement getSingleTripBtn() {
        return singleTripBtn;
    }

    public WebElement getRoundTripBtn() {
        return roundTripBtn;
    }

    public WebElement getMultiTripBtn() {
        return multiTripBtn;
    }

    public WebElement getAddReturnBtn() {
        return addReturnBtn;
    }

    public WebElement getDateInput() {
        return dateInput;
    }

    public WebElement getReturnDateInput() {
        return returnDateInput;
    }

    public WebElement getChildCheckbox() {
        return childCheckbox;
    }

    public WebElement getCabinTypeDropdown() {
        return cabinTypeDropdown;
    }

    public WebElement getEconomyClass() {
        return economyClass;
    }

    public WebElement getPassengerTypeDropdown() {
        return passengerTypeDropdown;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getDateElement12_08() {
        return dateElement12_08;
    }

    public WebElement getDirectFlightCheckbox() {
        return directFlightCheckbox;
    }

    public WebElement getAirlineDropdown() {
        return airlineDropdown;
    }

    public WebElement getMoreDropdown() {
        return moreDropdown;
    }

    public WebElement getLargeAircraftCheckbox() {
        return largeAircraftCheckbox;
    }

    public WebElement getMoreDateBtn() {
        return moreDateBtn;
    }

    public WebElement getMoreSortDropdown() {
        return moreSortDropdown;
    }

    public WebElement getPriceHighLowOption() {
        return priceHighLowOption;
    }

    public WebElement getAirlineCompareBtn() {
        return airlineCompareBtn;
    }

    public WebElement getNextBtn() {
        return nextBtn;
    }

    public WebElement getSwapBtn() {
        return swapBtn;
    }

    public WebElement getNearbyRoute() {
        return nearbyRoute;
    }

    public WebElement getGapRoute() {
        return gapRoute;
    }

    public WebElement getAddLegBtn() {
        return addLegBtn;
    }

    public WebElement getRefundInfo() {
        return refundInfo;
    }

    public WebElement getReturnTab() {
        return returnTab;
    }

    public WebElement getBookBtn() {
        return bookBtn;
    }

    public WebElement getReimbursement() {
        return reimbursement;
    }

    public WebElement getInvoice() {
        return invoice;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Actions getActions() {
        return actions;
    }
}

