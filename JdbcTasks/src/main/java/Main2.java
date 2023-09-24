import java.util.concurrent.ThreadLocalRandom;

public class Main2 {
    public static void main(String[] args) {
        String branch = "rel-adserving-dsb-logs-1.1";
        String[] version = branch.split("rel-");
        System.out.println(version);
    }

}
