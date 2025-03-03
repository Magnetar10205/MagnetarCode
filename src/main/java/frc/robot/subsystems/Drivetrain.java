// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;


// public class Drivetrain extends SubsystemBase {
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

//   public void stopMotors(){
//     drive.arcadeDrive(0, 0);
//   }
  
//   public void SureliDrive(double elapsedTime, double time, double x, double y){
//     if (elapsedTime < time){
//       drive.arcadeDrive(x, y);
//     }else{
//       drive.arcadeDrive(0, 0);
//     }



//   }


//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }



// }


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.studica.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;

public class Drivetrain extends SubsystemBase {
  public Joystick gamepad;
  public PWMVictorSPX leftFrontMotor;
  public PWMVictorSPX leftBackMotor;
  public PWMVictorSPX rightFrontMotor;
  public PWMVictorSPX rightBackMotor;
  public MotorControllerGroup leftMotors;
  public MotorControllerGroup rightMotors;
  public DifferentialDrive drive;
  public AHRS navx; // navX nesnesi tanımlandı
  
  private double targetAngle = 0; // Hedef açı (ilk başta 0 olarak başlatılır)
  private boolean isTurning = false; // Kullanıcının dönüş yapıp yapmadığını kontrol eder

  public Drivetrain(int gamepadPort, int leftFrontMotorPort, int leftBackMotorPort, int rightFrontMotorPort, int rightBackMotorPort, Joystick joystick) {
    gamepad = joystick;
    leftFrontMotor = new PWMVictorSPX(leftFrontMotorPort);
    leftBackMotor = new PWMVictorSPX(leftBackMotorPort);
    rightFrontMotor = new PWMVictorSPX(rightFrontMotorPort);
    rightBackMotor = new PWMVictorSPX(rightBackMotorPort);

    leftMotors = new MotorControllerGroup(leftFrontMotor, leftBackMotor);
    rightMotors = new MotorControllerGroup(rightFrontMotor, rightBackMotor);

    drive = new DifferentialDrive(leftMotors, rightMotors);

    navx = new AHRS(AHRS.NavXComType.kUSB1);
  }

  public void DriveArcade() {
    double rawForward = gamepad.getRawAxis(0); // Sağ joystick Y ekseni (ileri-geri)
    double rawTurn = -gamepad.getRawAxis(1);  // Sol joystick X ekseni (sağa-sola dönüş)

    double deadzone = 0.1; // Ölü bölge tanımlaması
    if (Math.abs(rawForward) < deadzone) rawForward = 0;
    if (Math.abs(rawTurn) < deadzone) rawTurn = 0;

    double forward = Math.signum(rawForward) * Math.pow(rawForward, 2);
    double turn = Math.signum(rawTurn) * Math.pow(rawTurn, 2);

    if (Math.abs(turn) > 0.05) { // Kullanıcı aktif olarak dönüş yapıyorsa
      isTurning = true;
      targetAngle = navx.getYaw(); // Yeni hedef açıyı belirle
    } else if (isTurning) { // Dönüş bittiyse düzeltme moduna gir
      isTurning = false;
    }

    // Eğer joystick ile dönüş yapılmıyorsa düzeltme uygula
    if (!isTurning) {
      double currentYaw = navx.getYaw();
      double error = targetAngle - currentYaw;
      double correction = error * 0.05; // Hata düzeltme katsayısı
      correction = Math.max(-0.5, Math.min(0.5, correction)); // Maksimum düzeltme sınırı
      turn += correction; // Düzeltmeyi mevcut dönüş komutuna ekle
    }

    drive.arcadeDrive(forward, turn);
  }

  public void NormalArcadeDrive(double x, double y) {
    if (Math.abs(y) > 0.05) { // Kullanıcı aktif olarak dönüş yapıyorsa
      isTurning = true;
      targetAngle = navx.getYaw(); // Yeni hedef açıyı belirle
    } else if (isTurning) { // Dönüş bittiyse düzeltme moduna gir
      isTurning = false;
    }

    // Eğer joystick ile dönüş yapılmıyorsa düzeltme uygula
    if (!isTurning) {
      double currentYaw = navx.getYaw();
      double error = targetAngle - currentYaw;
      double correction = error * 0.05; // Hata düzeltme katsayısı
      correction = Math.max(-0.5, Math.min(0.5, correction)); // Maksimum düzeltme sınırı
      y += correction; // Düzeltmeyi mevcut dönüş komutuna ekle
    }

    drive.arcadeDrive(x, y);
  }

  public void stopMotors() {
    drive.arcadeDrive(0, 0);
  }

  // navX'ten gelen verileri Shuffleboard'a yazdırma
  public void updateNavXData() {
    SmartDashboard.putNumber("NavX Yaw", navx.getYaw()); // Dönüş açısı
    SmartDashboard.putNumber("NavX Pitch", navx.getPitch()); // Öne-arkaya eğim
    SmartDashboard.putNumber("NavX Roll", navx.getRoll()); // Yana eğim
  }

  @Override
  public void periodic() {
    updateNavXData(); // Her döngüde navX verilerini güncelle
  }
}
