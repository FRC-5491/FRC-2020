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

import com.ctre.phoenix.motorcontrol.ControlMode;
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
    solenoid1.set(false);
    solenoid2.set(false);

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
}