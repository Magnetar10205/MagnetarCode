// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class Coral extends SubsystemBase {
  /** Creates a new Coral. */
  // Spark ile kontrol edilir. 

    private Joystick joystick;
    private int MotorPort1;
    private int MotorPort2;
    private PWMVictorSPX intakeMotor1;
    private PWMVictorSPX intakeMotor2;

  public Coral(Joystick joystick, int MotorPort1, int MotorPort2) {
      this.joystick = joystick;
      this.MotorPort1 = MotorPort1;
      this.MotorPort2 = MotorPort2;
      intakeMotor1 = new PWMVictorSPX(MotorPort1);
      intakeMotor2 = new PWMVictorSPX(MotorPort2);
  }

    public void intakeIn() {
      intakeMotor1.set(0.6); // İçeri al
      intakeMotor2.set(-0.6); // İçeri al
  }

  public void intakeOut() {
      intakeMotor1.set(-0.6); // Dışarı at
      intakeMotor2.set(0.6); // Dışarı at
  }

  public void stopMotor() {
      intakeMotor1.set(0); // Motoru durdur
      intakeMotor2.set(0); // Motoru durdur
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
