// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.DigitalInput;

// public class ElevatorCode extends SubsystemBase {
//     private final PWMVictorSPX elevatorMotor1;
//     private final PWMVictorSPX elevatorMotor2;
//     private final Joystick joystick;
    
//     // Motor ve joystick portları
//     private static final int JOYSTICK_PORT = 0;
    
//     // Manuel kontrol butonları
//     private static final int UP_BUTTON = 6; // RB tuşu
//     private static final int DOWN_BUTTON = 5; // LB tuşu
    
//     // Preset butonları
//     private static final int PRESET_1_BUTTON = 2; // X butonu
//     private static final int PRESET_2_BUTTON = 1; // Kare butonu
//     private static final int PRESET_3_BUTTON = 4; // Üçgen butonu

//     // Limit switch portları
//     private static final int LIMIT_SWITCH_1_PORT = 0;
//     private static final int LIMIT_SWITCH_2_PORT = 1;
//     private static final int LIMIT_SWITCH_3_PORT = 2;
    
//     private final DigitalInput limitSwitch1;
//     private final DigitalInput limitSwitch2;
//     private final DigitalInput limitSwitch3;
    
//     // Preset modu
//     private int targetPreset = 0;
    
//     public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
//         elevatorMotor1 = new PWMVictorSPX(MotorPort1);
//         elevatorMotor2 = new PWMVictorSPX(MotorPort2);
//         this.joystick = joystick;
        
//         limitSwitch1 = new DigitalInput(LIMIT_SWITCH_1_PORT);
//         limitSwitch2 = new DigitalInput(LIMIT_SWITCH_2_PORT);
//         limitSwitch3 = new DigitalInput(LIMIT_SWITCH_3_PORT);
//     }

//     @Override
//     public void periodic() {
//         if (targetPreset == 0) {
//             if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
//                 targetPreset = 1;
//             } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
//                 targetPreset = 2;
//             } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
//                 targetPreset = 3;
//             }
//         }
        
//         if (targetPreset != 0) {
//             switch(targetPreset) {
//                 case 1:
//                     if (!limitSwitch1.get()) {
//                         elevatorMotor1.set(-0.8);
//                         elevatorMotor2.set(-0.8);
//                     } else {
//                         elevatorMotor1.set(0.0);
//                         elevatorMotor2.set(0.0);
//                         targetPreset = 0;
//                     }
//                     break;
//                 case 2:
//                     if (!limitSwitch2.get()) {
//                         elevatorMotor1.set(0.8);
//                         elevatorMotor2.set(0.8);
//                     } else {
//                         elevatorMotor1.set(0.0);
//                         elevatorMotor2.set(0.0);
//                         targetPreset = 0;
//                     }
//                     break;
//                 case 3:
//                     if (!limitSwitch3.get()) {
//                         elevatorMotor1.set(0.8);
//                         elevatorMotor2.set(0.8);
//                     } else {
//                         elevatorMotor1.set(0.0);
//                         elevatorMotor2.set(0.0);
//                         targetPreset = 0;
//                     }
//                     break;
//             }
//         } else {
//             if (joystick.getRawButton(UP_BUTTON)) {
//                 elevatorMotor1.set(-0.8);
//                 elevatorMotor2.set(-0.8);
//             } else if (joystick.getRawButton(DOWN_BUTTON)) {
//                 elevatorMotor1.set(0.8);
//                 elevatorMotor2.set(0.8);
//             } else {
//                 elevatorMotor1.set(0.0);
//                 elevatorMotor2.set(0.0);  // Added this line to stop both motors
//             }
//         }
//     }
// }


package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;

public class ElevatorCode extends SubsystemBase {
    private final PWMVictorSPX elevatorMotor1;
    private final PWMVictorSPX elevatorMotor2;
    private final Joystick joystick;
    
    // Motor and joystick ports
    private static final int JOYSTICK_PORT = 0;
    
