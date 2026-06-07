/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package githubactionfindandreplace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//java -jar .\GitHubActionFindAndReplace.jar "C:\\Users\\Lukas\\OneDrive\\OneDrive - University of Guelph\\Documents\\ICS\\donau.ca\\www" ".html\" data-remove-file-extension=\"true\"" "\" data-remove-file-extension=\"true\""
/**
 *
 * @author Lukas
 */
public class GitHubActionFindAndReplace {

    public static ArrayList<File> filesToSearch = new ArrayList<>();

    public static String fileTypeForUseInMethod;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Check if there are 3 command line args
        /*
         * System.out.println("Arg [0]: " + args[0]);
         * System.out.println("Arg [1]: " + args[1]);
         * System.out.println("Arg [2]: " + args[2]);
         */
        if (args.length == 3) {
            // example of the program being run from a windows system:
            // java -jar .\GitHubActionFindAndReplace.jar
            // "C:\\Users\\Lukas\\OneDrive\\OneDrive - University of
            // Guelph\\Documents\\ICS\\donau.ca\\www" ".html\"
            // data-remove-file-extension=\"true\"" "\" data-remove-file-extension=\"true\""
            String rootDirString = args[0];
            String findString = args[1];
            String replaceString = args[2];
            String[] fileExtensions = new String[]{"html"};

            File rootDirFile = new File(rootDirString);

            findAllFiles(fileExtensions, rootDirFile);

            try {
                searchAllFiles(findString);

                // System.out.println("Files to repalce in:");
                for (File file : filesToSearch) {

                    // System.out.println(file.getName());
                    findAndReplaceFile(file, findString, replaceString);
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Not able to search the files:\n" + ex);
            }
        } else {
            System.out.println(
                    "Error: You must run the program from the command line and use include the 3 required command line arguments.");
        }

    }

    public static void findAndReplaceFile(File file, String findString, String replaceString) {
        StringBuilder oldContentBuilder = new StringBuilder();
        String contentOld;
        String newContent;

        BufferedReader reader = null;

        FileWriter writer = null;

        String line;

        try {
            reader = new BufferedReader(new FileReader(file));

            // Reading all the lines of input text file into oldContent
            line = reader.readLine();

            while (line != null) {
                oldContentBuilder.append(line);
                oldContentBuilder.append(System.lineSeparator());

                line = reader.readLine();
            }

            // Replacing oldString with newString in the oldContent
            contentOld = oldContentBuilder.toString();
            newContent = contentOld.replaceAll(findString, replaceString);

            // Rewriting the input text file with newContent
            writer = new FileWriter(file);

            writer.write(newContent);
            System.out.println("Successful findAndReplaceFile() on " + file.toString());
        } catch (IOException e) {
            System.err.println("Error: \n" + e);
        } finally {
            try {
                // Closing the resources

                reader.close();

                writer.close();
            } catch (IOException e) {
                System.err.println("Error: \n" + e);
            }
        }
    }

    public static void searchAllFiles(String find) throws FileNotFoundException {

        Scanner scanner;
        String line;
        boolean hit;
        ArrayList<File> retainList = new ArrayList<>();

        for (File file : filesToSearch) {
            // reset the hit
            hit = false;
            scanner = new Scanner(file);

            while (!hit && scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.contains(find)) {
                    // System.out.println("Found:\t\t" + line + "\nFile: " +
                    // file.getAbsolutePath());
                    hit = true;
                    retainList.add(file);
                }

            }
        }

        filesToSearch.retainAll(retainList);

    }

    public static void findAllFiles(String[] validExtensions, File givenFile) {

        // base case
        if (!givenFile.isDirectory()) { // if not a folder

            // grab file type
            fileTypeForUseInMethod = givenFile.getName().split("\\.", 0)[1];
            // check if type is good
            for (String str : validExtensions) {

                if (fileTypeForUseInMethod.equals(str)) {
                    // add it to the list
                    filesToSearch.add(givenFile);
                }
            }

        } else { // grab all the sub files in the directorie
            File[] currentSubFiles = givenFile.listFiles();

            for (File file : currentSubFiles) {
                findAllFiles(validExtensions, file);
            }
        }

    }

}
