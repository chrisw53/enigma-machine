public class Reflector extends Rotor{
    /*
    These are all the potential mappings for a reflector, they
    are made as private constant to avoid anything else to modify them
     */
    private final int[] REFLECTOR1MAPPING = {24,17,20,7,16,18,11,3,15,23,13,6,14,10,12,8,4,1,5,25,2,22,21,9,0,19};
    private final int[] REFLECTOR2MAPPING = {5,21,15,9,8,0,14,24,4,3,17,25,23,22,6,2,19,10,20,16,18,1,13,12,7,11};

    /**
     * The Reflector is initialized with its type so when a new Reflector
     * is instantiated, it will have its mapping all set and ready to go
     * @param name The type name of the reflector. It's either I or II
     */
    protected Reflector(String name) {
        this.initialise(name);
    }

    /**
     * This method assigns a mapping to the reflector based on the type
     * name passed in and it is called within the Reflector's constructor.
     * It also assigns the type of reflector as its name so it can be
     * referred to in the CommandLineApp when the user wishes to check
     * for the existing reflector
     * @param name The type name of the reflector. It's either I or II.
     */
    protected void initialise(String name) {
        this.name = name;
        switch (name.toUpperCase()) {
            case "I":
                this.mapping = REFLECTOR1MAPPING;
                break;
            case "II":
                this.mapping = REFLECTOR2MAPPING;
                break;
        }
    }

    /**
     * This method handles the substitution in the reflector, which takes in an
     * integer input and a integer array mapping, and returns the output that's
     * obtained by putting the input as the index position of the mapping.
     * @param input This is the input character represented as an integer. The
     *              conversion from a character to an integer is handled by the
     *              EnigmaMachine class.
     * @param mapping This is the mapping of the reflector. This is passed in in
     *                addition to the integer input so that in the BasicRotor class,
     *                there will not be a need for a substitute back method. As a
     *                result, the parameters for abstract method substitute is changed
     *                accordingly. See BasicRotor's substitute method for a more
     *                detailed explanation on this.
     * @return Integer, that represents a character that is encoded by the reflector
     * mapping.
     */
    protected int substitute(int input, int[] mapping) {
        return mapping[input];
    }
}
