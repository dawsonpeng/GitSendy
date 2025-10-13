import java.io.File;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class GitTester {

    public static void main(String args[]) {
        try {
            File myProgram = new File("myProgram/inner");
            myProgram.mkdirs();
            String helloContents = "Hello";
            Files.write(Paths.get("myProgram/hello.txt"), helloContents.getBytes());
            String worldContents = "World!";
            Files.write(Paths.get("myProgram/inner/world.txt"), worldContents.getBytes());
        } catch (Exception e) {
            System.out.println("Failed to make files!");
            e.printStackTrace();
        }

		GitWrapper gw = new GitWrapper();
        gw.add("myProgram/hello.txt");
        gw.add("myProgram/inner/world.txt");
        String hash = gw.commit("John Doe", "Initial commit");
        try {
            Files.walk(Paths.get("myProgram"))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (Exception e) {
            System.out.println("Failed to delete files!");
            e.printStackTrace();
        }
        gw.checkout(hash);
    }
    
}