import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Task extends Thread{
    String link;
    Proxy proxy;
    String size;
    WebDriver driver;
    ChromeOptions chromeOptions;

    Task(){

    }

    Task(String link, String ip, String port, String size){
        this.link = link;
        this.proxy = new Proxy();
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
            this.driver.get(this.link);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
