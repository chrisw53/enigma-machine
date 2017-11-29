import java.util.ArrayList;

public class Plugboard {
    private ArrayList<Plug> plugsList = new ArrayList<>();

    boolean addPlug(char end1, char end2) {
        Plug myPlug = new Plug(end1, end2);

        for(Plug plug : plugsList) {
            if (plug.clashesWith(myPlug)) {
                return false;
            }
        }

        plugsList.add(myPlug);
        return true;
    }

    public int getNumPlugs() {
        return plugsList.size();
    }

    void clear() {
        plugsList.clear();
    }

    char substitute(char input) {
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
