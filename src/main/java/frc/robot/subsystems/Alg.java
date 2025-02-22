// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Alg extends SubsystemBase {
  /** Creates a new Alg. */

  private Joystick joystick;
  private int MotorPort;
  private PWMVictorSPX intakeMotor;

  public Alg(Joystick joystick, int MotorPort) {
    this.joystick = joystick;
    this.MotorPort = MotorPort;
    intakeMotor = new PWMVictorSPX(MotorPort);
  }

  public void intakeIn() {
    intakeMotor.set(0.6); // İçeri al
}

  public void intakeOut() {
      intakeMotor.set(-0.6); // Dışarı at
  }

  public void stopMotor() {
      intakeMotor.set(0); // Motoru durdur
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
