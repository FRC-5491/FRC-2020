/*+----------------------------------------------------------------------+*/
/*| Explanation of Code:                                                 |*/
/*| Written for FRC Team 5491's 2020 Game, INFINITE RECHARGE             |*/
/*| and used on their robot, Charles Manson                              |*/
/*+----------------------------------------------------------------------+*/
/*| Author(s): Jack Pirone                                               |*/
/*+----------------------------------------------------------------------+*/
/*| A special thanks to Maria P. for keeping me from going               |*/
/*| insane while writing and documenting this code.                      |*/
/*+----------------------------------------------------------------------+*/
/*| Hours spent on this code: 15                                         |*/
/*+----------------------------------------------------------------------|*/

//PACKAGE DECLARATION
package frc.robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  //Air Pressure Readings//
  AnalogInput tankPressure = new AnalogInput(0); //Pressure readings
  AnalogInput regulatorPressure = new AnalogInput(1); //Pressure readings

  //Driver Joystick//
  XboxController driverStick = new XboxController(0);

  //CANbus <--> multiplexed. ADDRESSES ARE VERY IMPORTANT!!//
  PowerDistributionPanel pdp = new PowerDistributionPanel(0); //CAN ID: 0
  VictorSPX rightOne = new VictorSPX(1); //CAN ID: 1
  VictorSPX rightTwo = new VictorSPX(2); //CAN ID: 2
  VictorSPX leftOne = new VictorSPX(3);  //CAN ID: 3
  VictorSPX leftTwo = new VictorSPX(4);  //CAN ID: 4
  Compressor c = new Compressor(5);      //CAN ID: 5

  //Solenoids//
  Solenoid solenoid1 = new Solenoid(6, 0);
  Solenoid solenoid2 = new Solenoid(6, 1);
  Solenoid solenoid3 = new Solenoid(6, 6);
  Solenoid solenoid4 = new Solenoid(6, 7);

  //Drive Base//
  public DriveBase robotDrivetrain = new DriveBase(leftOne, rightOne);
  
<<<<<<< Autonomous
<<<<<<< Autonomous
  AnalogInput tankPressure = new AnalogInput(0); //Pressure readings
  AnalogInput regulatorPressure = new AnalogInput(1); //Pressure readings

  public static Compressor c = new Compressor(0);
=======
  //Air Pressure Sensors//
  AnalogInput tankPressure = new AnalogInput(0); //Pressure readings
  AnalogInput regulatorPressure = new AnalogInput(1); //Pressure readings

  //Compressor -- CAN ID: 5//
  public static Compressor c = new Compressor(5);

  //PDP -- CAN ID: 0//
  public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);  

>>>>>>> New Drive Code
//-----------------------------------------------------------------------------
=======
  
  //----------------------------------------------------------------------------

>>>>>>> Bug Fixes, Documentation Updates, Renamed Files, Added changelog
  @Override
  public void robotInit() {
    
    updateDiagnostics();

    solenoid1.setPulseDuration(0.5);
    solenoid2.setPulseDuration(0.5);
    solenoid3.setPulseDuration(0.5);
    solenoid4.setPulseDuration(0.5);

    leftOne.configOpenloopRamp(2.0);
    leftTwo.configOpenloopRamp(2.0);
    rightOne.configOpenloopRamp(2.0);
    rightTwo.configOpenloopRamp(2.0);
    leftTwo.follow(leftOne);
    rightTwo.follow(rightOne);
    rightOne.setInverted(true);
    rightTwo.setInverted(true);

    c.setClosedLoopControl(true);
<<<<<<< Autonomous

=======
>>>>>>> New Drive Code
  }

  //----------------------------------------------------------------------------

  @Override
  public void autonomousInit() {
    updateDiagnostics();
  }

  //----------------------------------------------------------------------------

  @Override
  public void autonomousPeriodic() {
    updateDiagnostics();
  }

  //----------------------------------------------------------------------------

  @Override
  public void teleopInit() {
    updateDiagnostics();
  }

//-----------------------------------------------------------------------------

  @Override
  public void teleopPeriodic() {
    updateDiagnostics();
  
  }

//-----------------------------------------------------------------------------

  @Override
  public void testInit() {
    updateDiagnostics();
  }

//-----------------------------------------------------------------------------

  @Override
  public void testPeriodic() {
    updateDiagnostics();

    robotDrivetrain.arcadeDrive(driverStick.getY(), driverStick.getX());
    
   
  }

//-----------------------------------------------------------------------------

/**Updates the data values on the smartdashboard.
 * The Shuffleboard application can also view these
 * values.
 * 
 */
  public void updateDiagnostics(){

    SmartDashboard.putNumber("Tank PSI", airPressure(tankPressure.getAverageValue()));
    SmartDashboard.putNumber("Regulator PSI", airPressure(regulatorPressure.getVoltage()));
    SmartDashboard.putBoolean("Compressor On", c.enabled());
    SmartDashboard.putBoolean("Pressure Switch", c.getPressureSwitchValue());
    SmartDashboard.putNumber("Compressor Current", c.getCompressorCurrent());
    SmartDashboard.putNumber("Right 1", (rightOne.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Right 2", (rightTwo.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Left 1", (leftOne.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Left 2", (leftTwo.getMotorOutputPercent() * 10));
    
    SmartDashboard.putData(pdp);
    SmartDashboard.putData(robotDrivetrain);
  }

<<<<<<< Autonomous
  /**
  //---------------------------------------------------------------------------
   * 
=======
  //---------------------------------------------------------------------------

  /**
   * This method calculates the air pressure in PSI from the REV Robotics PSI sensor.
   * 
   * @param aPv The voltage of the air pressure sensor.
   * @return The air pressure in PSI.
   */
>>>>>>> Bug Fixes, Documentation Updates, Renamed Files, Added changelog
  public double airPressure(double aPv) {
   */
    double aP;
    double math;
    double maath;
    maath = 250 * math;
    math = aPv / 5;
    return aP;
    aP = maath - 25;
  }
}

//---------------------------------------------------------------------------
public double airPressure(double aPv) {
  double aP;
  double math;
  double maath;
  math = aPv / 5;
  maath = 250 * math;
  aP = maath - 25;
  return aP;
}
}