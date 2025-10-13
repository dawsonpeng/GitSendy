# GitSendy

Git is the overarching class for all the functions
use Git.method() -all methods are static

initRepo()- initializes git folder with objects index and HEAD

deleteDir(File file)- deletes the file/directory

rmRepo()- removes git folder with objects index and HEAD

resetRepo()= deletes and recreates empty repo files, effective for testing

hash(String input)- hashes the input- got online

getContent(String filePath)- gets the content of the file

addIndex(String filePath, String hashText)- writes the parameters in the index file

createBlob(String filePath)- creates blob file, copies file, hashes contents, create new file in objects, writes the file into index

createTestFiles()- creates 5 test files with things inside them

deleteTestFiles()- deletes everything in testFiles folder

checkBlobExists(String filePath)- checks if blob exists in objects and index

compression(boolean e)- enables/disables compression



IndexEntry class stores all the info for a particular file / tree (hash + path) -> allows use of priority queue (can be initialized w/ either just a line from the index file -> always a blob or from a specified hash and path -> assumed tree bc items added back in recursively are always trees)

addIndex(String filePath) -> added this method (same as above but diff params) to break apart the commit function bc it now needs to be done seperately

BLOBFile(IndexEntry file) -> same thing done separately now (returns hash for convience)

createTree(ArrayList<IndexEntry> files) -> builds a tree from all the index entries in that subfolder returns hash for convience

buildTree() -> accesses index file and converts lines to priority queue of index entries. loops through, when the end of a subfolder is reached it builds that mini tree and then adds it to the working list, loops through until there are no more subfolders (you are at the root)

Commit functionality:

What bugs did you discover, and which ones did you fix? What functionality was missing? Did you implement any missing functionality?
I added some extra logic to return the hash of the root tree so a commit could be made.

