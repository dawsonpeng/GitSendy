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

commitFile(String filePath)- creates blob file, copies file, hashes contents, create new file in objects, writes the file into index

createTestFiles()- creates 5 test files with things inside them

deleteTestFiles()- deletes everything in testFiles folder

checkBlobExists(String filePath)- checks if blob exists in objects and index

compression(boolean e)- enables/disables compression
