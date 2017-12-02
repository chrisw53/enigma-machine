import java.util.Scanner;

public class CommandLineApp extends EnigmaMachine {
    private Scanner userInput = new Scanner(System.in);

    Integer intValidation(String input, Runnable callback) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("Please enter a valid integer");
            callback.run();
        }

        return 0;
    }

    char charValidation(String input, Runnable callback) {
        char output = input.toUpperCase().charAt(0);

        if (input.length() == 1 && output > 64 && output < 91) {
            return output;
        } else {
            System.err.println("Please enter only a single letter");
            callback.run();
        }

        return 'A';
    }

    void init() {
        System.out.println("======Main Menu======");
        System.out.println("[1] Quick Start");
        System.out.println("[2] Add Plugs");
        System.out.println("[3] Add Rotors");
        System.out.println("[4] Add Reflector");
        System.out.println("[5] Run");

        switch (intValidation(userInput.nextLine(), this::init)) {
            case 1:
                plugInit();
                break;
            case 2:
                plugInit();
                break;
            case 3:
                addMyRotor();
                break;
            case 4:
                addMyReflector();
                break;
            case 5:
                encode();
                break;
            default:
                System.err.println("Please enter a number from 1 - 5");
                init();
                break;
        }
    }

    void plugInit() {
        System.out.println("======Plugs======");
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
                break;
            case 3:
                addMyRotor();
                break;
            case 4:
                myPlugboard.showPlugs();
                plugInit();
                break;
            case 5:
                init();
                break;
            default:
                System.err.println("Pleas enter a number from 1 - 4");
                plugInit();
                break;
        }
    }

    void addPlug() {
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

    void addMyRotor() {
        System.out.println("Add rotor!");
    }

    void addMyReflector() {
        System.out.println("Add reflector!");
    }

    void encode() {
        System.out.println("Encode!");
    }

    public static void main(String[] args) {
        CommandLineApp myCommandLineApp = new CommandLineApp();

        myCommandLineApp.init();
    }
}
