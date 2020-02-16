/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.RobotContainer.DPad;
import frc.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class DriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrainSubsystem m_subsystem;
  
  private final double OFF_SPEED_MODE = 0.0; 
  private final double LOW_SPEED_MODE = 0.5; 
  private final double MED_SPEED_MODE = 0.7; 
  private final double HIGH_SPEED_MODE = 1.0; 


  private enum speedMode{
    LOW, MED, HIGH, OFF
  }

  private boolean m_precisionModeFlag = false;
  private boolean m_lastLeftStickButton = false;
  // starting with low speed for incrementing
  private DPad dPad = DPad.LEFT;

  private speedMode m_curSpeedMode = speedMode.LOW;

  private Timer timer = new Timer();



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
    timer.start();
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

    dPad = RobotContainer.getPOVLeftValue();
    if( dPad == DPad.LEFT ){
      m_curSpeedMode = speedMode.LOW; // low speed drive function
    }else if( dPad == DPad.UP ) {
      m_curSpeedMode = speedMode.MED; // medium speed drive function
    }else if( dPad == DPad.RIGHT ){
      m_curSpeedMode = speedMode.HIGH; // high speed drive function
    }else if( dPad == DPad.DOWN){
      m_curSpeedMode = speedMode.OFF;
    }

    if( m_curSpeedMode == speedMode.LOW ){
      driveLeftYAxis *= LOW_SPEED_MODE;
      driveRightXAxis *= LOW_SPEED_MODE;
    }else if( m_curSpeedMode == speedMode.MED){
      driveLeftYAxis *= MED_SPEED_MODE;
      driveRightXAxis *= MED_SPEED_MODE;
    }else if( m_curSpeedMode == speedMode.HIGH){
      driveLeftYAxis *= HIGH_SPEED_MODE;
      driveRightXAxis *= HIGH_SPEED_MODE;
    }else if( m_curSpeedMode == speedMode.OFF){
      driveLeftYAxis = OFF_SPEED_MODE;
      driveRightXAxis = OFF_SPEED_MODE;
    }

    // if( timer.hasPeriodPassed(5.0) ){
    //   System.out.println( "DPad Value: " + dPad + "\t Speed Mode:  " + m_curSpeedMode );
    // }
    // System.out.println(driveLeftYAxis + "\t\t" + driveRightXAxis );
    m_subsystem.cheesyDrive(driveLeftYAxis, -driveRightXAxis, m_precisionModeFlag);

    m_lastLeftStickButton = LeftStickButton;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println( "**************** Drive ended **********\t" + interrupted);
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
