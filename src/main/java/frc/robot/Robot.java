package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ElevatorCode;
import frc.robot.subsystems.Alg;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
    private final Joystick joystick = new Joystick(0); // USB port 0
    private final Drivetrain drivetrain = new Drivetrain(0, 2, 3, 0, 1, joystick);
    // private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(joystick, 6);
    private final DigitalInput photoSwitch = new DigitalInput(3);
    private final DigitalInput ortaSwitch = new DigitalInput(9);
    private final DigitalInput yukariSwitch = new DigitalInput(7);
    private final Coral coralSubsystem = new Coral(joystick, 5,6, photoSwitch); // iki motor bağlanacak ve birbirine ters olucak PhotoSwitch 6. DIO portuna girildi
    private final DigitalInput asagiSwitch = new DigitalInput(8);
    private final ElevatorCode elevatorSubsystem = new ElevatorCode(joystick, 8,9,asagiSwitch, ortaSwitch, yukariSwitch);
    private double startTime;
    private final Alg algSubsystem = new Alg(joystick, 4);

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
    
        if (elapsedTime < 3.0) {
            drivetrain.NormalArcadeDrive(0, 0.6);
        } else if (elapsedTime < 4.0) {
            drivetrain.NormalArcadeDrive(0.5, 0);
        } else if (elapsedTime < 5.0) {
            drivetrain.NormalArcadeDrive(-0.5, 0);
        } else {
            drivetrain.NormalArcadeDrive(0, 0);
        }
    }

    @Override
    public void teleopInit() {
        System.out.println("Teleop Mode Started");
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.DriveArcade();
        
        boolean button1 = joystick.getRawButton(7);
        boolean button2 = joystick.getRawButton(8);
        boolean button3 = joystick.getRawButton(9);
        boolean button4 = joystick.getRawButton(10);

        boolean coralintakeOutYapti = false;


        if (button1 && button2) {
            // coralSubsystem.stopMotor();
        } else if (button1) {
            coralSubsystem.intakeIn();
            // coralSubsystem.stopMotor();

        } else if (button2) {
            coralSubsystem.intakeOut();
            // coralSubsystem.stopMotor();
            if (photoSwitch.get()){
                Timer.delay(1);
                coralSubsystem.stopMotor();
                elevatorSubsystem.elevatorAsagi();
            }
        }else{
            coralSubsystem.stopMotor();
        }


        // ! deneme alanı

        // if (button1 && button2) {
        //     // coralSubsystem.stopMotor();
        // } else if (button1) {
        //     coralSubsystem.intakeIn();
        //     // coralintakeOutYapti = false;
        //     // coralSubsystem.stopMotor();

        // } else if (button2) {
        //     coralSubsystem.intakeOut();
        //     coralintakeOutYapti = true;
        //     Timer.delay(0.5);
        //     // coralSubsystem.stopMotor();

        // }else{
        //     coralSubsystem.stopMotor();

        // }

        // if (coralintakeOutYapti){
        //     coralSubsystem.stopMotor();
        //     Timer.delay(1);
        //     elevatorSubsystem.elevatorAsagi();
        //     Timer.delay(1);
        //     coralintakeOutYapti = false;
        // }








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
