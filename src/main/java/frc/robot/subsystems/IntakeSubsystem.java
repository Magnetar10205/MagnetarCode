package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    // Spark İle Çalışıyor
    // IntakeBase

    private Joystick joystick;
    private int MotorPort;
    private PWMVictorSPX intakeMotor;
    
    public IntakeSubsystem(Joystick joystick,int MotorPort) {
        this.joystick = joystick;
        this.MotorPort = MotorPort;
        intakeMotor = new PWMVictorSPX(MotorPort);
    }

    public void intakeIn() {
        intakeMotor.set(0.6); // İçeri al
    }

    public void intakeOut() {
        intakeMotor.set(-0.6); // Dışarı at
    }

    public void stopMotor() {
        intakeMotor.set(0); // Motoru durdur
    }
}