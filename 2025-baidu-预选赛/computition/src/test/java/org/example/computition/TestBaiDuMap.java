package org.example.computition;


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

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;



public class TestBaiDuMap {
    private WebDriver driver;
    
    @BeforeEach
    public void setup() {
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://map.baidu.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }



     // test-code-start

    public void  test_meth_public(){
        PageFactory.initElements(driver,this);
        //放一点公共的代码~~
    }

    // R001, R002, R003: 公交查询
    @FindBy(xpath = "//div[@class='searchbox-content-button right-button route-button loading-button']")
    private WebElement routeButton;

    @FindBy(xpath = "//div[@class='tab-item bus-tab']")
    private WebElement busTab;

    @FindBy(xpath = "//input[@placeholder='输入起点或在图区上选点']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[@placeholder='输入终点或在图区上选点']")
    private WebElement searchOutput;

    @FindBy(xpath = "//button[@id='search-button']")
    private WebElement searchButton;


    // --- 在此处插入符合命名规范的 JUnit5 测试方法 ---

    @ParameterizedTest
    @CsvSource({
            "南京大学(鼓楼校区), 新街口商业步行区, BaiDuMap_R001_001.png",
            "南京大学(鼓楼校区), 先锋书店(五台山店), BaiDuMap_R001_002.png",
            "东南大学(四牌楼校区), 新街口商业步行区, BaiDuMap_R001_003.png",
            "东南大学(四牌楼校区), 先锋书店(五台山店), BaiDuMap_R001_004.png"
    })
    public void test_BaiDuMap_R001(String startPoint, String endPoint, String caseId) throws InterruptedException {
        // R001: 有效公交线路查询
        test_meth_public();
        Thread.sleep(2000);
        routeButton.click();

        busTab.click();


        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(startPoint+ Keys.ENTER);
        Thread.sleep(1000);

        searchOutput.click();
        searchOutput.clear();
        searchOutput.sendKeys(endPoint+Keys.ENTER);
        Thread.sleep(1000);

        searchButton.click();
        Thread.sleep(2000);

        // 填充自动化逻辑...
        takeScreenshot(caseId); // 截图文件必须包含 .png 后缀
    }


    @ParameterizedTest
    @CsvSource({
            "南京大学(鼓楼校区), 南京大学(仙林校区), 0, BaiDuMap_R002_001.png",
            "南京大学(鼓楼校区), 南京大学(仙林校区), 5, BaiDuMap_R002_002.png",
            "南京大学(鼓楼校区), 南京大学(仙林校区), 2, BaiDuMap_R002_003.png",
            "南京大学(鼓楼校区), 南京大学(仙林校区), 3, BaiDuMap_R002_004.png"
    })
    public void test_BaiDuMap_R002(String startPoint, String endPoint, String tabName, String caseId) throws InterruptedException {
        // R002: 切换公交查询方案
        test_meth_public();
        Thread.sleep(2000);
        routeButton.click();

        busTab.click();



        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(startPoint+ Keys.ENTER);
        Thread.sleep(1000);

        searchOutput.click();
        searchOutput.clear();
        searchOutput.sendKeys(endPoint+Keys.ENTER);
        Thread.sleep(1000);

        searchButton.click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("#type"+tabName)).click();
        Thread.sleep(2000);


        takeScreenshot(caseId);
    }

    @FindBy(xpath = "//a[@data-name = \"先锋书店(五台山店)\" and @data-px = \"13222688\" and @data-py = \"3747906\"]")
    private  WebElement camera;
    @FindBy(xpath = "//body[1]/div[1]/div[2]/ul[2]/li[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]")
    private WebElement site;



//
    @Test
    public void test_BaiDuMap_R003() throws InterruptedException {
        // R003: 公交查询详情与街景
        test_meth_public();
        Thread.sleep(2000);
        routeButton.click();

        busTab.click();



        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys("玄武湖景区"+ Keys.ENTER);
        Thread.sleep(1000);

        searchOutput.click();
        searchOutput.clear();
        searchOutput.sendKeys("先锋书店（五台山店）"+Keys.ENTER);
        Thread.sleep(1000);

        searchButton.click();
        Thread.sleep(2000);
        Actions actions=new Actions(driver);
        actions.moveToElement(site).perform();

        driver.findElement(By.xpath("//body[1]/div[1]/div[2]/ul[2]/li[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/div[1]")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("div[id='scheme_3'] div[class='schemeName']")).click();
        Thread.sleep(2000);

        camera.click();
        camera.click();
        Thread.sleep(10000);


        String caseId = "BaiDuMap_R003_001";
        takeScreenshot(caseId + ".png");
    }

    @FindBy(xpath = "//a[@name='南京']")
    private WebElement NanJing;

    @FindBy(xpath = "//i[contains(text(),'路况')]")
    private WebElement road_state;

