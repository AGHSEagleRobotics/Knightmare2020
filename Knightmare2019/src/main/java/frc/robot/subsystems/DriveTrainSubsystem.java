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


/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrainSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final WPI_VictorSPX leftTalon = new WPI_VictorSPX(RobotMap.leftTalon);
  private final WPI_VictorSPX leftVictor = new WPI_VictorSPX(RobotMap.leftVictor);
  private final WPI_TalonSRX rightTalon = new WPI_TalonSRX(RobotMap.rightTalon);
  private final WPI_TalonSRX rightVictor = new WPI_TalonSRX(RobotMap.rightVictor);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  //TODO: Method to expose driving.

}
