# GitSendy

Git is the overarching class for all the functions
use Git.method() -all methods are static

initRepo()- initializes git folder with objects index and HEAD

deleteDir(File file)- deletes the objects in the file/directory- got online

rmRepo()- removes git folder with objects index and HEAD
<<<<<<< HEAD
resetRepo()= deletes and recreates empty repo files
=======

>>>>>>> 9b9d646 (checkblobexists() created, getContents() created)
hash(String input)- hashes the input- got online

getContent(String filename)- gets the content of the file

addIndex(String fileName, String hashText)- writes the parameters in the index file

createBlob(String fileName)- creates blob file, copies file, hashes contents, create new file in objects, writes the file into index
<<<<<<< HEAD
createTestFiles()- creates 5 test files with stuff in them
=======

createTestFiles()- creates 5 test files with things inside them

checkBlobExists(String filename)- checks if blob exists in objects and index
>>>>>>> 9b9d646 (checkblobexists() created, getContents() created)
