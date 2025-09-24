import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Tester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // Git.createTestFiles();
        // Git.rmRepo();
        // Git.initRepo();
        Git.compression(true);
        Git.resetRepo();
        Git.createBlob("f1.txt");
        Git.createBlob("f2.txt");
        Git.createBlob("f3.txt");
        Git.createBlob("f1.txt");
        Git.checkBlobExists("f1.txt");
        Git.checkBlobExists("f2.txt");
        Git.checkBlobExists("f3.txt");
    }
}