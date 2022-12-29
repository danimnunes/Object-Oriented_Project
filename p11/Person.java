public class Person extends Taxpayer {

    @Override
    public double accept(FriendlyIRS irs) {
        return irs.taxPerson(this);
    }
}