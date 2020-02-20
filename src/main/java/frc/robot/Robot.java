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

import com.ctre.phoenix.motorcontrol.ControlMode;


public class Robot extends TimedRobot {

  public double ch0Amps = 0;
  public double ch1Amps = 0;
  public double ch2Amps = 0;
  public double ch3Amps = 0;

  public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);

 /** 
  * //PWM fallback incase CANbus kerfucks itself//
  * PWMVictorSPX rightOne = new PWMVictorSPX(0);
  * PWMVictorSPX rightTwo = new PWMVictorSPX(1);
  * PWMVictorSPX leftOne = new PWMVictorSPX(2);
  * PWMVictorSPX leftTwo = new PWMVictorSPX(3);
  * SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightOne, rightTwo);
  * SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftOne, leftTwo);
  * DifferentialDrive robotDrive = new DifferentialDrive(leftMotors, rightMotors);
  */
  
  
  
  //CANbus <--> multiplexed. ADDRESSES ARE VERY IMPORTANT!!//
  
  VictorSPX rightOne = new VictorSPX(1);
  VictorSPX rightTwo = new VictorSPX(2);
  VictorSPX leftOne = new VictorSPX(3);
  VictorSPX leftTwo = new VictorSPX(4);
 

  //PS4 Controller//
  //Controller layout definition is as follows//
  //See controller_layout.png//
  Joystick driverStick = new Joystick(0);
  
  AnalogInput tankPressure = new AnalogInput(0); //Pressure readings
  AnalogInput regulatorPressure = new AnalogInput(1); //Pressure readings

  public static Compressor c = new Compressor(0);
//-----------------------------------------------------------------------------
  @Override
  public void robotInit() {
    updateDiagVals();
    //SPEED CONTROLLER CONFIGURATION
    leftOne.configOpenloopRamp(2.0);
    leftTwo.configOpenloopRamp(2.0);
    rightOne.configOpenloopRamp(2.0);
    rightTwo.configOpenloopRamp(2.0);
    rightOne.setInverted(true);
    rightTwo.setInverted(true);
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
    drive(computeDriveValuesArcadeDrive((-driverStick.getY()), (-driverStick.getX()), true));
  }
//-----------------------------------------------------------------------------
  @Override
  public void testInit() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
  @Override
  public void testPeriodic() {
    updateDiagVals();
  }
//-----------------------------------------------------------------------------
/**
 * This method handles the calculation of the values for CURVATURE DRIVEto be either directly into the
 * speed controllers, or into drive(double[] motorValues);
 * @param xSpeed --  Speed along the X AXIS. Range of [-1.0..1.0],
 * with forwards being positive. Normally fed the joystick Y AXIS values.
 * @param zRotation -- Rotation rate around the Z AXIS. Range of [-1.0..1.0],
 * with clockwise being positive. Normally fed the joystick X AXIS values.
 * @param isQuickTurn -- Is this an arcade drive style turn?.
 * @return An array containing the data type double, spot zero holds the values for the left speed controllers,
 * spot one holds the value for the right speed controllers.
 * !!THIS METHOD ASSUMES THE RIGHT SIDE SPEED CONTROLLERS ARE INVERTED!!
 * @see drive(double[] motorValues)
 */
