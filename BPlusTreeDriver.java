import java.util.*;
import java.util.regex.*;
import java.io.*;

public class BPlusTreeDriver {
    public static void main(String[] args) {
        String inputFile = null;
        if (args.length > 0) {
            try{
                inputFile = args[0];
                InputStream is = new FileInputStream(inputFile);
                Scanner bPlusTreeScanner = new Scanner(is);
                String initializerLine = bPlusTreeScanner.next();
                if (!initializerLine.contains("Initialize") && !initializerLine.contains("initialize")) {
                    System.out.println(
                            "The input file does not start with an initialization command \n Please pass in a file that contains an initialization command as the first line ");
                } else {
                    Pattern intializerRegex = Pattern.compile("(?!Initialize\\()[0-9](?=\\))");
                    Matcher m = intializerRegex.matcher(initializerLine);
                    if(!m.find()){
                        System.out.println("The first line of input.txt was not tree initialization. ");
                    }else{
                        int size = Integer.parseInt(m.group());
                        BPlusTree tree = new BPlusTree();
                        tree = tree.initialize(size);
                        System.out.println("The degree of the initialzed tree is " + size);
                    }
                }
                while (bPlusTreeScanner.hasNext()) {
                    String line = bPlusTreeScanner.next();
                }
            }catch(FileNotFoundException e){
                System.out.println("Input file was malformed or not found");
            }


        }

    }
}