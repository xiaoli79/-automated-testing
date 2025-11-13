package org.xiaoli._024ctrip_nation_test;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class TestCtripFlight {
    private WebDriver driver;


    @BeforeEach
    public void setup() {
        // 这个setup代码不能被修改
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "E:\\比赛\\软件测试\\驱动\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ctrip.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }
    // test-code-start
    // 只能在这里面插入代码

    public void  test_meth_public(){
        PageFactory.initElements(driver,this);
        //放一点公共的代码~~

    }


    @FindBy(css = ".pc_home-tabbtnIcon.lsn_ico_9C9TD")
    private WebElement init;

//  带儿童
    @FindBy(xpath = "//div[contains(@class,'form-select-input form-select-input-domestic')]//div[1]//i[1]")
    private WebElement baby;


    @FindBy(xpath = "//span[contains(text(),'不限舱等')]")
    private WebElement zhangkai;

    @FindBy(xpath = "//div[contains(text(),'经济舱')]")
    private WebElement cheap_flight;


    @FindBy(css = "button[aria-label='机票 按回车键打开菜单'] span[class='lsn_top_nav_font_4h1KU lsn_top_nav_font_line_0iVuu']")
    private WebElement flightTab;
    @FindBy(xpath = "//span[contains(text(),'单程')]")
    private WebElement singleTripRadio;
    @FindBy(xpath = "//div[@class='radio-wrap___3-85F']//span[text()='往返']")
    private WebElement roundTripRadio;
    @FindBy(xpath = "//div[text()='中转换乘']")
    private WebElement transferTripLink;
    @FindBy(xpath = "//span[text()='计次/定期票']")
    private WebElement multiRideTripLink;

    // R001: 单程查询
    @FindBy(name = "owDCity")
    private WebElement departCityInput;
    @FindBy(xpath = "//input[@name='owACity']")
    private WebElement arriveCityInput;
    @FindBy(xpath = "//div[@class='date-day ']//span[@class='date-d'][normalize-space()='1']")
    private WebElement departDateInput;
    @FindBy(xpath = "//span[text()='只搜高铁动车']")
    private WebElement highSpeedCheckbox;
    @FindBy(xpath = "//button[contains(text(),'搜索')]")
    private WebElement searchButton;



    // --- 在此处插入符合命名规范的 JUnit5 测试方法 ---


    @FindBy(xpath = "//input[@placeholder='yyyy-mm-dd']")
    private WebElement click_date;
    // 3.1 单程车票查询
    @ParameterizedTest
    @CsvSource({
            "北京, 广州, 2024-12-01, CtripFlight_R001_001",
            "北京, 成都, 2024-12-01, CtripFlight_R001_002",
            "上海, 广州, 2024-12-01, CtripFlight_R001_003",
            "上海, 成都, 2024-12-01, CtripFlight_R001_004",
            "北京, 成都, 2024-12-01, CtripFlight_R001_002",
    })
    public void test_CtripFlight_R001(String fromCity, String toCity, String date, String caseId) throws InterruptedException {
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys(fromCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys(toCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();

        baby.click();

        zhangkai.click();
        cheap_flight.click();
        Thread.sleep(500);
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }

        takeScreenshot(caseId + ".png");
    }



    @ParameterizedTest
    @CsvSource({
            "12345, CtripFlight_R002_001",
            "@@@, CtripFlight_R002_002",
            "NonExistentCity, CtripFlight_R002_003"
    })
    public void test_CtripFlight_R002(String invalidFromCity, String caseId) throws InterruptedException {

        // R002: 非法出发城市 [cite: 35]
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys(invalidFromCity);
        Thread.sleep(100);
        takeScreenshot(caseId + ".png");

    }


//  更多日期
    @FindBy(xpath = "//span[@class='open-btn']")
    private WebElement more_date;

    @FindBy(xpath = "(//div[@class='price'])[4]")
    private WebElement nov_third;


    @FindBy(xpath = "//i[@class='ico-checkbox']")
    private WebElement flight_stop;

    @FindBy(xpath = "//div[@class='filter-key'][contains(text(),'航空公司')]")
    private WebElement airport;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/div[1]/ul[1]/li[3]/div[2]/div[1]/ul[3]/li[1]")
    private WebElement airport_first;

    @FindBy(xpath = "//div[contains(text(),'更多')]")
    private WebElement flight_size;


    @Test
    public void test_CtripFlight_R003() throws InterruptedException {
        // R003: 单程查询结果筛选与排序 (价格高-低) [cite: 38]
        String caseId = "CtripFlight_R003_001";


        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        more_date.click();

        driver.findElement(By.xpath("(//div[@id='December-03-2025'])[1]")).click();
        Thread.sleep(500);
        flight_stop.click();

        airport.click();
        Thread.sleep(1000);
        airport_first.click();

        flight_size.click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//span[contains(text(),'大型机')]//i[@class='ico-checkbox']")).click();
        Thread.sleep(500);

        takeScreenshot(caseId + ".png");
    }

    @Test
    public void test_CtripFlight_R004() throws InterruptedException {
        // R004: 单程查询结果筛选与重置 [cite: 39]

        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();
        searchButton.click();

        more_date.click();

        driver.findElement(By.xpath("(//div[@id='December-03-2025'])[1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[1]/div[3]/div[2]/div[1]/ul[2]/li[4]/span[1]")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//li[contains(text(),'价格高-低')]")).click();
        Thread.sleep(500);

        String caseId = "CtripFlight_R004_001";
        takeScreenshot(caseId + ".png");

    }

    // 3.2 往返车票查询
    @ParameterizedTest
    @CsvSource({
            "北京, 东京, CtripFlight_R005_001",
            "北京, 大阪, CtripFlight_R005_002",
            "上海, 东京, CtripFlight_R005_003",
            "上海, 大阪, CtripFlight_R005_004"
    })
    public void test_CtripFlight_R005(String fromCity, String toCity, String caseId) throws InterruptedException {
        // R005: 往返去程筛选 [cite: 40]


//      这个去程筛选~~
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys(fromCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys(toCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();

//      儿童的选择

        driver.findElement(By.xpath("(//div[@class='form-item-v3 flt-field-v3 '])[1]")).click();
        driver.findElement(By.xpath("(//i[@class='iconf-count-del'][contains(text(),'\uE662')])[1]")).click();
        driver.findElement(By.xpath("(//i[@class='iconf-count-del'][contains(text(),'\uE662')])[2]")).click();
        driver.findElement(By.xpath("(//i[@class='iconf-count-del'][contains(text(),'\uE662')])[3]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'确定')]")).click();
        searchButton.click();

        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }


        takeScreenshot(caseId + ".png");
    }

//
    @Test
    public void test_CtripFlight_R006() throws InterruptedException {
        // R006: 往返返程筛选 [cite: 46]


        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys("东京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }



        driver.findElement(By.xpath("//div[@class='airline-compare']")).click();

//      往下一页~~
        driver.findElement(By.xpath("//div[@class='next-page']")).click();
        driver.findElement(By.xpath("//div[@class='next-page']")).click();
        Thread.sleep(1000);

        takeScreenshot("CtripFlight_R006_001.png");
    }

//
    @ParameterizedTest
    @CsvSource({
            "北京, 广州, CtripFlight_R007_001",
            "北京, 成都, CtripFlight_R007_002",
            "上海, 广州, CtripFlight_R007_003",
            "上海, 成都, CtripFlight_R007_004",

    })
    public void test_CtripFlight_R007(String fromCity,String toCity, String caseId) throws InterruptedException {

        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        singleTripRadio.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys(fromCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys(toCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();

//      添加返程
        driver.findElement(By.xpath("//a[contains(text(),'+ 添加返程')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[@class='date-day '])[1]")).click();
        Thread.sleep(500);
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
//      点击交换图标
        driver.findElement(By.xpath("//i[@class='iconf-exchange']")).click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
//      选择去程
        driver.findElement(By.xpath("(//div[@class='btn btn-book'][contains(text(),'选为去程')])[1]")).click();
//      进行展开
        driver.findElement(By.xpath("(//button[@class='btn btn-book'][contains(text(),'订票')])[1]")).click();

        driver.findElement(By.xpath("(//span[contains(text(),'退改签说明')])[1]")).click();
        Thread.sleep(1000);

        takeScreenshot(caseId + ".png");
    }

    @FindBy(xpath = "//span[contains(text(),'往返')]")
    WebElement WangFan;

    @ParameterizedTest
    @CsvSource({
            "北京,东京,CtripFlight_R008_001",
            "北京,大阪,CtripFlight_R008_002",
            "上海,东京,CtripFlight_R008_003",
            "上海,大阪,CtripFlight_R008_004"
    })
    public void test_CtripFlight_R008(String fromCity,String toCity, String caseId) throws InterruptedException {

        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        WangFan.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys(fromCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys(toCity);
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
        departDateInput.click();

        driver.findElement(By.xpath("(//div[@class='date-day '])[1]")).click();
        Thread.sleep(500);
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        //      选择去程
        driver.findElement(By.xpath("(//div[@class='btn btn-book'][contains(text(),'选为去程')])[1]")).click();
        //      进行展开
        driver.findElement(By.xpath("(//button[@class='btn btn-book'][contains(text(),'订票')])[1]")).click();
//      订票
        driver.findElement(By.xpath("(//button[@id='0_0'])[1]")).click();
        Thread.sleep(1500);



        takeScreenshot(caseId + ".png");
    }




    // 3.3 中转换乘车票查询
    @ParameterizedTest
    @CsvSource({
            "哈尔滨, 济南, CtripFlight_R009_001",
            "哈尔滨, 西安, CtripFlight_R009_002",
            "哈尔滨, 拉萨, CtripFlight_R009_003",
            "长春, 济南, CtripFlight_R009_004",
            "长春, 西安, CtripFlight_R009_005",
            "长春, 拉萨, CtripFlight_R009_006",
            "安吉, 济南, CtripFlight_R009_007",
            "安吉, 西安, CtripFlight_R009_008",
            "安吉, 拉萨, CtripFlight_R009_009"
    })
    public void test_CtripFlight_R009(String fromCity, String toCity, String caseId) {
        // R009: 有效中转查询 (不指定中转城市) [cite: 58]
        // String date = competitionDatePlusOne; [cite: 61]

        takeScreenshot(caseId + ".png");
    }




    @ParameterizedTest
    @CsvSource({
            "北京, 上海, @@@, CtripFlight_R010_001",
            "广州, 成都, 123, CtripFlight_R010_002",
            "哈尔滨, 拉萨, NonExistent, CtripFlight_R010_003"
    })
    public void test_CtripFlight_R010(String fromCity, String toCity, String invalidTransferCity, String caseId) {
        // R010: 无效中转城市 [cite: 62]
        // String date = competitionDatePlusOne; [cite: 65]

        takeScreenshot(caseId + ".png");
    }

    @ParameterizedTest
    @CsvSource({
            "哈尔滨, 南京, 济南, CtripFlight_R011_001",
            "哈尔滨, 南京, 安阳, CtripFlight_R011_002",
            "哈尔滨, 南京, 周口, CtripFlight_R011_003"
    })
    public void test_CtripFlight_R011(String fromCity, String toCity, String transferCity, String caseId) {
        // R011: 指定中转城市查询与排序 [cite: 67]
        // String date = competitionDatePlusTwo; [cite: 70]

        takeScreenshot(caseId + ".png");
    }

    // 3.4 计次/定期车票查询
    @ParameterizedTest
    @CsvSource({
            "北京南, 天津, CtripFlight_R012_001",
            "上海虹桥, 杭州东, CtripFlight_R012_002",
            "广州南, 深圳北, CtripFlight_R012_003"
    })
    public void test_CtripFlight_R012(String fromCity, String toCity, String caseId) {
        // R012: 有效计次/定期票查询 [cite: 73]

        takeScreenshot(caseId + ".png");
    }

    @ParameterizedTest
    @CsvSource({
            "北京南, 广州南, CtripFlight_R013_001",
            "123, @#$, CtripFlight_R013_002",
            "上海, 拉萨, CtripFlight_R013_003"
    })
    public void test_CtripFlight_R013(String fromCity, String toCity, String caseId) {
        // R013: 无效计次/定期票查询 [cite: 76]

        takeScreenshot(caseId + ".png");
    }
    // test-code-end





    @AfterEach
    public void teardown() {
        this.driver.quit();
    }

    private void takeScreenshot(String fileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssddSSS");
        String timestamp = dateFormat.format(new Date());
        String timestampedFileName = timestamp + "_" + fileName;
        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }
        String screenshotFilePath = screenshotsDir.getAbsolutePath() + File.separator + timestampedFileName;
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(screenshotFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