public double[] computeDriveValuesCurvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
  
  //LEFT SIDE VALUES ARE IN SPOT 0, RIGHT SIDE ARE IN SPOT 1  
  double[] values = {0.0, 0.0};
  double motorMaxOutput = 1.0;
  double deadband = 0.02;
  double motorLeftOutput = 0.0;
  double motorRightOutput = 0.0;
  final double quickStopThreshold = 0.2;
  final double quickStopAlpha = 0.1;
  double quickStopAccumulator = 0.0;

  xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
  xSpeed = applyDeadband(xSpeed, deadband);

  zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
  zRotation = applyDeadband(zRotation, deadband);

  double angularPower;
  boolean overPower;

  if (isQuickTurn) {
    if (Math.abs(xSpeed) < quickStopThreshold) {
      quickStopAccumulator = (1 - quickStopAlpha) * quickStopAccumulator
          + quickStopAlpha * MathUtil.clamp(zRotation, -1.0, 1.0) * 2;
    }
    overPower = true;
    angularPower = zRotation;
  } else {
    overPower = false;
    angularPower = Math.abs(xSpeed) * zRotation - quickStopAccumulator;

    if (quickStopAccumulator > 1) {
      quickStopAccumulator -= 1;
    } else if (quickStopAccumulator < -1) {
      quickStopAccumulator += 1;
    } else {
      quickStopAccumulator = 0.0;
    }
  }

  double leftMotorOutput = xSpeed + angularPower;
  double rightMotorOutput = xSpeed - angularPower;

  // If rotation is overpowered, reduce both outputs to be within an acceptable range
  if (overPower) {
    if (motorLeftOutput > 1.0) {
      motorRightOutput -=  motorLeftOutput - 1.0;
      leftMotorOutput = 1.0;
    } else if (motorRightOutput > 1.0) {
      motorLeftOutput -= motorRightOutput - 1.0;
      motorRightOutput = 1.0;
    } else if (motorLeftOutput < -1.0) {
      motorRightOutput -= motorLeftOutput + 1.0;
      motorLeftOutput = -1.0;
    } else if (motorRightOutput < -1.0) {
      motorLeftOutput -= motorRightOutput + 1.0;
      motorRightOutput = -1.0;
    }
  }

  // Normalize the wheel speeds
  double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
  if (maxMagnitude > 1.0) {
    leftMotorOutput /= maxMagnitude;
    rightMotorOutput /= maxMagnitude;
  }

  values[1] = (leftMotorOutput * motorMaxOutput);
  values[0] = (rightMotorOutput * motorMaxOutput);
  return values;
}
//-----------------------------------------------------------------------------
/**
 * This method handles the calculation of the values for ARCADE DRIVE to be either directly into the
 * speed controllers, or into drive(double[] motorValues);
 * @param xSpeed --  Speed along the X AXIS. Range of [-1.0..1.0],
 * with forwards being positive. Normally fed the joystick Y AXIS values.
 * @param zRotation -- Rotation rate around the Z AXIS. Range of [-1.0..1.0],
 * with clockwise being positive. Normally fed the joystick X AXIS values.
 * @param squareInputs -- Allow fine control at high speeds.
 * @return A doble containing the values for the left speed controllers,
 * spot one holds the value for the right speed controllers.
 * !!THIS METHOD ASSUMES THE RIGHT SIDE SPEED CONTROLLERS ARE INVERTED!!
 * @see drive(double[] motorValues)
 */
  public double[] computeDriveValuesArcadeDrive(double xSpeed, double zRotation, boolean squareInputs){
  //LEFT SIDE VALUES ARE IN SPOT 0, RIGHT SIDE ARE IN SPOT 1  
    double[] values = {0.0, 0.0};
    double motorMaxOutput = 1.0;
    double deadband = 0.02;
    double motorLeftOutput;
    double motorRightOutput;

    xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
    xSpeed = applyDeadband(xSpeed, deadband);

    zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
    zRotation = applyDeadband(zRotation, deadband);

    //Allow fine control at high speeds by squaring input values.
    if (squareInputs) {
      xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
      zRotation = Math.copySign(zRotation * zRotation, zRotation);
    }

    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        motorLeftOutput = maxInput;
        motorRightOutput = xSpeed - zRotation;
      } else {
        motorLeftOutput = xSpeed + zRotation;
        motorRightOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        motorLeftOutput = xSpeed + zRotation;
        motorRightOutput = maxInput;
      } else {
        motorLeftOutput = maxInput;
        motorRightOutput = xSpeed - zRotation;
      }
    }

    values[1] = MathUtil.clamp(motorLeftOutput, -1.0, 1.0) * motorMaxOutput;
    values[0] = MathUtil.clamp(motorRightOutput, -1.0, 1.0) * motorMaxOutput;
    return values;
  }
//-----------------------------------------------------------------------------
/**
 * This method handles the application of the deadband to the speed controller control signals.
 * @param value The control signal to send to the speed controller.
 * @param deadband The deadband value to apply to the control signals.
 * @return The value that gets written to the speed controller.
 */
  protected double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
//-----------------------------------------------------------------------------
/**
 * This method handles writing values to VictorSPX CANbus speed controllers.
 * @param motorValues An array containing 2 spots, with spot zero being the value
 * for the left side speed controllers, and spot one being the value for the right
 * side speed controllers.
 * @see double[] computeDriveValuesLowSpeed(double xSpeed, double zRotation, boolean squareInputs)
 */ 
  public void drive(double[] motorValues){
    leftOne.set(ControlMode.PercentOutput, motorValues[0]);
    leftTwo.set(ControlMode.PercentOutput, motorValues[0]);
    rightOne.set(ControlMode.PercentOutput, motorValues[1]);
    rightTwo.set(ControlMode.PercentOutput, motorValues[1]);
    
  }
//-----------------------------------------------------------------------------
/**
 * 
 */
  public void updateDiagVals(){
    double tankPSI = airPressure(airPressure(tankPressure.getVoltage()));
    double regulatorPSI = airPressure(airPressure(regulatorPressure.getVoltage()));
    ch0Amps = pdp.getCurrent(0);
    ch1Amps = pdp.getCurrent(1);
    ch2Amps = pdp.getCurrent(2);
    ch3Amps = pdp.getCurrent(3);

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