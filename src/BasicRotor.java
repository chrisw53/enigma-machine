public class BasicRotor extends Rotor {
    /*
    These integer arrays holds all the potential mappings of a Rotor.
    They are made to be private constants so nothing can modify them.
     */
    private final int MAPPING1[] = {4,10,12,5,11,6,3,16,21,25,13,19,14,22,24,7,23,20,18,15,0,8,1,17,2,9};
    private final int MAPPING2[] = {0,9,3,10,18,8,17,20,23,1,11,7,22,19,12,2,16,6,25,13,15,24,5,21,14,4};
    private final int MAPPING3[] = {1,3,5,7,9,11,2,15,17,19,23,21,25,13,24,4,8,22,6,0,10,12,20,18,16,14};
    private final int MAPPING4[] = {4,18,14,21,15,25,9,0,24,16,20,8,17,7,23,11,13,5,19,6,10,3,2,12,22,1};
    private final int MAPPING5[] = {21,25,1,17,6,8,19,24,20,15,18,3,13,7,11,23,0,22,12,9,16,14,5,4,2,10};

    /**
     * The BasicRotor is initialized with its type so when a new BasicRotor
     * is instantiated, it will have its mapping all set and ready to go
     * @param type The type name of the reflector. It's a roman numeral value
     *             from I to V
     */
    protected BasicRotor(String type) {
        this.initialise(type);
    }

    /**
     * This method handles the character encoding from a BasicRotor. As mentioned
     * in the rotor abstract class, this method deviates from the spec by having
     * an additional parameter: mapping. The reason for this deviation is because
     * in the work flow of the Enigma Machine, the character is passed from the
     * plugboard to the three rotors, and then through the reflector, it is passed
     * back through the three rotors, only in reverse order. After writing out the
     * substitute back method, I found out the method is practically identical to
     * that of substitute apart from the mapping. In order to avoid a bloated code,
     * I decided to simply have the substitute method take in the mapping, extract
     * out the inverse mapping into its own method and thereby not having to write
     * two almost identical methods.
     * @param input An integer from 0 to 25 representing a character
     * @param mapping An integer array that holds the mapping of the rotor
     * @return An integer that represents a character after the encoding
     */
    protected int substitute(int input, int[] mapping) {
        if (this.position == 0) {
            return mapping[input];
        } else {
            int offset = input - this.position;

            // This handles when the offset is negative
            if (offset < 0) {
                offset += 26;
                if ((mapping[offset] + this.position) <= 25) {
                    return (mapping[offset] + this.position);
                } else {
                    return ((mapping[offset] + this.position) - 26);
                }
            } else {
                int output = mapping[offset] + this.position;

                if (output <= 25) {
                    return output;
                } else {
                    /* This handles when output is larger than 25,
                    the integer representation of the last letter
                    of the alphabet
                     */
                    output -= 26;
                    return output;
                }
            }
        }
    }

    /**
     * This method takes in a mapping, loop through each element in
     * the mapping array, and uses the index number of each element
     * as the value of the inverseMapping array and the value of
     * each element in the mapping array as the index number of the
     * inverseMapping array.
     * @param mapping An integer array that represents the mapping of
     *                characters
     * @return A completed inverseMapping array constructed from the
     * methods described above
     */
    protected int[] inverseMapping(int[] mapping) {
        int inverseMapping[] = new int[26];

        for (int i = 0; i < mapping.length; i++) {
            inverseMapping[mapping[i]] = i;
        }

        return inverseMapping;
    }

    /**
     * This method handles the rotation of a BasicRotor by incrementing
     * the position of the rotor each time this method is called. The
     * method also resets the position back to zero when it is equal to
     * the rotor size (26)
     */
    protected void rotate() {
        this.position++;

        if(this.position == ROTORSIZE) {
            this.position = 0;
        }
    }

    /**
     * This method overrides the abstract method in the Rotor abstract
     * class. It assigns a mapping to the rotor based on its type and
     * also sets the name attribute of the rotor to the type of the rotor.
     * This is referred to in the CommandLineApp class when the user
     * wishes to see all the existing rotors. This method is called
     * in the constructor of the BasicRotor class.
     * @param name A string that represents the type of rotor,
     */
    protected void initialise(String name) {
        this.name = name;
        switch (name.toUpperCase()) {
            case "I":
                this.mapping = MAPPING1;
                break;
            case "II":
                this.mapping = MAPPING2;
                break;
            case "III":
                this.mapping = MAPPING3;
                break;
            case "IV":
                this.mapping = MAPPING4;
                break;
            case "V":
                this.mapping = MAPPING5;
                break;
        }
    }
}
