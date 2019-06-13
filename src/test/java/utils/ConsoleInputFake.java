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
        message = message + System.lineSeparator();
        sending = true;
        mockInput = new ByteArrayInputStream(message.getBytes());
        System.setIn(mockInput);
    }

    public void sendMany(String ... messages) {
        String message = String.join(System.lineSeparator(), messages);
        send(message);
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