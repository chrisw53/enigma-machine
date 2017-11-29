public abstract class Rotor {
    String name;
    int position = 0;
    final int ROTORSIZE = 26;
    int mapping[];

    void setPosition(int position) {
        this.position = position;
    }

    int getPosition() {
        return this.position;
    }

    abstract void initialise(String name);
    abstract int substitute(int input, int[] mapping);
}
