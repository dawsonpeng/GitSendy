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
        String[] brokenDownPathA = filePath.split("/");
        String[] brokenDownPathB = otherIndexEntry.filePath.split("/");
        if (brokenDownPathA.length == brokenDownPathB.length)
            return Integer.compare(brokenDownPathA.length - 1, brokenDownPathB.length - 1);
        for (int i = 0; i < brokenDownPathA.length; i++) {
            if (!brokenDownPathA[i].equals(brokenDownPathB[i]))
                return brokenDownPathA[i].compareTo(brokenDownPathB[i]);
        }
        return 0;
    }
}
