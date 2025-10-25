package org.example.test;

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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class 省赛TestCtrip答案 {
    private WebDriver driver;


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

    }
    // test-code-start
    public void test_meth_public(){
        PageFactory.initElements(driver,this);
        //step1 点击展开按钮
        zhankaibutton.click();
        Actions actions=new Actions(driver);
        //1.难点1
        //step2
        actions.moveToElement(huochepiaoElement).click().perform();
    }
    @FindBy(xpath ="//*[@id=\"leftSideNavLayer\"]/div/div/div[1]/div/div")
    public WebElement zhankaibutton;
    @FindBy(xpath ="//button[@class=\"lsn_top_nav_qdgwe\"and@aria-label=\"火车票 按回车键打开菜单\"]")
    public  WebElement huochepiaoElement;
    @FindBy(xpath="//input[@placeholder=\"出发城市\"]")
    public WebElement fromOwDCityElement;
    @FindBy(xpath = "//input[@placeholder=\"到达城市\"]")
    public  WebElement toOwDCityElement;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[3]/i")
    public  WebElement OnlySeacrhGT;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[4]/div[1]/div[1]")
    public  WebElement DepatureDate;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div[2]/ul[2]/li[20]")
    public  WebElement December20;
    @FindBy(xpath ="//button[@class=\"btn-blue btn-search\"]" )
    public  WebElement searchButton;

    @ParameterizedTest
    @CsvSource({
            "北京,广州,test_Ctrip_R001_001.png",
            "北京,成都,test_Ctrip_R001_002.png",
            "上海,广州,test_Ctrip_R001_003.png",
            "上海,成都,test_Ctrip_R001_004.png"
    })
    public void test_Ctrip_R001(String fromStation ,String toStation,String screenhots) throws InterruptedException {
        test_meth_public();
        //step3输入出发城市
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(1000);
        //step4 输入到达城市
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        //step5 勾选【只搜高铁动车】选择框
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]")).click();
        OnlySeacrhGT.click();
        //这里需要加一个强制等待,不然系统反应不过来报错
        Thread.sleep(1000);
        //注意:这里是这个小坑 需再次点击一下
        DepatureDate.click();
        //step6 模拟鼠标事件点击日期控件
        Actions actionsTwo=new Actions(driver);
        actionsTwo.moveToElement(DepatureDate).click().perform();
        //step7 选择出发日期为2024-12-20
        December20.click();
        //step8 点击搜索按钮
        searchButton.click();
        Thread.sleep(3000);
        //step9 截图
        takeScreenshot(screenhots);
    }
    @ParameterizedTest
    @CsvSource({
            "&&&,test_Ctrip_R002_001.png",
            "水电费,test_Ctrip_R002_002.png"
    })
    public  void  test_Ctrip_R002(String fromStation,String screenhosts) throws InterruptedException {
        test_meth_public();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(2000);
        takeScreenshot(screenhosts);
    }
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div[1]/ul[2]/li[23]")
    public  WebElement November18;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[1]/ul/li")
    public  WebElement OnlyHaveTickets;
    @FindBy(linkText = "重置")
    public  WebElement resetButton;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[2]/ul/li[1]")
    public  WebElement firstChecked;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[3]/ul/li[2]")
    public  WebElement goOff;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[1]/div[1]/ul[2]/li[3]/div")
    public  WebElement price;
    @Test
    public  void  test_Ctrip_R003() throws InterruptedException {
        test_meth_public();
        //step3 出发城市输入北京
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys("北京");
        //step4 到达城市输入上海
        toOwDCityElement.click();
        //这里需要加一个强制等待,不然系统反应不过来报错
        Thread.sleep(1000);
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys("上海");
        //step5 模拟鼠标事件点击日期控件
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        //注意:这里是这个小坑 需再次点击一下
        DepatureDate.click();
        //step6 选择日期
        November18.click();
        //step7 点击搜索按钮
        searchButton.click();
        //step8 仅有票车次
        OnlyHaveTickets.click();
        //重置
        resetButton.click();
        //step9 车型选择第一个复选框
        firstChecked.click();
        //step10 出发时间选择第二个复选框
        goOff.click();
        //价格这里需要点两次第一次是由低到高 第二次是由高到低
        price.click();
        price.click();
        Thread.sleep(3000);
        takeScreenshot("test_Ctrip_R003_001.png");
    }

    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[3]/ul/li[1]")
    public  WebElement  goDateOne;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[3]")
    public  WebElement unfoldElement;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[4]/ul/li[1]")
    public  WebElement arriveTimeOne;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[5]/ul/li[1]")
    public  WebElement sitdownOne;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[6]/ul/li[1]")
    public  WebElement DepartureStation;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[2]/div[7]/ul/li[1]")
    public  WebElement arriveStation;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div[2]/div[1]/div[3]/i")
    public  WebElement foldElement;
    @Test
    public  void  test_Ctrip_R004() throws InterruptedException {
        test_meth_public();
        //step3 出发城市
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys("南京");
        //这里需要加一个强制等待,不然系统反应不过来报错
        Thread.sleep(1000);
        //step4 到达城市
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys("广州");
        //step5 模拟鼠标事件点击日期控件
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        //注意:这里是这个小坑 需再次点击一下
        DepatureDate.click();
        //step6 点击日期
        November18.click();
        //step7 点击搜索按钮
        searchButton.click();
        //step11 展开筛选列表
        unfoldElement.click();
        //step8 仅有票车次
        OnlyHaveTickets.click();
        //step9 车型
        firstChecked.click();
        //step10 出发时间
        goDateOne.click();
        //step12 到达时间
        arriveTimeOne.click();
        //step13 坐席
        sitdownOne.click();
        //step14 出发车站
        DepartureStation.click();
        //step15 到达车站
        arriveStation.click();
        List<WebElement> chongzhiElements = driver.findElements(By.linkText("重置"));
        // 依次点击每个元素
        for (WebElement element : chongzhiElements) {
            element.click();
            Thread.sleep(2000);
        }
        foldElement.click();
        Thread.sleep(2000);
        takeScreenshot("test_Ctrip_R004_001.png");
    }
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[1]/div/ul/li[2]")
    public  WebElement WangFan;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[4]/div[1]/div[2]")
    public  WebElement returnDate;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div[1]/ul[2]/li[24]")
    public WebElement returnDateGo;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/ul/li[1]/div[1]")
    public WebElement QCModel;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/div")
    public  WebElement QCOpenAll;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/ul/li[4]/div[1]")
    public  WebElement QCSitdown;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/ul/li[6]/div[1]")
    public   WebElement QCArriveStation;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/ul/li[2]/div[2]")
    public  WebElement QCGoTime;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/div[3]/ul/li[3]/div[2]")
    public  WebElement QCArriveTime;
    @ParameterizedTest
    @CsvSource({
            "济南,西安,test_Ctrip_R005_001.png",
            "济南,杭州,test_Ctrip_R005_002.png",
            "天津,西安,test_Ctrip_R005_003.png",
            "天津,杭州,test_Ctrip_R005_004.png",
    })
    public  void test_XieCheng_R005(String fromStation ,String toStation,String screenhots) throws InterruptedException {
        test_meth_public();
        WangFan.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(2000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Thread.sleep(2000);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        Actions actionsTwo=new Actions(driver);
        actionsTwo.moveToElement(returnDate).click().perform();
        returnDate.click();
        returnDateGo.click();
        searchButton.click();
        QCModel.click();
        QCOpenAll.click();
        QCSitdown.click();
        QCArriveStation.click();
        QCGoTime.click();
        QCArriveTime.click();
        Thread.sleep(3000);
        takeScreenshot(screenhots);
    }
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[1]/div[1]")
    public  WebElement FCmodel;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[4]/div[1]")
    public   WebElement FCSitdown;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[6]/div[1]")
    public  WebElement FCArriveStation;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[2]/div[2]")
    public WebElement FCGoTime;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[3]/div[2]")
    public WebElement FCArriveTime;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/div")
    public  WebElement FCOpenAll;
    @ParameterizedTest
    @CsvSource({
            "兰州,郑州,test_Ctrip_R006_001.png",
            "兰州,厦门,test_Ctrip_R006_002.png",
            "徐州,郑州,test_Ctrip_R006_003.png",
            "徐州,厦门,test_Ctrip_R006_004.png",
    })
    public  void test_Ctrip_R006(String fromStation ,String toStation,String screenhots) throws InterruptedException {
        test_meth_public();
        WangFan.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(1000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        Actions actionsTwo=new Actions(driver);
        actionsTwo.moveToElement(returnDate).click().perform();
        returnDate.click();
        returnDateGo.click();
        searchButton.click();
        FCmodel.click();
        FCOpenAll.click();
        FCSitdown.click();
        FCArriveStation.click();
        FCGoTime.click();
        FCArriveTime.click();
        Thread.sleep(3000);
        takeScreenshot(screenhots);
    }
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[4]/div[1]/div[2]")
    public WebElement AddFC;
    @ParameterizedTest
    @CsvSource({
            "123,海南,test_Ctrip_R007_001.png",
            "&&&,海南,test_Ctrip_R007_002.png",
            "好想来,海南,test_Ctrip_R007_003.png"})
    public  void test_Ctrip_R007(String fromStation,String toStation,String screenhots) throws InterruptedException {
        test_meth_public();
        AddFC.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(1000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        Actions actionsTwo=new Actions(driver);
        actionsTwo.moveToElement(returnDate).click().perform();
        returnDate.click();
        returnDateGo.click();
        searchButton.click();
        Thread.sleep(3000);
        takeScreenshot(screenhots);
    }
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[2]/div/div[2]/ul[1]/li[5]")
    public  WebElement NPQRS;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[2]/div/div[2]/ul[6]/li[6]")
    public  WebElement ShangHaiElement;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[2]/div/div[2]/ul[1]/li[1]")
    public  WebElement PopluarChoiceElement;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[2]/div/div[2]/ul[2]/li[1]")
    public  WebElement BeiJingElement;
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div[1]/ul[2]/li[25]")
    public  WebElement NovemBer20;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/ul[1]/li[3]")
    public  WebElement QCNovemBer19;
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[3]/div[2]/div[3]/ul/li[5]/div[1]")
    public  WebElement FCGoStationOne;
    @Test
    public void test_Ctrip_R008() throws InterruptedException {
        test_meth_public();
        WangFan.click();
        fromOwDCityElement.click();
        Thread.sleep(2000);
        NPQRS.click();
        ShangHaiElement.click();
        toOwDCityElement.click();
        PopluarChoiceElement.click();
        BeiJingElement.click();
        Thread.sleep(2000);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        Actions actionsTwo=new Actions(driver);
        actionsTwo.moveToElement(returnDate).click().perform();
        returnDate.click();
        NovemBer20.click();
        OnlySeacrhGT.click();
        searchButton.click();
        Thread.sleep(2000);
        QCNovemBer19.click();
        Thread.sleep(2000);
        List<WebElement> DepartureTimes=driver.findElements(By.xpath("//div[@class=\"item\" and @data-ubt-key=\"item\"]"));
        for(int i=4;i<=7;i++){
            DepartureTimes.get(i).click();
            Thread.sleep(1000);
        }
        FCOpenAll.click();
        FCSitdown.click();
        FCGoStationOne.click();
        FCArriveStation.click();
        Thread.sleep(2000);
        takeScreenshot("test_Ctrip_R008_001.png");
    }
    @FindBy(xpath = "//*[@id=\"app\"]/div[2]/div[1]/div[1]/div/ul/li[3]")
    public  WebElement ZhongZhuanElement;
    @ParameterizedTest
    @CsvSource({
            "哈尔滨,济南,test_Ctrip_R009_001.png",
            "哈尔滨,西安,test_Ctrip_R009_002.png",
            "哈尔滨,拉萨,test_Ctrip_R009_003.png",
            "长春,济南,test_Ctrip_R009_004.png",
            "长春,西安,test_Ctrip_R009_005.png",
            "长春,拉萨,test_Ctrip_R009_006.png",
            "安吉,济南,test_Ctrip_R009_007.png",
            "安吉,西安,test_Ctrip_R009_008.png",
            "安吉,拉萨,test_Ctrip_R009_009.png"

    })
    public void test_Ctrip_R009(String fromStation,String toStation,String screenhots) throws InterruptedException {
        test_meth_public();
        ZhongZhuanElement.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(2000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Thread.sleep(1000);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        searchButton.click();
        Thread.sleep(3000);
        takeScreenshot(screenhots);
    }
    @FindBy(xpath = "//*[@id=\"label-transitStation\"]")
    public  WebElement ZhongZhuanInputElement;

    @ParameterizedTest
    @CsvSource({
            "徐州,厦门,&&,test_Ctrip_R010_001.png",
            "徐州,厦门,东方北,test_Ctrip_R010_002.png",
            "徐州,厦门,12306,test_Ctrip_R010_003.png",

    })
    public void test_Ctrip_R010(String fromStation,String toStation,String ZhongZhuanStation, String screenhots) throws InterruptedException {
        test_meth_public();
        ZhongZhuanElement.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(2000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Thread.sleep(1000);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        ZhongZhuanInputElement.click();
        ZhongZhuanInputElement.sendKeys(ZhongZhuanStation);
        Thread.sleep(2000);
        takeScreenshot(screenhots);
    }
    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[3]/div/div[1]/div[2]/ul[2]/li[2]/div")
    public WebElement RunningTime;
    @ParameterizedTest
    @CsvSource({
            "哈尔滨,南京,济南,test_Ctrip_R011_001.png",
            "哈尔滨,南京,安阳,test_Ctrip_R011_002.png",
            "哈尔滨,南京,周口,test_Ctrip_R011_003.png",

    })
    public void test_Ctrip_R011(String fromStation,String toStation,String ZhongZhuanStation, String screenhots) throws InterruptedException {
        test_meth_public();
        ZhongZhuanElement.click();
        fromOwDCityElement.click();
        fromOwDCityElement.clear();
        fromOwDCityElement.sendKeys(fromStation);
        Thread.sleep(2000);
        toOwDCityElement.click();
        toOwDCityElement.clear();
        toOwDCityElement.sendKeys(toStation);
        Thread.sleep(1000);
        Actions actionsOne=new Actions(driver);
        actionsOne.moveToElement(DepatureDate).click().perform();
        DepatureDate.click();
        November18.click();
        ZhongZhuanInputElement.click();
        ZhongZhuanInputElement.sendKeys(ZhongZhuanStation);
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div[1]")).click();
        Thread.sleep(1000);
        searchButton.click();
        Thread.sleep(1000);
        RunningTime.click();
        RunningTime.click();
        Thread.sleep(3000);
        takeScreenshot(screenhots);

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
