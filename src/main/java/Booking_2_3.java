import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertTrue;

public class Booking_2_3 {

    @Test
    public void BookingTask2_3() throws InterruptedException {
        int plusDays = 7;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sid-v\\Downloads\\chromedriver_win32\\chromedriver.exe");//your own path

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.booking.com/");
        System.out.println(driver.getTitle());

        WebElement searchInput = driver.findElement(By.xpath("//input[@id = 'ss']"));
        searchInput.sendKeys("Париж");
        Calendar calendar = new GregorianCalendar();
        setDays(driver, calendar, "yyyy-MM-dd", 3, 7);

        WebElement Guest = driver.findElement(By.xpath("//div[@data-visible='accommodation,flights']"));
        Guest.click();
        setRooms(driver, "adults", 2);
        searchInput.submit();
        sleep(5000);

        WebElement sortPrice = driver.findElement(By.xpath("//li[contains(@class,'sort_price')]"));
        sortPrice.click();
        WebElement enableHotelList = driver.findElement(By.xpath("(//div[contains(@class,'bui-price-display__value')])"));
        assertTrue(enableHotelList.isDisplayed());

        WebElement priceLimit = driver.findElement(By.xpath("//a[contains(@data-id,'pri-4')]//span[contains(@class,'filter_label')]"));
        String currentPriceLimit = priceLimit.getText().substring(priceLimit.getText().indexOf('-')).replaceAll("\\D", "");
        int priceHighLimit = Integer.parseInt(currentPriceLimit);
        priceLimit.click();

        WebElement maxHotelScore = driver.findElement(By.xpath("//a[@data-id = 'review_score-90']"));
        maxHotelScore.click();
        sleep(6000);

        WebElement priceOfHotel = driver.findElement(By.xpath("(//div[contains(@class,'bui-price-display__value')])[1]"));
        String currentPrice = priceOfHotel.getText().replaceAll("\\D", "");
        int priceForHotel = Integer.parseInt(currentPrice);
        assertTrue(priceHighLimit >= priceForHotel / plusDays);
        driver.quit();
    }

    public void setRooms(WebDriver driver, String param, int requireNumberOfParam) {
        int line;
        switch (param) {
            case "adults":
                line = 1;
                break;
            case "rooms":
                line = 3;
                break;
            default:
                throw new IllegalStateException("ERROR! Please insert adults, kids or rooms " + param);
        }
        WebElement numberOfParam = driver.findElement(By.xpath("(//span[@class= 'bui-stepper__display'])[" + line + "]"));
        WebElement increaseNumberOfParam = driver.findElement(By.xpath("(//span[text() = '+'])[" + line + "]"));
        WebElement reduceNumberOfParam = driver.findElement(By.xpath("(//span[text() = '−'])[" + line + "]"));
        int number = Integer.parseInt(numberOfParam.getText());
        int a = Math.abs(requireNumberOfParam - number);
        if (number <= requireNumberOfParam) {
            for (int i = 0; i < a; i++) {
                increaseNumberOfParam.click();
            }
        } else for (int i = 0; i < a; i++) {
            reduceNumberOfParam.click();
        }
    }
        public void setDays(WebDriver driver, Calendar calendar, String formatOfDate, int diffTodayCheckInDay, int plusDays) {
            WebElement calendarBar = driver.findElement(By.xpath("//div[@class = 'xp__dates-inner']"));
            calendarBar.click();
            calendar.add(Calendar.DAY_OF_YEAR, + diffTodayCheckInDay);
            Date checkInDate = calendar.getTime();
            SimpleDateFormat formatForCheckInDate = new SimpleDateFormat(formatOfDate);
            String currentCheckInDate = formatForCheckInDate.format(checkInDate);
            WebElement checkInDateWeb = driver.findElement(By.xpath("//td[contains(@data-date,'" + currentCheckInDate + "')]"));
            calendar.add(Calendar.DAY_OF_YEAR, + plusDays);
            Date checkOutDate = calendar.getTime();
            SimpleDateFormat formatForCheckOutDate = new SimpleDateFormat(formatOfDate);
            String currentCheckOutDate = formatForCheckOutDate.format(checkOutDate);
            WebElement checkOutDateWeb = driver.findElement(By.xpath("//td[contains(@data-date,'" + currentCheckOutDate + "')]"));
            checkInDateWeb.click();
            checkOutDateWeb.click();
        }

    }
