package Com.SeleniumAutomation;

import java.awt.AWTException;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileFunctions extends DriverScript {

	static AndroidDriver<AndroidElement> androidDriver;
	static AndroidDriver<MobileElement> mobdriver;

	public static void setUp() {
		try {

			File f = new File("Resources/APPS");
			File fs = new File(f, "app-qa_new-release.apk");
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			dc.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
			System.out.println(fs.getAbsolutePath());
			dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
			dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_4_API_30");
			// dc.setCapability("appPackage", "co.nayan.c3specialist_v2.qa_new");
			dc.setCapability("appWaitActivity", "co.nayan.c3v2.login.LoginActivity");
			dc.setCapability(MobileCapabilityType.NO_RESET, false);
			

			androidDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
		} catch (MalformedURLException e) {
			System.out.println("Please check driver port");
			e.printStackTrace();
		}
		androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	public static Boolean launchMobileBrowser(String Parameters) throws IOException {
		try {
			String workingDirectory = new java.io.File(".").getCanonicalPath();
			String Chromepath = workingDirectory +"\\Drivers\\chromedriver.exe";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
			chromePrefs.put("download.prompt_for_download", false);
			ChromeOptions options = new ChromeOptions();
		    //options.addArguments("--enable-javascript");
     		options.addArguments("-enable-automation");
    		options.addArguments("disable-extensions");
        //	options.setExperimentalOption("useAutomationExtension",  false);
			options.setExperimentalOption("w3c", false);
			//options.addArguments("use-fake-device-for-media-stream");
			//options.addArguments("use-fake-ui-for-media-stream");
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
			dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
			dc.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			dc.setCapability("unicodeKeyboard", true);
			dc.setCapability("resetKeyboard", true);
			//dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			dc.setCapability("chromedriverExecutable",Chromepath);
			dc.setCapability(MobileCapabilityType.NO_RESET, false);
			androidDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
			androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			androidDriver.get("https://test.simplysecuresign.com/SimplySignClient/auth/sign-in");
			System.out.println("Sucessfully lauched application in mobile");
		} catch (MalformedURLException e) {
			System.out.println("Please check driver port");
			e.printStackTrace();
		}
		
		return true;
	}
	public static Boolean sssLogin() {
		Boolean Status = true;
		androidDriver.get("https://test.simplysecuresign.com/SimplySignClient/auth/sign-in");
		
		return Status;
	}
	public static Boolean launchMobileApp() {
		Boolean Status = true;
		setUp();
		return Status;
	}

	public static Boolean killSession() {
		androidDriver.quit();
		return true;
	}

	public static Boolean mobileLogin(String Parameters) throws Throwable, Exception, InstantiationException,
	IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		Boolean Status = true;
		String[] arguments = null;
		try {
			arguments = splitfunction(Parameters, "->");
			if (hmap.containsKey(arguments[1])) {
				arguments[1] = hmap.get(arguments[1]);
			} 
			
			String[] args = splitfunction(arguments[1], ",");
			String xpathUN = "xpath://android.widget.EditText[@text=\"Email or Username\"]";
			String xpathpwd = "xpath://android.widget.EditText[@text=\"Password\"]";
			String xpathsignin = "xpath://android.widget.Button[@text=\"SIGN IN\"]";
			String verifylogin = "xpath://android.widget.TextView[@text=\"Let's Get Started\"]";
			AndroidElement username = getMobilelocator(xpathUN);
			AndroidElement password = getMobilelocator(xpathpwd);
			AndroidElement signin = getMobilelocator(xpathsignin);
			WebDriverWait wait = new WebDriverWait(androidDriver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(signin));

			// AndroidElement username
			// =androidDriver.findElement(By.xpath("//android.widget.EditText[@text=\"Email
			// or Username\"]"));
			username.sendKeys(args[0]);
			password.sendKeys(args[1]);
			signin.click();

			System.out.println("Sucessfully entered username");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Status;	
	}

	public static AndroidElement getMobilelocator(String parameters)
			throws InterruptedException, AWTException, IOException, ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException, Exception, StaleElementReferenceException {
		AndroidElement locpath = null;
		try {

			String[] arguments = null;
			//boolean b;
			//b = parameters.matches(".*:.*-=.*");

		
				if (parameters.contains("-=")) {
					arguments = splitfunction(parameters, "-=");
				} else if (parameters.contains("\\|"))  {
					arguments = splitfunction(parameters, "\\|");
				} else {
					arguments = splitfunction(parameters, ":");
				}
				

			String mode = arguments[0];
			arguments[0].trim();
			locator = arguments[1];

			switch (mode) {

			case "id":
				locpath = androidDriver.findElement(By.id(locator));
				break;
			case "name":
				locpath = androidDriver.findElement(By.name(locator));
				break;
			case "xpath":
				locpath = androidDriver.findElement(By.xpath(locator));
				break;
			case "className":
				locpath = androidDriver.findElement(By.className(locator));
				break;
			case "tagName":
				locpath = androidDriver.findElement(By.tagName(locator));
				break;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("unable to find the locator" + " " + locator);
		}

		return locpath;

	}

	public static boolean mobileVERIFYVALUE(String parameters)
			throws InterruptedException, AWTException, IOException, ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException, Exception, StaleElementReferenceException {
		flag = 1;
		String[] arguments = null;
		String keyvalue = null;
		arguments = splitfunction(parameters, "->");
		String value = null;
		try {
			if (hmap.containsKey(arguments[2])) {
				value = hmap.get(arguments[2]);
			} else {
				value = arguments[2];
			}

			String xpath = DriverScript.mobileprop.getProperty(arguments[1]);
			if (xpath.contains("+")) {
				String[] newval = splitfunction(xpath, "+");

				if (hmap.containsKey(newval[1])) {
					keyvalue = hmap.get(newval[1]);
				}
				xpath = xpath.replace(newval[1], keyvalue);
				xpath = xpath.replace("+", "").trim();
			}
			if (xpath != null) {
				AndroidElement locator = getMobilelocator(xpath);
				String ovalue = locator.getText();
				if (hmap.containsKey(arguments[2])) {
					value = hmap.get(arguments[2]);
				} else if (arguments[2].toUpperCase().contains("SYSDATE")) {
					String sysdat = Sysdate(arguments[2]);
					value = sysdat;
				} else if ((arguments[2].contains("+"))) {

					String[] values = splitfunction(arguments[3], "+");
					if (values.length > 1) {
						if ((hmap.containsKey(values[0]))
								&& (hmap.containsKey(values[1]) && (hmap.containsKey(values[2])))) {
							Float firstval = Float.parseFloat(hmap.get(values[0]));
							Float Secval = Float.parseFloat(hmap.get(values[1]));
							Float thirdval = Float.parseFloat(hmap.get(values[2]));
							value = String.valueOf(firstval + Secval + thirdval);
							hmap.put(arguments[2], value);
						} else {
							if ((hmap.containsKey(values[0])) && (hmap.containsKey(values[1]))) {
								Float firstval = Float.parseFloat(hmap.get(values[0]));
								Float Secval = Float.parseFloat(hmap.get(values[1]));
								value = String.valueOf(firstval + Secval);
								hmap.put(arguments[2], value);
							}
						}
					} else {

					}
				} else if ((arguments[2].contains("*"))) {

					String[] values = splitfunction(arguments[2], "*");
					if ((hmap.containsKey(values[0])) && (hmap.containsKey(values[1]))) {
						Float firstval = Float.parseFloat(hmap.get(values[0]));
						Float Secval = Float.parseFloat(hmap.get(values[1]));
						value = String.valueOf(firstval * Secval);
					} else {

					}
				} else {
					value = arguments[2];
				}
				if (value.equalsIgnoreCase("IS NOT NULL")) {
					int len = ovalue.length();
					if (len > 0) {

					} else {

						flag = 0;

					}
				} else {
					if (ovalue.equalsIgnoreCase(value)) {
						System.out.println("Both values Matched");

					} else {
						System.out.println("Values did not match");
						flag = 0;
					}
				}
			} else {
				System.out.println("unable to find the xpath" + " " + arguments[1]);
			}
		} catch (Exception e) {
			System.out.println("exception value : " + e.getMessage());
			flag = 0;
		}
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean mobileCLICK(String parameters)
			throws InterruptedException, AWTException, IOException, ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException, Exception, StaleElementReferenceException {
		Boolean Status = true;
		String[] arguments = null;
		String keyvalue = null;
		arguments = splitfunction(parameters, "->");
		try {
			String xpath = DriverScript.mobileprop.getProperty(arguments[1]);
			if (xpath.contains("+")) {
				String[] newval = splitfunction(xpath, "+");
				if (hmap.containsKey(newval[1])) {
					keyvalue = hmap.get(newval[1]);
				}
				xpath = xpath.replace(newval[1], keyvalue);
				xpath = xpath.replace("+", "").trim();
			}
			if (xpath != null) {
				AndroidElement locator = getMobilelocator(xpath);
				if (locator.isEnabled()) {
					String[] args = splitfunction(xpath, ":");
					WAITTIME("WAITTIME->2");
					locator.click();
					WAITTIME("WAITTIME->4");
					// clickElementUsingJavascriptExecutor(args[1]);
					System.out.println("Succesfully clicked on" + " " + arguments[1]);
				} else {
					System.out.println("unable to find" + " " + arguments[1]);
					Status = false;
				}
			} else {
				System.out.println("unable to find the xpath" + " " + arguments[1]);
			}
		} catch (Exception e) {
			System.out.println("unable to find the locator" + " " + e.getMessage());
			Status = false;

		}
		return Status;
	}

	public static void longPressElement1(MobileElement element, AppiumDriver<MobileElement> driver) {
		TouchAction action = new TouchAction(androidDriver);
		action.longPress(PointOption.point(element.getCenter().x, element.getCenter().y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).release().perform();


	}
	public static void longPressMovetoFromCoordinatesToCoordinates(int touchX, int touchY, int touchoffsetX,
			int touchoffsetY, AndroidDriver<AndroidElement> androidDriver) throws Exception, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {

		TouchAction action = new TouchAction(androidDriver);
		action.longPress(PointOption.point(touchX, touchY)).moveTo(PointOption.point(touchoffsetX, touchoffsetY))
		.release();
		waitSeconds(2);
		action.perform();

	}
	public static boolean annoateRecord(String Parameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		String[] arguments = null;
		arguments = splitfunction(Parameters, "->");
		int imgcount = Integer.parseInt(arguments[1]);
		for ( int i = 1; i <=imgcount; i++) {
			try {
				WAITTIME("WAITTIME->5");
				mobileCLICK("mobileCLICK->selectbutton");
				waitSeconds(2);
				longPressMovetoFromCoordinatesToCoordinates(83, 1027, 1254, 1681, androidDriver);
				waitSeconds(2);
				System.out.println("Sucessfully selected Image");
				AndroidElement submit = getMobilelocator("xpath-=//android.widget.ImageView[@resource-id=\"co.nayan.c3specialist_v2.qa_new:id/submitIv\"]");
				for (int j =1; j <=5;j++) {
					if(submit.isEnabled()) {
						submit.click();
						waitSeconds(1);
						break;
					} else {
						mobileCLICK("mobileCLICK->cropbutton");
						mobileCLICK("mobileCLICK->selectbutton");
						longPressMovetoFromCoordinatesToCoordinates(83, 1027, 1254, 1681, androidDriver);	
					}
				}

			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("sucessfully selected "+ imgcount +" images");
		return true;
	}

	public static void waitSeconds(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (Exception e) {

		}
	}

	public static boolean specialistValidation(String Parameters) throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception{
		AndroidElement alertmessage = null;
		String[] arguments = null;
		arguments = splitfunction(Parameters, "->");
		int imgcount = Integer.parseInt(arguments[1]);
		WAITTIME("WAITTIME->5");
		try {
			for(int i=1; i<=imgcount;i++) {
				mobileCLICK("mobileCLICK->truebutton");
			}
			alertmessage = getMobilelocator("xpath://android.widget.TextView[@text=\"There are no more records to process.\"]");
			if (alertmessage.isDisplayed()) {
				AndroidElement	popupok = getMobilelocator("xpath://android.widget.Button[@text=\"OK\"]");
				popupok.click();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		WAITTIME("WAITTIME->5");
		return true;

	}

	public static boolean tapOnScreen( ) {
		int touchX = 18;
		int touchY = 1534;		
		TouchAction touchAction = new TouchAction(androidDriver);

		PointOption touch = new PointOption();
		touch.withCoordinates(touchX, touchY);
		touchAction.press(touch).release().perform();
		System.out.println("sucessfully tapped on screen");
		return true;
	}

	public static boolean managerRejectsAll() throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		WAITTIME("WAITTIME->18");
		int touchA = 338;
		int touchB = 1233;
		int touchX = 1109;
		int touchY = 1240;
		int touchC = 427;
		int touchD = 1929;
		int touchE = 1068;
		int touchF = 1915;
		int touchG = 386;
		int touchH = 2625;
		int touchI = 1054;
		int touchJ = 2577;
		int touchk = 489;
		int touchl = 2722;
		TouchAction action = new TouchAction(androidDriver);
		action.longPress(PointOption.point(touchA, touchB)).release().perform();
		action.longPress(PointOption.point(touchX, touchY)).release().perform();
		action.longPress(PointOption.point(touchC, touchD)).release().perform();
		action.longPress(PointOption.point(touchE, touchF)).release().perform();
		action.longPress(PointOption.point(touchG, touchH)).release().perform();
		action.longPress(PointOption.point(touchI, touchJ)).release().perform();
		action.longPress(PointOption.point(touchk, touchl)).release().perform();
		System.out.println("Manager Rejected all reords sucessfully");
		mobileCLICK("mobileCLICK->managerreject");
		return true;
	}
	public static boolean managerApproveAll() throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		WAITTIME("WAITTIME->18");
		int touchA = 338;
		int touchB = 1233;
		int touchX = 1109;
		int touchY = 1240;
		int touchC = 427;
		int touchD = 1929;
		int touchE = 1068;
		int touchF = 1915;
		int touchG = 386;
		int touchH = 2625;
		int touchI = 1054;
		int touchJ = 2577;
		int touchk = 951;
		int touchl = 2742;
		TouchAction action = new TouchAction(androidDriver);
		action.longPress(PointOption.point(touchA, touchB)).release().perform();
		action.longPress(PointOption.point(touchX, touchY)).release().perform();
		action.longPress(PointOption.point(touchC, touchD)).release().perform();
		action.longPress(PointOption.point(touchE, touchF)).release().perform();
		action.longPress(PointOption.point(touchG, touchH)).release().perform();
		action.longPress(PointOption.point(touchI, touchJ)).release().perform();
		action.longPress(PointOption.point(touchk, touchl)).release().perform();
		mobileCLICK("mobileCLICK->managerapprove");
		System.out.println("Manager Rejected all reords sucessfully");
		return true;
	}
	public static boolean submitRecords() throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		int touchX = 703;
		int touchY = 2722;
		TouchAction action = new TouchAction(androidDriver);
		action.longPress(PointOption.point(touchX, touchY)).release().perform();
		mobileCLICK("mobileCLICK->submitreviewedrecords");
		AndroidElement alertmessage = null;
		try {
			alertmessage = getMobilelocator("xpath://android.widget.TextView[@text=\"There are no more records to process.\"]");
			if (alertmessage.isDisplayed()) {
				AndroidElement	popupok = getMobilelocator("xpath://android.widget.Button[@text=\"OK\"]");
				popupok.click();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Sucessfully submitted records");
		return true;
	}

	public static boolean annoateRecord_Manager(String Parameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		try {
			String[] arguments = null;
			arguments = splitfunction(Parameters, "->");
			int imgcount = Integer.parseInt(arguments[1]);
			for ( int i = 1; i <=imgcount; i++) {

				waitSeconds(5);
				mobileCLICK("mobileCLICK->selectbutton");
				waitSeconds(1);
				longPressMovetoFromCoordinatesToCoordinates(41,875, 1302, 1971, androidDriver);
				waitSeconds(1);
				System.out.println("Sucessfully selected Image");
				AndroidElement submit = getMobilelocator("xpath-=//android.widget.ImageView[@resource-id=\"co.nayan.c3specialist_v2.qa_new:id/submitIv\"]");
				for (int j =1; j <=5;j++) {
					if(submit.isEnabled()) {
						submit.click();
						waitSeconds(1);
						break;
					} else {
						mobileCLICK("mobileCLICK->cropbutton");
						mobileCLICK("mobileCLICK->selectbutton");
						longPressMovetoFromCoordinatesToCoordinates(83, 1027, 1254, 1681, androidDriver);	
					}
				}

			}
			AndroidElement alertmessage = getMobilelocator("xpath://android.widget.TextView[@text=\"There are no more records to process.\"]");
			if (alertmessage.isDisplayed()) {
				AndroidElement	popupok = getMobilelocator("xpath://android.widget.Button[@text=\"OK\"]");
				popupok.click();
			}

		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("sucessfully selected 6 images");
		return true;
	}
	public static boolean startWork() throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		Boolean status = true;
		mobileCLICK("mobileCLICK->firstNext");
		mobileCLICK("mobileCLICK->secondNext");
		mobileCLICK("mobileCLICK->StartWorking");
		mobileCLICK("mobileCLICK->SelectLanguage");
		waitSeconds(2);
		mobileCLICK("mobileCLICK->StartWork");
		waitSeconds(4);
		return status;
	}
	public static boolean logOut() throws StaleElementReferenceException, ClassNotFoundException, InstantiationException, IllegalAccessException, InterruptedException, AWTException, IOException, SQLException, Exception {
		Boolean status = true;
		waitSeconds(2);
		mobileCLICK("mobileCLICK->profile");
		mobileCLICK("mobileCLICK->logout");
		return status;
	}
	
	public static boolean mobileEntervalue(String parameters)
			throws InterruptedException, AWTException, IOException, ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException, Exception, StaleElementReferenceException {
		String[] arguments = null;
		Boolean Status = true;
		String keyvalue = null;
		arguments = splitfunction(parameters, "\\->");
		try {
			String xpath = DriverScript.mobileprop.getProperty(arguments[1]);
			if(xpath.contains("+")) {
				String[] newval = splitfunction(xpath, "+");
				if (hmap.containsKey(newval[1])) {
					keyvalue = hmap.get(newval[1]);
				}
				xpath = xpath.replace(newval[1], keyvalue);
				xpath = xpath.replace("+", "").trim();
			}
			if (xpath != null) {
				WebElement locator = getMobilelocator(xpath);
				if (locator.isEnabled()) {
					if (arguments[2].toUpperCase().contains("SYSDATE")) {
						String sysdat = Sysdate(arguments[2]);
						//locator.click();
						WAITTIME("WAITTIME->2");
						 locator.clear();
						locator.sendKeys(sysdat);
						System.out.println("Succesfully entered the Date" + " " + sysdat);
					} else if (hmap.containsKey(arguments[2])) {
						String value = hmap.get(arguments[2]);
						//locator.click();
						WAITTIME("WAITTIME->2");
						locator.clear();
						locator.sendKeys(value);
						System.out.println("Succesfully entered the value" + " " + value);
					} else {
						locator.click();
						WAITTIME("WAITTIME->2");
						String textvalue = locator.getAttribute("value");
						int len = textvalue.length();
						for (int i = 0; i <= len; i++) {   
							locator.sendKeys(Keys.BACK_SPACE);
						}
						locator.clear();
						locator.sendKeys(arguments[2]);
						System.out.println("Succesfully entered the value" + " " + arguments[2]);
					}
				} else {
					System.out.println("not able to find" + " " + arguments[2]);
					Status = false;
				}
			} else {
				System.out.println("unable to find the xpath" + " " + arguments[1]);
			}
			//androidDriver.hideKeyboard();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("unable to find the locator" + " " + arguments[1]);
			Status = false;
		}
		return Status;
	}
	public static boolean swipeToBottom()
	{
		org.openqa.selenium.Dimension dim = androidDriver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width/2;
		int top_y = (int)(height*0.80);
		int bottom_y = (int)(height*0.20);
		System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		//ts.longPress(x, top_y).moveTo(x, bottom_y).release().perform();
		swipeScreen(x, top_y, x, bottom_y);
		return true;
	}
	
	public static void swipeScreen(int touchX, int touchY, int swipeX, int swipeY) {
		TouchAction touchAction = new TouchAction(androidDriver);

		PointOption touch = new PointOption();
		touch.withCoordinates(touchX, touchY);

		PointOption swipe = new PointOption();
		swipe.withCoordinates(swipeX, swipeY);

		touchAction.press(touch).waitAction().moveTo(swipe).release().perform();
//		touchAction.press(touch).moveTo(swipe).release().perform();
		
	}

}
