import java.util.ArrayList;

public class Provincia extends Contribuinte{
    ArrayList<Distrito> _distritos = new ArrayList<Distrito>();

    @Override
    public int contribuicao() {
        int c = 0;
        for(Distrito d : _distritos) {
            c += d.contribuicao();
        }
        return c;
    }

    public void addDistrito(Distrito d) {
        _distritos.add(d);

    }

}