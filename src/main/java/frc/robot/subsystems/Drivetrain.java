// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  public Joystick gamepad;
  public PWMVictorSPX leftFrontMotor;
  public PWMVictorSPX leftBackMotor;
  public PWMVictorSPX rightFrontMotor;
  public PWMVictorSPX rightBackMotor;
  public MotorControllerGroup leftMotors;
  public MotorControllerGroup rightMotors;
  public DifferentialDrive drive;

  public Drivetrain(int gamepadPort, int leftFrontMotorPort, int leftBackMotorPort, int rightFrontMotorPort, int rightBackMotorPort, Joystick joystick) {
    gamepad = joystick;
    leftFrontMotor = new PWMVictorSPX(leftFrontMotorPort);
    leftBackMotor = new PWMVictorSPX(leftBackMotorPort);
    rightFrontMotor = new PWMVictorSPX(rightFrontMotorPort);
    rightBackMotor = new PWMVictorSPX(rightBackMotorPort);

    leftMotors = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

    drive = new DifferentialDrive(leftMotors, rightMotors);

  }

  public void DriveArcade() {
    double rawForward = gamepad.getRawAxis(0); // Sağ joystick Y ekseni (ileri-geri)
    double rawTurn = -gamepad.getRawAxis(1);  // Sol joystick X ekseni (sağa-sola dönüş)

    // Ölü bölge tanımlaması (örneğin 0.1)
    double deadzone = 0.1;

    if (Math.abs(rawForward) < deadzone) {
        rawForward = 0;
    }
    if (Math.abs(rawTurn) < deadzone) {
        rawTurn = 0;
    }

    // Hassasiyeti artırmak için x^2 kullanımı
    double forward = Math.signum(rawForward) * Math.pow(rawForward, 2);
    double turn = Math.signum(rawTurn) * Math.pow(rawTurn, 2);

    // Eğer joystick hareketsizse motorları durdur
    if (forward == 0 && turn == 0) {
        drive.arcadeDrive(0, 0);
    } else {
        drive.arcadeDrive(forward, turn);
    }
}


  public void NormalArcadeDrive(double x,double y){
    drive.arcadeDrive(x, y);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }



}
