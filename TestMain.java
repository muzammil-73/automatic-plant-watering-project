package eecs_1021;

// Imports
import java.util.HashMap;
import java.util.Timer;
import org.firmata4j.I2CDevice;
import org.firmata4j.firmata.*;
import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;

public class TestMain {

    final static String USBPORT = "COM5";
    final static int SENSOR = 15;
    final static int PUMP = 2;
    final static int BUTTON = 6;


    public static void main(String[] args)
            throws IOException, InterruptedException
    {
        int point = 1;

        IODevice myArduinoBoard = new FirmataDevice(USBPORT);

        // Starts and Initializes my Arduino Board
        myArduinoBoard.start();
        myArduinoBoard.ensureInitializationIsDone();

        // Sets the pins for sensor, pump, button
        Pin moisturePin = myArduinoBoard.getPin(SENSOR);
        Pin pumpPin = myArduinoBoard.getPin(PUMP);
        Pin buttonPin = myArduinoBoard.getPin(BUTTON);

        // OLED
        I2CDevice i2cObject = myArduinoBoard.getI2CDevice((byte) 0x3C); // Use 0x3C for the Grove OLED
        SSD1306 theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64); // 128x64 OLED SSD1515

        // Initializes the OLED (SSD1306) object
        theOledObject.init();

        // Sets pins
        moisturePin.setMode(Pin.Mode.ANALOG);
        pumpPin.setMode(Pin.Mode.OUTPUT);
        buttonPin.setMode(Pin.Mode.INPUT);

        // Adds eventlistener for button
        myArduinoBoard.addEventListener(new TestButton_Task(buttonPin));

        // Moisture task class instantiating
        TestMoistureTask moisture_Task = new TestMoistureTask(moisturePin, pumpPin);
        var myTask = new TestMoistureTask(myArduinoBoard);
        new Timer().schedule(myTask, 0, 1000);

        // Initialize HashMap to store moisture data
        HashMap<Integer, Double> moistureData = new HashMap<>();

        //graph setup
        StdDraw.setXscale(-10, 100);
        StdDraw.setYscale(-1, 5);

        //pen radius
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);

        StdDraw.line(0, 0, 0, 5); //vertical line
        StdDraw.line(0, 0, 100, 0); //horizontal line

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(45, -1.0, "Time");
        StdDraw.text(-10, 3.0, "[V]");
        StdDraw.text(50, 5, "Sensor Voltage V.S. Time");

        // Y-values of Graph
        StdDraw.text(-6, 0.0, "0");
        StdDraw.text(-6, 2.4, "2.5");
        StdDraw.text(-6, 4.8, "5");


        while (true) {

            double moistureValue = Double.parseDouble(TestMoistureTask.computeMoistureValue());
            moistureData.put(point, moistureValue);

            //Statement to print point numbers and their corresponding moisture values
            System.out.println("Point #" + point + ":" + "; Moisture Value: " + moistureValue);

            StdDraw.text((double) point, moistureValue, "*");

            if (moistureValue >= 3.4) {
                theOledObject.clear();
                theOledObject.getCanvas().drawString(0, 0, "Pump on!");
                theOledObject.getCanvas().drawString(0, 10, "Soil is dry!");
                theOledObject.getCanvas().drawString(0, 20, "Moisture Value: " + moistureValue);
                theOledObject.display();
            }
            else if (moistureValue <= 3.3) {
                theOledObject.clear();
                theOledObject.getCanvas().drawString(0, 0, "Pump off!");
                theOledObject.getCanvas().drawString(0, 10, "Soil is wet!");
                theOledObject.getCanvas().drawString(0, 20, "Moisture Value: " + moistureValue);
                theOledObject.display();
            }

            // Set colour to red
            StdDraw.setPenColor(StdDraw.RED);

            // for noting down number of points on the graph
            if (point > 1) {

                Double previousValue = moistureData.get(point - 1);

                // Draws a live line on the graph
                if (previousValue != null) {

                    StdDraw.line(point - 1, previousValue, point, moistureValue);
                }
            }
            StdDraw.point(point, moistureValue);
            StdDraw.show();
            point++;
            Thread.sleep(1000); // Adjust sleep time if needed
        }
    }
}
