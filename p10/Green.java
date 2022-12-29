public class Green extends Ticking {

    public Green(TrafficLight light) {
        super(light);
    }

    @Override
    public void tick() {
        setState(new Yellow());
    }


}