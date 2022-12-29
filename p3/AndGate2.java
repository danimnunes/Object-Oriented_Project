public class AndGate2 {
    private boolean a = false;
    private boolean b = false;

    public AndGate2() {

    }

    public AndGate2(boolean valor) {
        a = b = valor;
    }

    public AndGate2(boolean a, boolean b) {
        this.a = a;
        this.b = b;
    }

    public boolean getA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean getB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public void setGate(boolean valor) {
        a = b = valor;
    }

    public void setGate(boolean a, boolean b) {
        this.a = a;
        this.b = b;
    }

    public boolean getOutput() {
        return a && b;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AndGate2) {
            AndGate2 gate = (AndGate2) o;
            return a == gate.a && b == gate.b;
        }
        return false;
    }

    @Override
    public String toString() {
        return "A:" + a + "B:" + b;
    }


}