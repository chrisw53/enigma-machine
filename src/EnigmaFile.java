import java.io.*;
import java.util.ArrayList;

public class EnigmaFile {
    private static ArrayList<String> readFile(String fileName) {
        ArrayList<String> myMsgs = new ArrayList<>();
        try {
            BufferedReader getMsg = new BufferedReader(new FileReader(fileName));
            String msg = getMsg.readLine();

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

    private static String encode(ArrayList<String> inputs) {
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

        String output = "";
        for (int i = 0; i < inputs.size(); i++) {
            char charArray[] = inputs.get(i).toCharArray();
            if (i > 0) {
                output += "\n";
            }

            for(char letter: charArray) {
                if (letter >= 65 && letter <= 90) {
                    output += myEnigmaMachine.encodeLetter(letter);
                }
            }
        }

        return output;
    }

    private static void writeFile(String content) {
        try {
            PrintWriter writer = new PrintWriter("secured.txt", "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    public static void main(String[] args) {
        writeFile(encode(readFile("unsecured.txt")));
    }
}