    @FindBy(xpath = "//span[@id='tarffic_ss']")
    private WebElement time_road;

    @FindBy(xpath = "//span[@id='bt_trafficCtrl']")
    private WebElement flush;

    @Test
    public void test_BaiDuMap_R004() throws InterruptedException {
//      点击地点下拉
        test_meth_public();

        driver.findElement(By.cssSelector("a[class='ui3-city-change-inner ui3-control-shadow'] em")).click();

        NanJing.click();
        Thread.sleep(500);
        road_state.click();
        Thread.sleep(500);
        time_road.click();
        Thread.sleep(500);
        flush.click();
        Thread.sleep(2000);

        // R004: 实时路况刷新
        String caseId = "BaiDuMap_R004_001";
        takeScreenshot(caseId + ".png");
    }

    @FindBy(xpath = "//span[@id='tarffic_yc']")
    private WebElement road_predict;

    @ParameterizedTest
    @CsvSource({
            "0, BaiDuMap_R005_001",
            "1, BaiDuMap_R005_002",
            "2, BaiDuMap_R005_003",
            "3, BaiDuMap_R005_004",
            "4, BaiDuMap_R005_005",
            "5, BaiDuMap_R005_006",
            "6, BaiDuMap_R005_007"
    })
    public void test_BaiDuMap_R005(String day, String caseId) throws InterruptedException {
        // R005: 路况预测（切换星期）
        test_meth_public();

        driver.findElement(By.cssSelector("a[class='ui3-city-change-inner ui3-control-shadow'] em")).click();

        NanJing.click();
        Thread.sleep(500);
        road_state.click();
        Thread.sleep(500);
//      实时路况
        road_predict.click();
        Thread.sleep(1000);

//      进行拼接~~
        driver.findElement(By.cssSelector("#week_trafficCtrl_"+day)).click();
        Thread.sleep(1000);


        takeScreenshot(caseId + ".png");
    }

    @FindBy(css = "#bar_trafficCtrl")
    private WebElement bar_trafficCtrl;

    @Test
    public void test_BaiDuMap_R006() throws InterruptedException {
        // R006: 路况预测（拖动时间轴）
        test_meth_public();

        driver.findElement(By.cssSelector("a[class='ui3-city-change-inner ui3-control-shadow'] em")).click();

        NanJing.click();
        Thread.sleep(500);
        road_state.click();
        Thread.sleep(500);
//      实时路况
        road_predict.click();
        Thread.sleep(1000);

//      进行时间轴的拖动~~ 有bug需要手动设置~~
        Actions actions=new Actions(driver);
        int xOffset = 35;

        actions.dragAndDropBy(bar_trafficCtrl, xOffset, 0).perform();


        String caseId = "BaiDuMap_R006_001";
        takeScreenshot(caseId + ".png");
    }

    @FindBy(xpath = "//i[contains(text(),'地铁')]")
    private WebElement subway;

    @FindBy(id = "sub_start_input")
    private WebElement start_input;

    @FindBy(id = "sub_end_input")
    private WebElement end_input;

    @FindBy(id = "search-button")
    private WebElement search_button;


    @ParameterizedTest
    @CsvSource({
            "珠江路, 南京站, BaiDuMap_R007_001",
            "珠江路, 卡子门, BaiDuMap_R007_002",
            "新街口, 南京站, BaiDuMap_R007_003",
            "新街口, 卡子门, BaiDuMap_R007_004"
    })
    public void test_BaiDuMap_R007(String startStation, String endStation, String caseId) throws InterruptedException {
        // R007: 地铁线路（输入框查询）
        test_meth_public();

        driver.findElement(By.cssSelector("a[class='ui3-city-change-inner ui3-control-shadow'] em")).click();

        NanJing.click();
        Thread.sleep(500);
        subway.click();
        start_input.click();
        start_input.sendKeys(startStation);
        Thread.sleep(1000);
        end_input.click();
        end_input.sendKeys(endStation);
        Thread.sleep(1000);
        search_button.click();
        Thread.sleep(2000);





        takeScreenshot(caseId + ".png");
    }
//  最后一个代码定位不到~~未过
    @Test
    public void test_BaiDuMap_R008() throws InterruptedException {
        // R008: 地铁线路（图区选点查询）
        test_meth_public();
        driver.findElement(By.cssSelector("a[class='ui3-city-change-inner ui3-control-shadow'] em")).click();
        NanJing.click();
        Thread.sleep(500);
        subway.click();

        Thread.sleep(2000);
//      起点
        driver.findElement(By.cssSelector("#svgjsImage6391")).click();
//      终点
        driver.findElement(By.cssSelector("#svgjsImage6370")).click();
        Thread.sleep(2000);
        String caseId = "BaiDuMap_R008_001";
        takeScreenshot(caseId + ".png");

    }

    // 请在此处插入Selenium+JUnit5代码

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