public class VanillaTaxes extends FriendlyIRS {
    
    @Override 
    public double taxPerson(Person person) {
        return 1;
    }

    @Override 
    public double taxCompany(Company company) {
        double sum = 0;
        for(int i = 0; i < company.size(); i++) {
            sum += company.getEmployee(i).accept(this);
        }
        return sum;
    }

    @Override 
    public double taxRegion(Region region) {
        double sum = 0;
        for(int i = 0; i < region.size(); i++) {
            sum += region.getCompany(i).accept(this);
        }
        return sum;
    }
}