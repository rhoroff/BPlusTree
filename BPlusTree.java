import java.util.*;
import java.util.regex.*;
import java.io.*;

public class bplustree {
    public static void main(String[] args) {
        String inputFile = null;
        if (args.length > 0) {
            try {
                inputFile = args[0];
                InputStream is = new FileInputStream(inputFile);
                Scanner bPlusTreeScanner = new Scanner(is);
                String initializerLine = bPlusTreeScanner.next();
                if (!initializerLine.contains("Initialize") && !initializerLine.contains("initialize")) {
                    System.out.println(
                            "The input file does not start with an initialization command \n Please pass in a file that contains an initialization command as the first line ");
                            bPlusTreeScanner.close();
                    return;
                } else {
                    Pattern intializerRegex = Pattern.compile("(?!Initialize\\()[0-9](?=\\))");
                    Matcher m = intializerRegex.matcher(initializerLine);
                    if (!m.find()) {
                        System.out.println("The first line of input.txt was not tree initialization. ");
                    } else {
                        int size = Integer.parseInt(m.group());
                        BPlusTreeContainer tree = new BPlusTreeContainer();
                        tree = tree.initialize(size);
                        System.out.println("The degree of the initialzed tree is " + size);
                        while (bPlusTreeScanner.hasNextLine()) {
                            String line = bPlusTreeScanner.nextLine();
                            if (line.contains("Insert")) {
                                Pattern insertRegex = Pattern
                                        .compile("(?!Insert\\()(-)?[0-9]+,( )?(-)?[0-9]+(.[0-9]+)?(?=\\))");
                                Matcher insertMatcher = insertRegex.matcher(line);
                                if (!insertMatcher.find()) {
                                    System.out.println("Malformed input command");
                                } else {
                                    String[] arguments = insertMatcher.group().split(",");
                                    if (arguments.length != 2) {
                                        System.out.println("Too many arguments passed into insert command");
                                    } else {
                                        int key = Integer.parseInt(arguments[0]);
                                        double value = Double.parseDouble(arguments[1]);
                                        tree.insert(key, value);
                                    }
                                }
                            }
                        }
                        System.out.println("Stop here for debugging to see what the tree looks like");
                    }
                }
                bPlusTreeScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Input file was malformed or not found");
            }

        }

    }
}