public class TrafficLight {
    private State _state;

    public abstract class State {

        public abstract void panic();
        public abstract void tick();
        public  void on() {};
        public abstract void off();
        public abstract String status();

        protected void setState(State newState) {
            _state = newState;
        }

        protected TrafficLight getLight() {
            return TrafficLight.this;
        }


    }

}