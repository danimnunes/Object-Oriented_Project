public class App {
    public static void main(String[] args) {

        Provincia provincia = new Provincia();
        Distrito distrito = new Distrito();

        Cidade cidade1 = new Cidade();
        Cidade cidade2 = new Cidade();

        Pessoa pessoa1 = new Pessoa();
        Pessoa pessoa2 = new Pessoa();

        Empresa empresa1 = new Empresa();
        Empresa empresa2 = new Empresa();

        provincia.addDistrito(distrito);
        distrito.addCidade(cidade1);
        distrito.addEmpresa(empresa1);
        cidade1.addPessoa(pessoa1);
        distrito.addCidade(cidade2);
        cidade2.addPessoa(pessoa2);
        cidade2.addEmpresa(empresa2);

        System.out.println(provincia.contribuicao());
        

        
    }
}