// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;


// public class Drivetrain extends SubsystemBase {
//   /** Creates a new Drivetrain. */
//   public Joystick gamepad;
//   public PWMVictorSPX leftFrontMotor;
//   public PWMVictorSPX leftBackMotor;
//   public PWMVictorSPX rightFrontMotor;
//   public PWMVictorSPX rightBackMotor;
//   public MotorControllerGroup leftMotors;
//   public MotorControllerGroup rightMotors;
//   public DifferentialDrive drive;

//   public Drivetrain(int gamepadPort, int leftFrontMotorPort, int leftBackMotorPort, int rightFrontMotorPort, int rightBackMotorPort, Joystick joystick) {
//     gamepad = joystick;
//     leftFrontMotor = new PWMVictorSPX(leftFrontMotorPort);
//     leftBackMotor = new PWMVictorSPX(leftBackMotorPort);
//     rightFrontMotor = new PWMVictorSPX(rightFrontMotorPort);
//     rightBackMotor = new PWMVictorSPX(rightBackMotorPort);

//     leftMotors = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
//     rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

//     drive = new DifferentialDrive(leftMotors, rightMotors);

//   }

//   public void DriveArcade() {
//     double rawForward = gamepad.getRawAxis(0); // Sağ joystick Y ekseni (ileri-geri)
//     double rawTurn = -gamepad.getRawAxis(1);  // Sol joystick X ekseni (sağa-sola dönüş)

//     // Ölü bölge tanımlaması (örneğin 0.1)
//     double deadzone = 0.1;

//     if (Math.abs(rawForward) < deadzone) {
//         rawForward = 0;
//     }
//     if (Math.abs(rawTurn) < deadzone) {
//         rawTurn = 0;
//     }

//     // Hassasiyeti artırmak için x^2 kullanımı
//     double forward = Math.signum(rawForward) * Math.pow(rawForward, 2);
//     double turn = Math.signum(rawTurn) * Math.pow(rawTurn, 2);

//     // Eğer joystick hareketsizse motorları durdur
//     if (forward == 0 && turn == 0) {
//         drive.arcadeDrive(0, 0);
//     } else {
//         drive.arcadeDrive(forward, turn);
//     }
// }


//   public void NormalArcadeDrive(double x,double y){
//     drive.arcadeDrive(x, y);
//   }


//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }



// }


// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.wpilibj.SPI;     // For NavX communication

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
  public AHRS navx;
  
  private double targetAngle;  // Desired heading to maintain
  private final double KP = 0.03;  // Proportional gain for correction (needs tuning)

  public Drivetrain(int gamepadPort, int leftFrontMotorPort, int leftBackMotorPort, 
                    int rightFrontMotorPort, int rightBackMotorPort, Joystick joystick) {
    gamepad = joystick;
    leftFrontMotor = new PWMVictorSPX(leftFrontMotorPort);
    leftBackMotor = new PWMVictorSPX(leftBackMotorPort);
    rightFrontMotor = new PWMVictorSPX(rightFrontMotorPort);
    rightBackMotor = new PWMVictorSPX(rightBackMotorPort);

    leftMotors = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    // Initialize NavX (assuming it's connected via SPI)
    try {
      navx = new AHRS(NavXComType.kUSB1);
      navx.reset();
      targetAngle = navx.getAngle();  // Set initial angle as target
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX MXP: " + ex.getMessage(), true);
    }
  }

  public void DriveArcade() {
    double rawForward = gamepad.getRawAxis(0); // Right joystick Y axis (forward-back)
    double rawTurn = -gamepad.getRawAxis(1);   // Left joystick X axis (turn)

    // Deadzone definition
    double deadzone = 0.1;

    if (Math.abs(rawForward) < deadzone) {
        rawForward = 0;
    }
    if (Math.abs(rawTurn) < deadzone) {
        rawTurn = 0;
    }

    // Square inputs for better sensitivity
    double forward = Math.signum(rawForward) * Math.pow(rawForward, 2);
    double turn = Math.signum(rawTurn) * Math.pow(rawTurn, 2);

    // Calculate correction if we're trying to go straight (turn input is 0)
    double correction = 0;
    if (navx != null && rawTurn == 0 && rawForward != 0) {
      double currentAngle = navx.getAngle();
      double angleError = targetAngle - currentAngle;
      correction = angleError * KP;  // Simple P controller
      // Limit correction to prevent overcompensation
      correction = Math.max(-0.5, Math.min(0.5, correction));
    }

    // Apply drive with correction
    if (forward == 0 && turn == 0) {
        drive.arcadeDrive(0, 0);
    } else {
        drive.arcadeDrive(forward, turn + correction);
    }

    // Display useful info on SmartDashboard
    if (navx != null) {
      SmartDashboard.putNumber("NavX Angle", navx.getAngle());
      SmartDashboard.putNumber("Correction", correction);
    }
  }

  public void NormalArcadeDrive(double x, double y) {
    drive.arcadeDrive(x, y);
  }

  // Method to reset the target angle
  public void resetTargetAngle() {
    if (navx != null) {
      targetAngle = navx.getAngle();
    }
  }

  // @Override
  // public void periodic() {
  //   // This method will be called once per scheduler run
  //   if (navx != null) {
  //     SmartDashboard.putNumber("Yaw", navx.getYaw());
  //     SmartDashboard.putNumber("Target Angle", targetAngle);
  //   }
  // }

  @Override
  public void periodic() {
    if (navx != null) {
        SmartDashboard.putNumber("NavX Angle", navx.getAngle());  // Genel açı
        SmartDashboard.putNumber("Yaw", navx.getYaw());           // Yaw açısı
        SmartDashboard.putNumber("Pitch", navx.getPitch());       // Pitch açısı
        SmartDashboard.putNumber("Roll", navx.getRoll());         // Roll açısı
        SmartDashboard.putNumber("Target Angle", targetAngle);    // Hedef açı
        SmartDashboard.putNumber("Gyro Correction", KP * (targetAngle - navx.getAngle())); // Düzeltme oranı
    }
}

}



