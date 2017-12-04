public class Plug {
    private char end1;
    private char end2;

    /**
     * The constructor takes in the two ends of a plug and sets them
     * to their end1 and end2 so each plug will have 2 ends as soon
     * as they are instantiated.
     * @param end1 A character representing one end of the plug
     * @param end2 A character representing the other end of the plug
     */
    Plug(char end1, char end2) {
        setEnd1(end1);
        setEnd2(end2);
    }

    /**
     * This is a getter method that returns the private variable end1.
     * @return A character representing one end of the plug
     */
    protected char getEnd1() {
        return this.end1;
    }

    /**
     * This is a getter method that returns the private variable end2.
     * @return A character representing one end of the plug
     */
    protected char getEnd2() {
        return this.end2;
    }

    /**
     * This is a setter method that returns the private variable end1.
     * I made this private because the only time it is used is in the
     * constructor of this class and anyone using the code should not
     * be adjusting the ends of the plugs outside of this class.
     */
    private void setEnd1(char end1) {
        this.end1 = end1;
    }

    /**
     * This is a setter method that returns the private variable end2.
     * The reason I made this private is the same as stated above.
     */
    private void setEnd2(char end2) {
        this.end2 = end2;
    }

    /**
     * This method handles encoding characters based on whether the input
     * is matched up with one of the ends of the plug. If not, it returns
     * the original character
     * @param letterIn An input character to be encoded if it is the same
     *                 as one of the ends on the plug
     * @return An encoded character if the input matches up with one of
     * the ends of the plug, or the input character if the input does not
     * match up.
     */
    protected char encode(char letterIn) {
        if (letterIn == this.end1) {
            return this.end2;
        } else if (letterIn == this.end2) {
            return this.end1;
        } else {
            return letterIn;
        }
    }

    /**
     * This method handles whether a plug clashes with another plug. This
     * is used in plugboard to check against if a new plug conflicts with
     * a current plug.
     * @param plugin This is the plug to be compared
     * @return It returns false if any of the ends of the input plug matches
     * with the existing plug. Returns true otherwise.
     */
    protected boolean clashesWith(Plug plugin) {
        return (
            plugin.end1 == this.end1 ||
            plugin.end2 == this.end2 ||
            plugin.end2 == this.end1 ||
            plugin.end1 == this.end2
        );
    }
}
