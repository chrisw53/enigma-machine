public class EnigmaMachine {
    Plugboard myPlugboard;
    private BasicRotor rotorList[] = new BasicRotor[3];
    private Reflector myReflector;

    /**
     * This constructor makes sure a new Plugboard is instantiated
     * whenever a new enigma machine is instantiated
     */
    protected EnigmaMachine() {
        myPlugboard = new Plugboard();
    }

    /**
     * This method clears the plugboard array. This is used both
     * in the Bombe class and the TestHarness class.
     */
    protected void clearPlugboard() {
        myPlugboard.clear();
    }

    /**
     * This method adds a rotor to the rotor list.
     * @param rotor This is the rotor to be added. Since TurnoverRotor
     *              class is a subclass of the BasicRotor class, this
     *              input type will accept both basic rotors and turnover
     *              rotors
     * @param slot This is the index position to figure out where to insert
     *             the new rotor. This is useful in adding turnover rotor
     *             since these rotors have to be instantiated from 3 to 1.
     */
    protected void addRotor(BasicRotor rotor, int slot) {
        rotorList[slot] = rotor;
    }

    /**
     * This setter method adds a reflector to the enigma machine.
     * @param myReflector This is the input reflector to be added.
     */
    protected void addReflector(Reflector myReflector) {
        this.myReflector = myReflector;
    }

    /**
     * Since myReflector is a private variable, this is a getter
     * method to access myReflector. This is used in the command
     * line app to show the user if a reflector exist.
     * @return The current reflector if a reflector has been added,
     * null otherwise.
     */
    protected Reflector getReflector() {
        return myReflector;
    }

    /**
     * This getter method for the rotors in the rotorList array.
     * This is used extensively in command line app to check whether
     * there are rotors in the array and what kind of rotors are in it.
     * @param index An integer representing the index position of the
     *              rotor.
     * @return The current rotors if they have been added, null otherwise.
     */
    protected BasicRotor getRotor(int index) {
        return rotorList[index];
    }

    /**
     * This setter method sets the initial position of any rotor in
     * the rotorList array. This differs from the setPosition method
     * on Rotor abstract class by being able to access any of the
     * three rotors while the method on Rotor can only set the position
     * of the rotor it's being called on. setPosition(0, 23) is more
     * concise than getRotor(0).setPosition(23).
     * @param slot An integer representing the index position of the
     *             rotor the user wants to set position to.
     * @param position An integer from 0 to 25 representing the initial
     *                 position the user wants to set the rotor to.
     */
    protected void setPosition(int slot, int position) {
        rotorList[slot].position = position;
    }

    /**
     * This method loops through the rotorList array and shows all
     * existing rotors. This is used in the command line app. I decided
     * to extract it out into its own method here to improve code readability
     */
    protected void showRotors() {
        System.out.println("====== Current Rotors ======");
        for (int i = 0; i < rotorList.length; i++) {
            System.out.println("Rotor " + (i + 1) + ": ");
            System.out.println("Type: " + rotorList[i].name);
            System.out.println("Initial Position: " + rotorList[i].position + "\n");
        }
    }

    /**
     * This method handles the entire encoding process. It takes in an
     * initial output, puts it through the plugboard, then converts the
     * character into an integer using ascii code. Then, the input is
     * passed from rotor 1 to rotor 3, it hits the reflector and gets
     * passed back from rotor 3 to rotor 1. The output is converted
     * back into a character again using ascii code. Lastly, the left
     * most rotor, rotor1 is rotated. I decided to use for loops instead
     * of writing out each step of the encoding to avoid bloated code and
     * improve code readability.
     * @param initInput An input character.
     * @return An encoded output character.
     */
    protected char encodeLetter(char initInput) {

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
