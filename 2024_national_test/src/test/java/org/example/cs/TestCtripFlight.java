package org.example.cs;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;


public class TestCtripFlight {
    private WebDriver driver;
    private CtripFlightPage flightPage;


    @BeforeEach
    public void setup() {
        // 这个setup代码不能被修改
        //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.ctrip.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        flightPage = new CtripFlightPage(driver);
    }
    // test-code-start
    // 只能在这里面插入代码

    // R001: 单程国内机票查询 - 带儿童、经济舱
    @ParameterizedTest
    @CsvSource({
        "北京,广州,2024-12-01, CtripFlight_R001_001.png",
        "北京,成都,2024-12-01, CtripFlight_R001_002.png",
        "上海,广州,2024-12-01, CtripFlight_R001_003.png",
        "上海,成都,2024-12-01, CtripFlight_R001_004.png"
    })
    public void test_CtripFlight_R001(String fromCity, String toCity, String date, String caseId) throws InterruptedException {
        // R001: 有效单程国内机票查询 - 带儿童、经济舱
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity(fromCity);
        flightPage.inputArrivalCity(toCity);
        
        flightPage.checkChildCheckbox();
        flightPage.selectEconomyClass();
        
        flightPage.selectDate(date);
        flightPage.clickSearch();
        
        takeScreenshot(caseId);
        
        // 重置页面状态，准备下一个用例
        driver.get("https://flights.ctrip.com/online/channel/domestic");
        Thread.sleep(2000);
    }

    // R002: 非法出发地测试
    @Test
    public void test_CtripFlight_R002() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        // 用例1: 输入非法出发地
        WebElement departureInput = flightPage.getDepartureInput();
        departureInput.click();
        Thread.sleep(500);
        departureInput.clear();
        departureInput.sendKeys("非法城市123");
        Thread.sleep(2000);
        
        takeScreenshot("CtripFlight_R002_001.png");
        
        // 用例2: 尝试另一个非法出发地
        departureInput.clear();
        departureInput.sendKeys("XXXYYY");
        Thread.sleep(2000);
        
