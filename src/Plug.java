public class Plug {
    private char end1;
    private char end2;

    Plug(char end1, char end2) {
        this.end1 = end1;
        this.end2 = end2;
    }

    char getEnd1() { return this.end1; }
    char getEnd2() { return this.end2; }
    public void setEnd1(char end1) { this.end1 = end1; }
    public void setEnd2(char end2) { this.end2 = end2; }

    public char encode(char letterIn) {
        if (letterIn == this.end1) {
            return this.end2;
        } else if (letterIn == this.end2) {
            return this.end1;
        } else {
            return letterIn;
        }
    }

    boolean clashesWith(Plug plugin) {
        return (plugin.end1 == this.end1 || plugin.end2 == this.end2);
    }
}
