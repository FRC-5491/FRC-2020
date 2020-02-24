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
/*|                                                                      |*/
/*+----------------------------------------------------------------------|*/

//PACKAGE DECLARATION
package frc.robot;

//import edu.wpi.first.wpilibj.PWMVictorSPX;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

 /** 
  * //PWM fallback incase CANbus kerfucks itself//
  * PWMVictorSPX rightOne = new PWMVictorSPX(0);
  * PWMVictorSPX rightTwo = new PWMVictorSPX(1);
  * PWMVictorSPX leftOne = new PWMVictorSPX(2);
  * PWMVictorSPX leftTwo = new PWMVictorSPX(3);
  * 
  */
  
  
  //CANbus <--> multiplexed. ADDRESSES ARE VERY IMPORTANT!!//
  VictorSPX rightOne = new VictorSPX(1); //CAN ID: 1
  VictorSPX rightTwo = new VictorSPX(2); //CAN ID: 2
  VictorSPX leftOne = new VictorSPX(3);  //CAN ID: 3
  VictorSPX leftTwo = new VictorSPX(4);  //CAN ID: 4

  Solenoid solenoid1 = new Solenoid(0);
  Solenoid solenoid2 = new Solenoid(1);
  Solenoid solenoid3 = new Solenoid(6);
  Solenoid solenoid4 = new Solenoid(7);
  //Drive Base//
  public DifferentialDrive robot = new DifferentialDrive(leftOne, leftTwo, rightOne, rightTwo);
 
  //PS4 Controller//
  //Controller layout definition is as follows//
  //See controller_layout.png//
  XboxController driverStick = new XboxController(0);
  
  //Air Pressure Sensors//
  AnalogInput tankPressure = new AnalogInput(0); //Pressure readings
  AnalogInput regulatorPressure = new AnalogInput(1); //Pressure readings

  //Compressor -- CAN ID: 5//
  public static Compressor c = new Compressor(5);

  //PDP -- CAN ID: 0//
  public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);  

//-----------------------------------------------------------------------------
  @Override
  public void robotInit() {
    updateDiagVals();

    solenoid1.setPulseDuration(0.5);
    solenoid2.setPulseDuration(0.5);
    solenoid3.setPulseDuration(0.5);
    solenoid4.setPulseDuration(0.5);


    //SPEED CONTROLLER CONFIGURATION
    leftOne.configOpenloopRamp(2.0);
    leftTwo.configOpenloopRamp(2.0);
    rightOne.configOpenloopRamp(2.0);
    rightTwo.configOpenloopRamp(2.0);
    leftTwo.follow(leftOne);
    rightTwo.follow(rightOne);
    rightOne.setInverted(true);
    rightTwo.setInverted(true);

    //Compressor Configuration//
    c.setClosedLoopControl(true);
  }
//-----------------------------------------------------------------------------
  @Override
  public void autonomousInit() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
  @Override
  public void autonomousPeriodic() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
  @Override
  public void teleopInit() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
  @Override
  public void teleopPeriodic() {
    updateDiagVals();
  
  }
//-----------------------------------------------------------------------------
  @Override
  public void testInit() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
  @Override
  public void testPeriodic() {

    robot.arcadeDrive(driverStick.getY(), driverStick.getX());
    updateDiagVals();

    if(driverStick.getAButton()){
      solenoid1.startPulse();
    }
  }
//-----------------------------------------------------------------------------
/**
 * 
 */
  public void updateDiagVals(){

    SmartDashboard.putNumber("Tank PSI",airPressure(airPressure(tankPressure.getVoltage())));
    SmartDashboard.putNumber("Regulator PSI", airPressure(airPressure(regulatorPressure.getVoltage())));
    SmartDashboard.putBoolean("Compressor On", c.enabled());
    SmartDashboard.putBoolean("Pressure Switch", c.getPressureSwitchValue());
    SmartDashboard.putNumber("Compressor Current", c.getCompressorCurrent());
    SmartDashboard.putNumber("Right 1", (rightOne.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Right 2", (rightTwo.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Left 1", (leftOne.getMotorOutputPercent() * 10));
    SmartDashboard.putNumber("Left 2", (leftTwo.getMotorOutputPercent() * 10));
    
    

    SmartDashboard.putData(pdp);
    SmartDashboard.putData(robot);

    

  }

  //---------------------------------------------------------------------------
  /**
   * 
   */
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