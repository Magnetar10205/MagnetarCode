// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.Drivetrain;



/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  // Start Time
  private double startTime;
  // // Drive Train

  private Joystick gamepad = new Joystick(0);
  private Drivetrain driveTrain = new Drivetrain(0, 0, 1, 2, 3, gamepad);

  // private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  public Robot() {
    // Deneme Yorumu
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
      double elapsedTime = Timer.getFPGATimestamp() - startTime; // Geçen süreyi hesapla
  
      if (elapsedTime < 3.0) {
        driveTrain.NormalArcadeDrive(0,0.6); // 3 saniye boyunca ileri git (0.5 hız)
      } else if (elapsedTime < 4.0) {
        driveTrain.NormalArcadeDrive(0.5,0); // 1 saniye boyunca sağa dön
      } else if (elapsedTime < 5.0) {
        driveTrain.NormalArcadeDrive(-0.5,0); // 1 saniye boyunca sola dön
      } else {
          driveTrain.NormalArcadeDrive(0, 0); // Hareketi durdur
      }
      
      

      // drive.arcadeDrive(0,0.5); // İleri Dönüş
      // drive.arcadeDrive(0,-0.5); // Geriye Dönüş
      // drive.arcadeDrive(0.5,0); // Sağa dönüş
      // drive.arcadeDrive(-0.5,0); // Sola Dönüş


    }
  

  @Override
  public void teleopInit() {
    System.out.println("Teleop Mode Started");
  }

  @Override
  public void teleopPeriodic() {
    if (driveTrain.gamepad.getRawButton(8)){
      driveTrain.drive.arcadeDrive(0,0.7);
    }else if (driveTrain.gamepad.getRawButton(7)){
      driveTrain.drive.arcadeDrive(0,-0.7);
    }else{

      driveTrain.DriveArcade();

    }

      //  driveTrain.DriveArcade();




       for (int i = 1; i <= 16; i++) { // 1'den 12'ye kadar tüm tuşları kontrol et
           if (gamepad.getRawButtonPressed(i)) { // Eğer butona basıldıysa
               DriverStation.reportWarning("Basılan tuş ID: " + i, false);
               SmartDashboard.putNumber("Basılan Tuş", i);
           }
       }

       SmartDashboard.putNumber("Joystick Y ", -gamepad.getY());
       SmartDashboard.putNumber("Joystick Y ", gamepad.getX());

  
}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
