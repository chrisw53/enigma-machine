import java.util.Scanner;
import java.util.function.Function;

/*
I decided to have this class to extend to enigma machine so it inherits
all the methods from the EnigmaMachine class
*/
public class CommandLineApp extends EnigmaMachine {
    // This takes in the user input from the command line
    private Scanner userInput = new Scanner(System.in);

    /**
     * This is pulled out as its own independent method because I realized
     * that I was doing intValidation a whole lot throughout the app. Pulling
     * this out to its own method makes the code a lot easier to read. The
     * method itself is very simple, it just takes in a String input, and
     * check if it can be converted to an integer. If so, then do it. If not,
     * then call the callback. I'm using runnable here so I can pass in a method
     * as a parameter and run it later in the method.
     * @param input A string that can or cannot be converted to integer
     * @param callback A runnable that is called after the string input fails
     *                 the integer check
     * @return An integer that's converted from the string input
     */
    protected Integer intValidation(String input, Runnable callback) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("[FAIL] Please enter a valid integer");
            callback.run();
        }

        /* This is to circumvent the requirement to have a returned integer.
        This does absolutely nothing otherwise.
         */
        return 0;
    }

    /**
     * Similarly to the reason provided above, I extracted out this validation
     * into its own method to improve code readability. It checks for two
     * things. First, whether the input is not an empty string, and second,
     * whether the string is composed of a single letter. If the input satisfies
     * both of these requirements, then it is returned. Otherwise, the runnable
     * callback is ran.
     * @param input A string that can or cannot be converted to a single character
     * @param callback A runnable that may be called if the input does not satisfy
     *                 the requirements.
     * @return A character that's converted from an input string.
     */
    protected char charValidation(String input, Runnable callback) {
        // This is just to initialize the variable. It will be overrode.
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

        /*
        Once again, his is to circumvent the requirement to have a returned char.
        This does absolutely nothing otherwise.
         */
        return 'A';
    }

    /**
     * This is the main menu of the command line app. This kicks the user
     * to different functionality based on their choice. It also allows
     * user to exit the app.
     */
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
                /*
                This checks whether there're a reflector and some rotors before
                the user is allowed to encode messages.
                 */
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

    /**
     * This method displays and navigates the user through the plugs menu.
     */
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

    /**
     * This method handles adding plugs to the plugboard. It first checks if the two
     * ends are the same; then, it checks whether the plug user wants to add conflicts
     * with any other existing plugs by utilizing the boolean returned from addPlug method
     * on the Plugboard class. If the two ends pass all of these checks, then the plugs
     * are added to the plugboard.
     */
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

    /**
     * This method displays and navigates user through the rotor menu.
     */
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
                // Selection between basic or turnover rotor and add them
                selectRotorKind();
                break;
            case 2:
                /*
                This checks if there are rotors already added to prevent
                the app from crashing by asking for a rotor where there is
                no rotor.
                 */
                if (getRotor(0) != null) {
                    // Modifies any rotor's initial position
                    setRotorPosition();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 3:
                /*
                This checks if there are rotors already added to prevent
                the app from crashing by asking for a rotor where there is
                no rotor.
                 */
                if (getRotor(0) != null) {
                    setRotorType();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 4:
                /*
                This checks if there are rotors already added to prevent
                the app from crashing by asking for a rotor where there is
                no rotor.
                 */
                if (getRotor(0) != null) {
                    // Displays existing rotors
                    showRotors();
                    rotorInit();
                } else {
                    System.err.println("\n[FAIL] Please add rotors first\n");
                    rotorInit();
                }
                break;
            case 5:
                // Reflector menu
                reflectorInit();
                break;
            case 6:
                // Main menu
                init();
                break;
            default:
                System.err.println("\n[FAIL] Please enter a number from 1 - 5\n");
                rotorInit();
                break;
        }
    }

    /**
     * This method handles selecting whether the user wants to add basic rotors
     * or turnover rotors, and proceed to call the methods that add them. This
     * is extracted out as its own method because the process of adding a basic
     * rotor and adding a turnover rotor is quite different, and thereby requiring
     * their own separate methods. Therefore, there must be a method that determines
     * which one of the add rotor method to call.
     */
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
                /*
                Since turnover rotor has to be made aware of its nextRotor,
                it is easier to handle the entire adding logic in its own
                method rather than having a more generic method that adds
                one rotor at a time and using a for loop to call it 3 times
                 */
                addTurnoverRotor();
                rotorInit();
                break;
            default:
                System.err.println("\n[FAIL] Please enter either 1 or 2\n");
                selectRotorKind();
                break;
        }
    }

    /**
     * This method handles taking in user inputs about the type and
     * initial position of the rotor they are about to create. Since
     * this is needed for both basic rotor and turnover rotor, I pulled
     * it out as its own method to avoid bloated code and improve code
     * readability.
     * @param rotorNum An integer that represents which rotor does the
     *                 config belongs to. This goes from 1 - 3.
     * @return The method returns a string that has this format:
     * "type position rotorNum".
     */
    protected String rotorConfig(int rotorNum) {
        /*
        This lambda function here is used to create a new runnable
        that runs a method with parameter. This has to be done
        because rotorConfig has an integer parameter rotorNum.
         */
        Function<Integer, Runnable> createRunnable =
                input -> new Runnable(){
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

        /*
        This checks if the position entered is not negative nor larger
        than the rotor size (26)
         */
        if (position < 26 && position >= 0) {
            output += " " + position + " " + rotorNum;
        } else {
            System.err.println("\n[FAIL] Please enter a number from 0 to 25\n");
            rotorConfig(rotorNum);
        }

        return output;
    }

    /**
     * This method handles adding basic rotors to the rotor list using the
     * configs from the method above. This is made to be more generic, and
     * only adds one basic rotor per all.
     * @param config The string that specifies the type, position and
     *               the rotor number.
     */
    protected void addBasicRotor(String config) {
        String rotorType = config.split(" ")[0];
        int rotorPosition = Integer.parseInt(config.split(" ")[1]);
        int rotorNum = Integer.parseInt(config.split(" ")[2]);

        BasicRotor myRotor = new BasicRotor(rotorType);
        myRotor.setPosition(rotorPosition);
        addRotor(myRotor, (rotorNum - 1));

        /*
        No need for error catching here since all the user configs are
        pre-checked by the rotorConfig() method.
         */
        System.out.println("\n[SUCCESS] Rotor " + rotorNum + " is added \n");
    }

    /**
     * This method handles adding turnover rotors to the rotor list. It
     * differs from the addBasicRotor, since this handles adding all three
     * turnover rotors in one call.
     */
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

        /*
        No need for error catching here since all the user configs are
        pre-checked by the rotorConfig() method.
         */
        System.out.println("\n[SUCCESS] All rotors are added!\n");
    }

    /**
     * This method allows user to modify the initial position of the rotor
     * by calling setPosition() method in the EnigmaMachine class with the
     * rotor the user selected and the initial position the user wants to
     * change the rotor to. Since there're multiple nested conditions, if
     * statements is more readable than switch statement.
     */
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

    /**
     * This method allows the user to change the type of any existing rotor.
     * In reality, since I purposely made it so once created, a rotor's type
     * cannot be modified, to change the rotor type would require making a new
     * rotor that has the same initial position as the old rotor and a new type,
     * and replace the old rotor with this new rotor. This is exactly what happens
     * here. After user inputs what new type they would like to change the rotor to,
     * we record the old rotor's position and create a new rotor with the old
     * position and the new type to replace it.
     */
    protected void setRotorType() {
        System.out.println("Which rotor would you like to change?");
        int rotorInput = (intValidation(userInput.nextLine(), this::setRotorPosition) - 1);
        // This is where the old position is stored
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

            // checks if the old rotor is a basic or turnover rotor
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

    /**
     * This method displays and navigates the user around the reflector menu.
     */
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
                /*
                This prevents a potential crash by checking whether there is
                already an existing reflector before asking for its info
                 */
                if (getReflector() != null) {
                    System.out.println("\nYour current reflector is of type " + getReflector().name + "\n");
                    reflectorInit();
                } else {
                    System.err.println("\n[FAIL] Please add the reflector first\n");
                    reflectorInit();
                }
                break;
            case 3:
                /*
                This checks whether there're a reflector and some rotors before
                the user is allowed to encode messages.
                 */
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

    /**
     * This method handles adding a reflector to the Enigma machine. The
     * only thing to check here is whether the type of the reflector is
     * either I or II.
     */
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

    /**
     * This method displays and navigates user around the encode menu
     */
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

    /**
     * This method handles encoding messages. It asks the user for a
     * message to encode, and breaks that string down to individual
     * characters just like what I did in the TestHarness class. It then
     * checks whether the rotors are turnover rotors or basic rotors and
     * encode accordingly (turnover rotors needs an additional step of
     * calling rotateTurnover() on the left most rotor.
     */
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
