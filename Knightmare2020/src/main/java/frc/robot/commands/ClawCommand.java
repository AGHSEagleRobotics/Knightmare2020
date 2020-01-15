/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Claw;

public class ClawCommand extends CommandBase {

  private Claw m_clawSubsystem;
  /**
   * Creates a new ClawCommand.
   */
  public ClawCommand(Claw clawSubsystem) {
    m_clawSubsystem = clawSubsystem;
    addRequirements(m_clawSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_clawSubsystem.setClawMotorAlternate(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    }
  
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_clawSubsystem.setClawMotorAlternate(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_clawSubsystem.getClawLimitSwitch() || RobotContainer.driveController.getAButton();
  }
}
