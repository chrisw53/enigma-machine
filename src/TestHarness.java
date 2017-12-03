public class TestHarness extends EnigmaMachine {
    private void test1(String msg) {
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

        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));
        }

        System.out.println(output);

        clearPlugboard();
    }

    private void test2(String msg) {
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

        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));
        }

        System.out.println(output);

        clearPlugboard();
    }

    private void test3(String msg) {
        myPlugboard.addPlug('Q', 'F');

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

        char charArray[] = msg.toCharArray();
        StringBuilder output = new StringBuilder("");

        for(char letter: charArray) {
            output.append(encodeLetter(letter));

            rotor1.rotateTurnover();
        }

        System.out.println(output);
    }

    public static void main(String[] args) {
        TestHarness myTestHarness = new TestHarness();

        myTestHarness.test1("GFWIQH");
        myTestHarness.test2("GACIG");
        myTestHarness.test3("OJVAYFGUOFIVOTAYRNIWJYQWMXUEJGXYGIFT");
    }
}
