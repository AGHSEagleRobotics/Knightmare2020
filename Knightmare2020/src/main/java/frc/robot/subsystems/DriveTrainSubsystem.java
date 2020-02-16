/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


import com.ctre.phoenix.motorcontrol.NeutralMode;

public class DriveTrainSubsystem extends SubsystemBase {
  private final double secondsToFull = 2;
  
  private final WPI_VictorSPX leftFront = new WPI_VictorSPX(Constants.leftVictor);
  private final WPI_TalonSRX leftBack = new WPI_TalonSRX(Constants.leftTalon);
  private final WPI_TalonSRX rightFront = new WPI_TalonSRX(Constants.rightTalon);
  private final WPI_VictorSPX rightBack = new WPI_VictorSPX(Constants.rightVictor);
  
  private DifferentialDrive diffDrive;

  /**
   * Creates a new DriveTrainSubsystem.
   */
  public DriveTrainSubsystem() {
    leftFront.configFactoryDefault();
    leftBack.configFactoryDefault();
    rightBack.configFactoryDefault();
    rightFront.configFactoryDefault();
    // Uncomment which brake mode we want the motors to be in
    //setCoast();  
    setBrake();
      
    // Uncomment which drive mode we want to use at the moment,
    //useSpeedControllerGroups(); //2 speed controller groups
    useFollowMode(); //Follow mode, links sameside motors

    // rampAccel();

    // diffDrive = new DifferentialDrive(leftFront, rightFront);
    // Setup differential drive
    addChild("DifferentialDrive",diffDrive);
    diffDrive.setSafetyEnabled(true);
    diffDrive.setExpiration(0.1);
    diffDrive.setMaxOutput(1.0);
    diffDrive.setDeadband( 0.2 );
  }

  /**
   * The arcadeDrive method supports single stick control, where forward/backward on
   * the stick controls forward/backward motion, and left/right on the stick
   * controls rotation.This can be implemented using two control sticks as well, where one
   * stick controls forward/backward motion, and the other controls rotation.
   * <p>
   * The rotation is based around the center of the robot.
   * The calculated values will be squared to decrease sensitivity at low speeds.
   * 
   * @param  speed  The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param  rotation  The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   * @return      void
   */
  public void arcadeDrive(double speed, double rotation) {
    diffDrive.arcadeDrive(speed, rotation);
  }

  /**
   * The cheesyDrive method supports single stick control, where forward/backward on
   * the stick controls forward/backward motion, and left/right on the stick
   * controls rotation.This can be implemented using two control sticks as well, where one
   * stick controls forward/backward motion, and the other controls rotation.
   * <p>
   * The rotation argument controls the curvature of the robot's path rather than its
   * rate of heading change. This makes the robot more controllable at high speeds.
   * Also handles the robot's quick turn functionality - "quick turn" overrides
   * constant-curvature turning for turn-in-place maneuvers.
   * 
   * @param  speed  The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param  rotation  The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   * @param  isQuickTurn  If set, overrides constant-curvature turning for turn-in-place maneuvers.
   * @return      void
   */
  public void cheesyDrive( double speed, double rotation, boolean isQuickTurn ){
      diffDrive.curvatureDrive( speed, rotation, isQuickTurn);
  }

  /**
   * The tankDrive method supports double stick control, where forward/backward on
   * the left stick controls forward/backward motion on the left side, and forward/backward on
   * the right stick controls forward/backward motion on the right side.
   * <p>
   * The calculated values will be squared to decrease sensitivity at low speed
   * 
   * @param  leftSpeed  The robot's left side speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param  rightSpeed  The robot's right side speed along the X axis [-1.0..1.0]. Forward is positive.
   * @return      void
   */
  public void tankDrive( double leftSpeed, double rightSpeed ){
      diffDrive.tankDrive( leftSpeed, rightSpeed );
  }

  //========================================================================
  // Private Methods
  //========================================================================
  
  // Have back motor controllers follow the actions of front motor controllers
  private void useFollowMode() {
    leftBack.follow( leftFront );
    rightBack.follow( rightFront );

    diffDrive = new DifferentialDrive( leftFront, rightFront );
  }

  private void useSpeedControllerGroups() {
    SpeedControllerGroup leftGroup = new SpeedControllerGroup( leftFront, leftBack );
    SpeedControllerGroup rightGroup = new SpeedControllerGroup( rightFront, rightBack );

    diffDrive = new DifferentialDrive( leftGroup, rightGroup );
  }

  private void rampAccel(){
    leftBack.configOpenloopRamp( secondsToFull );
    leftBack.configOpenloopRamp( secondsToFull );
    leftBack.configOpenloopRamp( secondsToFull );
    leftBack.configOpenloopRamp( secondsToFull );
  }


  // Have the motors apply the brake when no power applied
  private void setBrake()
  {
    leftFront.setNeutralMode(NeutralMode.Brake);
    leftBack.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);
    rightBack.setNeutralMode(NeutralMode.Brake);
  }

  // Have the motors coast when no power applied
  private void setCoast()
  {
    leftFront.setNeutralMode(NeutralMode.Coast);
    leftBack.setNeutralMode(NeutralMode.Coast);
    rightFront.setNeutralMode(NeutralMode.Coast);
    rightBack.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
