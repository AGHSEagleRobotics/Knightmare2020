/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClawSubsystem;

public class ClawIntakeCommand extends CommandBase {

  private ClawSubsystem m_clawSubsystem;
  /**
   * Creates a new ClawCommand.
   */
  public ClawIntakeCommand(ClawSubsystem clawSubsystem) {
    m_clawSubsystem = clawSubsystem;
    addRequirements(m_clawSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_clawSubsystem.setClawForwardAlternate(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_clawSubsystem.setClawForwardAlternate(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_clawSubsystem.getClawLimitSwitch();
  }
}
