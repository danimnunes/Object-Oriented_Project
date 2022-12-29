import java.util.ArrayList;

public class Company extends Taxpayer {

    private ArrayList<Person> _employees = new ArrayList<Person>();

    public Company(int employees) {
        for (int i = 0; i < employees; i++)
            _employees.add(new Person());
    }

    public int size() {
        return _employees.size();
    }

    public Person getEmployee(int i) {
        return _employees.get(i);
    }

    @Override
    public double accept(FriendlyIRS irs) {
        return irs.taxCompany(this);
    }
}