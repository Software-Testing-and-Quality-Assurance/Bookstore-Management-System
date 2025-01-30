package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

// Wrapper class needed to make function mockable in unit testing since OOS has final methods
public class ObjectOutputStreamWrapper implements AutoCloseable {
    private final ObjectOutputStream objectOutputStream;

    public ObjectOutputStreamWrapper(OutputStream outputStream) throws IOException {
        this.objectOutputStream = new ObjectOutputStream(outputStream);
    }

    public void writeObject(Object obj) throws IOException {
        objectOutputStream.writeObject(obj);
    }

    @Override
    public void close() throws IOException {
        objectOutputStream.close();
    }
}
