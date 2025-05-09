package eecs_1021;

// Imports
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import org.firmata4j.IODeviceEventListener;

public class TestButton_Task implements IODeviceEventListener {
    private Pin buttonPin;
    public TestButton_Task (Pin buttonPin){
        this.buttonPin = buttonPin;
    }
    @Override
    public void onPinChange(IOEvent exit) {
        if (buttonPin.getValue() == 1){ // Closes program when button is pressed like a failsafe
            System.out.println("Ended Program.");
            System.exit(0);
        }
    }
    @Override
    public void onStart(IOEvent exit) {}
    @Override
    public void onStop(IOEvent exit) {}
    @Override
    public void onMessageReceive(IOEvent exit, String message) {}
}