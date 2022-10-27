package com.cst438;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.cst438.domain.Enrollment;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndAddNewStudentTest {
	public static final String CHROME_DRIVER_FILE_LOCATION  = "C:/chromedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_STUDENT_EMAIL = "test1@csumb.edu";
	public static final String TEST_STUDENT_NAME = "test1";

	public static final int SLEEP_DURATION = 1000; // 1 second.

	@Autowired
	StudentRepository studentRepository;
	
	@Test
	@Order(1)
	public void addNewStudentTest() throws Exception {
		// clean up database if it is exists
		Student x = null;
		do {
			x = studentRepository.findByEmail(TEST_STUDENT_EMAIL);
			if (x != null)
				studentRepository.delete(x);
		} while (x != null);
		
		// set the driver location and start driver
				//@formatter:off
				// browser	property name 				Java Driver Class
				// edge 	webdriver.edge.driver 		EdgeDriver
				// FireFox 	webdriver.firefox.driver 	FirefoxDriver
				// IE 		webdriver.ie.driver 		InternetExplorerDriver
				//@formatter:on

				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
				WebDriver driver = new ChromeDriver();
				// Puts an Implicit wait for 10 seconds before throwing exception
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
				try {
					driver.get(URL);
					Thread.sleep(SLEEP_DURATION);
					
					// Locate and click "Add Student" button.
					driver.findElement(By.xpath("//button[@id='AddStudent']")).click();
					Thread.sleep(SLEEP_DURATION);
					
					// enter email and name click Add button					
					driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_STUDENT_EMAIL);
					driver.findElement(By.xpath("//input[@name='name']")).sendKeys(TEST_STUDENT_NAME);
					driver.findElement(By.xpath("//button[@id='Add']")).click();
					Thread.sleep(SLEEP_DURATION);
					
					String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
					System.out.println("********************"+toast_text);
					assertEquals("Student successfully added",toast_text);
					Thread.sleep(SLEEP_DURATION);
					
				}catch (Exception ex) {
					throw ex;
				} finally {

					driver.quit();
				}
	}
	
	//test for emailID is already exists
	@Test
	@Order(2)
	public void addNewStudentErrorTest() throws Exception {
		
		// set the driver location and start driver
				//@formatter:off
				// browser	property name 				Java Driver Class
				// edge 	webdriver.edge.driver 		EdgeDriver
				// FireFox 	webdriver.firefox.driver 	FirefoxDriver
				// IE 		webdriver.ie.driver 		InternetExplorerDriver
				//@formatter:on

				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
				WebDriver driver = new ChromeDriver();
				// Puts an Implicit wait for 10 seconds before throwing exception
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
				try {
					driver.get(URL);
					Thread.sleep(SLEEP_DURATION);
					
					// Locate and click "Add Student" button.
					driver.findElement(By.xpath("//button[@id='AddStudent']")).click();
					Thread.sleep(SLEEP_DURATION);
					
					// enter email and name click Add button					
					driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_STUDENT_EMAIL);
					driver.findElement(By.xpath("//input[@name='name']")).sendKeys(TEST_STUDENT_NAME);
					driver.findElement(By.xpath("//button[@id='Add']")).click();
					Thread.sleep(SLEEP_DURATION);
					
					String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
					System.out.println("********************"+toast_text);
					assertEquals("emailID is already exists.",toast_text);
					Thread.sleep(SLEEP_DURATION);
					
				}catch (Exception ex) {
					throw ex;
				} finally {
					// clean up database 
					Student s = studentRepository.findByEmail(TEST_STUDENT_EMAIL);;
					if (s != null)
						studentRepository.delete(s);
					driver.quit();
				}
	}
}
