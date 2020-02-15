/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.ClawIntakeCommand;
import frc.robot.commands.ClawShooterCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrainSubsystem m_DriveTrainSubsystem = new DriveTrainSubsystem();

  private final ClawSubsystem m_clawSubsystem = new ClawSubsystem();

  private final DriveCommand m_driveCommand = new DriveCommand(m_DriveTrainSubsystem);

  private final Command m_autoCommand = new DriveCommand(m_DriveTrainSubsystem);

  private final Command m_clawIntakeCommand = new ClawIntakeCommand(m_clawSubsystem);

  private final Command m_clawShooterCommand = new ClawShooterCommand(m_clawSubsystem);

  public static enum DPad{
    UP, RIGHT, DOWN, LEFT, NONE
  }

  private UsbCamera m_cameraIntake;
  private HttpCamera m_limeLight;
  private VideoSink cameraSelection;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {


    m_DriveTrainSubsystem.setDefaultCommand(m_driveCommand);
    m_cameraIntake = CameraServer.getInstance().startAutomaticCapture();
    // m_cameraIntake.setConnectionStrategy(strategy);
    m_limeLight = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
    currVideoSource = m_cameraIntake;

    cameraSelection = CameraServer.getInstance().getServer();
    cameraSelection.setSource(m_cameraIntake);

    configureButtonBindings();
  }

  public static XboxController driveController = new XboxController(Constants.driveXboxController);

  private static double deadBand(double input) {
    if (input < 0.2 && input > -0.2) {
      return 0.0;
    } else {
      return input;
    }
  }

  public static double getDriveRightXAxis() {
    return deadBand(driveController.getX(Hand.kRight));
  }

  public static double getDriveLeftYAxis() {
    return deadBand(driveController.getY(Hand.kLeft));
  }

  public static boolean getAButton() {
    return driveController.getAButton();
  }

  public static boolean getLeftStickButton() {
    return driveController.getStickButton(Hand.kLeft);
  }

  public static DPad getPOVLeftValue(){
    int pOV = driveController.getPOV();
    // System.out.println( pOV );
    if( pOV == 0 || pOV == 45 ){
      return DPad.UP;
    }else if( pOV == 90 || pOV == 135 ) {
      return DPad.RIGHT;
    }
    else if( pOV == 180 || pOV == 225 ) {
      return DPad.DOWN;
    }
    else if( pOV == 270 || pOV == 315 ) {
      return DPad.LEFT;
    }else{
      return DPad.NONE;
    }

  }


  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driveController, XboxController.Button.kA.value)
        .whenPressed(m_clawIntakeCommand);
    new JoystickButton(driveController, XboxController.Button.kB.value)
        .cancelWhenPressed(m_clawIntakeCommand);
    new JoystickButton(driveController, XboxController.Button.kX.value)
        .whenPressed(m_clawShooterCommand.withTimeout(1));
    new JoystickButton(driveController, XboxController.Button.kBack.value).whenPressed(this::switchVideoSource );
  }

  private VideoSource currVideoSource;

  private void switchVideoSource() {
    if( currVideoSource.equals(m_cameraIntake) ){
      currVideoSource = m_limeLight;
    }else if( currVideoSource.equals(m_limeLight)) {
      currVideoSource = m_cameraIntake;
    }
    cameraSelection.setSource(currVideoSource);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An DriveCommand; will run in autonomous
    return m_autoCommand;
  }
}
