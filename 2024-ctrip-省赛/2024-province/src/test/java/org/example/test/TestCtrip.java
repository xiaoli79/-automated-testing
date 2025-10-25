package org.example.test;

import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.AbstractCollection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestCtrip {
    private WebDriver driver;

    private WebDriverWait wait;


    @BeforeEach
    public void setup() {
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ctrip.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // 初始化 @FindBy 元素
        PageFactory.initElements(driver, this);

        // 初始化 WebDriverWait，设置最长等待时间 (例如 10 秒)
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    // test-code-start

    // 请在此处插入JUnit5代码

    // --- 在此处定义所有 @FindBy 页面元素 ---

    @FindBy(css = ".pc_home-tabbtnIcon.lsn_ico_9C9TD")
    private WebElement ZhangKai;

    @FindBy(xpath = "//button[contains(@aria-label, '火车')]")
    private WebElement btnAdd;

    @FindBy(css = "button[aria-label='火车票 按回车键打开菜单'] span[class='lsn_top_nav_font_4h1KU lsn_top_nav_font_line_0iVuu']")
    private WebElement train_ticket;

    @FindBy(xpath = "//input[@id='label-departStation']")
    private WebElement start_city;

    @FindBy(xpath = "//input[@id='label-arriveStation']")
    private WebElement end_city;


    @FindBy(xpath = "//i[@class='ifont-checkbox']")
    private WebElement subway;

    @FindBy(xpath = "//div[@class='search-date']//div[1]")
    private WebElement click_date;

//  2025-10-26号
    @FindBy(xpath = "//div[@class='date-fade-enter-done']//div[1]//ul[2]//li[29]//strong[1]")
    private WebElement start_date;

    @FindBy(xpath = "//button[contains(text(),'搜索')]")
    private WebElement searchButton;


    @FindBy(xpath = "//button[contains(text(),'往返')]")
    private WebElement WangFan;



    // --- 在此处插入符合命名规范的 JUnit5 测试方法 ---

    // 3.1 单程车票查询
    @ParameterizedTest
    @CsvSource({
            "北京, 广州, 2024-12-20, Ctrip_R001_001.png",
            "北京, 成都, 2024-12-20, Ctrip_R001_002.png",
            "上海, 广州, 2024-12-20, Ctrip_R001_003.png",
            "上海, 成都, 2024-12-20, Ctrip_R001_004.png"
    })
    public void test_Ctrip_R001(String fromCity, String toCity, String date, String caseId) throws InterruptedException {
        // R001: 有效单程高铁动车查询
        // 填充自动化逻辑...

        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

//      通过这个进而取消下拉框~~nb
        driver.findElement(By.cssSelector("div[role='booking']")).click();
        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);
        searchButton.click();

        Thread.sleep(2000);
        takeScreenshot(caseId);
    }

    @ParameterizedTest
    @CsvSource({
            "@@@, Ctrip_R002_001.png",
            "123, Ctrip_R002_002.png"
    })
    public void test_Ctrip_R002(String invalidFromCity, String caseId) throws InterruptedException {
        // R002: 非法出发城市


        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys(invalidFromCity);
        Thread.sleep(2000);


        takeScreenshot(caseId);
    }

    @Test
    public void test_Ctrip_R003() throws InterruptedException {
        // R003: 单程查询结果筛选与排序
        String caseId = "Ctrip_R003_001.png";


        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys("北京");
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys("上海");

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);
        searchButton.click();

        driver.findElement(By.xpath("//strong[contains(text(),'仅显示有票车次')]")).click();

        driver.findElement(By.xpath("//strong[contains(text(),'高铁(G/C)')]")).click();


        driver.findElement(By.xpath("//div[@class='train-right']//div[3]//ul[1]//li[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[contains(text(),'价格排序')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[contains(text(),'价格排序')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[contains(text(),'价格排序')]")).click();


        Thread.sleep(2000);

        takeScreenshot(caseId);
    }

    @Test
    public void test_Ctrip_R004() throws InterruptedException {
        // R004: 单程查询结果筛选与重置
        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys("南京");
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys("广州");

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);
        searchButton.click();

        driver.findElement(By.xpath("//div[@class='screen-right-more']")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//strong[contains(text(),'仅显示有票车次')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//strong[contains(text(),'高铁(G/C)')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='train-right']//div[3]//ul[1]//li[1]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[@class='train-right']//div[4]//ul[1]//li[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//ul[@class='select-type']//strong[contains(text(),'二等座')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//ul[@class='select-type']//strong[contains(text(),'南京南')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//strong[contains(text(),'广州南')])[6]")).click();
        Thread.sleep(2000);
//        全部选中
        driver.findElement(By.xpath("//div[@class='filter-list open']//div[2]//div[1]//span[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='train-right']//div[3]//div[1]//span[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("body > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > div:nth-child(4) > div:nth-child(1) > span:nth-child(2)")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[5]//div[1]//span[1]")).click();

        driver.findElement(By.xpath("//div[@id='hp_container']//div[6]//div[1]//span[1]")).click();
        Thread.sleep(15000);
//        driver.findElement(By.xpath("//div[@id='hp_container']//div[6]//div[1]//span[1]")).click();
//        driver.findElement(By.xpath("//div[@id='hp_container']//div[7]//div[1]//span[1]")).click();
//        Thread.sleep(4000);
//        driver.findElement(By.xpath("//div[@class='screen-right-more open']")).click();

//        Thread.sleep(2000);
        String caseId = "Ctrip_R004_001.png";
        takeScreenshot(caseId);
    }

    @FindBy(xpath = "//div[@class='search-date']//div[2]")
    private WebElement end_dateClick;


    @FindBy(xpath = "//strong[normalize-space()='31']")
    private  WebElement end_date;

    // 3.2 往返车票查询
    @ParameterizedTest
    @CsvSource({
            "济南, 西安, 2024-11-17, 2024-11-20, Ctrip_R005_001.png", // 返回日期需根据实际可选确定
            "天津, 杭州, 2024-11-17, 2024-11-21, Ctrip_R005_002.png"  // 返回日期需根据实际可选确定
    })

