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

public class Robot extends TimedRobot {
    private final Joystick joystick = new Joystick(0); // USB port 0
    private final Drivetrain drivetrain = new Drivetrain(0, 0, 1, 2, 3, joystick);
    // private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem(joystick, 6);
    private final Coral coralSubsystem = new Coral(joystick, 6);
    private final ElevatorCode elevatorSubsystem = new ElevatorCode(joystick, 5);
    private double startTime;

    @Override
    public void robotPeriodic() {}

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
        
        if (button1 && button2) {
            coralSubsystem.stopMotor();
        } else if (button1) {
            coralSubsystem.intakeIn();
        } else if (button2) {
            coralSubsystem.intakeOut();
        } else {
            coralSubsystem.stopMotor();
        }

        elevatorSubsystem.periodic();
        
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
