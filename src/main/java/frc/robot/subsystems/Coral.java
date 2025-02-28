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
        intakeMotor1.set(0.4);
        intakeMotor2.set(0.4);
    }

    public void intakeOut() {
        intakeMotor1.set(-0.4);
        intakeMotor2.set(-0.4);
    }

    public void stopMotor() {
        intakeMotor1.set(0);
        intakeMotor2.set(0);
    }

    public void adjustPositionWithPhotoSwitch() {
        boolean calisti = false;

        if (isRunning) return; // Eğer fonksiyon zaten çalışıyorsa, tekrar çalıştırma
        isRunning = true; // Fonksiyon başladığında işaretle

        while (!photoSwitch.get()){
            intakeOut();
        }
        stopMotor();
        Timer.delay(0.25);

        while (photoSwitch.get()){
            intakeIn();
        }
        stopMotor();
        Timer.delay(0.25);

        Timer timer = new Timer();
        timer.start();
        double maxReverseTime = 0.1; 

        while (timer.get()<maxReverseTime){
            intakeOut();
        }
        stopMotor();
        timer.delay(0.25);
        

        isRunning = false; // İşlem tamamlandığında sıfırla
    }

    @Override
    public void periodic() {
        // Eğer joystick'in 3. butonu basılmışsa, işlemi başlat
        if (joystick.getRawButtonPressed(3)) {
            adjustPositionWithPhotoSwitch();
        }

        boolean sensor_boolen = !photoSwitch.get();
        // photo = true !photo = false (boştayken)
        SmartDashboard.putBoolean("Sensör Coral", sensor_boolen );
    }
}