/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrainSubsystem extends Subsystem {
  
  // Put methods for controlling this subsystem here. 
  // Call Public Methods from Commands.

  private final WPI_VictorSPX leftFront = new WPI_VictorSPX(RobotMap.leftVictor);
  private final WPI_VictorSPX leftBack = new WPI_VictorSPX(RobotMap.leftTalon);
  private final WPI_TalonSRX rightFront = new WPI_TalonSRX(RobotMap.rightTalon);
  private final WPI_TalonSRX rightBack = new WPI_TalonSRX(RobotMap.rightVictor);

  private DifferentialDrive diffDrive;

  //========================================================================
  // Constructor
  //========================================================================

  public DriveTrainSubsystem() {
    
    // Uncomment which brake mode we want the motors to be in
    //setCoast();  
    setBrake();
      
    // Uncomment which drive mode we want to use at the moment,
    //useFrontWheelsOnly(); //2 motor controllers
    //useSpeedControllerGroups(); //2 speed controller groups
    useFollowMode(); //Follow mode, links sameside motors

    // Setup differential drive
    addChild("DifferentialDrive",diffDrive);
    diffDrive.setSafetyEnabled(true);
    diffDrive.setExpiration(0.1);
    diffDrive.setMaxOutput(1.0);
    diffDrive.setDeadband( 0.2 );
  }

  //========================================================================
  // FRC Default Methods
  //========================================================================
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
     setDefaultCommand(new DriveCommand());
  }

  //========================================================================
  // Public Methods
  //========================================================================
  
  public void arcadeDrive(double speed, double rotation) {
    diffDrive.arcadeDrive(speed, rotation);
  }

  public void cheesyDrive( double speed, double rotation, boolean isQuickTurn ){
      diffDrive.curvatureDrive( speed, rotation, isQuickTurn);
  }

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



 

 
}
