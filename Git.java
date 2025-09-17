import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Git {
    public static void initRepo() throws IOException {
        File git = new File("git");
        File objects = new File("git", "objects");
        File index = new File("git", "index");

        if(!git.exists()) {
            git.mkdir();
        } else System.out.println("git already exists dawg🥀");

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
        String hashtext = no.toString(16);
        while (hashtext.length() < 40) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
