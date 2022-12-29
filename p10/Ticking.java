public class Ticking extends TrafficLight.State {

    public Ticking(TrafficLight light) {
        light.super();
    }

    @Override
    public void panic() {
        setState(new Panic());
    }

    @Override
    public void off() {
        setState(new Blinking());
    }

    
}