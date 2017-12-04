import java.util.ArrayList;

public class Plugboard {
    private ArrayList<Plug> plugsList = new ArrayList<>();

    /**
     * This method adds two plugs that links two letters together
     * and uses the clashesWith method on the Plug class to return
     * true if it does not clash with any of the existing plugs.
     * @param end1 The first end of the link, it is a single character
     * @param end2 The second end the link, it is a single character
     * @return Boolean, dependent on whether the new plugs clashes with
     * existing plugs
     */
    protected boolean addPlug(char end1, char end2) {
        Plug myPlug = new Plug(end1, end2);
        Boolean plugClash = false;

        for(Plug plug : plugsList) {
            if (plug.clashesWith(myPlug)) {
                plugClash = true;
            }
        }

        if (!plugClash) {
            plugsList.add(myPlug);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns the size of the plugsList array because plugsList
     * is set as private so you cannot call plugList.size() outside of the
     * Plugboard class directly
     * @return Integer, that
     */
    protected int getNumPlugs() {
        return plugsList.size();
    }

    /**
     * This method is used to clear the plugsList array because plugList is
     * set private so you cannot call plugList.clear() outside of the
     * Plugboard class directly
     */
    protected void clear() {
        plugsList.clear();
    }

    /**
     * This method is used to show all the current plugs from plugsList so
     * that the user can view all the current plugs from the Command Line App.
     */
    protected void showPlugs() {
        System.out.println("====== Current Plugs ======");
        for (Plug myPlug : plugsList) {
            System.out.println(myPlug.getEnd1() + "-" + myPlug.getEnd2());
        }
        System.out.println();
    }

    /**
     * This method handles the substitution process that maps the character to
     * another if there is a plug link connecting the two.
     * @param input This is the input character
     * @return If there is a link between the input and another character, the
     * other character is returned. If there is not a link, then the original
     * input is returned
     */
    protected char substitute(char input) {
        for (Plug plug : plugsList) {
            if (input == plug.getEnd1()) {
                return plug.getEnd2();
            } else if (input == plug.getEnd2()) {
                return plug.getEnd1();
            }
        }

        return input;
    }
}
