public class AndGate3a extends AndGate2 {
    private boolean c = false;
    
    public AndGate3a() {

    }

    public AndGate3a(boolean valor) {
        super(valor);
        c = valor;
    }

    public AndGate3a(boolean a, boolean b, boolean c) {
        super(a, b);
        this.c = c;
    }

    public boolean getC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    @Override
    public void setGate(boolean valor) {
        super.setGate(valor);
        c = valor;
    }

    public void setGate(boolean a, boolean b, boolean c) {
        super.setGate(a, b);
        this.c = c;
    }

    @Override
    public boolean getOutput() {
        return super.getOutput() && c;

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AndGate3a) {
            AndGate3a gate = (AndGate3a) o;
            return super.equals(o) && c == gate.c;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "C:" + c;
    }

}