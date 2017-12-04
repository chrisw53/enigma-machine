import java.io.*;
import java.util.ArrayList;


public class FilesIO {
    /**
     * This method reads text from file line by line, and output them
     * into an string array list.
     * @param fileName This is the file path for the input file
     * @return A string array list where each element is a line of text
     * from the input file.
     */
    protected static ArrayList<String> readFile(String fileName) {
        ArrayList<String> myMsgs = new ArrayList<>();
        try {
            BufferedReader getMsg = new BufferedReader(new FileReader(fileName));
            String msg = getMsg.readLine();

            /*
            This while loop continues to add lines of text into the
            array list until every line is read.
              */
            while (msg != null) {
                myMsgs.add(msg);
                msg = getMsg.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find input file");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error");
            System.exit(0);
        }

        return myMsgs;
    }

    /**
     * This method handles creating a new enigma machine and encoding
     * the array list of strings.
     * @param inputs An array list of strings to be encoded
     * @return A string containing the encoded message
     */
    protected static String encode(ArrayList<String> inputs) {
        EnigmaMachine myEnigmaMachine = new EnigmaMachine();

        myEnigmaMachine.myPlugboard.addPlug('A', 'M');
        myEnigmaMachine.myPlugboard.addPlug('G', 'L');
        myEnigmaMachine.myPlugboard.addPlug('E', 'T');

        BasicRotor rotor1 = new BasicRotor("I");
        rotor1.setPosition(6);
        BasicRotor rotor2 = new BasicRotor("II");
        rotor2.setPosition(12);
        BasicRotor rotor3 = new BasicRotor("III");
        rotor3.setPosition(5);

        myEnigmaMachine.addRotor(rotor1, 0);
        myEnigmaMachine.addRotor(rotor2, 1);
        myEnigmaMachine.addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        myEnigmaMachine.addReflector(myReflector);

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < inputs.size(); i++) {
            char charArray[] = inputs.get(i).toCharArray();
            if (i > 0) {
                output.append("\n");
            }

            for(char letter: charArray) {
                if (letter >= 65 && letter <= 90) {
                    output.append(myEnigmaMachine.encodeLetter(letter));
                }
            }
        }

        return output.toString();
    }

    /**
     * This writes the input string to the file path specified in the
     * parameter.
     * @param content A string with content to be written to.
     * @param fileName A string with the file path to the file to be
     *                 written to.
     */
    protected static void writeFile(String content, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    public static void main(String[] args) {
        writeFile(encode(readFile("unsecured.txt")), "secured.txt");
    }
}
