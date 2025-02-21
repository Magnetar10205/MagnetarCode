package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorCode extends SubsystemBase {
    private final Spark elevatorMotor;
    private final Joystick joystick;
    
    // Motor ve joystick portları
    private static final int MOTOR_PORT = 5;
    private static final int JOYSTICK_PORT = 0;
    
    // Manuel kontrol butonları (örneğin, A: yukarı, B: aşağı)
    private static final int UP_BUTTON = 1;
    private static final int DOWN_BUTTON = 2;
    
    // Preset butonları (limit switch presetleri için)
    // Örneğin, X, Y, RB, LB gibi butonlar kullanılabilir
    private static final int PRESET_1_BUTTON = 3; // Örneğin: X butonu
    private static final int PRESET_2_BUTTON = 4; // Örneğin: Y butonu
    private static final int PRESET_3_BUTTON = 1; // Örneğin: RB butonu
    private static final int PRESET_4_BUTTON = 2; // Örneğin: LB butonu

    // Limit switch portları (digital input port numaraları)
    private static final int LIMIT_SWITCH_1_PORT = 0;
    private static final int LIMIT_SWITCH_2_PORT = 1;
    private static final int LIMIT_SWITCH_3_PORT = 2;
    private static final int LIMIT_SWITCH_4_PORT = 3;
    
    private final DigitalInput limitSwitch1;
    private final DigitalInput limitSwitch2;
    private final DigitalInput limitSwitch3;
    private final DigitalInput limitSwitch4;
    
    // Preset modu: 0 = devrede değil, 1-4 = ilgili preset aktif
    private int targetPreset = 0;
    
    public ElevatorCode(Joystick joystick,int MotorPort) {
        elevatorMotor = new Spark(MOTOR_PORT);
        this.joystick = joystick;
        
        limitSwitch1 = new DigitalInput(LIMIT_SWITCH_1_PORT);
        limitSwitch2 = new DigitalInput(LIMIT_SWITCH_2_PORT);
        limitSwitch3 = new DigitalInput(LIMIT_SWITCH_3_PORT);
        limitSwitch4 = new DigitalInput(LIMIT_SWITCH_4_PORT);
    }

   @Override
    public void periodic() {
        // Eğer zaten bir preset modu aktif değilse, preset butonlarına bak.
        if (targetPreset == 0) {
            if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
                targetPreset = 1;
            } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
                targetPreset = 2;
            } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
                targetPreset = 3;
            } else if (joystick.getRawButtonPressed(PRESET_4_BUTTON)) {
                targetPreset = 4;
            }
        }
        
        // Eğer bir preset modu aktifse, ilgili limit switch’e ulaşana kadar asansörü çalıştır.
        if (targetPreset != 0) {
            switch(targetPreset) {
                case 1:
                    // Preset 1: Örneğin, asansörün alt konuma inmesi gerekiyor.
                    // Limit switch tetiklenene kadar aşağı doğru hareket ettir.
                    if (!limitSwitch1.get()) {  // false: limit switch henüz tetiklenmedi
                        elevatorMotor.set(-0.8);
                    } else {
                        elevatorMotor.set(0.0);
                        targetPreset = 0; // Preset tamamlandı.
                    }
                    break;
                case 2:
                    // Preset 2: Asansörü yukarı kaldırmak için.
                    if (!limitSwitch2.get()) {
                        elevatorMotor.set(0.8);
                    } else {
                        elevatorMotor.set(0.0);
                        targetPreset = 0;
                    }
                    break;
                case 3:
                    // Preset 3: Asansörü yukarı kaldırmak için.
                    if (!limitSwitch3.get()) {
                        elevatorMotor.set(0.8);
                    } else {
                        elevatorMotor.set(0.0);
                        targetPreset = 0;
                    }
                    break;
                case 4:
                    // Preset 4: En üst konum, asansörü yukarı kaldır.
                    if (!limitSwitch4.get()) {
                        elevatorMotor.set(0.8);
                    } else {
                        elevatorMotor.set(0.0);
                        targetPreset = 0;
                    }
                    break;
            }
        } else {
            // Preset modu aktif değilse, manuel kontrol devrede.
            if (joystick.getRawButton(UP_BUTTON)) {
                elevatorMotor.set(0.8);
            } else if (joystick.getRawButton(DOWN_BUTTON)) {
                elevatorMotor.set(-0.8);
            } else {
                elevatorMotor.set(0.0);
            }
        }
    }
}
