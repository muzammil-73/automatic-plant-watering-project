# automatic-plant-watering-project
This project uses sensors (moisture) + Arduino (brain) to automate watering without human intervention, making it a smart, connected system. Key for AgTech/smart homes.
This project automatically waters a plant when it is dry and in need of water and automatically stops when the soil is wet. This would be essential as this would automate the watering process for plants, which removes the effort and need of watering the plant regularly. This would also make sure that the plant is always full of water and the moment the soil is dry and it’s in need, the automatic plant watering project would notice right away and provide it water until the soil is wet.


Here’s a list of things the system  is required to do:
-	Pump should pump water into the plant when soil is considered dry from the moisture sensor
-	Pump should not water when soil is considered wet from the moisture sensor 
-	OLED display should display moisture sensor voltage, whether pump is pumping or not, and if soil is wet or dry
-	Potentiometer should be operable and be able to be used at any time if you want stop the program immediately
-	System should graph senor voltage vs. time displaying points on a live active graph 
-	System should print out “read checks” every second to serial monitor
-	System should print out if plant is wet or dry
-	If an error occurs mid program, system should print out “Pump not working!” to serial monitor


Components List
-	Arduino
-	Moisture Sensor
-	Pump
-	Potentiometer
-	Plant
-	Mosfet
-	Battery


Procedure

A MOSFET and moisture sensor were linked to an Arduino Grove Board, and a Java program was created on IntelliJ IDEA so that the MOSFET sensor would activate the water pump to supply water when it detected that the soil was dry. 

When the moisture sensor detects that the soil is wet, it shuts off the water pump and waits for the soil to dry out before repeating the process. For CLO5 When the soil was dry, the coded Automatic Plant Watering system pumped water into it and when it was wet, it stopped pumping. 
