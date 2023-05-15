package connection;

import java.io.*;

public class SerializationManager {
    public static byte[] serialize(Object obj){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(obj);
            return baos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object deserialize(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);

        return ois.readObject();
    }

    public static boolean isEmpty(byte[] arr){
        for (byte n : arr)
            if (n != 0)
                return false;
        return true;
    }
}