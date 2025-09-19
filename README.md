# GitSendy

Git is the overarching class for all the functions
use Git.method() -all methods are static

initRepo()- initializes git folder with objects index and HEAD
deleteDir(File file)- deletes the objects in the file/directory- got online
rmRepo()- removes git folder with objects index and HEAD
hash(String input)- hashes the input- got online
addIndex(String fileName, String hashText)- writes the parameters in the index file
createBlob(String fileName)- creates blob file, copies file, hashes contents, create new file in objects, writes the file into index
<<<<<<< HEAD
createTestFiles()- creates 5 test files with things inside them
=======
>>>>>>> origin/main
