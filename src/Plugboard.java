import java.util.ArrayList;

public class Plugboard {
    private ArrayList<Plug> plugsList = new ArrayList<>();

    boolean addPlug(char end1, char end2) {
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

    public int getNumPlugs() {
        return plugsList.size();
    }

    void clear() {
        plugsList.clear();
    }

    void showPlugs() {
        System.out.println("====== Current Plugs ======");
        for (Plug myPlug : plugsList) {
            System.out.println(myPlug.getEnd1() + "-" + myPlug.getEnd2());
        }
        System.out.println();
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
