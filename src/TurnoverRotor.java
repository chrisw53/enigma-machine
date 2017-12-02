public class TurnoverRotor extends BasicRotor {
    private int turnoverPosition;
    private TurnoverRotor nextRotor;

    TurnoverRotor(String type, TurnoverRotor nextRotor) {
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

    TurnoverRotor getNextRotor() { return this.nextRotor; }

    void setNextRotor(TurnoverRotor nextRotor) { this.nextRotor = nextRotor; }

    void rotateTurnover() {
        if (this.turnoverPosition == this.position) {
            if (this.nextRotor != null) {
                nextRotor.rotate();
                nextRotor.rotateTurnover();
            }
        }
    }
}
