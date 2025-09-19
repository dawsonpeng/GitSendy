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
        File HEAD = new File("git", "HEAD");

        if(git.exists() && objects.exists() && index.exists()) {
            System.out.println("git repo already exists dawg 🥀");
            return;
        }
        if(!git.exists()) {git.mkdir();}
        if(!objects.exists()) {objects.mkdir();}
        if(!index.exists()) {index.createNewFile();}
        if(!HEAD.exists()) {HEAD.createNewFile();}
        System.out.println("git repo successfully made :o");
    }

    public static void deleteDir(File file)
    {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDir(subfile);
            }
            subfile.delete();
        }
    }

    public static void rmRepo() {
        File git = new File("git");
        if(git.exists()) {
            deleteDir(git);
            git.delete();
            System.out.println("git repo successfully deleted :o");
        } else System.out.println("git repo doesnt exist- take some schizo meds");
    }

    public static void resetRepo() throws IOException {
        rmRepo();
        initRepo();
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
        FileReader reader = new FileReader("git/index");
        if(!reader.ready()) {
            writer.write(hashText + " " + fileName);
        } else writer.write("\n" + hashText + " " + fileName);
        reader.close();
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
        System.out.println("BLOB file succesfully created yay");
    }

    public static void createTestFiles() throws IOException {
        File f1 = new File("f1.txt");
        File f2 = new File("f2.txt");
        File f3 = new File("f3.txt");
        File f4 = new File("f4.txt");  
        File f5 = new File("f5.txt");
        f1.createNewFile();
        f2.createNewFile();
        f3.createNewFile();
        f4.createNewFile();
        f5.createNewFile();
        FileWriter writer1 = new FileWriter(f1);
        writer1.write("67");
        writer1.close();
        FileWriter writer2 = new FileWriter(f2);
        writer2.write("hellobodorpbadinga");
        writer2.close();
        FileWriter writer3 = new FileWriter(f3);
        writer3.write("finormanascheiss");
        writer3.close();
        FileWriter writer4 = new FileWriter(f4);
        writer4.write("bazingagahook");
        writer4.close();
        FileWriter writer5 = new FileWriter(f5);
        writer5.write("sam and dawson is awesome(amazing grammar)");
        writer5.close();
    }

}
