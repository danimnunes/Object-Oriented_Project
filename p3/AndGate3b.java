public class AndGate3b {
    private AndGate2 gate1;
    private AndGate2 gate2;
    
    public AndGate3b() {
        gate1 = new AndGate2();
        gate2 = new AndGate2(gate1.getOutput(), false);
    }

    public AndGate3b(boolean valor) {
        gate1 = new AndGate2(valor);
        gate2 = new AndGate2(gate1.getOutput(), valor); 
    }

    public AndGate3b(boolean a, boolean b, boolean c) {
        gate1 = new AndGate2(a, b);
        gate2 = new AndGate2(gate1.getOutput(), c);
    }

    public void setA(boolean a) {
        gate1.setA(a);
        gate2.setA(gate1.getOutput());

    }

    public boolean getOutput() {
        return gate2.getOutput();
    }
}