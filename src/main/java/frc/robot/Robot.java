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

//IMPORT STATEMENTS
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< Autonomous
=======

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
>>>>>>> Fixed issues created from merging
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveBase;
import frc.robot.PressureSensor;

public class Robot extends TimedRobot {

  //Pressure Sensors//
  PressureSensor tankPressure = new PressureSensor(0);
  PressureSensor regulatorPressure = new PressureSensor(1); 

  //Distance Sensors//
  AnalogInput front = new AnalogInput(2);
  AnalogInput rear = new AnalogInput(3);
  
  //Driver Joystick//
  Joystick driverStick = new Joystick(0);

  //CANbus <--> multiplexed. ADDRESSES ARE VERY IMPORTANT!!//
  PowerDistributionPanel pdp = new PowerDistributionPanel(0); //CAN ID: 0
  VictorSPX rightOne = new VictorSPX(1); //CAN ID: 1
  VictorSPX rightTwo = new VictorSPX(2); //CAN ID: 2
  VictorSPX leftOne = new VictorSPX(3);  //CAN ID: 3
  VictorSPX leftTwo = new VictorSPX(4);  //CAN ID: 4
  public static Compressor c = new Compressor(6);      //CAN ID: 5

  //Solenoids//
  Solenoid solenoid1 = new Solenoid(5, 7);
  Solenoid solenoid2 = new Solenoid(5, 6);

  //Drive Base//
  public DriveBase robotDrivetrain = new DriveBase(leftOne, rightOne);
<<<<<<< Autonomous
  
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

<<<<<<< Autonomous
>>>>>>> Bug Fixes, Documentation Updates, Renamed Files, Added changelog
=======
  //PS4 Controller//
  //Controller layout definition is as follows//
  //See controller_layout.png//
  Joystick driverStick = new Joystick(0);
  
=======
>>>>>>> Fixed issues created from merging
//-----------------------------------------------------------------------------
>>>>>>> Undid a mistake
  @Override
  public void robotInit() {
    updateDiagnostics();

    leftOne.configOpenloopRamp(1.75);
    leftTwo.configOpenloopRamp(1.75);
    rightOne.configOpenloopRamp(1.75);
    rightTwo.configOpenloopRamp(1.75);
    leftTwo.follow(leftOne);
    rightTwo.follow(rightOne);
    rightOne.setInverted(true);
    rightTwo.setInverted(true);
<<<<<<< Autonomous
    c.setClosedLoopControl(true);
<<<<<<< Autonomous
<<<<<<< Autonomous

=======
>>>>>>> New Drive Code
=======
    
=======
    solenoid1.set(false);
    solenoid2.set(false);
>>>>>>> Autonomous + Competition Changes

>>>>>>> Undid a mistake
  }

  //----------------------------------------------------------------------------

  @Override
  public void autonomousInit() {
    updateDiagnostics();
    solenoid1.set(false);
    solenoid2.set(false);
  }

  //----------------------------------------------------------------------------

  @Override
  public void autonomousPeriodic() {
    updateDiagnostics();
    while(front.getVoltage() > 0.49) {
      solenoid1.set(false);
      solenoid2.set(false);
      leftOne.set(ControlMode.PercentOutput, 0.35);
      rightOne.set(ControlMode.PercentOutput, 0.35);
      
    }
    leftOne.set(ControlMode.PercentOutput, 0.0);
    rightOne.set(ControlMode.PercentOutput, 0.0);
      solenoid1.set(true);
      solenoid2.set(true);
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
    robotDrivetrain.arcadeDrive(((driverStick.getY() * -1)*0.95), (driverStick.getX()* 0.75));
  
    if (driverStick.getRawButton(2)){
      solenoid1.set(true);
    }else{
      solenoid1.set(false);
    }

    if (driverStick.getRawButton(1)){
      solenoid2.set(true);
    }else{
      solenoid2.set(false);
    }
  
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

    robotDrivetrain.arcadeDrive(driverStick.getY() * -1, driverStick.getX());
    
    if (driverStick.getRawButton(2)){
      solenoid1.set(true);
    }else{
      solenoid1.set(false);
    }

    if (driverStick.getRawButton(1)){
      solenoid2.set(true);
    }else{
      solenoid2.set(false);
    }

    
  }

//-----------------------------------------------------------------------------

<<<<<<< Autonomous
/**Updates the data values on the smartdashboard.
 * The Shuffleboard application can also view these
 * values.
 * 
 */
  public void updateDiagVals(){
    ch0Amps = pdp.getCurrent(0);
    ch1Amps = pdp.getCurrent(1);
    ch2Amps = pdp.getCurrent(2);
    ch3Amps = pdp.getCurrent(3);

    SmartDashboard.putData(pdp);
  }
<<<<<<< Autonomous
<<<<<<< Autonomous

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
=======
>>>>>>> Undid a mistake
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
<<<<<<< Autonomous
=======
>>>>>>> See Changelog
=======
>>>>>>> Undid a mistake
=======
 /**Updates the data values on the smartdashboard.
  * The Shuffleboard application can also view these
  * values.
  * 
  */
  public void updateDiagnostics(){
    double tankPSI = tankPressure.airPressure();
    double regulatorPSI = regulatorPressure.airPressure();
    SmartDashboard.putNumber("distance", front.getVoltage());
    SmartDashboard.putData(pdp);
    SmartDashboard.putNumber("Left 1", leftOne.getMotorOutputPercent());
    SmartDashboard.putNumber("Left 2", leftTwo.getMotorOutputPercent());
    SmartDashboard.putNumber("Right 2", rightTwo.getMotorOutputPercent());
    SmartDashboard.putNumber("Right 1", rightOne.getMotorOutputPercent());

    SmartDashboard.putNumber("Tank PSI", tankPSI);
    SmartDashboard.putNumber("Regulator PSI", regulatorPSI);
    SmartDashboard.putBoolean("Front Piston", solenoid1.get());
    SmartDashboard.putBoolean("Rear Piston", solenoid2.get());
  }
>>>>>>> Fixed issues created from merging
}