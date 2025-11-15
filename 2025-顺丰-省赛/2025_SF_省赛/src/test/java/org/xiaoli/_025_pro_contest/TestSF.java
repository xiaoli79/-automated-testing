package org.xiaoli._025_pro_contest;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;



public class TestSF {
    private WebDriver driver;
    
    @BeforeEach
    public void setup() {
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", "E:\\比赛\\软件测试\\驱动\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.sf-express.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }



     // test-code-start
     public void scrollOneScreen(WebDriver driver) {
         JavascriptExecutor js = (JavascriptExecutor) driver;
         // 获取当前视口高度，滚动 exactly 一屏
         js.executeScript("window.scrollBy(0, window.innerHeight);");
     }


    public void  test_meth_public(){
        PageFactory.initElements(driver,this);
        //放一点公共的代码~~
    }

    @FindBy(xpath = "//a[contains(text(),'服务支持')]")
    WebElement search_support;
    @FindBy(xpath = "//li[contains(text(),'运费时效')]")
    WebElement Yun_price;
    @FindBy(xpath = "//div[@placeholder='请选择始发地地区。例如：北京市']")
    WebElement from_city;
    @FindBy(xpath = "//div[@placeholder='请选择目的地地区。例如：北京市']")
    WebElement to_city;
    @FindBy(xpath = "//div[@class='weight']//input[@type='tel']")
    WebElement weight;
    @FindBy(xpath = "//input[@placeholder='长']")
    WebElement length;

    @FindBy(xpath = "//input[@placeholder='宽']")
    WebElement width;

    @FindBy(xpath = "//input[@placeholder='高']")
    WebElement height;


    @FindBy(xpath = "//input[@class='el-input__inner']")
    WebElement date_click;

    @FindBy(xpath = "//span[contains(text(),'确定')]")
    WebElement date_ensure;


    @FindBy(xpath = "//button[contains(text(),'查询')]")
    WebElement search;

