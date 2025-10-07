import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Tester {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Git.createTestFiles();
        // Git.rmRepo();
        // Git.initRepo();
        Git.compression(false);//compression doesnt work rn
        Git.resetRepo();
        
        // Git.commitFile("testFiles/f1.txt");
        // Git.commitFile("testFiles/f2.txt");
        // Git.commitFile("testFiles/f3.txt");
        // Git.commitFile("testFiles/f1.txt");
        // Git.checkBlobExists("testFiles/f1.txt");
        // Git.checkBlobExists("testFiles/f2.txt");
        // Git.checkBlobExists("testFiles/f3.txt");

        // ADD FILES TO INDEX
        Git.addIndex("f0.txt");
        Git.addIndex("f1.txt");
        Git.addIndex("testFiles/f2.txt");
        Git.addIndex("testFiles/f3.txt");
        Git.addIndex("testFiles/testFiles2/f4.txt");
        Git.addIndex("testFiles/testFiles2/f5.txt");

        Git.buildTree();
    }
}