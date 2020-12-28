import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Task extends Thread{
    String link;
    Proxy proxy;
    String size;
    WebDriver driver;
    ChromeOptions chromeOptions;
    String productId;
    Profile profile;

    Task(){

    }
    //<meta data-react-helmet="true" name="branch:deeplink:productId" content="2eaeaf7d-b6ef-5285-b2d5-c2e799f86e0f">
    //https://www.nike.com/at/launch/t/air-jordan-11-adapt-eu?size=11&productId=2eaeaf7d-b6ef-5285-b2d5-c2e799f86e0f
    //https://www.nike.com/at/launch/t/air-jordan-11-adapt-eu?size=11&productId=3f9fce22-e8fd-5e79-94ca-2a0f5e18e141

    //<meta data-react-helmet="true" name="branch:deeplink:productId" content="2eaeaf7d-b6ef-5285-b2d5-c2e799f86e0f">

    Task(String link, String ip, String port, String size, Profile profile){
        this.link = link;
        this.proxy = new Proxy();
        this.profile = profile;
        this.proxy.setHttpProxy("<"+ip+":"+port+">");

        this.size = size;
        this.chromeOptions = new ChromeOptions();
        this.chromeOptions.setCapability("proxy",proxy);
        this.chromeOptions.setHeadless(false);

    }

    @Override
    public void run() {
        try {
            this.driver = new ChromeDriver(this.chromeOptions);
            this.productId = getProductId(this.link);

            String checkoutLink = generateLink(this.link, this.size, this.productId);

            this.driver.get(checkoutLink);
            sleep(1000);

            if (this.driver.findElement(By.name("emailAddress")) != null){
                login(this.driver);
                this.driver.get(checkoutLink);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    private String generateLink(String link, String size, String productId){
        return link+"?size="+size+"&productId="+productId;
    }

    private String getProductId(String websiteLink) throws IOException {
        URL oracle = new URL(websiteLink);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String totalInput = "";
        String inputLine = "";
        while ((inputLine = in.readLine()) != null)
            totalInput += inputLine;
        in.close();

        String[] splitted = totalInput.split("<meta data-react-helmet=\"true\" name=\"branch:deeplink:productId\" content=\"");

        String productId = splitted[1].split("\"/>")[0];
        return productId;
    }

    private void login(WebDriver webdriver){
        WebElement email = webdriver.findElement(By.name("emailAddress"));
        email.sendKeys(this.profile.username);

        WebElement password = webdriver.findElement(By.name("password"));
        password.sendKeys(this.profile.password);


        WebElement submit = webdriver.findElement(By.cssSelector("div[class='nike-unite-submit-button loginSubmit nike-unite-component']"));
        submit.click();
    }
}
