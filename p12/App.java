import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        Cat cat1 = new Cat(3, 10, "Tareco");



        try {
            
            CatOutputChannel channel = new CatOutputChannel(new DataOutputStream(new BufferedOutputStream(new FileOutputStream("cats.dat"))));

            channel.put(cat1);
            channel.close();
        }

        catch(IOException e) {
            e.printStackTrace();
        }

        
    }
}