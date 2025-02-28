package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorCode extends SubsystemBase {
    private final PWMVictorSPX elevatorMotor1;
    private final PWMVictorSPX elevatorMotor2;
    private final Joystick joystick;
    
    // Manuel kontrol tuşları
    private static final int UP_BUTTON = 6;   // RB butonu
    private static final int DOWN_BUTTON = 5; // LB butonu
    
    // Preset butonları
    private static final int PRESET_1_BUTTON = 2; // X button
    private static final int PRESET_2_BUTTON = 1; // Square button
    private static final int PRESET_3_BUTTON = 4; // Triangle button

    private static boolean yukari_cikildi = false;
    
    private final DigitalInput ortaSwitch;           // 9. DIO pini için switch
    private final DigitalInput yukariSwitch;
    private final DigitalInput asagiSwitch;

    public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2,DigitalInput asagiSwitch,DigitalInput ortaSwitch,DigitalInput yukariSwitch) {
        elevatorMotor1 = new PWMVictorSPX(MotorPort1);
        elevatorMotor2 = new PWMVictorSPX(MotorPort2);

        this.joystick = joystick;
        this.yukariSwitch = yukariSwitch;
        this.ortaSwitch = ortaSwitch;
        this.asagiSwitch = asagiSwitch;

    }

    private void stopMotors() {
        elevatorMotor1.set(0.0);
        elevatorMotor2.set(0.0);
    }

    public void elevatorAsagi (){
        while (!asagiSwitch.get()){
            elevatorMotor1.set(0.7);
            elevatorMotor2.set(0.7);
            yukari_cikildi = false;
        }
        stopMotors();

    }

    public void elevatorYukari(){
        while (!yukariSwitch.get()){
            elevatorMotor1.set(-0.7);
            elevatorMotor2.set(-0.7);
            yukari_cikildi = true;
        }
        stopMotors();
    }

    public void elevatorOrta(){
        if (yukari_cikildi == true){
            elevatorAsagi(); // Asansörü aşağı indir
            while (!ortaSwitch.get()){
                elevatorMotor1.set(-0.7);
                elevatorMotor2.set(-0.7);
            }
        }else if (yukari_cikildi == false){
            while (!ortaSwitch.get()) { // 9. DIO pinine bağlı switch tetiklenene kadar
                elevatorMotor1.set(-0.7); // Yukarı çık
                elevatorMotor2.set(-0.7);
            }

        }
        stopMotors();
    }


    @Override
    public void periodic() {
        
        // **Preset 2 (Orta Seviye): 9. DIO Pinindeki Switch'e Kadar Yükselme**
        if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
            elevatorOrta();
        }else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)){            
            elevatorYukari();
        }else if (joystick.getRawButtonPressed(PRESET_1_BUTTON)){
            elevatorAsagi();
        }


        // !Eski Yedek Asansör Kodları
        // if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
        //     if (yukari_cikildi == true){
        //         while (!asagiSwitch.get()) { // 9. DIO pinine bağlı switch tetiklenene kadar
        //             elevatorMotor1.set(0.7); // Aşağı in
        //             elevatorMotor2.set(0.7);
        //         }
        //         while (!ortaSwitch.get()){
        //             elevatorMotor1.set(-0.7);
        //             elevatorMotor2.set(-0.7);
        //         }
        //     }else if (yukari_cikildi == false){
        //         while (!ortaSwitch.get()) { // 9. DIO pinine bağlı switch tetiklenene kadar
        //             elevatorMotor1.set(-0.7); // Yukarı çık
        //             elevatorMotor2.set(-0.7);
        //         }

        //     }
        //     stopMotors();
        // }else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)){            
        //     while (!yukariSwitch.get()){
        //         elevatorMotor1.set(-0.7);
        //         elevatorMotor2.set(-0.7);
        //         yukari_cikildi = true;
        //     }
        //     stopMotors();
        // }else if (joystick.getRawButtonPressed(PRESET_1_BUTTON)){
        //     while (!asagiSwitch.get()){
        //         elevatorMotor1.set(0.7);
        //         elevatorMotor2.set(0.7);
        //         yukari_cikildi = false;
        //     }
        //     stopMotors();
        // }


        // **Manuel Kontrol**
        // ! Orta Switch Sensörü ile Asansör pozisyon kontrolü yapılıyor.
        if (joystick.getRawButton(UP_BUTTON) && !yukariSwitch.get()) {
            elevatorMotor1.set(-0.7); // Yukarı
            elevatorMotor2.set(-0.7);
            if (ortaSwitch.get()){
                yukari_cikildi = true;
            }else if (yukariSwitch.get()){
                yukari_cikildi = true;
                stopMotors();
            }
        } else if (joystick.getRawButton(DOWN_BUTTON) && !asagiSwitch.get()) {
            elevatorMotor1.set(0.7);  // Aşağı
            elevatorMotor2.set(0.7);
            if (ortaSwitch.get()){
                yukari_cikildi = false;
            }else if (asagiSwitch.get()){
                yukari_cikildi = false;
                stopMotors();
            } // Güvenlik Amaçlı Motorları Durdurma Komutları
        }else if (yukariSwitch.get()){
            stopMotors();
            yukari_cikildi = true;
        }else if (asagiSwitch.get()){
            stopMotors();
            yukari_cikildi = false;
        }
         else {
            stopMotors();
        }

        // ! Shuffle boarda verileri yazdırma
        boolean dioState = ortaSwitch.get(); // 9. DIO pininin durumu
        boolean dio7 = yukariSwitch.get();
        boolean asagiDeger = asagiSwitch.get();
        System.out.println("DIO Switch State (9. DIO): " + dioState); // Konsola yazdır
        System.out.println("DIO Switch State (7. DIO): " + dio7);
        System.out.println("DIO 8. pin" + asagiDeger);
        SmartDashboard.putBoolean("PHOTO SWITCH", dioState);
        SmartDashboard.putBoolean("7 Switch", dio7);
        SmartDashboard.putBoolean("AsagiSwitch", asagiDeger);
    }
    

}
