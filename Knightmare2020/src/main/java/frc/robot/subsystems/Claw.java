/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
  
  private WPI_VictorSPX clawMotor;

  /**
   * Creates a new Claw.
   */
  public Claw() {
    clawMotor = new WPI_VictorSPX(Constants.clawMotor);

  }

  public void setClawMotor(double speed) {
    clawMotor.set(speed);
  }

  public void setClawMotorAlternate(boolean isOn) {
    if(isOn){
      clawMotor.set(0.7);
    }else{
      clawMotor.set(0);
    }

  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
