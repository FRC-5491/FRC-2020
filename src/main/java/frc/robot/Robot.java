/*+----------------------------------------------------------------------+*/
/*| Explanation of Code:                                                 |*/
/*| Written for FRC Team 5491's 2020 Game, INFINITE RECHARGE             |*/
/*| and used on their robot, Charles Manson                              |*/
/*+----------------------------------------------------------------------+*/
/*| Author(s): Jack Pirone                                               |*/
/*+----------------------------------------------------------------------+*/
/*| A special thanks to Maria P. and George R. for keeping me from going |*/
/*| insane while writing and documenting this code.                      |*/
/*+----------------------------------------------------------------------+*/
/*|                                                                      |*/
/*+----------------------------------------------------------------------|*/

//PACKAGE DECLARATION
package frc.robot;

//IMPORT STATEMENTS
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpiutil.math.MathUtil;

/** 
 * //FOR PWM fallback//
 * import edu.wpi.first.wpilibj.PWMVictorSPX;
 * import edu.wpi.first.wpilibj.SpeedControllerGroup;
 * import edu.wpi.first.wpilibj.drive.DifferentialDrive;
 */

//FOR CANbus//
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import java.util.Map;

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

  //Drive Base//
  public DifferentialDrive robot = new DifferentialDrive(leftOne, leftTwo);

  //PS4 Controller//
  //Controller layout definition is as follows//
  //See controller_layout.png//
  Joystick driverStick = new Joystick(0);
  
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
  @Override
  public void robotInit() {

    updateDiagVals();

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
<<<<<<< Autonomous

=======
>>>>>>> New Drive Code
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
  }
//-----------------------------------------------------------------------------
/**
 * 
 */
  public void updateDiagVals(){
    double tankPSI = airPressure(airPressure(tankPressure.getVoltage()));
    double regulatorPSI = airPressure(airPressure(regulatorPressure.getVoltage()));
    
    //SmartDashboard.putNumber(key, value)
    Shuffleboard.getTab("My Tab").add("Tank PSI", tankPSI).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("Min", 0, "Max", 150, "Show value", 1)).getEntry();
    Shuffleboard.getTab("My Tab").add("Regulator PSI", regulatorPSI).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("Min", 0, "Max", 150, "Show value", 1)).getEntry();
  }

  /**
  //---------------------------------------------------------------------------
   * 
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