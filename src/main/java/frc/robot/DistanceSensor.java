package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
public class DistanceSensor {

  private static final double VOLTS_TO_DIST = 5.0;
  private AnalogInput mb1013;

    public DistanceSensor(int analogChannel){
    // (pins 3, 6 and 7 from sensor to analog input 0)
    mb1013 = new AnalogInput(analogChannel);
    }
    
    public double getVoltage() {
      return mb1013.getVoltage();
    }
    
    public double getDistance() {
      return getVoltage() * VOLTS_TO_DIST;
    }
  }