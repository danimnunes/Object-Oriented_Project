public class App {
    public static void main(String[] args) {
        AndGate2 gate1 = new AndGate2(true, false);
        AndGate2 gate2 = new AndGate2(true);
        AndGate3a gate3 = new AndGate3a(true, false, true);
        AndGate3a gate4 = new AndGate3a(true);
        System.out.println(gate3 == gate4 ? "true" : "false");
        System.out.println(gate4 == gate4 ? "true" : "false");
        System.out.println(gate3.getOutput());
        System.out.println(gate4);
    }
}