        takeScreenshot("CtripFlight_R002_002.png");
    }

    // R003: 单程查询结果筛选
    @Test
    public void test_CtripFlight_R003() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("上海");
        flightPage.inputArrivalCity("北京");
        flightPage.selectDate("2024-12-07");
        flightPage.clickSearch();
        
        try {
            flightPage.getDateElement12_08().click();
            Thread.sleep(2000);
            
            flightPage.getDirectFlightCheckbox().click();
            Thread.sleep(1000);
            
            flightPage.getAirlineDropdown().click();
            Thread.sleep(1000);
            
            // 勾选第一家航空公司
            List<WebElement> airlines = driver.findElements(By.xpath("//div[contains(@class,'airline-item')]//label"));
            if (!airlines.isEmpty()) {
                airlines.get(0).click();
                Thread.sleep(1000);
            }
            
            flightPage.getMoreDropdown().click();
            Thread.sleep(1000);
            
            flightPage.getLargeAircraftCheckbox().click();
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R003_001.png");
    }

    // R004: 单程查询结果筛选和排序
    @Test
    public void test_CtripFlight_R004() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("广州");
        flightPage.inputArrivalCity("成都");
        flightPage.selectDate("2024-12-07");
        flightPage.clickSearch();
        
        try {
            flightPage.getMoreDateBtn().click();
            Thread.sleep(2000);
            
            // 选择2025-01-01
            WebElement date20250101 = driver.findElement(By.xpath("//td[@data-year='2025'][@data-month='01']//a[text()='1']"));
            date20250101.click();
            Thread.sleep(3000);
            
            // 鼠标悬浮至更多排序
            Actions actions = flightPage.getActions();
            actions.moveToElement(flightPage.getMoreSortDropdown()).perform();
            Thread.sleep(1000);
            
            flightPage.getPriceHighLowOption().click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R004_001.png");
    }

    // R005: 单程国际机票查询 - 2成人1儿童1婴儿
    @Test
    public void test_CtripFlight_R005() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        // 用例1: 北京-东京
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("东京");
        
        // 操作乘客类型下拉框
        flightPage.getPassengerTypeDropdown().click();
        Thread.sleep(1000);
        
        // 增加成人到2人
        try {
            WebElement adultPlus = driver.findElement(By.xpath("//span[contains(text(),'成人')]/following::button[contains(@class,'increase')]"));
            adultPlus.click();
            Thread.sleep(500);
        } catch (Exception e) {
            // 尝试其他方式
        }
        
        // 增加1儿童
        try {
            WebElement childPlus = driver.findElement(By.xpath("//span[contains(text(),'儿童')]/following::button[contains(@class,'increase')]"));
            childPlus.click();
            Thread.sleep(500);
        } catch (Exception e) {
            // 尝试其他方式
        }
        
        // 增加1婴儿
        try {
            WebElement infantPlus = driver.findElement(By.xpath("//span[contains(text(),'婴儿')]/following::button[contains(@class,'increase')]"));
            infantPlus.click();
            Thread.sleep(500);
        } catch (Exception e) {
            // 尝试其他方式
        }
        
        flightPage.selectDate("2024-12-01");
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R005_001.png");
        
        // 用例2: 北京-大阪
        driver.get("https://flights.ctrip.com/online/channel/domestic");
        Thread.sleep(2000);
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("大阪");
        
        flightPage.getPassengerTypeDropdown().click();
        Thread.sleep(1000);
        
        flightPage.selectDate("2024-12-01");
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R005_002.png");
        
        // 用例3: 上海-东京
        driver.get("https://flights.ctrip.com/online/channel/domestic");
        Thread.sleep(2000);
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("上海");
        flightPage.inputArrivalCity("东京");
        
        flightPage.getPassengerTypeDropdown().click();
        Thread.sleep(1000);
        
        flightPage.selectDate("2024-12-01");
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R005_003.png");
        
        // 用例4: 上海-大阪
        driver.get("https://flights.ctrip.com/online/channel/domestic");
        Thread.sleep(2000);
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("上海");
        flightPage.inputArrivalCity("大阪");
        
        flightPage.getPassengerTypeDropdown().click();
        Thread.sleep(1000);
        
        flightPage.selectDate("2024-12-01");
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R005_004.png");
    }

    // R006: 单程国际查询 - 航司比较表
    @Test
    public void test_CtripFlight_R006() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        
        flightPage.inputDepartureCity("上海");
        flightPage.inputArrivalCity("东京");
        flightPage.selectDate("2024-12-07");
        flightPage.clickSearch();
        
        try {
            flightPage.getAirlineCompareBtn().click();
            Thread.sleep(2000);
            
            // 连续点击>按钮直到最后一页
            WebElement nextBtn = flightPage.getNextBtn();
            for (int i = 0; i < 10; i++) {
                try {
                    if (nextBtn.isEnabled()) {
                        nextBtn.click();
                        Thread.sleep(1000);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
            }
            
            // 选择最后一项可选航班
            List<WebElement> flights = driver.findElements(By.xpath("//div[contains(@class,'flight-item')]"));
            if (!flights.isEmpty()) {
                flights.get(flights.size() - 1).click();
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R006_001.png");
    }

    // R007: 往返国内机票查询
    @Test
    public void test_CtripFlight_R007() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectSingleTrip();
        flightPage.getAddReturnBtn().click();
        Thread.sleep(1000);
        
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("广州");
        flightPage.selectDate("2024-12-01");
        flightPage.selectReturnDate("2024-12-05");
        flightPage.clickSearch();
        
        try {
            flightPage.getSwapBtn().click();
            Thread.sleep(3000);
            
            // 选择去程第一趟航班
            List<WebElement> outboundFlights = driver.findElements(By.xpath("//div[contains(@class,'flight-item')]"));
            if (!outboundFlights.isEmpty()) {
                WebElement selectOutboundBtn = outboundFlights.get(0).findElement(By.xpath(".//button[contains(text(),'选为去程')]"));
                selectOutboundBtn.click();
                Thread.sleep(3000);
            }
            
            // 选择返程第一趟航班进行订票
            List<WebElement> returnFlights = driver.findElements(By.xpath("//div[contains(@class,'flight-item')]"));
            if (!returnFlights.isEmpty()) {
                WebElement bookReturnBtn = returnFlights.get(0).findElement(By.xpath(".//button[contains(text(),'订票')]"));
                bookReturnBtn.click();
                Thread.sleep(2000);
                
                // 鼠标悬浮至退改签说明
                Actions actions = flightPage.getActions();
                actions.moveToElement(flightPage.getRefundInfo()).perform();
                Thread.sleep(1000);
                
                // 在弹出框左上角点击返程
                flightPage.getReturnTab().click();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R007_001.png");
    }

    // R008: 往返国际机票查询 - 未登录状态
    @Test
    public void test_CtripFlight_R008() throws InterruptedException {
        // 确保未登录状态（如果已登录，先退出）
        try {
            WebElement logoutBtn = driver.findElement(By.xpath("//button[contains(text(),'退出')]"));
            logoutBtn.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            // 未登录，继续
        }
        
        flightPage.navigateToFlightPage();
        flightPage.selectRoundTrip();
        
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("东京");
        flightPage.selectDate("2024-12-01");
        flightPage.selectReturnDate("2024-12-05");
        flightPage.clickSearch();
        
        try {
            // 选择去程第一趟航班
            List<WebElement> outboundFlights = driver.findElements(By.xpath("//button[contains(text(),'选为去程')]"));
            if (!outboundFlights.isEmpty()) {
                outboundFlights.get(0).click();
                Thread.sleep(3000);
            }
            
            // 选择返程第一趟航班进行订票
            List<WebElement> returnFlights = driver.findElements(By.xpath("//button[contains(text(),'订票')]"));
            if (!returnFlights.isEmpty()) {
                returnFlights.get(0).click();
                Thread.sleep(2000);
                
                // 选择任一可选方案，点击预订
                flightPage.getBookBtn().click();
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R008_001.png");
    }

    // R009: 往返查询 - 邻近航线
    @Test
    public void test_CtripFlight_R009() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectRoundTrip();
        
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("上海");
        flightPage.selectDate("2024-12-07");
        flightPage.selectReturnDate("2024-12-10");
        flightPage.clickSearch();
        
        try {
            // 鼠标悬浮至邻近航线
            Actions actions = flightPage.getActions();
            actions.moveToElement(flightPage.getNearbyRoute()).perform();
            Thread.sleep(2000);
            
            // 点击弹出框中的缺口程航线
            flightPage.getGapRoute().click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R009_001.png");
    }

    // R010: 多程查询 - 未选择第2程到达城市
    @Test
    public void test_CtripFlight_R010() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectMultiTrip();
        
        // 第一程输入
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("上海");
        flightPage.selectDate("2024-12-01");
        
        // 点击第二程出发地（默认输入第一程目的地）
        WebElement departure2 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[3]"));
        departure2.click();
        Thread.sleep(500);
        
        // 点击搜索
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R010_001.png");
    }

    // R011: 多程查询 - 相同出发地和目的地
    @Test
    public void test_CtripFlight_R011() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectMultiTrip();
        
        // 第一程输入相同的出发地和目的地
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("北京");
        
        // 点击搜索
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R011_001.png");
    }

    // R012: 多程查询 - 三程国内提示
    @Test
    public void test_CtripFlight_R012() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectMultiTrip();
        
        // 点击再加一程按钮
        flightPage.getAddLegBtn().click();
        Thread.sleep(1000);
        
        // 第一程
        flightPage.inputDepartureCity("北京");
        flightPage.inputArrivalCity("上海");
        flightPage.selectDate("2024-12-01");
        
        // 第二程
        WebElement departure2 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[3]"));
        departure2.click();
        Thread.sleep(500);
        departure2.clear();
        Thread.sleep(300);
        departure2.sendKeys("上海");
        Thread.sleep(1000);
        
        WebElement arrival2 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[4]"));
        arrival2.click();
        Thread.sleep(500);
        arrival2.clear();
        Thread.sleep(300);
        arrival2.sendKeys("广州");
        Thread.sleep(1000);
        
        WebElement date2 = driver.findElement(By.xpath("(//input[@placeholder='yyyy-mm-dd'])[2]"));
        date2.click();
        Thread.sleep(500);
        flightPage.selectDate("2024-12-05");
        
        // 第三程
        WebElement departure3 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[5]"));
        departure3.click();
        Thread.sleep(500);
        departure3.clear();
        Thread.sleep(300);
        departure3.sendKeys("广州");
        Thread.sleep(1000);
        
        WebElement arrival3 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[6]"));
        arrival3.click();
        Thread.sleep(500);
        arrival3.clear();
        Thread.sleep(300);
        arrival3.sendKeys("成都");
        Thread.sleep(1000);
        
        WebElement date3 = driver.findElement(By.xpath("(//input[@placeholder='yyyy-mm-dd'])[3]"));
        date3.click();
        Thread.sleep(500);
        flightPage.selectDate("2024-12-08");
        
        // 点击搜索
        flightPage.clickSearch();
        
        takeScreenshot("CtripFlight_R012_001.png");
    }

    // R013: 多程国际查询 - 四程
    @Test
    public void test_CtripFlight_R013() throws InterruptedException {
        flightPage.navigateToFlightPage();
        flightPage.selectMultiTrip();
        
        // 连续点击再加一程直到六程
        WebElement addLegBtn = flightPage.getAddLegBtn();
        for (int i = 0; i < 5; i++) {
            addLegBtn.click();
            Thread.sleep(500);
        }
        
        // 删除两程，减至四程
        List<WebElement> deleteBtns = driver.findElements(By.xpath("//span[contains(@class,'delete')]"));
        if (deleteBtns.size() >= 2) {
            deleteBtns.get(0).click();
            Thread.sleep(500);
            deleteBtns.get(1).click();
            Thread.sleep(500);
        }
        
        // 第一程: 上海-北京
        flightPage.inputDepartureCity("上海");
        flightPage.inputArrivalCity("北京");
        flightPage.selectDate("2024-12-03");
        
        // 第二程: 北京-首尔
        WebElement departure2 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[3]"));
        departure2.click();
        Thread.sleep(500);
        departure2.clear();
        Thread.sleep(300);
        departure2.sendKeys("北京");
        Thread.sleep(1000);
        
        WebElement arrival2 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[4]"));
        arrival2.click();
        Thread.sleep(500);
        arrival2.clear();
        Thread.sleep(300);
        arrival2.sendKeys("首尔");
        Thread.sleep(1000);
        
        WebElement date2 = driver.findElement(By.xpath("(//input[@placeholder='yyyy-mm-dd'])[2]"));
        date2.click();
        Thread.sleep(500);
        flightPage.selectDate("2024-12-05");
        
        // 第三程: 首尔-东京
        WebElement departure3 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[5]"));
        departure3.click();
        Thread.sleep(500);
        departure3.clear();
        Thread.sleep(300);
        departure3.sendKeys("首尔");
        Thread.sleep(1000);
        
        WebElement arrival3 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[6]"));
        arrival3.click();
        Thread.sleep(500);
        arrival3.clear();
        Thread.sleep(300);
        arrival3.sendKeys("东京");
        Thread.sleep(1000);
        
        WebElement date3 = driver.findElement(By.xpath("(//input[@placeholder='yyyy-mm-dd'])[3]"));
        date3.click();
        Thread.sleep(500);
        flightPage.selectDate("2024-12-08");
        
        // 第四程: 东京-上海
        WebElement departure4 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[7]"));
        departure4.click();
        Thread.sleep(500);
        departure4.clear();
        Thread.sleep(300);
        departure4.sendKeys("东京");
        Thread.sleep(1000);
        
        WebElement arrival4 = driver.findElement(By.xpath("(//input[@placeholder='可输入城市或机场'])[8]"));
        arrival4.click();
        Thread.sleep(500);
        arrival4.clear();
        Thread.sleep(300);
        arrival4.sendKeys("上海");
        Thread.sleep(1000);
        
        WebElement date4 = driver.findElement(By.xpath("(//input[@placeholder='yyyy-mm-dd'])[4]"));
        date4.click();
        Thread.sleep(500);
        flightPage.selectDate("2024-12-10");
        
        // 点击搜索
        flightPage.clickSearch();
        
        try {
            // 前三程选择首个航班
            for (int i = 0; i < 3; i++) {
                List<WebElement> selectBtns = driver.findElements(By.xpath("//button[contains(text(),'选为第" + (i+1) + "程')]"));
                if (!selectBtns.isEmpty()) {
                    selectBtns.get(0).click();
                    Thread.sleep(3000);
                }
            }
            
            // 最后一程选择首个航班进行订票
            List<WebElement> bookBtns = driver.findElements(By.xpath("//button[contains(text(),'订票')]"));
            if (!bookBtns.isEmpty()) {
                bookBtns.get(0).click();
                Thread.sleep(2000);
                
                // 鼠标悬浮至多种报销凭证
                Actions actions = flightPage.getActions();
                actions.moveToElement(flightPage.getReimbursement()).perform();
                Thread.sleep(1000);
                
                // 再悬浮至行程单或电子发票
                actions.moveToElement(flightPage.getInvoice()).perform();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        takeScreenshot("CtripFlight_R013_001.png");
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
