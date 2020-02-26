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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveBase;
import frc.robot.DistanceSensor;
import frc.robot.PressureSensor;

public class Robot extends TimedRobot {

  //Pressure Sensors//
  PressureSensor tankPressure = new PressureSensor(0);
  PressureSensor regulatorPressure = new PressureSensor(1); 

  //Distance Sensors//
  DistanceSensor front = new DistanceSensor(2);
  DistanceSensor rear = new DistanceSensor(3);
  
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
  Solenoid solenoid3 = new Solenoid(5, 1);

  //Drive Base//
  public DriveBase robotDrivetrain = new DriveBase(leftOne, rightOne);
  
  
  //----------------------------------------------------------------------------

  //PS4 Controller//
  //Controller layout definition is as follows//
  //See controller_layout.png//
  Joystick driverStick = new Joystick(0);
  
//-----------------------------------------------------------------------------
  @Override
  public void robotInit() {
    
    updateDiagnostics();

    solenoid1.setPulseDuration(1.0);
    solenoid2.setPulseDuration(1.0);
    solenoid3.setPulseDuration(1.0);

    leftOne.configOpenloopRamp(2.0);
    leftTwo.configOpenloopRamp(2.0);
    rightOne.configOpenloopRamp(2.0);
    rightTwo.configOpenloopRamp(2.0);
    leftTwo.follow(leftOne);
    rightTwo.follow(rightOne);
    rightOne.setInverted(true);
    rightTwo.setInverted(true);
    

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

    if (driverStick.getRawButton(3)){
      solenoid3.set(true);
    }else{
      solenoid3.set(false);
    }
   
  }

//-----------------------------------------------------------------------------

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