    // Manual control buttons
    private static final int UP_BUTTON = 6;   // RB button
    private static final int DOWN_BUTTON = 5; // LB button
    
    // Preset buttons
    private static final int PRESET_1_BUTTON = 2; // X button
    private static final int PRESET_2_BUTTON = 1; // Square button
    private static final int PRESET_3_BUTTON = 4; // Triangle button

    // Limit switch ports
    private static final int LIMIT_SWITCH_1_PORT = 0;     // Bottom
    private static final int LIMIT_SWITCH_2_UP_PORT = 1;   // Level 2 upper switch
    private static final int LIMIT_SWITCH_2_DOWN_PORT = 2; // Level 2 lower switch
    private static final int LIMIT_SWITCH_3_PORT = 3;     // Top
    
    private final DigitalInput limitSwitch1;      // Bottom
    private final DigitalInput limitSwitch2Up;    // Level 2 upper
    private final DigitalInput limitSwitch2Down;  // Level 2 lower
    private final DigitalInput limitSwitch3;      // Top
    
    // Preset mode
    private int targetPreset = 0;
    private boolean movingDown = false;  // Track direction
    
    public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
        elevatorMotor1 = new PWMVictorSPX(MotorPort1);
        elevatorMotor2 = new PWMVictorSPX(MotorPort2);
        this.joystick = joystick;
        
        limitSwitch1 = new DigitalInput(LIMIT_SWITCH_1_PORT);
        limitSwitch2Up = new DigitalInput(LIMIT_SWITCH_2_UP_PORT);
        limitSwitch2Down = new DigitalInput(LIMIT_SWITCH_2_DOWN_PORT);
        limitSwitch3 = new DigitalInput(LIMIT_SWITCH_3_PORT);
    }

    @Override
    public void periodic() {
        // Handle preset selection
        if (targetPreset == 0) {
            if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
                targetPreset = 1;
                movingDown = limitSwitch3.get() || limitSwitch2Up.get();
            } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
                targetPreset = 2;
                movingDown = limitSwitch3.get();
            } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
                targetPreset = 3;
                movingDown = false;
            }
        }
        
        // Handle preset movement
        if (targetPreset != 0) {
            switch(targetPreset) {
                case 1: // Bottom level
                    if (!limitSwitch1.get()) {
                        elevatorMotor1.set(0.8);  // Down is positive
                        elevatorMotor2.set(0.8);
                        movingDown = true;
                    } else {
                        stopMotors();
                        targetPreset = 0;
                    }
                    break;
                case 2: // Middle level
                    if (movingDown) {
                        if (!limitSwitch2Down.get()) {
                            elevatorMotor1.set(0.8);  // Down
                            elevatorMotor2.set(0.8);
                        } else {
                            stopMotors();
                            targetPreset = 0;
                        }
                    } else {
                        if (!limitSwitch2Up.get()) {
                            elevatorMotor1.set(-0.8); // Up
                            elevatorMotor2.set(-0.8);
                        } else {
                            stopMotors();
                            targetPreset = 0;
                        }
                    }
                    break;
                case 3: // Top level
                    if (!limitSwitch3.get()) {
                        elevatorMotor1.set(-0.8); // Up is negative
                        elevatorMotor2.set(-0.8);
                        movingDown = false;
                    } else {
                        stopMotors();
                        targetPreset = 0;
                    }
                    break;
            }
        } else {
            // Manual control with limit switch safety
            if (joystick.getRawButton(UP_BUTTON) && !limitSwitch3.get()) {
                elevatorMotor1.set(-0.8); // Up
                elevatorMotor2.set(-0.8);
                movingDown = false;
            } else if (joystick.getRawButton(DOWN_BUTTON) && !limitSwitch1.get()) {
                elevatorMotor1.set(0.8);  // Down
                elevatorMotor2.set(0.8);
                movingDown = true;
            } else {
                stopMotors();
            }
        }
    }
    
    private void stopMotors() {
        elevatorMotor1.set(0.0);
        elevatorMotor2.set(0.0);
    }
}