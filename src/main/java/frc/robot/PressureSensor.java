package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor {
    
    private AnalogInput pressureSensor;

    public PressureSensor(int analogChannel){
        pressureSensor = new AnalogInput(analogChannel);
    }
    
    /**
     * This method calculates the air pressure in PSI from the REV Robotics PSI sensor.
     * 
     * @param aPv The voltage of the air pressure sensor.
     * @return The air pressure in PSI.
     */
    public double airPressure() {
        double aP;
        double math;
        double maath;
        math = pressureSensor.getVoltage() / 5;
        maath = 250 * math;
        aP = maath - 25;
        return aP;
    }
}