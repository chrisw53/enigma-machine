public class Test {
    static void test1() {
        Rotor myRotor = new BasicRotor("I");
        System.out.println(myRotor instanceof BasicRotor);
    }

    public static void main(String[] args) {
        test1();
    }
}
