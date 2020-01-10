/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveCommand extends CommandBase {
  private final DriveTrainSubsystem m_subsystem;

  public DriveCommand( DriveTrainSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double driveRightXAxis = Robot.oi.getDriveRightXAxis();
    double driveLeftYAxis = Robot.oi.getDriveLeftYAxis();
    boolean AButton = Robot.oi.getAButton();

    Robot.driveTrainSubsystem.cheesyDrive(driveLeftYAxis, driveRightXAxis, AButton);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end( boolean interrupted) {
  }
}