//! DriveTrain Dönüşleride Düzelten kod


// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import com.studica.frc.AHRS;
// import com.studica.frc.AHRS.NavXComType;

// public class Drivetrain extends SubsystemBase {
//     public Joystick gamepad;
//     public PWMVictorSPX leftFrontMotor;
//     public PWMVictorSPX leftBackMotor;
//     public PWMVictorSPX rightFrontMotor;
//     public PWMVictorSPX rightBackMotor;
//     public MotorControllerGroup leftMotors;
//     public MotorControllerGroup rightMotors;
//     public DifferentialDrive drive;
//     public AHRS navx;
    
//     private double targetAngle;  // Hedef açı
//     private final double KP = 0.03;  // P kontrol katsayısı
//     private final double KD = 0.01;  // D kontrol katsayısı (hızlı değişimlerde ani tepkiler için)
//     private double lastError = 0;    // Önceki hata değeri

//     public Drivetrain(int gamepadPort, int leftFrontMotorPort, int leftBackMotorPort, 
//                       int rightFrontMotorPort, int rightBackMotorPort, Joystick joystick) {
//         gamepad = joystick;
//         leftFrontMotor = new PWMVictorSPX(leftFrontMotorPort);
//         leftBackMotor = new PWMVictorSPX(leftBackMotorPort);
//         rightFrontMotor = new PWMVictorSPX(rightFrontMotorPort);
//         rightBackMotor = new PWMVictorSPX(rightBackMotorPort);

//         leftMotors = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
//         rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);
//         drive = new DifferentialDrive(leftMotors, rightMotors);

//         // NavX Başlatma
//         try {
//             navx = new AHRS(NavXComType.kUSB1);
//             navx.reset();
//             targetAngle = navx.getAngle();  // İlk açıyı belirle
//         } catch (RuntimeException ex) {
//             DriverStation.reportError("NavX başlatma hatası: " + ex.getMessage(), true);
//         }
//     }

//     public void DriveArcade() {
//         double rawForward = gamepad.getRawAxis(0); // İleri-geri hareket (Y ekseni)
//         double rawTurn = -gamepad.getRawAxis(1);   // Dönüş hareketi (X ekseni)

//         // Deadzone tanımlama
//         double deadzone = 0.1;
//         if (Math.abs(rawForward) < deadzone) rawForward = 0;
//         if (Math.abs(rawTurn) < deadzone) rawTurn = 0;

//         // Joystick girişlerini hassaslaştırma (kare alma)
//         double forward = Math.signum(rawForward) * Math.pow(rawForward, 2);
//         double turn = Math.signum(rawTurn) * Math.pow(rawTurn, 2);

//         // NavX düzeltme faktörü hesaplama
//         double correction = 0;
//         if (navx != null) {
//             double currentAngle = navx.getAngle();
//             double angleError = targetAngle - currentAngle;
//             double derivative = angleError - lastError; // Hata değişim oranı (D bileşeni)

//             correction = (angleError * KP) + (derivative * KD); // PID kontrolü (P + D)
//             lastError = angleError; // Önceki hata değerini güncelle

//             // Düzeltme sınırlarını belirleme (çok agresif olmasını önler)
//             correction = Math.max(-0.5, Math.min(0.5, correction));
//         }

//         // Eğer joystick dönüş yapmıyorsa yönü koru
//         if (turn == 0) {
//             turn = correction;
//         } else {
//             targetAngle = navx.getAngle(); // Yeni dönüş açısını hedef olarak al
//         }

//         // Sürüşü uygula
//         drive.arcadeDrive(forward, turn);

//         // SmartDashboard'a veri gönderme
//         if (navx != null) {
//             SmartDashboard.putNumber("NavX Angle", navx.getAngle());
//             SmartDashboard.putNumber("Correction", correction);
//             SmartDashboard.putNumber("Target Angle", targetAngle);
//         }
//     }

//     public void resetTargetAngle() {
//         if (navx != null) {
//             targetAngle = navx.getAngle();
//         }
//     }

//     @Override
//     public void periodic() {
//         if (navx != null) {
//             SmartDashboard.putNumber("NavX Angle", navx.getAngle());  
//             SmartDashboard.putNumber("Yaw", navx.getYaw());           
//             SmartDashboard.putNumber("Pitch", navx.getPitch());       
//             SmartDashboard.putNumber("Roll", navx.getRoll());         
//             SmartDashboard.putNumber("Target Angle", targetAngle);    
//             SmartDashboard.putNumber("Gyro Correction", KP * (targetAngle - navx.getAngle())); 
//         }
//     }
// }
