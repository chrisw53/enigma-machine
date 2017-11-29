public class Bombe extends EnigmaMachine {
    private String outputString(String msg) {
        char charArray[] = msg.toCharArray();
        String output = "";

        for(char letter: charArray) {
            output += (encodeLetter(letter));
        }

        return output;
    }

    private void test1() {
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

    private void test2() {
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

    private void test3() {
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

    public static void main(String[] args) {
        Bombe myBombe = new Bombe();

        myBombe.test3();
    }
}
