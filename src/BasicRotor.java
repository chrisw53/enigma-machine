public class BasicRotor extends Rotor {
    private final int MAPPING1[] = {4,10,12,5,11,6,3,16,21,25,13,19,14,22,24,7,23,20,18,15,0,8,1,17,2,9};
    private final int MAPPING2[] = {0,9,3,10,18,8,17,20,23,1,11,7,22,19,12,2,16,6,25,13,15,24,5,21,14,4};
    private final int MAPPING3[] = {1,3,5,7,9,11,2,15,17,19,23,21,25,13,24,4,8,22,6,0,10,12,20,18,16,14};
    private final int MAPPING4[] = {4,18,14,21,15,25,9,0,24,16,20,8,17,7,23,11,13,5,19,6,10,3,2,12,22,1};
    private final int MAPPING5[] = {21,25,1,17,6,8,19,24,20,15,18,3,13,7,11,23,0,22,12,9,16,14,5,4,2,10};

    BasicRotor(String type) { this.initialise(type); }

    public int substitute(int input, int[] mapping) {
        if (this.position == 0) {
            return mapping[input];
        } else {
            int offset = input - this.position;

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
                    output -= 26;
                    return output;
                }
            }
        }
    }

    int[] inverseMapping(int[] mapping) {
        int inverseMapping[] = new int[26];

        for (int i = 0; i < mapping.length; i++) {
            inverseMapping[mapping[i]] = i;
        }

        return inverseMapping;
    }

    void rotate() {
        this.position++;

        if(this.position == ROTORSIZE) {
            this.position = 0;
        }
    }

    void initialise(String name) {
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
