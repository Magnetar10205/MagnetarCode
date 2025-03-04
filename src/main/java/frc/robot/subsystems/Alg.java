// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Alg extends SubsystemBase {
  /** Creates a new Alg. */

  private Joystick joystick;
  private int MotorPort;
  private PWMVictorSPX intakeMotor;
  private DigitalInput AlgPhotoSwitch;
  private boolean duvaraYaklasildi = false;

  public Alg(Joystick joystick, int MotorPort,DigitalInput AlgPhotoSwitch ) {
    this.joystick = joystick;
    this.MotorPort = MotorPort;
    intakeMotor = new PWMVictorSPX(MotorPort);
    this.AlgPhotoSwitch = AlgPhotoSwitch;
  }

  public void intakeIn() {
    // intakeMotor.set(0.6); // İçeri al
    intakeMotor.set(1); // İçeri al

}

  public void intakeOut() {
      // intakeMotor.set(-0.6); // Dışarı at
      intakeMotor.set(-1); // Dışarı at

    }

  public void stopMotor() {
      intakeMotor.set(0); // Motoru durdur
  }


  public void setDuvaraYaklasildi(boolean duvaraYaklasildi) {
    this.duvaraYaklasildi = duvaraYaklasildi;
  }

  public void ParkModu(){
    if (duvaraYaklasildi  && AlgPhotoSwitch.get()){
      intakeIn();
    }else {
      stopMotor();
    }
  }

  @Override
  public void periodic() {
    // ParkModu();
    SmartDashboard.putBoolean("ALg Photo Sensor", AlgPhotoSwitch.get());
  }




}
