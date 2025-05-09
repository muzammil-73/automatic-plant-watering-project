package eecs_1021;

import java.util.HashMap;
import java.util.Timer;
import org.firmata4j.I2CDevice;
import org.firmata4j.firmata.*;
import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
public class MainProject {

    static final String COM_PORT = "COM5";

    static final int SENSOR_PIN = 15;
    static final int PUMP_PIN = 7;

    public static void main(String[] args)
        throws IOException, InterruptedException {


        IODevice myArduinoBoard = new FirmataDevice(COM_PORT);
        myArduinoBoard.start();
        myArduinoBoard.ensureInitializationIsDone();

        Pin sensorPin = myArduinoBoard.getPin(SENSOR_PIN);
        //Pin buttonPin = myArduinoBoard.getPin(BUTTON_PIN);
        Pin pumpPin = myArduinoBoard.getPin(PUMP_PIN);

        sensorPin.setMode(Pin.Mode.ANALOG);
        pumpPin.setMode(Pin.Mode.OUTPUT);

        I2CDevice i2cObject = myArduinoBoard.getI2CDevice((byte) 0x3C); // Use the 0x3C I2C address for the OLED
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // Initializes the OLED object and also contains the size of the display




    }




}
