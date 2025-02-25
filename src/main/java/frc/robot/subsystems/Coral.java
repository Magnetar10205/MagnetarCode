// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// public class Coral extends SubsystemBase {
//   /** Creates a new Coral. */
//   // Spark ile kontrol edilir. 

//     private Joystick joystick;
//     private int MotorPort1;
//     private int MotorPort2;
//     private PWMVictorSPX intakeMotor1;
//     private PWMVictorSPX intakeMotor2;

//   public Coral(Joystick joystick, int MotorPort1, int MotorPort2) {
//       this.joystick = joystick;
//       this.MotorPort1 = MotorPort1;
//       this.MotorPort2 = MotorPort2;
//       intakeMotor1 = new PWMVictorSPX(MotorPort1);
//       intakeMotor2 = new PWMVictorSPX(MotorPort2);
//   }

//     public void intakeIn() {
//       intakeMotor1.set(0.4); // İçeri al
//       intakeMotor2.set(0.4); // İçeri al
//   }

//   public void intakeOut() {
//       intakeMotor1.set(-0.4); // Dışarı at
//       intakeMotor2.set(-0.4); // Dışarı at
//   }

//   public void stopMotor() {
//       intakeMotor1.set(0); // Motoru durdur
//       intakeMotor2.set(0); // Motoru durdur
//   }

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//   }
// }




package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;

public class Coral extends SubsystemBase {
    private Joystick joystick;
    private int MotorPort1;
    private int MotorPort2;
    private PWMVictorSPX intakeMotor1;
    private PWMVictorSPX intakeMotor2;
    private DigitalInput photoSwitch;
    private boolean isRunning = false; // Yeni boolean değişken

    public Coral(Joystick joystick, int MotorPort1, int MotorPort2, int photoSwitchPort) {
        this.joystick = joystick;
        this.MotorPort1 = MotorPort1;
        this.MotorPort2 = MotorPort2;
        intakeMotor1 = new PWMVictorSPX(MotorPort1);
        intakeMotor2 = new PWMVictorSPX(MotorPort2);
        photoSwitch = new DigitalInput(photoSwitchPort);
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
        if (isRunning) return; // Eğer fonksiyon zaten çalışıyorsa, tekrar çalıştırma
        isRunning = true; // Fonksiyon başladığında işaretle

        while (photoSwitch.get()){
            intakeIn();
        }
        stopMotor();
        // Timer timer = new Timer();
        // timer.start();
        // double maxReverseTime = 5.0; 
        // while (!photoSwitch.get() && timer.get() < maxReverseTime ){
        //     intakeOut();
        // }
        // stopMotor();

        // if (photoSwitch.get()) {
        //     // Motorları ileri al
        //     intakeIn();
        //     Timer.delay(1.0);
        //     stopMotor();

        //     // Geri alma işlemi (5 saniyeye kadar)
        //     // Timer timer = new Timer();
        //     // timer.start();
        //     // double maxReverseTime = 5.0; 

        //     while (!photoSwitch.get() && timer.get() < maxReverseTime) {
        //         intakeOut();
        //     }
        //     stopMotor();

        //     timer.stop();
        //     timer.reset();
        // }

        isRunning = false; // İşlem tamamlandığında sıfırla
    }

    @Override
    public void periodic() {
        // Eğer joystick'in 3. butonu basılmışsa, işlemi başlat
        if (joystick.getRawButtonPressed(3)) {
            adjustPositionWithPhotoSwitch();
        }

        boolean sensor_boolen = photoSwitch.get();

        SmartDashboard.putBoolean("Sensör Coral", sensor_boolen );
    }
}
