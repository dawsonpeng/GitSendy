public class IndexEntry implements Comparable<IndexEntry> {

    private String filePath;
    private String hash;
    private boolean isBLOB;

    public IndexEntry(String line) {
        String[] lineContents = line.split(" ");
        this.isBLOB = (lineContents[0]).equals("BLOB");
        this.hash = lineContents[1];
        this.filePath = lineContents[2];
    }

    public int compareTo(IndexEntry otherIndexEntry) {
        return Integer.compare(countSlashes(filePath), countSlashes(otherIndexEntry.filePath));
    }

    public static int countSlashes(String string) {
        if (string.indexOf("/") != -1)
            return 1 + countSlashes(string.substring(string.indexOf("/") + 1));
        return 0;
    }
}
