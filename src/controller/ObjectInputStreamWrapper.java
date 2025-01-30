package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

// Wrapper class needed to make function mockable in unit testing since OIS has final methods
public class ObjectInputStreamWrapper implements AutoCloseable {
    private final ObjectInputStream inputStream;

    public ObjectInputStreamWrapper(FileInputStream fileInputStream) throws IOException {
        this.inputStream = new ObjectInputStream(fileInputStream);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}