//  去程筛选~~
    public void test_Ctrip_R005(String fromCity, String toCity, String departDate, String returnDate, String caseId) throws InterruptedException {
        // R005: 往返去程筛选

        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        WangFan.click();
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);

        end_dateClick.click();

        end_date.click();

        Thread.sleep(2000);



        searchButton.click();

        Thread.sleep(3000);
//      点击展开按钮
        driver.findElement(By.cssSelector("div[class='item-more']")).click();


        driver.findElement(By.xpath("//body[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[3]/ul[1]/li[1]/div[1]/strong[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[4]//div[1]//strong[1]")).click();

        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[5]//div[1]//i[1]")).click();
        Thread.sleep(2000);
//      出发时间
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[2]//div[2]//i[1]")).click();

//      到达时间~~
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[3]//div[2]//i[1]")).click();

        Thread.sleep(2000);


        takeScreenshot(caseId);
    }


//  返程筛选~~
    @ParameterizedTest
    @CsvSource({
            "兰州, 郑州, 2024-11-17, 2024-11-20, Ctrip_R006_001.png", // 返回日期需根据实际可选确定
            "徐州, 厦门, 2024-11-17, 2024-11-21, Ctrip_R006_002.png"  // 返回日期需根据实际可选确定
    })
    public void test_Ctrip_R006(String fromCity, String toCity, String departDate, String returnDate, String caseId) throws InterruptedException {
        // R006: 往返返程筛选
        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        WangFan.click();
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);

        end_dateClick.click();

        end_date.click();

        Thread.sleep(2000);



        searchButton.click();

