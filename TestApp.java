package pts;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestApp {
	
	WebDriver driver;
	
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Saj\\Desktop\\Data\\GeckoDriver\\geckodriver.exe");
        new TestApp();
    }
    
    public TestApp() {
    	driver = new FirefoxDriver();
    	driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    	driver.get("https://ais2.ukf.sk/ais/start.do");
		cookies();
		wait(2000);
		login();
		wait(2000);
		openSchedule();
		wait(2000);
		takeScreenshot();
		wait(2000);
		logout();
		wait(2000);
    	driver.close();
	}
    
    private void cookies() {
		WebElement cookiesAcceptButton = driver.findElement(By.id("accept-cookies"));
		if(cookiesAcceptButton == null) return;
		cookiesAcceptButton.click();
	}
    
    private void login() {
		WebElement loginUser = driver.findElement(By.id("login"));
		loginUser.sendKeys(InputData.LOGIN_USER.get());
		WebElement loginPassword = driver.findElement(By.id("heslo"));
		loginPassword.sendKeys(InputData.LOGIN_PASSWORD.get());
		loginPassword.submit();
	}
	
	private void openSchedule() {
		driver.findElement(By.xpath("//*[@id=\"col3\"]/app-body/div[1]/div/app-rozvrh/div/div/button/span[5]")).click();
		driver.findElement(By.xpath("/html/body/app-root/div/app-rozvrh/div[1]/div[1]/app-rozvrh-switch/div/div[1]/button[2]")).click();
	}
	
	private void takeScreenshot() {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File("screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private void logout() {
    	driver.findElement(By.xpath("/html/body/app-root/lib-app-header/nav/div[3]/a")).click();
    	driver.findElement(By.xpath("/html/body/app-root/lib-app-header/nav/div[3]/div/a")).click();
    }
    
    private void wait(int millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
    }
    
}