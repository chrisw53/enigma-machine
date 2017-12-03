import java.util.Scanner;
import java.util.function.Function;

public class CommandLineApp extends EnigmaMachine {
    private Scanner userInput = new Scanner(System.in);

    protected Integer intValidation(String input, Runnable callback) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("[FAIL] Please enter a valid integer");
            callback.run();
        }

        return 0;
    }

    protected char charValidation(String input, Runnable callback) {
        char output = 'A';

        if (input.length() > 0) {
            output = input.toUpperCase().charAt(0);
        } else {
            System.err.println("[FAIL] Do not leave the field empty");
            callback.run();
        }

        if (input.length() == 1 && output > 64 && output < 91) {
            return output;
        } else {
            System.err.println("[FAIL] Please enter only a single letter");
            callback.run();
        }

        return 'A';
    }

    protected void init() {
        System.out.println("====== Main Menu ======");
        System.out.println("[1] Quick Start");
        System.out.println("[2] Add Plugs");
        System.out.println("[3] Add Rotors");
        System.out.println("[4] Add Reflector");
        System.out.println("[5] Run");
        System.out.println("[6] Exit");

        switch (intValidation(userInput.nextLine(), this::init)) {
            case 1:
                plugInit();
                break;
            case 2:
                plugInit();
                break;
            case 3:
                rotorInit();
                break;
            case 4:
                reflectorInit();
                break;
            case 5:
                if (getReflector() != null && getRotor(0) != null) {
                    encodeInit();
                } else {
                    System.err.println("\n[FAIL] Make sure you have added a reflector and the rotors before encoding\n");
                    reflectorInit();
                }
                break;
            case 6:
                System.exit(0);
            default:
                System.err.println("[FAIL] Please enter a number from 1 - 5");
                init();
                break;
        }
    }

    protected void plugInit() {
        System.out.println("====== Plugs ======");
        System.out.println("[1] Add More Plugs");
        System.out.println("[2] Clear Plugboard");
        System.out.println("[3] Proceed to Add Rotors");
        System.out.println("[4] Show Current Plugs");
        System.out.println("[5] Return to Main Menu");

        switch (intValidation(userInput.nextLine(), this::plugInit)) {
            case 1:
                addPlug();
                break;
            case 2:
                myPlugboard.clear();
                System.out.println("\n[SUCCESS] Plugboard Cleared\n");
                plugInit();
                break;
            case 3:
                rotorInit();
                break;
            case 4:
                myPlugboard.showPlugs();
                plugInit();
                break;
            case 5:
                init();
                break;
            default:
                System.err.println("\n[FAIL] Please enter a number from 1 - 4\n");
                plugInit();
                break;
        }
    }

    protected void addPlug() {
        System.out.println("What is the first end?");
        char end1 = charValidation(userInput.nextLine(), this::addPlug);
        System.out.println("What is the second end?");
        char end2 = charValidation(userInput.nextLine(), this::addPlug);

        if (end1 != end2) {
            if (myPlugboard.addPlug(end1, end2)) {
                System.out.println("\n[SUCCESS] Plug Added\n");
            } else {
                System.err.println("\n[FAIL] Your plug conflict with an existing plug\n");
            }
        } else {
            System.err.println("\n[FAIL] Your two ends are the same dummy, try again\n");
        }

        plugInit();
    }

    protected void rotorInit() {
        System.out.println("====== Rotors ======");
        System.out.println("[1] Add Rotors");
        System.out.println("[2] Modify Rotors' Initial Position");
        System.out.println("[3] Change Rotor Type");
        System.out.println("[4] View Existing Rotors");
        System.out.println("[5] Proceed to Reflector Config");
        System.out.println("[6] Return to Main Menu");

        switch (intValidation(userInput.nextLine(), this::rotorInit)) {
            case 1:
                selectRotorKind();
                break;
            case 2:
                if (getRotor(0) != null) {
                    setRotorPosition();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 3:
                if (getRotor(0) != null) {
                    setRotorType();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 4:
                if (getRotor(0) != null) {
                    showRotors();
                    rotorInit();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 5:
                reflectorInit();
                break;
            case 6:
                init();
                break;
            default:
                System.err.println("\n[FAIL] Please enter a number from 1 - 5\n");
                rotorInit();
                break;
        }
    }

    protected void selectRotorKind() {
        System.out.println("====== Rotor Kind ======");
        System.out.println("[1] Basic Rotor");
        System.out.println("[2] Turnover Rotor");

        switch (intValidation(userInput.nextLine(), this::selectRotorKind)) {
            case 1:
                for (int i = 1; i < 4; i++) {
                    addBasicRotor(rotorConfig(i));
                }
                rotorInit();
                break;
            case 2:
                addTurnoverRotor();
                rotorInit();
                break;
            default:
                System.err.println("\n[FAIL] Please enter either 1 or 2\n");
                selectRotorKind();
                break;
        }
    }

    protected String rotorConfig(int rotorNum) {
        Function<Integer, Runnable> createRunnable = input -> new Runnable(){
            public void run() {
                rotorConfig(input);
            }
        };

        Runnable myRunnable = createRunnable.apply(rotorNum);

        System.out.println("====== Rotor " + rotorNum + " ======");
        System.out.println("What is the rotor type?");
        String output = "";

        switch (intValidation(userInput.nextLine(), myRunnable)) {
            case 1:
                output = "I";
                break;
            case 2:
                output = "II";
                break;
            case 3:
                output = "III";
                break;
            case 4:
                output = "IV";
                break;
            case 5:
                output = "V";
                break;
            default:
                System.err.println("\n[FAIL] Please enter a number from 1 - 5\n");
                rotorConfig(rotorNum);
                break;
        }

        System.out.println("What is the initial position?");
        int position = intValidation(userInput.nextLine(), myRunnable);
        if (position < 26 && position >= 0) {
            output += " " + position + " " + rotorNum;
        } else {
            System.err.println("\n[FAIL] Please enter a number from 0 to 25\n");
            rotorConfig(rotorNum);
        }

        return output;
    }

    protected void addBasicRotor(String config) {
        String rotorType = config.split(" ")[0];
        int rotorPosition = Integer.parseInt(config.split(" ")[1]);
        int rotorNum = Integer.parseInt(config.split(" ")[2]);

        BasicRotor myRotor = new BasicRotor(rotorType);
        myRotor.setPosition(rotorPosition);
        addRotor(myRotor, (rotorNum - 1));

        System.out.println("\n[SUCCESS] Rotor " + rotorNum + " is added \n");
    }

    protected void addTurnoverRotor() {
        String rotor1Config = rotorConfig(1);
        String rotor2Config = rotorConfig(2);
        String rotor3Config = rotorConfig(3);

        TurnoverRotor rotor3 = new TurnoverRotor(rotor3Config.split(" ")[0], null);
        rotor3.setPosition(Integer.parseInt(rotor3Config.split(" ")[1]));
        TurnoverRotor rotor2 = new TurnoverRotor(rotor2Config.split(" ")[0], rotor3);
        rotor2.setPosition(Integer.parseInt(rotor2Config.split(" ")[1]));
        TurnoverRotor rotor1 = new TurnoverRotor(rotor1Config.split(" ")[0], rotor2);
        rotor1.setPosition(Integer.parseInt(rotor1Config.split(" ")[1]));

        addRotor(rotor1, 0);
        addRotor(rotor2, 1);
        addRotor(rotor3, 2);

        System.out.println("\n[SUCCESS] All rotors are added!\n");
    }

    protected void setRotorPosition() {
        System.out.println("Which rotor would you like to change?");
        int rotorInput = (intValidation(userInput.nextLine(), this::setRotorPosition) - 1);

        if (rotorInput >= 0 && rotorInput < 3) {
            System.out.println("What initial position would you like?");
            int positionInput = intValidation(userInput.nextLine(), this::setRotorPosition);

            if (positionInput >= 0 && positionInput < 26) {
                setPosition(rotorInput, positionInput);
                System.out.println("\n[SUCCESS] Rotor " + (rotorInput + 1) + "'s initial position is now " + positionInput + "\n");
                rotorInit();
            } else {
                System.err.println("\n[FAIL] Please enter a number from 0 to 25\n");
                setRotorPosition();
            }
        } else {
            System.err.println("\n[FAIL] Please enter a number from 1 to 3\n");
            setRotorPosition();
        }
    }

    protected void setRotorType() {
        System.out.println("Which rotor would you like to change?");
        int rotorInput = (intValidation(userInput.nextLine(), this::setRotorPosition) - 1);
        int rotorPosition;
        String rotorType = "";

        if (rotorInput >= 0 && rotorInput < 3) {
            Rotor myRotor = getRotor(rotorInput);
            rotorPosition = myRotor.position;
            System.out.println("What type would you like?");

            switch (intValidation(userInput.nextLine(), this::setRotorPosition)) {
                case 1:
                    rotorType = "I";
                    break;
                case 2:
                    rotorType = "II";
                    break;
                case 3:
                    rotorType = "III";
                    break;
                case 4:
                    rotorType = "IV";
                    break;
                case 5:
                    rotorType = "V";
                    break;
                default:
                    System.err.println("\n[FAIL] Please enter a number from 1 - 5\n");
                    setRotorType();
                    break;
            }

            if (myRotor instanceof TurnoverRotor) {
                TurnoverRotor nextRotor = ((TurnoverRotor) myRotor).getNextRotor();
                TurnoverRotor newRotor = new TurnoverRotor(rotorType, nextRotor);
                newRotor.setPosition(rotorPosition);
                addRotor(newRotor, rotorInput);
                System.out.println("\n[SUCCESS] Rotor " + (rotorInput + 1) + " now has the type of " + rotorType + "\n");
                rotorInit();
            } else {
                BasicRotor newRotor = new BasicRotor(rotorType);
                newRotor.setPosition(rotorPosition);
                addRotor(newRotor, rotorInput);
                System.out.println("\n[SUCCESS] Rotor " + (rotorInput + 1) + " now has the type of " + rotorType + "\n");
                rotorInit();
            }
        } else {
            System.err.println("\n[FAIL] Please enter a number from 1 to 3\n");
            setRotorPosition();
        }
    }

    protected void reflectorInit() {
        System.out.println("====== Reflector ======");
        System.out.println("[1] Add/Modify Reflector");
        System.out.println("[2] Show Current Reflector Type");
        System.out.println("[3] Run Enigma Machine");
        System.out.println("[4] Return to Main Menu");

        switch (intValidation(userInput.nextLine(), this::reflectorInit)) {
            case 1:
                addMyReflector();
                break;
            case 2:
                if (getReflector() != null) {
                    System.out.println("\nYour current reflector is of type " + getReflector().name + "\n");
                    reflectorInit();
                } else {
                    System.err.println("\n[FAIL] Please add the reflector first\n");
                    reflectorInit();
                }
                break;
            case 3:
                if (getReflector() != null && getRotor(0) != null) {
                    encodeInit();
                } else {
                    System.err.println("\n[FAIL] Make sure you have added a reflector and the rotors before encoding\n");
                    reflectorInit();
                }
                break;
            case 4:
                init();
                break;
            default:
                System.err.println("\n[FAIL] Please enter a number from 1 - 3\n");
                reflectorInit();
                break;
        }
    }

    protected void addMyReflector() {
        System.out.println("What type of reflector would you like?");
        String reflectorType = "";

        switch (intValidation(userInput.nextLine(), this::addMyReflector)) {
            case 1:
                reflectorType = "I";
                break;
            case 2:
                reflectorType = "II";
                break;
            default:
                System.err.println("\n[FAIL] Please enter either 1 or 2\n");
                addMyReflector();
                break;
        }

        Reflector myReflector = new Reflector(reflectorType);
        addReflector(myReflector);

        System.out.println("\n[SUCCESS] Reflector type " + getReflector().name + " is added\n");

        reflectorInit();
    }

    protected void encodeInit() {
        System.out.println("====== Encode Message ======");
        System.out.println("[1] Encode");
        System.out.println("[2] Return to Main Menu");

        switch (intValidation(userInput.nextLine(), this::encodeInit)) {
            case 1:
                encode();
                break;
            case 2:
                init();
                break;
            default:
                System.err.println("\n[FAIL] Please enter either 1 or 2\n");
                encodeInit();
                break;
        }
    }

    protected void encode() {
        System.out.println("What is the message you would like to encode?");
        char inputCharArray[] = userInput.nextLine().toCharArray();
        StringBuilder output = new StringBuilder("");

        if (getRotor(0) instanceof TurnoverRotor) {
            for(char letter: inputCharArray) {
                output.append(encodeLetter(letter));
                ((TurnoverRotor) getRotor(0)).rotateTurnover();
            }
        } else {
            for(char letter: inputCharArray) {
                output.append(encodeLetter(letter));
            }
        }

        System.out.println("\n[SUCCESS] Your output is " + output + "\n");
        encodeInit();
    }

    public static void main(String[] args) {
        CommandLineApp myCommandLineApp = new CommandLineApp();

        myCommandLineApp.init();
    }
}
