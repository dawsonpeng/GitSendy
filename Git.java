import java.io.BufferedReader;
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
import java.util.Comparator;
import java.util.PriorityQueue;
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
        file.delete();
    }

    public static void rmRepo() {
        File git = new File("git");
        if(git.exists()) {
            deleteDir(git);
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

    public static void addIndex(String filePath, String hashText) throws IOException {
    File index = new File("git/index");
    FileWriter writer = new FileWriter(index, true);
    if (index.length() == 0) {
        writer.write(hashText + " " + filePath);
    } else {
        writer.write("\n" + hashText + " " + filePath);
    }
    writer.close();
    }

    public static String getContent(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
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

    public static void commitFile(String filePath) throws IOException, NoSuchAlgorithmException {
        String content;
        if(compression) {
            zipFile(filePath);
            content = getContent(filePath + ".zip");
        } else content = getContent(filePath);
        String hashed = hash(content);
        File blob = new File("git/objects", hashed);
        if (!blob.exists()) {
            blob.createNewFile();
            FileWriter writer = new FileWriter(blob);
            writer.write(content);
            writer.close();
        }
        addIndex(filePath, hashed);
        System.out.println("BLOB file succesfully created yay");
    }

    public static void createTestFiles() throws IOException {
        File testFolder = new File("testFiles");
        testFolder.mkdir();
        File f1 = new File("testFiles/f1.txt");
        File f2 = new File("testFiles/f2.txt");
        File f3 = new File("testFiles/f3.txt");
        File f4 = new File("testFiles/f4.txt");
        File f5 = new File("testFiles/f5.txt");
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

    public static void deleteTestFiles() {
        File testFiles = new File("testFiles");
        deleteDir(testFiles);
    }

    public static void checkBlobExists(String filePath) throws IOException, NoSuchAlgorithmException {
        String hashedFile;
        if (compression) {
            hashedFile = hash(getContent(filePath + ".zip"));
        } else
            hashedFile = hash(getContent(filePath));
        File blob = new File("git/objects/" + hashedFile);
        if (blob.exists()) {
            String indexContents = Files.readString(Path.of("git/index"));
            if (indexContents.contains(filePath)) {
                System.out.println("blob exists - good job");
                return;
            }
        }
        System.out.println("blob doesnt exist lol");
    }
    
    public static void createBasicTree(String hash, String filePath) {
        StringBuilder treeContents = new StringBuilder("");
        treeContents.append("BLOB " + hash + " " + filePath.substring(filePath.indexOf("/") + 1));
        try {
            File tree = new File(hash(treeContents.toString()));
            tree.createNewFile();
            FileWriter writer = new FileWriter(tree);
            writer.write(treeContents.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createWorkingList() {
        PriorityQueue<IndexEntry> workingList = new PriorityQueue<IndexEntry>();
        try (BufferedReader br = new BufferedReader(new FileReader("git/index"))) {
            String line;
            while ((line = br.readLine()) != null) {
                workingList.add(new IndexEntry(line));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
