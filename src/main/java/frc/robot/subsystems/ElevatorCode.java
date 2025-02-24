package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorCode extends SubsystemBase {
    private final PWMVictorSPX elevatorMotor1;
    private final PWMVictorSPX elevatorMotor2;
    private final Joystick joystick;
    
    // Motor ve joystick portları
    private static final int JOYSTICK_PORT = 0;
    
    // Manuel kontrol butonları
    private static final int UP_BUTTON = 6; // RB tuşu
    private static final int DOWN_BUTTON = 5; // LB tuşu
    
    // Preset butonları
    private static final int PRESET_1_BUTTON = 2; // X butonu
    private static final int PRESET_2_BUTTON = 1; // Kare butonu
    private static final int PRESET_3_BUTTON = 4; // Üçgen butonu

    // Limit switch portları
    private static final int LIMIT_SWITCH_1_PORT = 0;
    private static final int LIMIT_SWITCH_2_PORT = 1;
    private static final int LIMIT_SWITCH_3_PORT = 2;
    
    private final DigitalInput limitSwitch1;
    private final DigitalInput limitSwitch2;
    private final DigitalInput limitSwitch3;
    
    // Preset modu
    private int targetPreset = 0;
    
    public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
        elevatorMotor1 = new PWMVictorSPX(MotorPort1);
        elevatorMotor2 = new PWMVictorSPX(MotorPort2);
        this.joystick = joystick;
        
        limitSwitch1 = new DigitalInput(LIMIT_SWITCH_1_PORT);
        limitSwitch2 = new DigitalInput(LIMIT_SWITCH_2_PORT);
        limitSwitch3 = new DigitalInput(LIMIT_SWITCH_3_PORT);
    }

    @Override
    public void periodic() {
        if (targetPreset == 0) {
            if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
                targetPreset = 1;
            } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
                targetPreset = 2;
            } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
                targetPreset = 3;
            }
        }
        
        if (targetPreset != 0) {
            switch(targetPreset) {
                case 1:
                    if (!limitSwitch1.get()) {
                        elevatorMotor1.set(-0.8);
                        elevatorMotor2.set(-0.8);
                    } else {
                        elevatorMotor1.set(0.0);
                        elevatorMotor2.set(0.0);
                        targetPreset = 0;
                    }
                    break;
                case 2:
                    if (!limitSwitch2.get()) {
                        elevatorMotor1.set(0.8);
                        elevatorMotor2.set(0.8);
                    } else {
                        elevatorMotor1.set(0.0);
                        elevatorMotor2.set(0.0);
                        targetPreset = 0;
                    }
                    break;
                case 3:
                    if (!limitSwitch3.get()) {
                        elevatorMotor1.set(0.8);
                        elevatorMotor2.set(0.8);
                    } else {
                        elevatorMotor1.set(0.0);
                        elevatorMotor2.set(0.0);
                        targetPreset = 0;
                    }
                    break;
            }
        } else {
            if (joystick.getRawButton(UP_BUTTON)) {
                elevatorMotor1.set(0.8);
                elevatorMotor2.set(0.8);
            } else if (joystick.getRawButton(DOWN_BUTTON)) {
                elevatorMotor1.set(-0.8);
                elevatorMotor2.set(-0.8);
            } else {
                elevatorMotor1.set(0.0);
                elevatorMotor2.set(0.0);  // Added this line to stop both motors
            }
        }
    }
}