package utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ConsoleInputFake {

    private ByteArrayInputStream mockInput;
    private InputStream previous;
    private boolean sending;

    public void start() {
        if (sending) {
            return;
        }
        previous = System.in;
    }

    public void send(String message) {
        sending = true;
        mockInput = new ByteArrayInputStream(message.getBytes());
        System.setIn(mockInput);
    }

    public void stop() {
        if (sending) {
            System.setIn(previous);

            try {
                mockInput.close();   
            } catch (Exception e) {
                
            }
            mockInput = null;
            previous = null;
            sending = false;   
        }
    }
}