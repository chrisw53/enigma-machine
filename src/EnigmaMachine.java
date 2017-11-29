public class EnigmaMachine {
    Plugboard myPlugboard;
    private BasicRotor rotorList[] = new BasicRotor[3];
    private Reflector myReflector;

    EnigmaMachine() {
        myPlugboard = new Plugboard();
    }

    void clearPlugboard() {
        myPlugboard.clear();
    }

    void addRotor(BasicRotor rotor, int slot) {
        rotorList[slot] = rotor;
    }

    void addReflector(Reflector myReflector) {
        this.myReflector = myReflector;
    }

    public Reflector getReflector() {
        return myReflector;
    }

    public void setPosition(int slot, int position) {
        rotorList[slot].position = position;
    }

    char encodeLetter(char initInput) {

        int encodeOutput = myPlugboard.substitute(initInput) - 65;

        for (BasicRotor myRotor : rotorList) {
            encodeOutput = myRotor.substitute(encodeOutput, myRotor.mapping);
        }

        encodeOutput = myReflector.substitute(encodeOutput, myReflector.mapping);

        for (int i = rotorList.length - 1; i >= 0; i--) {
            encodeOutput = rotorList[i].substitute(encodeOutput, rotorList[i].inverseMapping(rotorList[i].mapping));
        }

        encodeOutput += 65;
        rotorList[0].rotate();

        return myPlugboard.substitute((char)encodeOutput);
    }
}
