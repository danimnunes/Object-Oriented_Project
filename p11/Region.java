import java.util.ArrayList;

public class Region extends Taxpayer {

    private ArrayList<Company> _companies = new ArrayList<Company>();

    public Region(int companies) {
        for (int i = 0; i < companies; i++)
            _companies.add(new Company(3));
    }

    public int size() {
        return _companies.size();
    }

    public Company getCompany(int i) {
        return _companies.get(i);
    }

    @Override
    public double accept(FriendlyIRS irs) {
        return irs.taxRegion(this);
    }
}