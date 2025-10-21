    package com.example.demo;

    import org.apache.commons.io.FileUtils;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
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
    import java.util.Date;
    import java.util.concurrent.TimeUnit;


    public class TestCtripFlight {
        private WebDriver driver;
        private WebDriverWait wait;


        @BeforeEach
        public void setup() {
            //提交最终代码脚本时，请将驱动路径换回官方路径"C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe"
    //      System.setProperty("webdriver.chrome.driver", "C:\\Users\\86153\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
            driver.get("https://www.ctrip.com");
            driver.manage().window().maximize();
    //      隐式等待
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            PageFactory.initElements(driver, this);


        }
        // test-code-start

        // 请在此处插入Selenium+JUnit5代码


        @FindBy(xpath = "//button[contains(@aria-label, '机票')]")
        public WebElement flight;

        @FindBy(xpath = "//span[@class = \"radio-label\"]")
        public WebElement select3;

        @FindBy(xpath = "//input[@aria-label = \"可输入城市或机场\"]")
        public WebElement from;

        @FindBy(xpath = "//input[ @name = \"owACity\"]")
        public WebElement to;

//      带婴儿选项~~
        @FindBy(xpath = "//span[text() = \"带儿童\"]")
        public WebElement select4;

//      展开勾选列表
        @FindBy(xpath = "//span[contains(text(), \"不限舱等\")]")
        public WebElement select5;
//       经济舱
        @FindBy(xpath = "//div[contains(text(), \"经济舱\")]")
        public WebElement select6;


        @FindBy(xpath = "//span[@class =\"form-label-v3\"]")
        public WebElement form;



        @FindBy(xpath = "//input[@aria-label = \"请选择日期\"]" )
        public WebElement date;


        @FindBy(xpath = "//span[@class = \"date-d\" and text() = 23 ]")
        public WebElement Oct23;

        @FindBy(xpath = "//button[@class = \"search-btn\"]")
        public WebElement search;


        @FindBy(xpath = "//div[contains(@class, 'passenger-item') and .//*[contains(text(), '成人')]]//div[@class='btn ']")
        public WebElement adult;
        @FindBy(xpath = "//div[contains(@class, 'passenger-item') and .//*[contains(text(), '儿童')]]//div[@class='btn ']")
        public WebElement student;
        @FindBy(xpath = "//div[contains(@class, 'passenger-item') and .//*[contains(text(), '婴儿')]]//div[@class='btn ']")
        public WebElement baby;


        @FindBy(xpath = "//a[@class = \"btn-sure\"]")
        public WebElement sure;


    //  参数化测试~~
        @ParameterizedTest


        @CsvSource({
                "北京,广州",
                "北京,成都",
                "上海,广州",
                "上海,成都"

        })



    //  需求1
        public void test_CtripFlight_R001(String fromStation,String toStation) throws InterruptedException {

    //
            Actions actions = new Actions(driver);
            flight.click();
            flight.click();

//          勾选单程
            select3.click();
//
//            Thread.sleep(10000000);

            from.click();
            actions.sendKeys(Keys.BACK_SPACE).build().perform();
            from.sendKeys(fromStation);
            Thread.sleep(2000);

            to.click();
            actions.sendKeys(Keys.BACK_SPACE).build().perform();
            to.sendKeys(toStation);
            Thread.sleep(1000);
            actions.sendKeys(Keys.ENTER).build().perform();
            Thread.sleep(2000);

            select4.click();
            select5.click();
            select6.click();



            date.click();
            actions.moveToElement(date).click().perform();
            Oct23.click();
            Thread.sleep(1000);
            search.click();
            Thread.sleep(20000);
//            takeScreenshot("CtripFlight_R001_001.png");


        }


        @ParameterizedTest
        @CsvSource({
                "#&",
                "@@@"
        })


    //  需求2
        public void test_CtripFlight_R002(String illegal_String) throws InterruptedException {

            flight.click();
            flight.click();
            select3.click();
            from.click();
            from.sendKeys(illegal_String);
            Thread.sleep(2000);
//            takeScreenshot("CtripFlight_R002_001.png");

        }

        @ParameterizedTest
        @CsvSource({
                "上海,大阪"
//                "北京,东京",
//                "北京,大阪",
//                "上海,东京"

        })

    //  需求3

        public void test_CtripFlight_R003(String fromStation,String toStation) throws InterruptedException {

            flight.click();
            flight.click();


            Actions actions = new Actions(driver);
            //          勾选单程
            select3.click();

            from.click();
            actions.sendKeys(Keys.BACK_SPACE).build().perform();
            from.sendKeys(fromStation);
            Thread.sleep(2000);

            to.click();
            actions.sendKeys(Keys.BACK_SPACE).build().perform();
            to.sendKeys(toStation);
            actions.sendKeys(Keys.ENTER).build().perform();
            Thread.sleep(2000);


            form.click();
            adult.click();
            student.click();
            baby.click();
            sure.click();
            Thread.sleep(3000);


            date.click();
            actions.moveToElement(date).click().perform();
            Oct23.click();
            Thread.sleep(1000);
            search.click();
            Thread.sleep(15000);
//            takeScreenshot("CtripFlight_R003_001.png");
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
