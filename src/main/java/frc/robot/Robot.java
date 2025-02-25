package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private final Coral coralSubsystem = new Coral(joystick, 5,6); // iki motor bağlanacak ve birbirine ters olucak PhotoSwitch 6. DIO portuna girildi
    private final ElevatorCode elevatorSubsystem = new ElevatorCode(joystick, 8,9);
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


        if (button1 && button2) {
            coralSubsystem.stopMotor();
        } else if (button1) {
            coralSubsystem.intakeIn();
        } else if (button2) {
            coralSubsystem.intakeOut();
        } else {
            coralSubsystem.stopMotor();
        }

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
