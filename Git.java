import java.io.File;

public class Git {
    public static void initRepo() {
        File dir = new File("myDirectory");
        if(!dir.exists()) {
            dir.mkdir();
        } else System.out.println("File already exists dawg🥀");
    }
}
