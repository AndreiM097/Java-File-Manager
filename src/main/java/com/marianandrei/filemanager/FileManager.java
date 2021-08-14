package com.marianandrei.filemanager;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;


public class FileManager {
    private String fromPath;
    private String toPath;
    private ArrayList<String> programmingLanguagesArray;
    private ArrayList<String> coursesArray;
    private ArrayList<String> executableFileFormatsArray;
    private ArrayList<String> imageFileFormatsArray;
    private ArrayList<String> videoFileFormatsArray;
    private ArrayList<String> audioFileFormatsArray;
    private ArrayList<String> archiveFileFormatsArray;
    private ArrayList<String> documentFileFormatsArray;

    public void manageFiles(String currentPathToManage, String newManagedPath){

        File folder = new File(currentPathToManage);
        File[] listOfFiles = folder.listFiles();
        
        folder.listFiles();

        for (int i = 0 ; i < listOfFiles.length; i++){
            System.out.println(i + ": " + listOfFiles[i]);
        }

        for (File file : listOfFiles) {
            fromPath = currentPathToManage + "\\" +file.getName();
            toPath = newManagedPath + "\\" + FilenameUtils.getExtension(currentPathToManage + "/" +file.getName()) + "\\" + file.getName();

//            if (file.isFile()) {
                checkAndMoveFiles(file, currentPathToManage, newManagedPath);

                System.out.println("======================================");
//            }
        }
    }

    public void createNewFolder(String path){

        File file = new File(path);

        boolean bool = file.mkdir();  // returns true if and only if the directory was created; false otherwise
        if(bool){
            System.out.println("Folder created successfully, path : " + path);
        }
        else{
            System.out.println("There has been an error while trying to create the folder.Maybe the folder already exists.");
        }

    }

