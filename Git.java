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

    public static void deleteDirectory(File file)
    {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }

    public static void rmRepo() {
        File git = new File("git");
        if(git.exists()) {
            deleteDirectory(git);
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
        FileWriter writer = new FileWriter("git/index", true);
        writer.write("\n" + hashText + " " + fileName);
        writer.close();
    }

    public static void createBlob(String fileName) throws IOException, NoSuchAlgorithmException {
        FileReader reader = new FileReader(fileName);
        String content = "";
        while(reader.ready()) {
            content += (char)reader.read();
        }
        reader.close();
        String hashed = hash(content);
        File blob = new File("git/objects", hashed);
        if(blob.exists()) {
            return;
        }
        blob.createNewFile();
        FileWriter writer = new FileWriter("git/objects/" + hashed, true);
        writer.write(content);
        addIndex(fileName, hashed);
        writer.close();
    }

    public static void resetTest() {

    }

}
