public class TurnoverRotor extends BasicRotor {
    private int turnoverPosition;
    private TurnoverRotor nextRotor;

    /**
     * This constructor calls the constructor of its super class
     * BasicRotor, thereby be assigned with a mapping array based
     * on the type passed in (roman numeral from I to V). There is
     * also a switch statement that sets the turnoverPosition of the
     * rotor based on its type. Lastly, the turnoverRotor is also
     * passed its nextRotor so it is aware of the rotor next to it
     * when it comes to rotating the nextRotor when its own
     * turnoverPosition is hit.
     * @param type The type name of the reflector. It's a roman numeral value
     *             from I to V
     * @param nextRotor The turnover rotor to the right of the rotor being
     *                  created.
     */
    protected TurnoverRotor(String type, TurnoverRotor nextRotor) {
        super(type);

        switch (type.toUpperCase()) {
            case "I":
                this.turnoverPosition = 24;
                break;
            case "II":
                this.turnoverPosition = 12;
                break;
            case "III":
                this.turnoverPosition = 3;
                break;
            case "IV":
                this.turnoverPosition = 17;
                break;
            case "V":
                this.turnoverPosition = 7;
                break;
        }

        this.setNextRotor(nextRotor);
    }

    /**
     * This method is a getter method that returns the next rotor
     * since the variable nextRotor is set as private to avoid any
     * changes to it once a turnoverRotor is created. This is used
     * in the CommandLineApp to temporarily store the value of the
     * nextRotor into a variable when the user is only changing the
     * type of the turnover rotor and nothing else.
     * @return the turnover rotor to the right of this rotor.
     */
    protected TurnoverRotor getNextRotor() {
        return this.nextRotor;
    }

    /**
     * This is a setter method that sets the next rotor. Because this
     * is only used in the constructor of this class and I do not think
     * nextRotor should be modified by anything outside of this class.
     * @param nextRotor The turnoverRotor to the right of this rotor
     */
    private void setNextRotor(TurnoverRotor nextRotor) {
        this.nextRotor = nextRotor;
    }

    /**
     * This method checks if the turnoverPosition of the current rotor
     * is the same as the its rotor position, then it rotates the rotor
     * to the right of it. This is initialized with a call to this method
     * by the very first rotor.
     */
    void rotateTurnover() {
        if (this.turnoverPosition == this.position) {
            if (this.nextRotor != null) {
                nextRotor.rotate();
                nextRotor.rotateTurnover();
            }
        }
    }
}
