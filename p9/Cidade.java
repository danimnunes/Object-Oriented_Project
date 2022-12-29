import java.util.ArrayList;

public class Cidade extends Contribuinte{
    ArrayList<Pessoa> _pessoas = new ArrayList<Pessoa>();
    ArrayList<Empresa> _empresas = new ArrayList<Empresa>();

    @Override
    public int contribuicao() {
        int c = 0;
        for(Pessoa p : _pessoas) {
            c += p.contribuicao();
        }
        for(Empresa e : _empresas) {
            c += e.contribuicao();
        }
        return c;
    }

    public void addPessoa(Pessoa p) {
        _pessoas.add(p);

    }

    public void addEmpresa(Empresa e) {
        _empresas.add(e);

    }
}