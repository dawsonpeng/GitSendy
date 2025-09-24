import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Git {
    static boolean compression = false;

    public static void compression(boolean e) {
        compression = e;
        System.out.println("compression = " + e);
    }

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
        if(file == null) {
            return;
        }
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

    public static String hash(String text) throws NoSuchAlgorithmException { //got online, https://www.geeksforgeeks.org/java/sha-1-hash-in-java/ 
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
    File index = new File("git/index");
    FileWriter writer = new FileWriter(index, true);
    if (index.length() == 0) {
        writer.write(hashText + " " + fileName);
    } else {
        writer.write("\n" + hashText + " " + fileName);
    }
    writer.close();
    }

    public static String getContent(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        StringBuilder content = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            content.append((char) ch);
        }
        reader.close();
        return content.toString();
    }

    private static void zipFile(String filePath) { //got from online, https://www.codejava.net/java-se/file-io/how-to-compress-files-in-zip-format-in-java
        try {
            File file = new File(filePath);
            String zipFileName = file.getName().concat(".zip");
 
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
 
            zos.putNextEntry(new ZipEntry(file.getName()));
 
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
            zos.close();
 
        } catch (FileNotFoundException ex) {
            System.err.format("The file %s does not exist", filePath);
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
    }

    public static void createBlob(String fileName) throws IOException, NoSuchAlgorithmException {
        String content;
        if(compression) {
            zipFile(fileName);
            content = getContent(fileName + ".zip");
        } else content = getContent(fileName);
        String hashed = hash(content);
        File blob = new File("git/objects", hashed);
        if (!blob.exists()) {
            blob.createNewFile();
            FileWriter writer = new FileWriter(blob);
            writer.write(content);
            writer.close();
        }
        addIndex(fileName, hashed);
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

    public static void checkBlobExists(String fileName) throws IOException, NoSuchAlgorithmException {
        String hashedFile;
        if(compression) {
            hashedFile = hash(getContent(fileName + ".zip"));
        } else hashedFile = hash(getContent(fileName));
        File blob = new File("git/objects/" + hashedFile);
        if(blob.exists()) {
            String indexContents = Files.readString(Path.of("git/index"));
            if (indexContents.contains(fileName)) {
                System.out.println("blob exists - good job");
                return;
            }
        }
        System.out.println("blob doesnt exist lol");
    }

}