//      点击展开按钮
        driver.findElement(By.xpath("//body/div[@id='hp_container']/div[@id='content']/div[@id='main']/div[@id='__next']/div/div[@class='train-wrapper return-view']/div[@class='return-box']/div[3]/div[2]/div[3]/div[1]")).click();

        driver.findElement(By.xpath("//ul[@class='screen-list open']//strong[contains(text(),'高铁(G/C)')]")).click();
        driver.findElement(By.xpath("//ul[@class='screen-list open']//strong[contains(text(),'二等座')]")).click();

        driver.findElement(By.xpath("//ul[@class='screen-list open']//li[5]//div[1]//i[1]")).click();
        Thread.sleep(2000);
//      出发时间
        driver.findElement(By.xpath("//ul[@class='screen-list open']//li[2]//div[2]//i[1]")).click();

//      到达时间~~
        driver.findElement(By.xpath("//ul[@class='screen-list open']//li[3]//div[2]//i[1]")).click();

        Thread.sleep(2000);


        takeScreenshot(caseId);

    }




    @ParameterizedTest
    @CsvSource({
            "12345, 海南, 2024-11-18, 2024-11-22, Ctrip_R007_001.png", // 返回日期需根据实际可选确定
            "!@#$%, 海南, 2024-11-18, 2024-11-23, Ctrip_R007_002.png" // 返回日期需根据实际可选确定
    })
    public void test_Ctrip_R007(String invalidFromCity, String toCity, String departDate, String returnDate, String caseId) throws InterruptedException {
        // R007: 添加返程后输入无效出发城市


        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        WangFan.click();
        start_city.click();
        start_city.clear();
        start_city.sendKeys(invalidFromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);


        Thread.sleep(2000);

        Actions actions=new Actions(driver);
        actions.moveToElement(click_date).click().perform();

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);

        Actions actions1=new Actions(driver);
        actions1.moveToElement(end_dateClick).click().perform();

        end_dateClick.click();

        end_date.click();

        Thread.sleep(2000);



        searchButton.click();
        Thread.sleep(2000);
        takeScreenshot(caseId);
    }


    

    @Test
    public void test_Ctrip_R008() throws InterruptedException {
        // R008: 往返复杂筛选（车站控件+高铁+多选）



        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Thread.sleep(1000);
        WangFan.click();

        start_city.click();

        driver.findElement(By.xpath("//li[normalize-space()='NPQRS']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//body[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[2]/ul[6]/li[6]")).click();


        Thread.sleep(2000);
        end_city.click();

        driver.findElement(By.xpath("//li[contains(text(),'热门选择')]")).click();
        driver.findElement(By.xpath("//ul[@class='widget-city-bd']//li[contains(text(),'北京')]")).click();



        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();
        Thread.sleep(1000);

        end_dateClick.click();

        end_date.click();

        subway.click();

        Thread.sleep(2000);

        searchButton.click();

//     去程的展开按钮~~
        //      点击展开按钮
        driver.findElement(By.cssSelector("div[class='item-more']")).click();



//        日期tab
//        driver.findElement(By.xpath("///body/div[@id='hp_container']/div[@id='content']/div[@id='main']/div[@id='__next']/div/div[@class='train-wrapper return-view']/div[@class='return-box']/div[1]/div[2]/ul[1]/li[3]")).click();


        Thread.sleep(2000);
//      出发时间
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[2]//div[1]//i[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[2]//div[2]//i[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[2]//div[3]//i[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[2]//div[4]//i[1]")).click();
//      到达时间~~
//        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[3]//div[1]//i[1]")).click();

//      坐席、出发车站、到达车站

        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[4]//div[1]//i[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[5]//div[1]//i[1]")).click();
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//div[3]//ul[1]//li[6]//div[1]//i[1]")).click();
        Thread.sleep(2000);


//      返程的展开按钮
//      点击展开按钮
        driver.findElement(By.xpath("//body/div[@id='hp_container']/div[@id='content']/div[@id='main']/div[@id='__next']/div/div[@class='train-wrapper return-view']/div[@class='return-box']/div[3]/div[2]/div[3]/div[1]")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='train-wrapper return-view']//div[3]//div[2]//div[3]//ul[1]//li[4]//div[1]//i[1]")).click();
        Thread.sleep(2000);
//      出发车站
        driver.findElement(By.xpath("//div[@class='train-wrapper return-view']//div[3]//div[2]//div[3]//ul[1]//li[5]//div[1]//i[1]")).click();
        Thread.sleep(2000);
//      到达车站~~
        driver.findElement(By.xpath("//div[@class='train-wrapper return-view']//div[3]//div[2]//div[3]//ul[1]//li[6]//div[1]//i[1]")).click();

        Thread.sleep(2000);


        String caseId = "Ctrip_R008_001.png";
        takeScreenshot(caseId);
    }


    @FindBy(xpath = "//button[contains(text(),'中转')]")
    private  WebElement Zhongzhuan;

    @FindBy(xpath = "//input[@id='label-transitStation']")
    private  WebElement transitStation;


    // 3.3 中转车票查询
    @ParameterizedTest
    @CsvSource({
            "哈尔滨, 济南, Ctrip_R009_001.png",
            "长春, 西安, Ctrip_R009_002.png",
            "安吉, 拉萨, Ctrip_R009_003.png"
    })
    public void test_Ctrip_R009(String fromCity, String toCity, String caseId) throws InterruptedException {
        // R009: 有效中转查询 (不指定中转城市)


        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Zhongzhuan.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();


        transitStation.click();
        transitStation.clear();
        transitStation.sendKeys("郑州");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("div[role='booking']")).click();
        Thread.sleep(1000);
        searchButton.click();
        Thread.sleep(3000);
        takeScreenshot(caseId);
    }

    @ParameterizedTest
    @CsvSource({
            "北京, 上海, @@@, Ctrip_R010_001.png",
            "广州, 成都, 123, Ctrip_R010_002.png"
    })
    public void test_Ctrip_R010(String fromCity, String toCity, String invalidTransferCity, String caseId) throws InterruptedException {
        // R010: 无效中转城市
        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Zhongzhuan.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();


        transitStation.click();
        transitStation.clear();
        transitStation.sendKeys(invalidTransferCity);
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("div[role='booking']")).click();
        Thread.sleep(1000);
        searchButton.click();
        Thread.sleep(3000);
        takeScreenshot(caseId);
    }

    @ParameterizedTest
    @CsvSource({
            "哈尔滨, 南京, 济南, Ctrip_R011_001.png",
            "哈尔滨, 南京, 安阳, Ctrip_R011_002.png",
            "哈尔滨, 南京, 周口, Ctrip_R011_003.png"
    })
    public void test_Ctrip_R011(String fromCity, String toCity, String transferCity, String caseId) throws InterruptedException {
        // R011: 指定中转城市查询与排序

        ZhangKai.click();
        Thread.sleep(1000);
        train_ticket.click();
        Zhongzhuan.click();
        Thread.sleep(1000);
        start_city.click();
        start_city.clear();
        start_city.sendKeys(fromCity);
        Thread.sleep(2000);

        end_city.click();
        end_city.clear();
        end_city.sendKeys(toCity);

        driver.findElement(By.cssSelector("div[role='booking']")).click();
//        subway.click();

        Thread.sleep(2000);

        click_date.click();
        Thread.sleep(1000);
        start_date.click();


        transitStation.click();
        transitStation.clear();
        transitStation.sendKeys(transferCity);
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("div[role='booking']")).click();
        Thread.sleep(1000);
        searchButton.click();
        Thread.sleep(3000);
//      运行时长~~~
        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//ul[2]//li[2]")).click();


        driver.findElement(By.xpath("//div[@class='return-box']//div[1]//div[2]//ul[2]//li[2]")).click();
        Thread.sleep(1000);
        // String date = competitionDatePlusTwo;
        takeScreenshot(caseId);
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