    // 请在此处插入Selenium+JUnit5代码
    @ParameterizedTest
    @CsvSource({
            "广州市, 黄埔区, 黄埔东苑, 南京市, 鼓楼区, 南京大学, SF_R001_001",
            "南京市, 鼓楼区, 南京大学, 广州市, 黄埔区, 黄埔东苑, SF_R001_002"
    })
    public void test_SF_R001(String originCity, String originDistrict, String originDetail, String destCity, String destDistrict, String destDetail, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        // 2. 点击左侧【运费时效】
        Thread.sleep(2000);
        Yun_price.click();
        // 3. 点击始发地，输入 originCity, 选择 originDistrict
        from_city.click();
        driver.findElement(By.xpath("//input[@class='city-search']")).click();
        driver.findElement(By.xpath("//input[@class='city-search']")).sendKeys(originCity);
        Thread.sleep(2000);
//      点击下拉框
        driver.findElement(By.xpath("(//div[@class='address-name'])[6]")).click();
        Thread.sleep(1000);

//      始发地的详细地址~~
        driver.findElement(By.xpath("(//input[@placeholder='详细地址（选填）'])[1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("(//input[@placeholder='详细地址（选填）'])[1]")).sendKeys(originDetail);
        Thread.sleep(2000);

        // 5. 点击目的地，输入 destCity, 选择 destDistrict
        to_city.click();
        driver.findElement(By.xpath("//input[@class='city-search']")).click();
        driver.findElement(By.xpath("//input[@class='city-search']")).sendKeys(destCity);

        driver.findElement(By.xpath("(//div[@class='address-name'])[4]")).click();
        Thread.sleep(2000);

        // 6. 输入目的地详细地址 destDetail
        driver.findElement(By.xpath("(//input[@placeholder='详细地址（选填）'])[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("(//input[@placeholder='详细地址（选填）'])[2]")).sendKeys(destDetail);

        // 7. 输入重量 "5"，体积 "20", "15", "25"
        weight.click();
        Thread.sleep(500);
        weight.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
        weight.sendKeys("5");

        length.click();
        Thread.sleep(500);
        length.sendKeys("20");

        width.click();
        Thread.sleep(500);
        width.sendKeys("15");

        height.click();
        Thread.sleep(500);
        height.sendKeys("25");

        // 8. 选择寄件日期 "16" 号，点击确定
        date_click.click();
        driver.findElement(By.xpath("//span[normalize-space()='16']")).click();
        Thread.sleep(2000);
        date_ensure.click();
        Thread.sleep(2000);
        // 9. 点击【查询】
        search.click();
        // 10. 断言结果
        //常用到最后拍照的时候~~
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'前往寄件 >')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(3000);
        // 11. 截图
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R002: 运费时效查询 - 港澳台(香港)到内地(南京) - 此刻
     * [cite: 99]
     */
    @Test
    public void test_SF_R002() throws InterruptedException {
        String caseId = "SF_R002_001"; // [cite: 4]
        System.out.println("正在执行测试用例: " + caseId);
        // 1. 访问首页，点击【服务支持】
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        // 2. 点击左侧【运费时效】
        Thread.sleep(2000);
        Yun_price.click();
        // 2. 点击左侧【运费时效】
        // 3. 点击始发地，点击【港澳台】，选择 香港 九龙城区
        from_city.click();
        driver.findElement(By.xpath("//li[@class='tab-item cursor-point'][contains(text(),'港澳台')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='card-name cursor-point'][contains(text(),'香港')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'九龙城区')]")).click();

        Thread.sleep(1000);

        // 4. 点击目的地，点击 热门城市【南京市】，选择 鼓楼区
        to_city.click();
        driver.findElement(By.xpath("//span[contains(text(),'南京市')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'鼓楼区')]")).click();
        Thread.sleep(500);

        weight.click();
        Thread.sleep(500);
        weight.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
        weight.sendKeys("5");

        length.click();
        Thread.sleep(500);
        length.sendKeys("20");

        width.click();
        Thread.sleep(500);
        width.sendKeys("15");

        height.click();
        Thread.sleep(500);
        height.sendKeys("25");
        // 6. 点击寄件时间【此刻】
        date_click.click();
        driver.findElement(By.xpath("//span[contains(text(),'此刻')]")).click();
        Thread.sleep(2000);
        // 7. 点击【查询】
        search.click();
        // 8. 断言结果
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'前往寄件 >')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(3000);
        // 9. 截图
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R003: 运费时效查询 - 港澳台到内地 - 指定日期时间
     * [cite: 100, 101, 102]
     */
    @Test
    public void test_SF_R003() throws InterruptedException {
        String caseId = "SF_R003_001"; // 假设的用例ID
        System.out.println("正在执行测试用例: " + caseId);
        // 1. 访问首页，点击【服务支持】
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        // 2. 点击左侧【运费时效】
        Thread.sleep(2000);
        Yun_price.click();
        // 2. 点击左侧【运费时效】
        // 3. 点击始发地，点击【港澳台】，选择 香港 九龙城区
        from_city.click();
        driver.findElement(By.xpath("//li[@class='tab-item cursor-point'][contains(text(),'港澳台')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='card-name cursor-point'][contains(text(),'香港')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'九龙城区')]")).click();

        Thread.sleep(1000);

        // 4. 点击目的地，点击 热门城市【南京市】，选择 鼓楼区
        to_city.click();
        driver.findElement(By.xpath("//span[contains(text(),'南京市')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'鼓楼区')]")).click();
        Thread.sleep(500);

        weight.click();
        Thread.sleep(500);
        weight.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
        weight.sendKeys("100");

        // 6. 点击寄件时间【此刻】
        date_click.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='选择日期']")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@placeholder='选择日期']")).sendKeys("2025-11-17");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys(Keys.DELETE);;
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys("15:00:00");


        Thread.sleep(2000);

        date_ensure.click();
        Thread.sleep(2000);
        // 7. 点击【查询】
        search.click();
        // 8. 断言结果
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'前往寄件 >')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(3000);
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R004: 运费时效查询 - 港澳台到内地 - 大件(20kg+)
     * [cite: 103, 104, 105, 106, 107, 108, 109]
     */
    @ParameterizedTest
    @CsvSource({
            "19, 5, 8, 6, SF_R004_001",
            "20, 5, 8, 6, SF_R004_002",
            "19, 100, 80, 90, SF_R004_003",
            "20, 100, 80, 90, SF_R004_004"
    })
    public void test_SF_R004(String weight1, String length1, String width1, String height1, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);
        // 1. 访问首页，点击【服务支持】
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        // 2. 点击左侧【运费时效】
        Thread.sleep(2000);
        Yun_price.click();
        // 2. 点击左侧【运费时效】
        // 3. 点击始发地，点击【港澳台】，选择 香港 九龙城区
        from_city.click();
        driver.findElement(By.xpath("//li[@class='tab-item cursor-point'][contains(text(),'港澳台')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='card-name cursor-point'][contains(text(),'香港')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'九龙城区')]")).click();

        Thread.sleep(1000);

        // 4. 点击目的地，点击 热门城市【南京市】，选择 鼓楼区
        to_city.click();
        driver.findElement(By.xpath("//span[contains(text(),'南京市')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'鼓楼区')]")).click();
        Thread.sleep(500);

        weight.click();
        Thread.sleep(500);
        weight.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
        weight.sendKeys(weight1);

        length.click();
        Thread.sleep(500);
        length.sendKeys(length1);

        width.click();
        Thread.sleep(500);
        width.sendKeys(width1);

        height.click();
        Thread.sleep(500);
        height.sendKeys(height1);

        // 6. 点击寄件时间【此刻】
        date_click.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@placeholder='选择日期']")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@placeholder='选择日期']")).sendKeys("2025-11-18");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys(Keys.DELETE);;
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='选择时间']")).sendKeys("08:00:00");


        Thread.sleep(2000);

        date_ensure.click();
        Thread.sleep(2000);
        // 7. 点击【查询】
        search.click();
        // 8. 断言结果
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'前往寄件 >')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[contains(text(),'大件（20kg+）')]")).click();
        Thread.sleep(3000);
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R005: 运费时效查询 - 其他服务(保价/代收/签单/包装)
     * [cite: 110]
     */
    @ParameterizedTest
    @CsvSource({
            "1, SF_R005_001",
            "2, SF_R005_002",
            "3, SF_R005_003",
            "4, SF_R005_004"
    })
    public void test_SF_R005(String serviceName, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        // 2. 点击左侧【运费时效】
        Thread.sleep(2000);
        Yun_price.click();
        // 3. 滚动到页面下方 "其他服务"
        // 4. 点击链接 serviceName (例如 "保价")
        driver.findElement(By.xpath("(//div[@class='service-info'])["+serviceName+"]")).click();

        Thread.sleep(3000);
        // 5. (页面跳转或新窗口)
        // 6. 断言页面内容
        // 假设 driver 已经初始化
        String current = driver.getWindowHandle();          // ① 当前页句柄
        for (String h : driver.getWindowHandles()) {        // ② 集合大小=2
            if (!h.equals(current)) {                       // ③ 另一个就是目标
                driver.switchTo().window(h);                // 切过去
                break;
            }
        }
        System.out.println("已经切换网页");
        Thread.sleep(2000);


        // 7. 截图
        takeScreenshot(caseId + ".png");
        // 8. (可能需要关闭新窗口并切回)
    }

    @FindBy(xpath = "//li[contains(text(),'服务网点')]")
    WebElement netWork;

    @FindBy(xpath = "//input[@placeholder='选择收寄件区域']")
    WebElement Jijian;

    @FindBy(xpath = "//input[@id='range-key-word']")
    WebElement Key_Word;

    @FindBy(xpath = "//button[contains(text(),'查询')]")
    WebElement search2;

    /**
     * 需求R006: 服务网点查询 - 关键词查询与地图缩放(市)
     * [cite: 112, 113]
     */
    @Test
    public void test_SF_R006() throws InterruptedException {
        String caseId = "SF_R006_001"; // 假设的用例ID
        System.out.println("正在执行测试用例: " + caseId);
        test_meth_public();
        // 1. 访问首页，点击【服务支持】
        search_support.click();
        Thread.sleep(1000);
        netWork.click();
        Thread.sleep(2000);
        // 3. 点击【选择收寄件区域】，热门城市选 "上海市", "黄浦区"
        Jijian.click();
        driver.findElement(By.xpath("//span[contains(text(),'上海市')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'黄浦区')]")).click();
        Thread.sleep(1000);

        Key_Word.click();
        Thread.sleep(1000);
        Key_Word.sendKeys("兴业太古汇");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@id='chn']//li[1]//div[1]//span[1]")).click();

        search.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(),'自营服务点')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(3000);

        // 5. 点击【查询】
        // 6. 在地图中查找并点击 "国泰君安快递服务中心"
        // 7. 点击地图右下角缩放条的 【市】
        // 8. 断言结果
        // 9. 截图
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R007: 服务网点查询 - 关键词查询与地图缩放(拖动)
     * [cite: 114, 115, 116, 118, 119]
     */
    @ParameterizedTest
    @CsvSource({
            "广州市, 天河区, 天河城, 正佳广场店, SF_R007_001",
            "深圳市, 福田区, 天虹商场, 耀华楼店, SF_R007_002",
            "南京市, 玄武区, 新世界百货, 洪武北路店, SF_R007_003",
            "杭州市, 西湖区, 西城广场, 电信大楼店, SF_R007_004"
    })
    public void test_SF_R007(String city, String district, String keyword, String targetStore, String caseId) {
        System.out.println("正在执行测试用例: " + caseId);
        // 1. 访问首页，点击【服务支持】
        // 2. 点击左侧【服务网点】
        // 3. 点击【选择收寄件区域】，输入 city, 选择 district
        // 4. 输入关键词 keyword
        // 5. 点击【查询】
        // 6. 在地图中查找并点击 targetStore
        // 7. 定位到地图缩放条的滑块
        // 8. 拖动滑块到最顶端 (最大)
        // 9. 断言结果
        // 10. 截图
        takeScreenshot(caseId + ".png");
    }


    @FindBy(xpath = "//li[contains(text(),'收寄标准')]")
    WebElement ShouJi_Standard;
    @FindBy(xpath = "//input[@placeholder='请选择始发地国家/地区名称']")
    WebElement init;
    @FindBy(xpath = "//input[@placeholder='请选择目的地国家/地区名称']")
    WebElement Final;

    @FindBy(xpath = "//input[@placeholder='请输入托寄物品名称，例如：电池']")
    WebElement things;

    @FindBy(xpath = "//li[contains(text(),'省/直辖市')]")
    WebElement privince;


    @FindBy(xpath = "//button[contains(text(),'查询')]")
    WebElement search3;
    /**
     * 需求R008: 收寄标准查询 - 内地互寄 - 托寄物品
     * [cite: 122, 123, 124]
     */
    @ParameterizedTest
    @CsvSource({
            "电子,8, SF_R008_002",
            "电脑,4, SF_R008_002"
    })
    public void test_SF_R008(String itemKeyword, String itemToSelect, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);
        test_meth_public();

        search_support.click();

        // 1. 访问首页，点击【服务支持】
        Thread.sleep(2000);
        ShouJi_Standard.click();
        // 2. 点击左侧【收寄标准】
        // 3. 始发地: "广东省", "深圳市", "光明区"
        init.click();
        Thread.sleep(2000);

        // 4. 目的地: "江苏省", "南京市", "鼓楼区"
        privince.click();
        driver.findElement(By.xpath("//span[contains(text(),'广东省')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'深圳市')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'光明区')]")).click();
        Thread.sleep(500);
        Final.click();
        Thread.sleep(500);
        privince.click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//span[contains(text(),'江苏省')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'南京市')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'鼓楼区')]")).click();

        Thread.sleep(500);
        things.click();
        Thread.sleep(500);
        things.sendKeys(itemKeyword);
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@id='accept-consignment-input']//li["+itemToSelect+"]")).click();
        Thread.sleep(500);
        search3.click();
        try {
            // 使用显示等待方法等待元素出现
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//th[contains(text(),'托寄物名称')]")));
            System.out.println("元素已找到");
        } catch (TimeoutException e) {
            System.out.println("等待超时，未找到元素");
        }
        Thread.sleep(3000);


        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R009: 收寄标准查询 - 港澳台(香港) - 违禁品
     * [cite: 125]
     */
    @ParameterizedTest
    @CsvSource({
            "猫咪, SF_R009_001",
            "硫酸, SF_R009_002",
            "现金, SF_R009_003",
            "古董, SF_R009_004"
    })
    public void test_SF_R009(String itemKeyword, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);
        test_meth_public();

        search_support.click();

        // 1. 访问首页，点击【服务支持】
        Thread.sleep(2000);
        ShouJi_Standard.click();
        // 2. 点击左侧【收寄标准】
        // 3. 始发地: "广东省", "深圳市", "光明区"
        init.click();
        Thread.sleep(2000);
        // 3. 始发地: 热门城市 "深圳市", "南山区"
        driver.findElement(By.xpath("//span[contains(text(),'深圳市')]")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//span[contains(text(),'南山区')]")).click();
        Thread.sleep(500);
        Final.click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//li[@class='tab-item cursor-point'][contains(text(),'港澳台')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'香港')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'湾仔区')]")).click();

        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(text(),'铜锣湾')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[contains(text(),'请选择寄件人身份')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("(//div[contains(text(),'个人')])[1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[contains(text(),'请选择收件人身份')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='selectbar idArea']//div[2]//div[1]//div[2]//div[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='请输入托寄物品名称，例如：电池']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@placeholder='请输入托寄物品名称，例如：电池']")).sendKeys(itemKeyword);
        Thread.sleep(3000);

        search3.click();

        Thread.sleep(5000);

        // 4. 目的地: 【港澳台】, "香港", "湾仔区", "铜锣湾"
        // 5. 寄件人身份: "个人"
        // 6. 收件人身份: "公司"
        // 7. 托寄物品输入 itemKeyword
        // 8. 点击【查询】
        // 9. 断言结果 (应为禁寄提示)
        // 10. 截图
        takeScreenshot(caseId + ".png");
    }

    /**
     * 需求R010: 服务范围查询
     * [cite: 127, 128, 130, 131]
     */
    @ParameterizedTest
    @CsvSource({
            "广东省, 广州市番禺区, '', SF_R010_001",
            "广东省, 广州市番禺区, 大石街道, SF_R010_002",
            "新疆维吾尔自治区, 喀什地区伽师县, '', SF_R010_003",
            "新疆维吾尔自治区, 喀什地区伽师县, 米夏乡, SF_R010_004"
    })
    public void test_SF_R010(String province, String cityDistrict, String street, String caseId) throws InterruptedException {
        System.out.println("正在执行测试用例: " + caseId);


        test_meth_public();

        search_support.click();
        scrollOneScreen(driver);
        Thread.sleep(2000);
        // 1. 访问首页，点击【服务支持】

        // 1. 访问首页，点击【服务支持】
        // 2. 点击左侧【服务范围】
        // 3. 【收寄件】选择框: 输入 province, 选择 cityDistrict
        // 4. 【选择乡/镇/街道】:
        //    if (street is not empty) { 选择 street }
        // 5. 点击【查询】
        // 6. 断言结果
        // 7. 截图
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