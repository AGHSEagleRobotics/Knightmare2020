/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class DriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrainSubsystem m_subsystem;
  private boolean m_precisionModeFlag = false;
  private boolean m_lastLeftStickButton = false;

  /**
   * Creates a new DriveCommand. 
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveCommand(DriveTrainSubsystem subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveRightXAxis = RobotContainer.getDriveRightXAxis();
    double driveLeftYAxis = RobotContainer.getDriveLeftYAxis();
    boolean LeftStickButton = RobotContainer.getLeftStickButton();
    
    // Toggles  between precision mode if A button pressed
    if(LeftStickButton && !m_lastLeftStickButton){
      m_precisionModeFlag = !m_precisionModeFlag;
    }

    // scales the speed of the axis
    driveRightXAxis *= 0.7;
    driveLeftYAxis *= 0.5;

    m_subsystem.cheesyDrive(driveLeftYAxis, driveRightXAxis, m_precisionModeFlag);

    m_lastLeftStickButton = LeftStickButton;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
