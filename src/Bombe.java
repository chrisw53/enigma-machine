import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Bombe extends EnigmaMachine {
    /**
     * This method handles breaking down the message to be encoded into
     * a char array, encode each character and put them together into a
     * string to output.
     * @param msg The input string to be encoded
     * @return An encoded string.
     */
    private String outputString(String msg) {
        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder();

        for(char letter: charArray) {
            output.append(encodeLetter(letter));
        }

        return output.toString();
    }

    /**
     * This is a bombe test with basic rotors and missing plugs. The bombe
     * cycles through each possibility until one hits the keyword "ANSWER",
     * it then outputs the output along with missing plugs. Lastly, it clears
     * the plugboard to ready it if other tests follow
     *
     * There are quite a few outputs with the keyword answer in them, I think
     * the correct output in this test is DAISYDAISYGIVEMEYOURANSWERDO.
     */
    private void plugTest() {
        char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        String msg = "JBZAQVEBRPEVPUOBXFLCPJQSYFJI";

        BasicRotor rotor1 = new BasicRotor("IV");
        rotor1.setPosition(8);
        BasicRotor rotor2 = new BasicRotor("III");
        rotor2.setPosition(4);
        BasicRotor rotor3 = new BasicRotor("II");
        rotor3.setPosition(21);

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);


        for (char firstPlug : alphabet) {
            myPlugboard.addPlug('D', firstPlug);

            for (char secondPlug : alphabet) {
                myPlugboard.addPlug('S', secondPlug);
                String temp = outputString(msg);
                if (temp.contains("ANSWER")) {
                    System.out.println(temp);
                    System.out.println("The first missing plug is " + firstPlug);
                    System.out.println("The second missing plug is " + secondPlug);
                } else {
                    myPlugboard.clear();
                    myPlugboard.addPlug('D', firstPlug);
                }
            }
        }

        clearPlugboard();
    }

    /**
     * This is a bombe test with missing rotor positions. Similarly to the
     * test above, the bombe cycles through all the possibilities for the rotor
     * position until it hits one with the keyword "ELECTRIC". It then prints
     * out the output as well as the missing positions. Lastly, the plugboard
     * is cleared in case there are any other tests following.
     *
     * The correct output for this test is
     * WELLALWAYSBETOGETHERHOWEVERFARITSEEMSWELLALWAYSBETOGETHERTOGETHERINELECTRICDREAMS
     */
    private void rotorPositionTest() {
        String msg = "AVPBLOGHFRLTFELQEZQINUAXHTJMXDWERTTCHLZTGBFUPORNHZSLGZMJNEINTBSTBPPQFPMLSVKPETWFD";

        myPlugboard.addPlug('H', 'L');
        myPlugboard.addPlug('G', 'P');

        BasicRotor rotor1 = new BasicRotor("V");
        BasicRotor rotor2 = new BasicRotor("III");
        BasicRotor rotor3 = new BasicRotor("II");

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);


        for (int i = 0; i < 26; i++) {
            rotor1.setPosition(i);

            for (int j = 0; j < 26; j++) {
                rotor2.setPosition(j);

                for (int k = 0; k < 26; k++) {
                    rotor3.setPosition(k);

                    String temp = outputString(msg);
                    if (temp.contains("ELECTRIC")) {
                        System.out.println(temp);
                        System.out.println("Rotor 1's position is " + rotor1.getPosition());
                        System.out.println("Rotor 2's position is " + rotor2.getPosition());
                        System.out.println("Rotor 3's position is " + rotor3.getPosition());
                    }
                }
            }
        }

        clearPlugboard();
    }

    /**
     * This is a bombe test with missing rotor types. Similarly to the
     * test above, the bombe cycles through all the possibilities for the rotor
     * types until it hits one with the keyword "JAVA". It then prints
     * out the output as well as the missing positions. Lastly, the plugboard
     * is cleared in case there are any other tests following. This differs from
     * the tests above because it has to reset the rotor1 and rotor2 position each
     * run in order to get the correct answer.
     *
     * The correct output for this test is ILOVECOFFEEILOVETEAILOVETHEJAVAJIVEANDITLOVESME.
     *
     */
    private void rotorTypeTest() {
        String types[] = {"I", "II", "III", "IV", "V"};
        String msg = "WMTIOMNXDKUCQCGLNOIBUYLHSFQSVIWYQCLRAAKZNJBOYWW";

        myPlugboard.addPlug('M', 'F');
        myPlugboard.addPlug('O', 'I');

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);

        for (String rotor1Type : types) {
            BasicRotor rotor1 = new BasicRotor(rotor1Type);
            rotor1.setPosition(22);
            addRotor(rotor1, 0);

            for (String rotor2Type : types) {
                BasicRotor rotor2 = new BasicRotor(rotor2Type);
                rotor2.setPosition(24);
                addRotor(rotor2, 1);

                for (String rotor3Type : types) {
                    BasicRotor rotor3 = new BasicRotor(rotor3Type);
                    rotor3.setPosition(23);
                    addRotor(rotor3, 2);

                    String temp = outputString(msg);
                    if (temp.contains("JAVA")) {
                        System.out.println(temp);
                        System.out.println("Rotor 1's type was " + rotor1Type);
                        System.out.println("Rotor 2's type was " + rotor2Type);
                        System.out.println("Rotor 3's type was " + rotor3Type);
                    } else {
                        rotor1.setPosition(22);
                        rotor2.setPosition(24);
                    }
                }
            }
        }

        clearPlugboard();
    }

    /**
     * This is an extension done on the plug test. While keeping the main mechanisms
     * the same, this bombe test read in two files of the 10000 most common words in
     * the english language compiled by google, and stores all of them in a hashset.
     * It then uses this to check the different outputs. This is less efficient than
     * the single keyword approach, as words such as "give" and "your" will all trigger
     * the output being labeled as the right one as well as answer.
     */
    private void enhancedPlugTest() {
        char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        String msg = "JBZAQVEBRPEVPUOBXFLCPJQSYFJI";

        BasicRotor rotor1 = new BasicRotor("IV");
        rotor1.setPosition(8);
        BasicRotor rotor2 = new BasicRotor("III");
        rotor2.setPosition(4);
        BasicRotor rotor3 = new BasicRotor("II");
        rotor3.setPosition(21);

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);

        HashSet<String> words = new HashSet<>();

        try {
            Scanner longWordFile = new Scanner(new File("long-words.txt"));

            // Reads in each word line by line and add it to the hashset
            while (longWordFile.hasNext()) {
                words.add(longWordFile.next().trim().toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        try {
            Scanner shortWordFile = new Scanner(new File("med-words.txt"));

            // Reads in each word line by line and add it to the hashset
            while (shortWordFile.hasNext()) {
                words.add(shortWordFile.next().trim().toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        for (char firstPlug : alphabet) {
            myPlugboard.addPlug('D', firstPlug);

            for (char secondPlug : alphabet) {
                myPlugboard.addPlug('S', secondPlug);
                String temp = outputString(msg);
                for (String myWord : words) {
                    if (temp.contains(myWord)) {
                        System.out.println(temp);
                        System.out.println("The first missing plug is " + firstPlug);
                        System.out.println("The second missing plug is " + secondPlug);
                    } else {
                        myPlugboard.clear();
                        myPlugboard.addPlug('D', firstPlug);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Bombe myBombe = new Bombe();

        //myBombe.plugTest();
        //myBombe.rotorPositionTest();
        //myBombe.rotorTypeTest();
        //myBombe.enhancedPlugTest();
    }
}
