public abstract class Rotor {
    String name;
    int position = 0;
    final int ROTORSIZE = 26;
    int mapping[];

    /**
     * This method is used to set the initial position of any rotor.
     * This is defined here so every rotor class that inherit from
     * this abstract class would automatically have it.
     * @param position An integer from 0 to 25 that represents the
     *                 initial position of the rotor
     */
    protected void setPosition(int position) {
        this.position = position;
    }

    /**
     * This method is a getter method that returns the initial
     * position of a rotor. It is defined here for similar reason
     * as the setter method setPosition()
     * @return An integer from 0 to 25 that represents the
     * initial position of the rotor
     */
    protected int getPosition() {
        return this.position;
    }

    /**
     * This abstract method is defined here so every rotor class
     * that inherit from it must override it.
     * @param name A string that represents the type of rotor,
     *             it is a roman numeral from I to V.
     */
    abstract void initialise(String name);

    /**
     * This abstract method is defined here so every rotor class
     * that inherit from it must override it. This deviates from
     * the spec by having an additional parameter, an integer array
     * mapping. This is here because this way, the BasicRotor class
     * would not have to have a substitute back method. See the
     * substitution method in the BasicRotor class for a more detailed
     * explanation on this.
     * @param input An integer from 0 to 25 representing a character
     * @param mapping An integer array that holds the mapping of the rotor
     * @return An integer from 0 to 25 representing a character that's
     * encoded via the rotor's substitute method
     */
    abstract int substitute(int input, int[] mapping);
}
