package org.example.cs;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCtripFlightN {
    private WebDriver driver;


    @BeforeEach
    public void setup() {
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "E:\\driver\\chromedriver-win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ctrip.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }
    // test-code-start

    // 请在此处插入JUnit5代码




    public void  test_meth_public(){
        PageFactory.initElements(driver,this);
        Actions actions =new Actions(driver);
        actions.moveToElement(daohangElement).perform();
        actions.moveToElement(jipiaoElement).perform();
        FlightElement.click();
    }

    @FindBy(id = "leftSideNavLayer")//左侧导航栏
    public WebElement daohangElement;
    @FindBy(xpath = "//*[@id=\"leftSideNavLayer\"]/div/div/div[2]/div/div[1]/div/div[2]/button/span[2]")//机票
    public WebElement jipiaoElement;
    @FindBy(xpath = "//*[@id=\"popup-2\"]/div[2]/a[1]/span")//国内/国际机票
    public WebElement FlightElement;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[1]/ul/li[1]/span")//单程按钮
    public  WebElement DCButton;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[1]/div/div[1]/div/div/div[1]/input")//出发地
    public  WebElement  toDCity;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[1]/div/div[3]/div/div/div[1]/input")//目的地
    public  WebElement  fromDCity;
    @FindBy(xpath = "//*[@id=\"datePicker\"]/div[1]/span/div/div/div/input")//日期
    public  WebElement toDate;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div/div[1]/div[1]")
    public  WebElement toChooseDate;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div/div/div/div[1]")
    public  WebElement bringChild;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[1]/div/div/div[1]")
    public  WebElement cabin;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[1]/div/div/div[2]/ul/li[2]/div[2]")
    public  WebElement cabinEconomy;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/button")//搜索按钮
    public  WebElement searchButton;

    //R001
    @ParameterizedTest
    @CsvSource({
            "北京,广州,CtripFlight_R001_001.png",
            "北京,成都,CtripFlight_R001_002.png",
            "上海,广州,CtripFlight_R001_003.png",
            "上海,成都,CtripFlight_R001_004.png"
    })
    public void  test_CtripFlight_R001(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys(Keys.BACK_SPACE);
        toDCity.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(Keys.BACK_SPACE);
        fromDCity.sendKeys(toFlight);
        Thread.sleep(2000);
        toDate.click();
        toChooseDate.click();
        bringChild.click();
        cabin.click();
        cabinEconomy.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        takeScreenshot(screen);
    }

    //R002
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[1]/div/div[1]/div/div/div[2]/div[1]")
    public  WebElement errorMessage;
    @ParameterizedTest
    @CsvSource({
            "**,CtripFlight_R002_001.png",
            "红苹果,CtripFlight_R002_002.png"

    })

    public void  test_CtripFlight_R002(String fromFlight, String screen) throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys(Keys.BACK_SPACE);
        toDCity.sendKeys(fromFlight);
        Thread.sleep(2000);
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBePresentInElement(errorMessage,"对不起，暂不支持该地点"));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        takeScreenshot(screen);

    }
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div/div[1]/div[7]")
    public  WebElement December07;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div/div/div/div/div[3]/ul/li[12]")
    public  WebElement December08;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")
    public  WebElement FlyStop;
    @FindBy (xpath = "//*[@id=\"filter_item_airline\"]/div")
    public  WebElement airways;
    @FindBy (xpath = "//*[@id=\"filter_group_airline__airline\"]/li[1]")
    public  WebElement firstAirways;
    @FindBy(xpath = "//*[@id=\"filter_item_other\"]/div")
    public  WebElement more;
    @FindBy(xpath = "//*[@id=\"filter_group_other__aircraft_size\"]/li[2]")
    public  WebElement bigPlane;


    //R003
    @Test
    public void  test_CtripFlight_R003() throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys(Keys.BACK_SPACE);
        toDCity.sendKeys("上海");
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(Keys.BACK_SPACE);
        fromDCity.sendKeys("北京");
        Thread.sleep(2000);
        toDate.click();
        Thread.sleep(2000);
        December07.click();
        searchButton.click();
        
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }

        December08.click();

        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }

        FlyStop.click();
        airways.click();
        firstAirways.click();
        more.click();
        bigPlane.click();
        Thread.sleep(1000);
        takeScreenshot("CtripFlight_R003_001.png");
    }
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div/div/div/span")
    public  WebElement moreDate;
    @FindBy(xpath = "//*[@id=\"January-01-2025\"]")
    public  WebElement  firstDay2025;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[2]/li[4]")
    public  WebElement moreSort;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[2]/li[4]/span/div/ul/li[6]")
    public  WebElement priceHightoLow;

    //R004
    @Test
    public void  test_CtripFlight_R004() throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys(Keys.BACK_SPACE);
        toDCity.sendKeys("广州");
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(Keys.BACK_SPACE);
        fromDCity.sendKeys("成都");
        Thread.sleep(2000);
        toDate.click();
        December07.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        moreDate.click();

        firstDay2025.click();
        Thread.sleep(2000);
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Actions actions = new Actions(driver);
        actions.moveToElement(moreSort).perform();
        priceHightoLow.click();
        actions.moveToElement(moreDate).perform();
        Thread.sleep(3000);
        takeScreenshot("CtripFlight_R004_001.png");

    }

    //R005
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div/div[1]/div[1]")
    public  WebElement December01;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div[1]")
    public  WebElement passangerGategory;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div[2]/div[1]/div[2]/div[3]")
    public  WebElement ChenRen;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div[2]/div[2]/div[2]/div[3]")
    public  WebElement Child;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div[2]/div[3]/div[2]/div[3]")
    public  WebElement Baby;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div/div/div/div[2]/div[4]/a[1]")
    public  WebElement btnSure;
    @ParameterizedTest
    @CsvSource({"北京,东京,CtripFlight_R005_001.png",
            "北京,大坂,CtripFlight_R005_002.png",
            "上海,东京,CtripFlight_R005_003.png",
            "上海,大坂,CtripFlight_R005_004.png"
    })
    public void  test_CtripFlight_R005(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys(Keys.BACK_SPACE);
        toDCity.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(Keys.BACK_SPACE);
        fromDCity.sendKeys(toFlight);
        Thread.sleep(2000);
        passangerGategory.click();
        ChenRen.click();
        Child.click();
        Baby.click();
        btnSure.click();
        toDate.click();
        Thread.sleep(3000);
        December01.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[3]/div[2]/div/ul[1]/li[1]/div/span")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        takeScreenshot(screen);

    }

    //R006
    @Test
    public void  test_CtripFlight_R006() throws InterruptedException {
        test_meth_public();
        DCButton.click();
        toDCity.click();
        toDCity.sendKeys("上海");
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys("东京");
        Thread.sleep(2000);
        toDate.click();
        December07.click();
        searchButton.click();
        Thread.sleep(2000);
        WebElement cmp=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[3]/div[1]/div/div[2]/div[3]/div"));
        cmp.click();
        Thread.sleep(2000);
        WebElement nxt=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[3]/div[1]/span/div/div[4]/i"));
        nxt.click();
        Thread.sleep(2000);
        nxt.click();
        Thread.sleep(2000);
        WebElement t=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[3]/div[1]/span/div/div[2]/div/ul/li[16]/div[3]"));
        t.click();
        takeScreenshot("CtripFlight_R006_001.png");
    }

    //R007
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[2]/div[3]")
    public WebElement arriveDateChoose12_10;
    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div[1]/div/form/div/div/div/div[2]/div[2]/div/div[2]/div/a")
    public  WebElement addFancheng;
    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div[1]/div/form/div/div/div/div[2]/div[2]/div/div[3]/span/div/div/div/input")
    public WebElement arriveDate;
    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/div[1]/div[1]/form/div/div/div/div[2]/div[1]/div/div[2]/div/i")//出发到达城市交换
    public  WebElement exchange;

    @ParameterizedTest
    @CsvSource({"北京,广州,CtripFlight_R007_001.png",
            "北京,成都,CtripFlight_R007_002.png",
            "上海,广州,CtripFlight_R007_003.png",
            "上海,成都,CtripFlight_R007_004.png"
    })
    public void  test_CtripFlight_R007(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        DCButton.click();
        addFancheng.click();
        toDCity.click();
        toDCity.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(toFlight);
        Thread.sleep(2000);
        toDate.click();
        December07.click();
        Thread.sleep(2000);
        arriveDate.click();
        arriveDateChoose12_10.click();
        searchButton.click();
        Thread.sleep(7000);
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div[1]/form/div/div/div/div[2]/div[1]/div/div[2]/div/i")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        exchange.click();
        Thread.sleep(2000);
        WebElement qucheng=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div"));
        qucheng.click();
        Thread.sleep(5000);
        WebElement fancheng=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div[2]/div/div[2]/div[2]/button"));
        fancheng.click();
        Thread.sleep(3000);
        WebElement tuigaiqian=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div[2]/div[2]/div[1]/div[2]/div[1]/span[1]/span"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tuigaiqian).perform();
        Thread.sleep(1000);
        WebElement fancheng2=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[1]/div/div/div/div/div/div[1]/a[2]"));
        fancheng2.click();

        takeScreenshot(screen);
    }

    //R008
    @FindBy(xpath = "/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div[1]/div/form/div/div/div/div[1]/ul/li[2]/span/i[2]")//出发到达城市交换
    public  WebElement wangfanBtn;
    @ParameterizedTest
    @CsvSource({"北京,东京,CtripFlight_R008_001.png",
            "上海,东京,CtripFlight_R008_002.png",
            "上海,大阪,CtripFlight_R008_003.png"
    })
    public void  test_CtripFlight_R008(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        wangfanBtn.click();
        toDCity.click();
        toDCity.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys(toFlight);
        Thread.sleep(2000);
        toDate.click();
        December01.click();
        Thread.sleep(2000);
        arriveDate.click();
        arriveDateChoose12_10.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(2000);
        WebElement qucheng=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div"));
        qucheng.click();
        Thread.sleep(5000);
        WebElement fancheng=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/button"));
        fancheng.click();
        Thread.sleep(3000);
        WebElement yuding=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/div[2]/span/div[1]/div/div/div/div[2]/div[1]/div[4]/div/div[2]/button"));
        yuding.click();
        Thread.sleep(2000);

        takeScreenshot(screen);
    }

    //R009
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[5]/div[3]/span")
    WebElement December12_31;
    @Test
    public void  test_CtripFlight_R009() throws InterruptedException {
        test_meth_public();
        wangfanBtn.click();
        toDCity.click();
        toDCity.sendKeys("北京");
        Thread.sleep(2000);
        fromDCity.click();
        fromDCity.sendKeys("上海");
        Thread.sleep(2000);
        toDate.click();
        December07.click();
        Thread.sleep(2000);
        arriveDate.click();
        December12_31.click();
        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/ul[1]/li[1]/div[1]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(1000);
        WebElement linjin=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/ul[1]/li[1]/div[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(linjin).perform();
        Thread.sleep(1000);
        WebElement quekoucheng=driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div/div[5]/div[1]/div/div/ul[1]/li[1]/div[2]/div/div"));
        quekoucheng.click();
        Thread.sleep(2000);

        takeScreenshot("CtripFlight_R009_001.png");
    }


    //R010
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[1]/ul/li[3]/span/i[1]")
    public  WebElement DuochengBtn;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[1]/div[1]/div/div[2]/div/div/div[1]/input")
    public  WebElement toDCity01;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[1]/div[1]/div/div[4]/div/div/div[1]/input")
    public  WebElement fromDCity01;
    @FindBy(xpath = "//*[@id=\"multiDatePicker0\"]/div/span/div/div/div/input")
    public  WebElement toDate01;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[2]/div[2]/div/div[1]/div[3]/span")
    public  WebElement December03;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div[1]/input")
    public  WebElement toDCity02;
    @ParameterizedTest
    @CsvSource({"北京,东京,CtripFlight_R010_001.png",
            "北京,上海,CtripFlight_R010_002.png"
    })
    public void  test_CtripFlight_R010(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        DuochengBtn.click();
        toDCity01.click();
        toDCity01.sendKeys(Keys.BACK_SPACE);
        toDCity01.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity01.click();
        fromDCity01.sendKeys(Keys.BACK_SPACE);
        fromDCity01.sendKeys(toFlight);
        Thread.sleep(2000);

        toDate01.click();
        Thread.sleep(3000);
        December03.click();
        toDCity02.click();

        searchButton.click();
        Thread.sleep(1000);
        takeScreenshot(screen);

    }

    //R011
    @ParameterizedTest
    @CsvSource({"北京,北京,CtripFlight_R011_001.png"
    })
    public void  test_CtripFlight_R011(String fromFlight,String toFlight, String screen) throws InterruptedException {
        test_meth_public();
        DuochengBtn.click();
        toDCity01.click();
        toDCity01.sendKeys(Keys.BACK_SPACE);
        toDCity01.sendKeys(fromFlight);
        Thread.sleep(2000);
        fromDCity01.click();
        fromDCity01.sendKeys(Keys.BACK_SPACE);
        fromDCity01.sendKeys(toFlight);
        Thread.sleep(2000);

        searchButton.click();
        Thread.sleep(1000);
        takeScreenshot(screen);

    }


    //R012
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[2]/div[1]/div/div[4]/div/div/div[1]/input")
    public  WebElement fromDCity02;
    @FindBy(xpath = "//*[@id=\"multiDatePicker1\"]/div/span/div/div/div/input")
    public  WebElement toDate02;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[1]/div[5]/span")
    public  WebElement December05;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div[1]/div/div[2]/div/div/div[1]/input")
    public  WebElement toDCity03;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[3]/div[1]/div/div[4]/div/div/div[1]/input")
    public  WebElement fromDCity03;
    @FindBy(xpath = "//*[@id=\"multiDatePicker2\"]/div/span/div/div/div/input")
    public  WebElement toDate03;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[1]/div[6]")
    public  WebElement December06;

    @ParameterizedTest
    @CsvSource({"北京,广州,上海,南京,CtripFlight_R012_001.png"
    })
    public void  test_CtripFlight_R012(String fromFlight1,String toFlight1, String toFlight2, String toFlight3, String screen) throws InterruptedException {
        test_meth_public();
        DuochengBtn.click();
        toDCity01.click();
        toDCity01.sendKeys(Keys.BACK_SPACE);
        toDCity01.sendKeys(fromFlight1);
        Thread.sleep(2000);
        fromDCity01.click();
        fromDCity01.sendKeys(Keys.BACK_SPACE);
        fromDCity01.sendKeys(toFlight1);
        Thread.sleep(2000);
        toDate01.click();
        Thread.sleep(3000);
        December03.click();
        Thread.sleep(1000);
        toDCity02.click();
        Thread.sleep(1000);
        fromDCity02.click();
        fromDCity02.sendKeys(Keys.BACK_SPACE);
        fromDCity02.sendKeys(toFlight2);
        Thread.sleep(2000);
        toDate02.click();
        Thread.sleep(3000);
        December05.click();
        Thread.sleep(1000);
        toDCity03.click();
        Thread.sleep(1000);
        fromDCity03.click();
        fromDCity03.sendKeys(Keys.BACK_SPACE);
        fromDCity03.sendKeys(toFlight3);
        Thread.sleep(2000);
        toDate03.click();
        Thread.sleep(3000);
        December06.click();
        Thread.sleep(1000);
        searchButton.click();
        Thread.sleep(1000);
        takeScreenshot(screen);

    }

    //R013
    @FindBy(linkText = "+ 再加一程")
    public  WebElement AddTrip;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[5]/a/i")
    public  WebElement SubTrip;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[4]/div[1]/div/div[2]/div/div/div[1]/input")
    public  WebElement toDCity04;
    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/div/div/div[2]/div[4]/div[1]/div/div[4]/div/div/div[1]/input")
    public  WebElement fromDCity04;
    @FindBy(xpath = "//*[@id=\"multiDatePicker3\"]/div/span/div/div/div/input")
    public  WebElement toDate04;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[2]/div[2]")
    public  WebElement December09;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div[1]/div[2]/div/div[2]/div[5]")
    public  WebElement December12;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[3]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div")
    public  WebElement Trip01;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[4]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div")
    public  WebElement Trip02;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[4]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div")
    public  WebElement Trip03;
    @FindBy(xpath = "//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[4]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/button")
    public  WebElement OrderTrip;

    @ParameterizedTest
    @CsvSource({"上海,北京,首尔,东京,上海,CtripFlight_R013_001.png"
    })
    public void  test_CtripFlight_R013(String fromFlight1,String toFlight1, String toFlight2, String toFlight3, String toFlight4, String screen) throws InterruptedException {
        test_meth_public();
        DuochengBtn.click();
        for (int i = 0; i < 3; i++) {
            AddTrip.click();
        }
        for (int i = 0; i < 2; i++) {
            SubTrip.click();
        }

        toDCity01.click();
        toDCity01.sendKeys(Keys.BACK_SPACE);
        toDCity01.sendKeys(fromFlight1);
        Thread.sleep(2000);
        fromDCity01.click();
        fromDCity01.sendKeys(Keys.BACK_SPACE);
        fromDCity01.sendKeys(toFlight1);
        Thread.sleep(2000);
        toDate01.click();
        Thread.sleep(3000);
        December03.click();
        Thread.sleep(1000);

        toDCity02.click();
        Thread.sleep(1000);
        fromDCity02.click();
        fromDCity02.sendKeys(Keys.BACK_SPACE);
        fromDCity02.sendKeys(toFlight2);
        Thread.sleep(2000);
        toDate02.click();
        Thread.sleep(3000);
        December05.click();
        Thread.sleep(1000);

        toDCity03.click();
        Thread.sleep(1000);
        fromDCity03.click();
        fromDCity03.sendKeys(Keys.BACK_SPACE);
        fromDCity03.sendKeys(toFlight3);
        Thread.sleep(2000);
        toDate03.click();
        Thread.sleep(3000);
        December09.click();
        Thread.sleep(1000);

        toDCity04.click();
        Thread.sleep(1000);
        fromDCity04.click();
        fromDCity04.sendKeys(Keys.BACK_SPACE);
        fromDCity04.sendKeys(toFlight4);
        Thread.sleep(2000);
        toDate04.click();
        Thread.sleep(3000);
        December12.click();
        Thread.sleep(1000);

        searchButton.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[3]/div[2]/span/div[1]/div/div/div/div/div[2]/div[2]/div")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Trip01.click();
        Thread.sleep(5000);
        Trip02.click();
        Thread.sleep(5000);
        Trip03.click();
        Thread.sleep(5000);
        WebElement zhifei = driver.findElement(By.xpath("//*[@id=\"hp_container\"]/div[2]/div/div[2]/div[3]/div/ul[2]/li[1]/span"));
        Actions actions = new Actions(driver);
        actions.moveToElement(zhifei).perform();
        Thread.sleep(1000);
        OrderTrip.click();
        Thread.sleep(1000);
        WebElement baoxiao=driver.findElement(By.xpath("//*[@id=\"MultiInvoice-0_0\"]/span"));
        actions.moveToElement(baoxiao).perform();
        Thread.sleep(1000);
        WebElement form=driver.findElement(By.xpath("//*[@id=\"行程单或电子发票_0_0_0\"]"));
        actions.moveToElement(form).perform();
        Thread.sleep(1000);
        takeScreenshot(screen);

    }

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