    public void checkAndMoveFiles(File file, String currentPathToManage, String newManagedPath) {
        setProgrammingLanguagesArray();
        setCoursesArray();
        setExecutableFileFormats();
        setImageFileFormats();
        setVideoFileFormats();
        setAudioFileFormats();
        setArchiveFileFormats();
        setDocumentFileFormats();

        ArrayList<ArrayList> formats = new ArrayList<>();
        formats.add(programmingLanguagesArray);
        formats.add(coursesArray);
        formats.add(executableFileFormatsArray);
        formats.add(imageFileFormatsArray);
        formats.add(videoFileFormatsArray);
        formats.add(audioFileFormatsArray);
        formats.add(archiveFileFormatsArray);
        formats.add(documentFileFormatsArray);

        String[] folderNames = {"Programming", "Courses", "Applications", "Images", "Videos", "Music", "Archives", "Documents"};

        //If the first the method fails,the program will proceed to the next method.If it succeeded,firstMethodFailed will become true and the programm will exit this method.
        Boolean firstMethodFailed = true;
        Boolean secondMethodFailed = true;

        //First method
        for (int i = 0; i < formats.size(); i++) {
            //First we check if the file has a programming language in it's name, regardless of file type( file / directory ).
            for (int j = 0; j < formats.get(i).size(); j++) {

                //Checking if there is a programming language name present in the name of the file
                if (checkIfContainsString(formats.get(i).get(j).toString().toLowerCase(), file.getName().toLowerCase())) {

                    if(file.isFile()) {
                        createNewFolder(currentPathToManage + "\\" + formats.get(i).get(j).toString());
                    }
                    else{
                        createNewFolder(newManagedPath + "\\Courses");
                        createNewFolder(newManagedPath + "\\Courses\\" + formats.get(i).get(j).toString());
                    }

                    System.out.println("File name:     " + file.getName());
                    if(file.isFile()){
                        System.out.println("Extension:     " + FilenameUtils.getExtension(currentPathToManage + "/" + file.getName()));
                    }
                    System.out.println("Absolute Path: " + currentPathToManage + "\\" + file.getName());
                    if(file.isFile()){
                        System.out.println("Moving to :    " + newManagedPath + "\\" + formats.get(i).get(j).toString() + "\\" + file.getName() + "\n");
                    }
                    else{
                        System.out.println("Moving to :    " + newManagedPath + "\\" + formats.get(i).get(j).toString() + "\\" + file.getName());
                    }

                    if(file.isFile()) {
                        //Moving files...
                        try {
                            Files.move(Paths.get(fromPath), Paths.get(newManagedPath + "\\" + formats.get(i).get(j).toString() + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                            if (Files.exists(Path.of(newManagedPath + "\\" + formats.get(i).get(j).toString() + "\\" + file.getName()))) {
                                System.out.println("File moved successfuly!");
                                firstMethodFailed = false;
                                return;
                            } else {
                                if (Files.exists(Path.of(file.getAbsolutePath()))) {
                                    System.out.println("The file has not been moved.");
                                }
                            }

                        } catch (NoSuchFileException noSuchFileException) {
                            System.out.println("No such file found: " + file.getName());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else{
                        try {
                            Files.move(Paths.get(fromPath), Paths.get(newManagedPath + "\\Courses\\" + formats.get(i).get(j).toString() + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                            if (Files.exists(Path.of(newManagedPath + "\\Courses\\" + formats.get(i).get(j).toString() + "\\" + file.getName()))) {
                                System.out.println("File moved successfuly!");
                                firstMethodFailed = false;
                                return;
                            } else {
                                if (Files.exists(Path.of(file.getAbsolutePath()))) {
                                    System.out.println("The file has not been moved.");
                                }
                            }

                        } catch (NoSuchFileException noSuchFileException) {
                            System.out.println("No such file found: " + file.getName());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        }

        //Second method
        if (firstMethodFailed && file.isFile()) {
            for (int i = 0; i < formats.size(); i++) {
                for (int j = 0; j < formats.get(i).size(); j++) {
                    //TODO : checking if the file extension matches any of the extensions found in the arrays...
                    if (formats.get(i).get(j).toString().toLowerCase().contains(FilenameUtils.getExtension(currentPathToManage + "/" + file.getName()).toLowerCase())) {
                        createNewFolder(currentPathToManage + "\\" + folderNames[i]);

                        System.out.println("File name:     " + file.getName());
                        System.out.println("Extension:     " + FilenameUtils.getExtension(currentPathToManage + "/" + file.getName()));
                        System.out.println("Absolute Path: " + currentPathToManage + "\\" + file.getName());
                        System.out.println("Moving to :    " + currentPathToManage + "\\" + folderNames[i] + "\\" + file.getName() + "\n");

                        //Moving files...
                        try {
                            Files.move(Paths.get(fromPath), Paths.get(newManagedPath + "\\" + folderNames[i] + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING);

                            System.out.println("File moved successfuly!");

                            if (Files.exists(Path.of(newManagedPath + "\\" + folderNames[i] + "\\" + file.getName()))) {
                                System.out.println("File moved successfuly!");
                                secondMethodFailed = false;
                                return;
                            } else {
                                if (Files.exists(Path.of(file.getAbsolutePath()))) {
                                    System.out.println("The file has not been moved.");
                                }
                            }

                        } catch (NoSuchFileException noSuchFileException) {
                            System.out.println("No such file found: " + file.getName());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        }

//      If none of the previous methods worked,the program will create the folders with the name of the file extension and it will move the files inside them and all of this will happen in a "RANDOM" folder
        if( firstMethodFailed && secondMethodFailed && file.isFile()) {
            for (int i = 0; i < formats.size(); i++) {


                System.out.println("File name:     " + file.getName());
                System.out.println("Extension:     " + FilenameUtils.getExtension(currentPathToManage + "/" +file.getName()));

                //Creating the "Random files" folder
                if(Files.exists(Path.of(newManagedPath + "\\Random files"))){
                    System.out.println("'Random files' folder already exists.Proceeding...");
                }
                else{
                    createNewFolder(newManagedPath + "\\Random files");
                }

                String newFolder = newManagedPath + "\\Random files\\" + FilenameUtils.getExtension(currentPathToManage + "/" +file.getName());

                if(Files.exists(Path.of(newFolder))){
                    System.out.println("A folder with the same name already exists and will be used.Proceeding...");
                }
                else{
                    createNewFolder(newFolder);
                }

                //TODO: CREATE FOLDERS WITH THE EXTENSIONS OF THE FILES AS NAMES AND IMPROVE CODE( ASSIGN THE PATHS TO VARIABLES ).Folders have to be created before trying to move files in order to be able to move them.
                try {
                    Files.move(Paths.get(currentPathToManage + "\\" +file.getName()),Paths.get( newManagedPath + "\\Random files\\" + FilenameUtils.getExtension(currentPathToManage + "/" +file.getName()) + "\\" + file.getName()), StandardCopyOption.REPLACE_EXISTING );
                    System.out.println("File moved successfully, third method used!");
                    return;
                } catch(NoSuchFileException noSuchFileException){
                    System.out.println("No such file found: " + file.getName());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                System.out.println("======================================");
            }
            }
        }

    public boolean checkIfContainsString(String wordToFind, String inSentence) {
        String[] words = inSentence.split("\\.| |-|_|,");
        Boolean bool = false;

        for ( int i = 0; i < words.length; i++){
            if(words[i].equals(wordToFind)){
                System.out.println("'" + wordToFind + "'" + " and " + words[i] + " matching: " + words[i].equals(wordToFind));
                bool = true;
                break;
            }else if( i > 0 && (words[i-1] + " " +words[i]).equals(wordToFind)){
                System.out.println("'" + wordToFind + "'" + " and " + words[i-1] + " " +words[i] + " matching: " + (words[i-1] + " " +words[i]).equals(wordToFind));
                bool = true;
                break;
            }
        }
        if(bool){
            return true;
        }
        else {
            return false;
        }

//        for (String token : words) {
//            if (token.equals(wordToFind)) {
//                System.out.println("'" + wordToFind + "'" + " and " + token + " matching: " + token.equals(wordToFind));
//                bool = true;
//                break;
//            }
//        }
//        if(bool){
//            return true;
//        }
//        else {
//            return false;
//        }
    }

    //**This file will also contain stuff related to programming like databases,frameworks,etc.**
    public void setProgrammingLanguagesArray(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("programming.languages.txt");
        Scanner myReader = new Scanner(file);
        programmingLanguagesArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            programmingLanguagesArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setCoursesArray(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("courses.txt");
        Scanner myReader = new Scanner(file);
        coursesArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            coursesArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setExecutableFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream textFile = classloader.getResourceAsStream("executable.file.formats.txt");
        Scanner myReader = new Scanner(textFile);
        executableFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            executableFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setImageFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("image.file.formats.txt");
        Scanner myReader = new Scanner(file);
        imageFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            imageFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setVideoFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("video.file.formats.txt");
        Scanner myReader = new Scanner(file);
        videoFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            videoFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setAudioFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("audio.file.formats.txt");
        Scanner myReader = new Scanner(file);
        audioFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            audioFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setArchiveFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("archives.file.formats.txt");
        Scanner myReader = new Scanner(file);
        archiveFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            archiveFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }

    public void setDocumentFileFormats(){
        int i = 0;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("document.file.formats.txt");
        Scanner myReader = new Scanner(file);
        documentFileFormatsArray = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            documentFileFormatsArray.add(data);
            i++;
        }
        myReader.close();
    }
}
