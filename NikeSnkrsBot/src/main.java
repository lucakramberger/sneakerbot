public class main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
        Task task = new Task("https://www.nike.com/at/launch/t/kobe-6-protro-green-apple", "178.115.231.163","8080", "45");
        task.start();
    }
}
