import java.io.DataOutputStream;
import java.io.IOException;

public class CatOutputChannel {
    private DataOutputStream _stream;
    
    public CatOutputChannel(DataOutputStream stream) {
        _stream = stream;
    }

    public void put(Cat cat) throws IOException {

        _stream.writeInt(cat.getAge());
        _stream.writeFloat(cat.getWeight());
        _stream.writeUTF(cat.getName());

    }

    public void close() throws IOException {
        _stream.close();
    }
}