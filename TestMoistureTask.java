package eecs_1021;

// Imports
import java.util.TimerTask;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import java.io.IOException;
import java.text.DecimalFormat;

public class TestMoistureTask extends TimerTask {
    private Pin pPin;                                 // declares all the input pin

    static private Pin mPin;
    static final int SENSOR_PIN = 15;
    static final int PUMP_PIN = 2;


    private static final DecimalFormat df = new DecimalFormat("0.00");

    public TestMoistureTask(Pin moisturePin, Pin pumpPin) {
        this.mPin = moisturePin;
        this.pPin = pumpPin;
    }

    public TestMoistureTask(IODevice myArduinoBoard) {
        this.pPin = myArduinoBoard.getPin(SENSOR_PIN);
        this.mPin = myArduinoBoard.getPin(PUMP_PIN);
    }

    public static String computeMoistureValue() {     // MoistVal gets calculated

        int VoltValue = (int) mPin.getValue();
        double voltageRange = 5.0; //
        double moistureValue = (VoltValue * voltageRange) / 1023.0;
        return df.format(moistureValue);
    }

    @Override     // determines time to pump water as per State Machine Algorithm
    public void run() { // prints out whether plant is wet or dry
        // if error occurs then throws error statement/pump not working
        try {
            double moistureValue = Double.parseDouble(TestMoistureTask.computeMoistureValue());

            if (moistureValue >= 3.4){
                System.out.println("Plant is dry!");
                pPin.setValue(1);
            }
            else if (moistureValue <= 3.3) {
                System.out.println("Plant is wet!");
                pPin.setValue(0);
            }
        }
        catch (IOException ex) {
            System.out.println("Pump not working.");
        }
    }
}