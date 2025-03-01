package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ElevatorCode;
import frc.robot.subsystems.Alg;

import com.ctre.phoenix6.configs.ClosedLoopGeneralConfigs;

import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
    private final Joystick joystick = new Joystick(0); // USB port 0
    private final Drivetrain drivetrain = new Drivetrain(0, 2, 3, 0, 1, joystick);
    private final DigitalInput photoSwitch = new DigitalInput(3);
    private final DigitalInput ortaSwitch = new DigitalInput(9);
    private final DigitalInput yukariSwitch = new DigitalInput(7);
    private final Coral coralSubsystem = new Coral(joystick, 5,6, photoSwitch); // iki motor bağlanacak ve birbirine ters olucak PhotoSwitch 6. DIO portuna girildi
    private final DigitalInput asagiSwitch = new DigitalInput(8);
    private final ElevatorCode elevatorSubsystem = new ElevatorCode(joystick, 8,9,asagiSwitch, ortaSwitch, yukariSwitch);
    private double startTime;
    private final Alg algSubsystem = new Alg(joystick, 4);
    private boolean autonomousElevatorControl = false;
    private double coralTimer;
    private boolean CoralBos = false;

    @Override
    public void robotPeriodic() {}

    @Override
    public void robotInit() {
    CameraServer.startAutomaticCapture();
    }


    @Override
    public void autonomousInit() {
        startTime = Timer.getFPGATimestamp();
        
    }

    @Override
    public void autonomousPeriodic() {
        double elapsedTime = Timer.getFPGATimestamp() - startTime;
    
        // if (elapsedTime < 3.0) {
        //     drivetrain.NormalArcadeDrive(0, 0.6);
        // } else if (elapsedTime < 4.0) {
        //     drivetrain.NormalArcadeDrive(0.5, 0);
        // } else if (elapsedTime < 5.0) {
        //     drivetrain.NormalArcadeDrive(-0.5, 0);
        // } else {
        //     drivetrain.NormalArcadeDrive(0, 0);
        // }

        // ? ileri -->  drivetrain.NormalArcadeDrive(0, 0.6); 
        // ? Sağa dönüş --> drivetrain.NormalArcadeDrive(0.5, 0);


        // ? double startTime, double time, double x, double y
        // drivetrain.SureliDrive(elapsedTime, 2.7, 0,0.6 ); // İleri
 
        // Timer.delay(0.5);
        // elevatorSubsystem.elevatorYukari();


        if (elapsedTime < 2.7){
            drivetrain.NormalArcadeDrive(-0.07,0.6);

        }else if (!autonomousElevatorControl && elapsedTime > 2.7 && !yukariSwitch.get() ){
            drivetrain.stopMotors();
            Timer.delay(0.5);
            elevatorSubsystem.elevatorYukari();
            if (yukariSwitch.get()){
                autonomousElevatorControl = true;
                coralTimer = Timer.getFPGATimestamp();
            }
        
        }else if (true ){
            Timer.delay(0.5);
            coralSubsystem.intakeOut();
            if (photoSwitch.get() == false){
                coralSubsystem.stopMotor();
            }else if (photoSwitch.get() == true){
                coralSubsystem.intakeOut();
            }

            
        }
        // coralSubsystem.stopMotor();




        // Timer.delay(0.25);
        // elevatorSubsystem.elevatorAsagi();

        // startTime = Timer.getFPGATimestamp();
        // drivetrain.SureliDrive(elapsedTime, 0.9, 0.5, 0); // Sağa dön
        System.out.println(elapsedTime);
        
    }

    @Override
    public void teleopInit() {
        System.out.println("Teleop Mode Started");
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.DriveArcade();
        
        // Coral Butonları
        boolean button1 = joystick.getRawButton(7);
        boolean button2 = joystick.getRawButton(8);

        // Alg Butonları
        boolean button3 = joystick.getRawButton(9);
        boolean button4 = joystick.getRawButton(10);



        // ! Coral Subsystem Kontrolü

        if (button1) {
            coralSubsystem.intakeIn();
        } else if (button2) {
            coralSubsystem.intakeOut();
            if (photoSwitch.get()){
                Timer.delay(1);
                coralSubsystem.stopMotor();
                elevatorSubsystem.elevatorAsagi();
            }
        }else{
            coralSubsystem.stopMotor();
        }

        // ! Alg Subsystem Kontrolü
        if (button3 && button4) {
            algSubsystem.stopMotor();
        } else if (button3) {
            algSubsystem.intakeIn();
        } else if (button4) {
            algSubsystem.intakeOut();
        } else {
            algSubsystem.stopMotor();
        }


        elevatorSubsystem.periodic();
        coralSubsystem.periodic();

        
        // ! ShuffleBoard Verileri Yazdırma
        for (int i = 1; i <= 12; i++) { // 12 tuş sınırı varsayımı
            if (joystick.getRawButtonPressed(i)) {
                SmartDashboard.putNumber("Basılan Tuş", i);
            }
        }
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
