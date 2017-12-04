/**
 * This class is simply a test harness for the Enigma Machine.
 * It is easier to see all the test process, result and debug
 * by having an independent test harness class set up instead
 * of doing it in the main function of the Enigma Machine.
 */
public class TestHarness extends EnigmaMachine {
    /**
     * This is the first basic rotor test, the intended output
     * is BADGER
     * @param msg This is the input message provided by spec
     */
    private void basicRotorTest1(String msg) {
        myPlugboard.addPlug('A', 'M');
        myPlugboard.addPlug('G', 'L');
        myPlugboard.addPlug('E', 'T');

        BasicRotor rotor1 = new BasicRotor("I");
        rotor1.setPosition(6);
        BasicRotor rotor2 = new BasicRotor("II");
        rotor2.setPosition(12);
        BasicRotor rotor3 = new BasicRotor("III");
        rotor3.setPosition(5);

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);

        /*
        This breaks down the input message to a char array
        so each letter can be encoded independently
         */
        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));
        }

        System.out.println(output);

        clearPlugboard();
    }

    /**
     * This is the second basic rotor test, the intended output
     * is SNAKE. The entire process is identical to that of
     * basicRotorTest1, with just some deviation in the settings
     * of the rotors and plugs in the plugboard
     * @param msg This is the input message provided by spec
     */
    private void basicRotorTest2(String msg) {
        myPlugboard.addPlug('B', 'C');
        myPlugboard.addPlug('R', 'I');
        myPlugboard.addPlug('S', 'M');
        myPlugboard.addPlug('A', 'F');

        BasicRotor rotor1 = new BasicRotor("IV");
        rotor1.setPosition(23);
        BasicRotor rotor2 = new BasicRotor("V");
        rotor2.setPosition(4);
        BasicRotor rotor3 = new BasicRotor("II");
        rotor3.setPosition(9);

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("II");
        addReflector(myReflector);

        /*
        This breaks down the input message to a char array
        so each letter can be encoded independently
         */
        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));
        }

        System.out.println(output);

        clearPlugboard();
    }

    /**
     * This is a test for the turnover rotor. The intended output
     * is THEQUICKBROWNFOXJUMPEDOVERTHELAZYDOG
     * @param msg This is the input message provided by spec
     */
    private void turnoverRotorTest(String msg) {
        myPlugboard.addPlug('Q', 'F');

        /*
        The three rotors are added backwards (as opposed from rotor1, rotor2,
        rotor3) because rotor 1 and rotor 2 needs to be passed in rotor 2 and
        rotor 3 respectively as the next rotor so it can rotate the correct
        rotor when itself is at the turnover position
         */
        TurnoverRotor rotor3 = new TurnoverRotor("III", null);
        TurnoverRotor rotor2 = new TurnoverRotor("II", rotor3);
        TurnoverRotor rotor1 = new TurnoverRotor("I", rotor2);

        rotor1.setPosition(23);
        rotor2.setPosition(11);
        rotor3.setPosition(7);

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        Reflector myReflector = new Reflector("I");
        addReflector(myReflector);

        /*
        This breaks down the input message to a char array
        so each letter can be encoded independently
         */
        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));

            // This starts the process that rotates turnover rotors
            rotor1.rotateTurnover();
        }

        System.out.println(output);
    }

    public static void main(String[] args) {
        TestHarness myTestHarness = new TestHarness();

        myTestHarness.basicRotorTest1("GFWIQH");
        myTestHarness.basicRotorTest2("GACIG");
        myTestHarness.turnoverRotorTest("OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT");
    }
}
