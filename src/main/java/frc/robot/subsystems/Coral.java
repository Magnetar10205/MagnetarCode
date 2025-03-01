package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;

public class Coral extends SubsystemBase {
    private Joystick joystick;

    private PWMVictorSPX intakeMotor1;
    private PWMVictorSPX intakeMotor2;
    private DigitalInput photoSwitch;
    private boolean isRunning = false; // Yeni boolean değişken


    public Coral(Joystick joystick, int MotorPort1, int MotorPort2,DigitalInput photoSwitch) {
        this.joystick = joystick;
        intakeMotor1 = new PWMVictorSPX(MotorPort1);
        intakeMotor2 = new PWMVictorSPX(MotorPort2);
        this.photoSwitch =photoSwitch;
    }

    public void intakeIn() {
        intakeMotor1.set(0.3);
        intakeMotor2.set(0.3);
    }

    public void intakeOut() {
        intakeMotor1.set(-0.3);
        intakeMotor2.set(-0.3);
    }

    public void stopMotor() {
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }

    public void adjustPositionWithPhotoSwitch() {
        isRunning = true; // Fonksiyon başladığında işaretle
    
        Timer timer = new Timer();
    
        // 1. Aşama: Nesne algılanana kadar dışarı çıkar
        timer.reset();
        timer.start();
        while (!photoSwitch.get() && timer.get() < 5.0) {
            intakeOut();
        }
        stopMotor();
        Timer.delay(0.25);
    
        // 2. Aşama: Nesne algılandığında içeri al
        timer.reset();
        while (photoSwitch.get() && timer.get() < 5.0) {
            intakeIn();
        }
        stopMotor();
        Timer.delay(0.25);
    
        // 3. Aşama: Kısa bir geri hareket yap
        timer.reset();
        timer.start();
        double maxReverseTime = 0.1;
        while (timer.get() < maxReverseTime) {
            intakeOut();
        }
        stopMotor();
        Timer.delay(0.25);
    
        isRunning = false;
    }
    

    @Override
    public void periodic() {
        // Eğer joystick'in 3. butonu basılmışsa, işlemi başlat
        if (joystick.getRawButtonPressed(3)) {
            adjustPositionWithPhotoSwitch();
        }

        boolean sensor_boolen = !photoSwitch.get();
        // photo = true !photo = false (boştayken)
        SmartDashboard.putBoolean("Sensör Coral 3", sensor_boolen );
    }
}


