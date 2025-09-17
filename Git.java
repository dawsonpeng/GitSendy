import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Git {
    public static void initRepo() throws IOException {
        File git = new File("git");
        File objects = new File("git", "objects");
        File index = new File("git", "index");

        if(git.exists() && objects.exists() && index.exists()) {
            System.out.println("Git repo already exists dawg 🥀");
            return;
        }

        if(!git.exists()) {
            git.mkdir();
        }

        if(!objects.exists()) {
            objects.mkdir();
        }

        if(!index.exists()) {
            index.createNewFile();
        }
    }

    public static void rmRepo() {
        File objects = new File("git", "objects");
        File index = new File("git", "index");
        File git = new File("git");

        if(objects.exists()) {
            objects.delete();
        }

        if(index.exists()) {
            index.delete();
        }

        if(git.exists()) {
            git.delete();
        }
    }

    public static String hash(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(text.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashText = no.toString(16);
        while (hashText.length() < 40) {
            hashText = "0" + hashText;
        }
        return hashText;
    }

    public static void addIndex(String fileName, String hashText) throws IOException {
        FileWriter writer = new FileWriter("index");
        writer.write("\n" + hashText + " " + fileName);
    }

    public static void createBlob(String fileName) throws IOException, NoSuchAlgorithmException {
        FileReader reader = new FileReader(fileName);
        String content = "";
        while(reader.ready()) {
            content += reader.read();
        }
        String hashed = hash(content);
        File blob = new File("objects", "hashed");
        FileWriter writer = new FileWriter("hashed");
        writer.write(content);
        addIndex(fileName, hashed);
    }

}
