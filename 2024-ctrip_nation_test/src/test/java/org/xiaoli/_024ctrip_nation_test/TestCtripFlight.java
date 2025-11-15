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



//   未完成~~最后一个专用
    // 3.3 中转换乘车票查询
    @Test
    public void test_CtripFlight_R009() throws InterruptedException {

        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        WangFan.click();
        departCityInput.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(1000);
        departCityInput.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Thread.sleep(500);
        arriveCityInput.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        click_date.click();
        Thread.sleep(500);
//        departDateInput.click();
        driver.findElement(By.xpath("(//div[@class='date-day '])[23]")).click();

        driver.findElement(By.xpath("(//span[contains(@class,'date-d')][normalize-space()='17'])[1]")).click();
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
        driver.findElement(By.xpath("//div[contains(text(),'邻近航线')]")).click();
//      缺口程航线
        driver.findElement(By.xpath("//div[@class='recommend-content']")).click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='form-label']")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }




        takeScreenshot("CtripFlight_R009_001"+ ".png");
    }


    @FindBy(xpath = "//li[contains(@class,'last')]//span[@class='radio-label']")
    WebElement DuoCheng;


    @FindBy(name = "mtDCity1")
    WebElement Fcity1;

    @FindBy(name = "mtDCity2")
    WebElement Fcity2;

    @FindBy(name = "mtDCity3")
    WebElement Fcity3;

    @FindBy(name = "mtDCity4")
    WebElement Fcity4;


    @FindBy(name = "mtACity1")
    WebElement Tcity1;

    @FindBy(name = "mtACity2")
    WebElement Tcity2;

    @FindBy(name = "mtACity3")
    WebElement Tcity3;

    @FindBy(name = "mtACity4")
    WebElement Tcity4;

    @FindBy(xpath = "(//input[contains(@aria-label,'请选择日期')])[1]")
    WebElement date1;

    @FindBy(xpath = "(//input[contains(@aria-label,'请选择日期')])[2]")
    WebElement date2;

    @FindBy(xpath = "(//input[contains(@aria-label,'请选择日期')])[3]")
    WebElement date3;

    @FindBy(xpath = "(//input[contains(@aria-label,'请选择日期')])[4]")
    WebElement date4;



    @Test
    public void test_CtripFlight_R010() throws InterruptedException {
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        DuoCheng.click();
        Fcity1.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Fcity1.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Tcity1.click();
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Tcity1.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);

        date1.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='19'])[1]")).click();
        Thread.sleep(2000);

        Fcity2.click();
        Fcity2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity2.sendKeys(Keys.DELETE); // 或者 Keys.BACK_SPACE
        Fcity2.sendKeys("北京");




        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        searchButton.click();




        takeScreenshot("CtripFlight_R010_001.png");
    }

    @Test
    public void test_CtripFlight_R011() throws InterruptedException {
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        DuoCheng.click();
        Fcity1.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Fcity1.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Tcity1.click();
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Tcity1.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(500);
        searchButton.click();


        takeScreenshot( "CtripFlight_R011_001.png");
    }

    // 3.4 计次/定期车票查询
    @Test
    public void test_CtripFlight_R012() throws InterruptedException {
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        DuoCheng.click();
        Fcity1.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Fcity1.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Tcity1.click();
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Tcity1.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);

        date1.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='19'])[1]")).click();
        Thread.sleep(2000);


        Fcity2.click();
        Fcity2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity2.sendKeys(Keys.DELETE);
        Fcity2.sendKeys("郑州");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();


        Tcity2.click();
        Tcity2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Tcity2.sendKeys(Keys.DELETE);
        Tcity2.sendKeys("哈尔滨");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        date2.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='20'])[1]")).click();
        Thread.sleep(2000);



        Fcity3.click();
        Fcity3.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity3.sendKeys(Keys.DELETE);
        Fcity3.sendKeys("上海");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();


        Tcity3.click();
        Tcity3.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Tcity3.sendKeys(Keys.DELETE);
        Tcity3.sendKeys("厦门");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        date3.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='21'])[1]")).click();
        Thread.sleep(2000);


        searchButton.click();

        takeScreenshot("CtripFlight_R012_001.png");
    }

//  有bug~~
    @Test
    public void test_CtripFlight_R013() throws InterruptedException {
        // R013: 无效计次/定期票查询 [cite: 76]
        test_meth_public();
        init.click();
        flightTab.click();
        Thread.sleep(500);
        DuoCheng.click();

        driver.findElement(By.xpath("//a[contains(text(),'+ 再加一程')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[contains(text(),'+ 再加一程')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[contains(text(),'+ 再加一程')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[6]//a[1]//i[1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[5]//a[1]//i[1]")).click();

        Fcity1.click();
        Actions actions = new  Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Fcity1.sendKeys("上海");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Tcity1.click();
        actions.sendKeys(Keys.BACK_SPACE).perform();
        Tcity1.sendKeys("北京");
        Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);

        date1.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='19'])[1]")).click();
        Thread.sleep(2000);


        Fcity2.click();
        Fcity2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity2.sendKeys(Keys.DELETE);
        Fcity2.sendKeys("北京");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();


        Tcity2.click();
        Tcity2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Tcity2.sendKeys(Keys.DELETE);
        Tcity2.sendKeys("首尔");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        date2.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='20'])[1]")).click();
        Thread.sleep(2000);



        Fcity3.click();
        Fcity3.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity3.sendKeys(Keys.DELETE);
        Fcity3.sendKeys("首尔");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();


        Tcity3.click();
        Tcity3.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Tcity3.sendKeys(Keys.DELETE);
        Tcity3.sendKeys("东京");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        date3.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='21'])[1]")).click();
        Thread.sleep(2000);

//      第4个选项
        Fcity4.click();
        Fcity4.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Fcity4.sendKeys(Keys.DELETE);
        Fcity4.sendKeys("首尔");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();


        Tcity4.click();
        Tcity4.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        Tcity4.sendKeys(Keys.DELETE);
        Tcity4.sendKeys("东京");
        Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform();

        date4.click();
        driver.findElement(By.xpath("(//span[@class='date-d'][normalize-space()='22'])[1]")).click();
        Thread.sleep(2000);



        searchButton.click();

        Thread.sleep(10000);
        driver.findElement(By.xpath("//div[@class='flight-list root-flights']//span//div//div//div//div[@class='flight-item domestic']//div[@class='btn btn-book'][contains(text(),'选为第1程')]")).click();

        Thread.sleep(10000);
        driver.findElement(By.xpath("(//div[@class='btn btn-book'])[1]")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("(//div[@class='btn btn-book'][contains(text(),'选为第3程')])[1]"));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='flight-list root-flights select-flight']//span//div//div//div//div[@class='flight-item domestic']//button[@class='btn btn-book txt-overflow'][contains(text(),'订票')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='abbr']")).click();
        Thread.sleep(2000);
        takeScreenshot( "CtripFlight_R013_001.png");
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
