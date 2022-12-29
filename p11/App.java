public class App {
    public static void main(String[] args) {
        Taxpayer c = new Company(5);
        Taxpayer r = new Region(5);
        Taxpayer p = new Person();

        FriendlyIRS v = new VanillaTaxes();

        System.out.println(c.accept(v));
        System.out.println(r.accept(v));
        System.out.println(p.accept(v));

    }
}