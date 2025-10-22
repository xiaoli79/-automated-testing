
package com.example.demo;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Test12306 {
    private WebDriver driver;

    @BeforeEach
    public void setup() {
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.12306.cn/index/index.html");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    // test-code-start
    // 你可以在这里定义所有页面元素

    @FindBy(linkText = "车票")
    private WebElement ticketClick;

    @FindBy(linkText = "单程")
    private WebElement DanCheng;

    @FindBy(linkText = "往返")
    private WebElement WangFan;
    @FindBy(linkText = "中转换乘")
    private WebElement ZhongZhuan;

    @FindBy(xpath = "//input[@id='fromStationText']")
    private WebElement fromStationInput;

    @FindBy(css = "div[id='citem_2'] span:nth-child(1)")
    private WebElement   toEnsureFrom;

    @FindBy(xpath = "//input[@id='toStationText']")
    private WebElement toStationInput;

//  点击搜索框~~
    @FindBy(xpath = "//a[@id='query_ticket']")
    private WebElement queryTicket;

    @FindBy(xpath = "//input[@id='train_date']")
    private WebElement trainDateInput;

    @FindBy(xpath = "//input[@id='back_train_date']")
    private WebElement backTrainDateInput;





    // --- Test Methods (Strictly follow naming convention) ---

    @ParameterizedTest
    @CsvSource({
            "北京, 广州, 2025-10-30, 12306_R001_001.png",
            "北京, 成都, 2025-10-31, 12306_R001_002.png",
            "上海, 广州, 2025-10-28, 12306_R001_003.png",
            "上海, 成都, 2025-10-29, 12306_R001_004.png"
    })
    public void test_12306_R001(String from, String to, String date, String caseId) throws InterruptedException {
        // 需求1: 有效单程查询

        // 2. 【关键步骤】等待并点击精确的建议项
        // 构造一个动态的、精确的 XPath
//        String fromSuggestionXPath = "//li[text()='" + from + "']";
//        String toSuggestionXPath = "//li[text()='" + from + "']";
        // 填充自动化逻辑
        ticketClick.click();
        DanCheng.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        if (!Objects.equals(from,"上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        if (!Objects.equals(to,"上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys(date);

        Thread.sleep(1000);
        queryTicket.click();

        Thread.sleep(1000);

        takeScreenshot(caseId);
        Thread.sleep(2000);
    }

    @ParameterizedTest
    @CsvSource({
            "北京, 广州, 2026-01-01, 12306_R002_001.png",
            "上海, 成都, 2026-02-30, 12306_R002_002.png"
    })
    public void test_12306_R002(String from, String to, String invalidDate, String caseId) throws InterruptedException {
        // 需求2: 无效出发日
        ticketClick.click();
        DanCheng.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        if (!Objects.equals(from,"上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        if (!Objects.equals(to,"上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys(invalidDate);

        Thread.sleep(1000);
        queryTicket.click();

        Thread.sleep(1000);
        takeScreenshot(caseId);
    }

    @FindBy(xpath = "//div[@id='cc_train_type_btn_all']//li[1]")
    private WebElement trainTypeBtn;
    @FindBy(xpath = "//div[@id='cc_from_station_name_all']//li[1]")
    private WebElement fromStationName;
    @FindBy(xpath = "//label[@id='cc_to_station_上海松江']")
    private WebElement toStationName;
    @FindBy(css = "#cc_seat_type_9")
    private WebElement seatType;


    @Test
    public void test_12306_R003() throws InterruptedException {
        String caseId = "12306_R003_001.png";
        // 需求3: 单程查询结果筛选
        ticketClick.click();
        DanCheng.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys("北京"+Keys.ENTER);
        if (!Objects.equals("北京","上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys("上海"+Keys.ENTER);
        if (!Objects.equals("上海","上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys("2025-10-29");

        Thread.sleep(1000);
        queryTicket.click();

        Thread.sleep(1000);
//      对类型进行选择
        trainTypeBtn.click();
        fromStationName.click();
        toStationName.click();
        seatType.click();

        Thread.sleep(2000);

        takeScreenshot(caseId);
    }

    @ParameterizedTest
    @CsvSource({
            "北京, 上海, 2025-10-25, 2025-10-29, 12306_R004_001.png",
            "广州, 成都, 2025-10-25, 2025-10-28, 12306_R004_002.png"
    })
    public void test_12306_R004(String from, String to, String departDate, String returnDate, String caseId) throws InterruptedException {
        // 需求4: 有效往返查询
        ticketClick.click();
        WangFan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        if (!Objects.equals(from,"上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        if (!Objects.equals(to,"上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys(departDate);

        backTrainDateInput.click();
        backTrainDateInput.clear();
        backTrainDateInput.sendKeys(returnDate);

        Thread.sleep(1000);
        queryTicket.click();
        Thread.sleep(2000);
        takeScreenshot(caseId);
        Thread.sleep(2000);
    }

    @ParameterizedTest
    @CsvSource({ "北京, 上海, 2024-11-02, 2024-11-01, 12306_R005_001" })
    public void test_12306_R005(String from, String to, String departDate, String invalidReturnDate, String caseId) throws InterruptedException {
        // 需求5: 无效返程日
        ticketClick.click();
        WangFan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        if (!Objects.equals(from,"上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        if (!Objects.equals(to,"上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys(departDate);

        backTrainDateInput.click();
        backTrainDateInput.clear();
        backTrainDateInput.sendKeys(invalidReturnDate);

        Thread.sleep(1000);
        queryTicket.click();
        Thread.sleep(2000);
        takeScreenshot(caseId);
        Thread.sleep(2000);
        takeScreenshot(caseId);
    }

    @FindBy(id = "nav_list3")
    private WebElement navList;
    @FindBy(xpath = "//li[@title='福州南']")
    private WebElement FuZhouNan;

    @FindBy(xpath = "//li[@title='厦门']")
    private WebElement XiaMeng;


    @FindBy(id = "sf2_label")
    private WebElement Studnet_Ticket;


    @Test


//  不可交互~~·
    public void test_12306_R006() throws InterruptedException {
        // 需求6: 往返学生票与筛选
        ticketClick.click();
        WangFan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        navList.click();
        FuZhouNan.click();

        toStationInput.click();
        XiaMeng.click();
        Thread.sleep(2000);

        trainDateInput.click();
        trainDateInput.clear();
        trainDateInput.sendKeys("2025-10-25");

        backTrainDateInput.click();
        backTrainDateInput.clear();
        backTrainDateInput.sendKeys("2025-10-29");

        Studnet_Ticket.click();

        queryTicket.click();
//        driver.findElement(By.xpath("//span[contains(text(),'11-01 周六')]")).click();

        driver.findElement(By.xpath("//label[contains(text(),'智能动车组')]")).click();

        driver.findElement(By.cssSelector("#from_station_name_all")).click();

        driver.findElement(By.cssSelector("#to_station_name_all")).click();

        driver.findElement(By.cssSelector("label[for='avail_zk']")).click();

        Thread.sleep(2000);





        String caseId = "12306_R006_001.png";
        takeScreenshot(caseId);
    }

    @ParameterizedTest
    @CsvSource({ "哈尔滨,济南, 12306_R007_001.png",
                 "长春,西安, 12306_R007_002.png",
                 "安吉,拉萨, 12306_R007_003.png" })
    public void test_12306_R007(String from, String to, String caseId) throws InterruptedException {
        // 需求7: 有效中转查询 (不指定换乘站)
        ticketClick.click();
        ZhongZhuan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        Thread.sleep(2000);
        if (Objects.equals(from,"哈尔滨")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        Thread.sleep(2000);
        if (Objects.equals(to,"西安")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }
        driver.findElement(By.cssSelector("#_a_search_btn")).click();
        Thread.sleep(2000);

        takeScreenshot(caseId);
    }

    @FindBy(css = "#radio_input_search")
    private WebElement HuanChengzhan;

    @FindBy(css="#changeStationText")
    private WebElement changeStationText;

    @ParameterizedTest
    @CsvSource({ "北京, 上海, '', 12306_R008_001.png",
                 "北京, 上海, @#$, 12306_R008_002.png",
                 "北京, 上海, XXX, 12306_R008_003.png" })
    public void test_12306_R008(String from, String to, String invalidTransfer, String caseId) throws InterruptedException {
        // 需求8: 无效换乘站
        ticketClick.click();
        ZhongZhuan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from+Keys.ENTER);
        if (!Objects.equals(from,"上海")){
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to+Keys.ENTER);
        if (!Objects.equals(to,"上海")){
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }

        HuanChengzhan.click();
        changeStationText.click();
        changeStationText.sendKeys(invalidTransfer);

        driver.findElement(By.cssSelector("#_a_search_btn")).click();
        Thread.sleep(2000);
        takeScreenshot(caseId);
        Thread.sleep(2000);
    }

    @ParameterizedTest
    @CsvSource({ "哈尔滨, 南京, 济南, 12306_R009_001.png"
            , "哈尔滨, 南京, 安阳, 12306_R009_002.png" })
    public void test_12306_R009(String from, String to, String transferStation, String caseId) throws InterruptedException {
        // 需求9: 指定换乘站


        ticketClick.click();
        ZhongZhuan.click();
        Thread.sleep(1000);
        fromStationInput.click();
        fromStationInput.clear();
        fromStationInput.sendKeys(from + Keys.ENTER);
        Thread.sleep(2000);
        if (Objects.equals(from, "哈尔滨")) {
            fromStationInput.sendKeys(Keys.BACK_SPACE);
        }

        Thread.sleep(1000);
        toStationInput.click();
        toStationInput.clear();
        toStationInput.sendKeys(to + Keys.ENTER);
        Thread.sleep(2000);
        if (Objects.equals(to, "西安")) {
            toStationInput.sendKeys(Keys.BACK_SPACE);
        }

        HuanChengzhan.click();
        changeStationText.click();
        changeStationText.sendKeys(transferStation);

        driver.findElement(By.cssSelector("#_a_search_btn")).click();
        Thread.sleep(2000);

        takeScreenshot(caseId);
        Thread.sleep(2000);

    }

    @FindBy(linkText = "计次•定期票")
    private WebElement DingTicket;


    //TODO
    //R010

    @ParameterizedTest
    @CsvSource({ "北京, 大厂, 12306_R010_001.png",
                 "上海, 唐山, 12306_R010_002.png" })
    public void test_12306_R010(String from, String to, String caseId) throws InterruptedException {
        // 需求10: 有效计次/定期票查询
        // 注意: 此需求可能需要扫码登录，无法完全自动化

        ticketClick.click();
        DingTicket.click();
        Thread.sleep(15000);

        driver.findElement(By.cssSelector(".nav-hd.item[href='javascript:void(0)']")).click();
        driver.findElement(By.cssSelector("a[name='g_href'][data-type='2'][data-href='view/commutation_index.html']")).click();

        driver.findElement(By.cssSelector("#city-start")).click();
        driver.findElement(By.cssSelector("#city-start")).sendKeys(from);

//      需要写动态xpath
        String fromOptionXPath = "//ul[@id='start-box']//li[normalize-space(text())='" + from + "']";
        driver.findElement(By.xpath(fromOptionXPath)).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#city-end")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#city-end")).sendKeys(to);
//      这是北京南的选择
        Thread.sleep(2000);
//      需要写动态xpath
        String toOptionXPath = "//ul[@id='end-box']//li[normalize-space(text())='" + to + "']";
        driver.findElement(By.xpath(toOptionXPath)).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".btn.btn-primary.w140.go-search")).click();


        Thread.sleep(2000);
        takeScreenshot(caseId);

    }


    //TODO
//    R011
    @ParameterizedTest
    @CsvSource({ "北京, @#$, 12306_R011_001.png",
                 "哈尔滨, 拉萨, 12306_R011_002.png" })
    public void test_12306_R011(String from, String to, String caseId) throws InterruptedException {
        ticketClick.click();
        DingTicket.click();
        Thread.sleep(15000);

        driver.findElement(By.cssSelector(".nav-hd.item[href='javascript:void(0)']")).click();
        driver.findElement(By.cssSelector("a[name='g_href'][data-type='2'][data-href='view/commutation_index.html']")).click();

        driver.findElement(By.cssSelector("#city-start")).click();
        driver.findElement(By.cssSelector("#city-start")).sendKeys(from);
        String fromOptionXPath = "//ul[@id='start-box']//li[normalize-space(text())='" + from + "']";
        driver.findElement(By.xpath(fromOptionXPath)).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#city-end")).click();
        driver.findElement(By.cssSelector("#city-end")).sendKeys(to);
//      这是北京南的选择
//        String toOptionXPath = "//ul[@id='end-box']//li[normalize-space(text())='" + to + "']";
//        driver.findElement(By.xpath(toOptionXPath)).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".btn.btn-primary.w140.go-search")).click();


        Thread.sleep(2000);
        takeScreenshot(caseId);
    }

    // test-code-end

    @AfterEach
    public void teardown() {
        this.driver.quit();
    }

    private void takeScreenshot(String fileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
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
