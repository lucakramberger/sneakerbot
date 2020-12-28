public class main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");

        Profile profile = new Profile("spl3shy@gmail.com","!Freddy1234!");

        Task task = new Task("https://www.nike.com/at/launch/t/air-jordan-11-adapt-eu", "178.115.231.163","8080", "11", profile);
        task.start();
    }
}
