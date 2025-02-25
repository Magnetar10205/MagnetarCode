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

// !AYRAÇ

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.DigitalInput;

// public class ElevatorCode extends SubsystemBase {
//     private final PWMVictorSPX elevatorMotor1;
//     private final PWMVictorSPX elevatorMotor2;
//     private final Joystick joystick;
    
//     // Motor and joystick ports
//     private static final int JOYSTICK_PORT = 0;
    
//     // Manual control buttons
//     private static final int UP_BUTTON = 6;   // RB button
//     private static final int DOWN_BUTTON = 5; // LB button
    
//     // Preset buttons
//     private static final int PRESET_1_BUTTON = 2; // X button
//     private static final int PRESET_2_BUTTON = 1; // Square button
//     private static final int PRESET_3_BUTTON = 4; // Triangle button

//     // Limit switch ports
//     private static final int LIMIT_SWITCH_1_PORT = 0;     // Bottom
//     private static final int LIMIT_SWITCH_2_UP_PORT = 1;   // Level 2 upper switch
//     private static final int LIMIT_SWITCH_2_DOWN_PORT = 2; // Level 2 lower switch
//     private static final int LIMIT_SWITCH_3_PORT = 3;     // Top
    
//     private final DigitalInput limitSwitch1;      // Bottom
//     private final DigitalInput limitSwitch2Up;    // Level 2 upper
//     private final DigitalInput limitSwitch2Down;  // Level 2 lower
//     private final DigitalInput limitSwitch3;      // Top
    
//     // Preset mode
//     private int targetPreset = 0;
//     private boolean movingDown = false;  // Track direction
    
//     public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
//         elevatorMotor1 = new PWMVictorSPX(MotorPort1);
//         elevatorMotor2 = new PWMVictorSPX(MotorPort2);
//         this.joystick = joystick;
        
//         limitSwitch1 = new DigitalInput(LIMIT_SWITCH_1_PORT);
//         limitSwitch2Up = new DigitalInput(LIMIT_SWITCH_2_UP_PORT);
//         limitSwitch2Down = new DigitalInput(LIMIT_SWITCH_2_DOWN_PORT);
//         limitSwitch3 = new DigitalInput(LIMIT_SWITCH_3_PORT);
//     }

//     @Override
//     public void periodic() {
//         // Handle preset selection
//         if (targetPreset == 0) {
//             if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
//                 targetPreset = 1;
//                 movingDown = limitSwitch3.get() || limitSwitch2Up.get();
//             } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
//                 targetPreset = 2;
//                 movingDown = limitSwitch3.get();
//             } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
//                 targetPreset = 3;
//                 movingDown = false;
//             }
//         }
        
//         // Handle preset movement
//         if (targetPreset != 0) {
//             switch(targetPreset) {
//                 case 1: // Bottom level
//                     if (!limitSwitch1.get()) {
//                         elevatorMotor1.set(0.8);  // Down is positive
//                         elevatorMotor2.set(0.8);
//                         movingDown = true;
//                     } else {
//                         stopMotors();
//                         targetPreset = 0;
//                     }
//                     break;
//                 case 2: // Middle level
//                     if (movingDown) {
//                         if (!limitSwitch2Down.get()) {
//                             elevatorMotor1.set(0.8);  // Down
//                             elevatorMotor2.set(0.8);
//                         } else {
//                             stopMotors();
//                             targetPreset = 0;
//                         }
//                     } else {
//                         if (!limitSwitch2Up.get()) {
//                             elevatorMotor1.set(-0.8); // Up
//                             elevatorMotor2.set(-0.8);
//                         } else {
//                             stopMotors();
//                             targetPreset = 0;
//                         }
//                     }
//                     break;
//                 case 3: // Top level
//                     if (!limitSwitch3.get()) {
//                         elevatorMotor1.set(-0.8); // Up is negative
//                         elevatorMotor2.set(-0.8);
//                         movingDown = false;
//                     } else {
//                         stopMotors();
//                         targetPreset = 0;
//                     }
//                     break;
//             }
//         } else {
//             // Manual control with limit switch safety
//             if (joystick.getRawButton(UP_BUTTON) && !limitSwitch3.get()) {
//                 elevatorMotor1.set(-0.8); // Up
//                 elevatorMotor2.set(-0.8);
//                 movingDown = false;
//             } else if (joystick.getRawButton(DOWN_BUTTON) && !limitSwitch1.get()) {
//                 elevatorMotor1.set(0.8);  // Down
//                 elevatorMotor2.set(0.8);
//                 movingDown = true;
//             } else {
//                 stopMotors();
//             }
//         }
//     }
    
//     private void stopMotors() {
//         elevatorMotor1.set(0.0);
//         elevatorMotor2.set(0.0);
//     }
// }

// ! AYRAÇ


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
    
//     // Manuel kontrol tuşları
//     private static final int UP_BUTTON = 6;   // RB butonu
//     private static final int DOWN_BUTTON = 5; // LB butonu
    
//     // Preset butonları
//     private static final int PRESET_1_BUTTON = 2; // X button
//     private static final int PRESET_2_BUTTON = 1; // Square button
//     private static final int PRESET_3_BUTTON = 4; // Triangle button

//     // Limit switch portları
//     private static final int LIMIT_SWITCH_BOTTOM = 0; // Alt switch
//     private static final int LIMIT_SWITCH_TOP = 3;    // Üst switch
    
//     private final DigitalInput limitSwitchBottom;  // Alt
//     private final DigitalInput limitSwitchTop;     // Üst
    
//     public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
//         elevatorMotor1 = new PWMVictorSPX(MotorPort1);
//         elevatorMotor2 = new PWMVictorSPX(MotorPort2);
//         this.joystick = joystick;
        
//         limitSwitchBottom = new DigitalInput(LIMIT_SWITCH_BOTTOM);
//         limitSwitchTop = new DigitalInput(LIMIT_SWITCH_TOP);
//     }

//     @Override
//     public void periodic() {
// /*
//         // Eski kodlar yorum satırına alındı

//         // Handle preset selection
//         if (targetPreset == 0) {
//             if (joystick.getRawButtonPressed(PRESET_1_BUTTON)) {
//                 targetPreset = 1;
//                 movingDown = limitSwitch3.get() || limitSwitch2Up.get();
//             } else if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
//                 targetPreset = 2;
//                 movingDown = limitSwitch3.get();
//             } else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)) {
//                 targetPreset = 3;
//                 movingDown = false;
//             }
//         }
        
//         // Handle preset movement
//         if (targetPreset != 0) {
//             switch(targetPreset) {
//                 case 1: // Bottom level
//                     if (!limitSwitch1.get()) {
//                         elevatorMotor1.set(0.8);  // Down is positive
//                         elevatorMotor2.set(0.8);
//                         movingDown = true;
//                     } else {
//                         stopMotors();
//                         targetPreset = 0;
//                     }
//                     break;
//                 case 2: // Middle level
//                     if (movingDown) {
//                         if (!limitSwitch2Down.get()) {
//                             elevatorMotor1.set(0.8);  // Down
//                             elevatorMotor2.set(0.8);
//                         } else {
//                             stopMotors();
//                             targetPreset = 0;
//                         }
//                     } else {
//                         if (!limitSwitch2Up.get()) {
//                             elevatorMotor1.set(-0.8); // Up
//                             elevatorMotor2.set(-0.8);
//                         } else {
//                             stopMotors();
//                             targetPreset = 0;
//                         }
//                     }
//                     break;
//                 case 3: // Top level
//                     if (!limitSwitch3.get()) {
//                         elevatorMotor1.set(-0.8); // Up is negative
//                         elevatorMotor2.set(-0.8);
//                         movingDown = false;
//                     } else {
//                         stopMotors();
//                         targetPreset = 0;
//                     }
//                     break;
//             }
//         } else {
//             // Manual control with limit switch safety
//             if (joystick.getRawButton(UP_BUTTON) && !limitSwitch3.get()) {
//                 elevatorMotor1.set(-0.8); // Up
//                 elevatorMotor2.set(-0.8);
//                 movingDown = false;
//             } else if (joystick.getRawButton(DOWN_BUTTON) && !limitSwitch1.get()) {
//                 elevatorMotor1.set(0.8);  // Down
//                 elevatorMotor2.set(0.8);
//                 movingDown = true;
//             } else {
//                 stopMotors();
//             }
//         }
//         */

//         // **Preset 2 (Orta Seviye): 9. DIO Pinindeki Switch'e Kadar Yükselme**
//         if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {
//             while (!new DigitalInput(9).get()) { // 9. DIO pinine bağlı switch tetiklenene kadar
//                 elevatorMotor1.set(-0.8); // Yukarı çık
//                 elevatorMotor2.set(-0.8);
//             }
//             stopMotors();
//         }

//         // **Manuel Kontrol**
//         if (joystick.getRawButton(UP_BUTTON) && !limitSwitchTop.get()) {
//             elevatorMotor1.set(-0.8); // Yukarı
//             elevatorMotor2.set(-0.8);
//         } else if (joystick.getRawButton(DOWN_BUTTON) && !limitSwitchBottom.get()) {
//             elevatorMotor1.set(0.8);  // Aşağı
//             elevatorMotor2.set(0.8);
//         } else {
//             stopMotors();
//         }
//     }
    
//     private void stopMotors() {
//         elevatorMotor1.set(0.0);
//         elevatorMotor2.set(0.0);
//     }
// }


// ? AYraç 2


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
    
    // Motor ve joystick portları
    private static final int JOYSTICK_PORT = 0;
    
    // Manuel kontrol tuşları
    private static final int UP_BUTTON = 6;   // RB butonu
    private static final int DOWN_BUTTON = 5; // LB butonu
    
    // Preset butonları
    private static final int PRESET_1_BUTTON = 2; // X button
    private static final int PRESET_2_BUTTON = 1; // Square button
    private static final int PRESET_3_BUTTON = 4; // Triangle button

    // Limit switch portları
    private static final int LIMIT_SWITCH_BOTTOM = 0; // Alt switch
    private static final int LIMIT_SWITCH_TOP = 3;    // Üst switch
    private static boolean yukari_cikildi = false;
    
    // private final DigitalInput limitSwitchBottom;  // Alt
    // private final DigitalInput limitSwitchTop;     // Üst
    private final DigitalInput ortaSwitch;           // 9. DIO pini için switch
    private final DigitalInput yukariSwitch;
    private final DigitalInput asagiSwitch;

    public ElevatorCode(Joystick joystick, int MotorPort1, int MotorPort2) {
        elevatorMotor1 = new PWMVictorSPX(MotorPort1);
        elevatorMotor2 = new PWMVictorSPX(MotorPort2);
        this.joystick = joystick;
        

        ortaSwitch = new DigitalInput(9); // 9. DIO pini için DigitalInput nesnesi
        yukariSwitch = new DigitalInput(7);
        asagiSwitch = new DigitalInput(8);

    }

    @Override
    public void periodic() {
        /*
        // Eski kodlar yorum satırına alındı

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
        */

        // **Preset 2 (Orta Seviye): 9. DIO Pinindeki Switch'e Kadar Yükselme**
        // ! awofheırgyı3rugyıu
        if (joystick.getRawButtonPressed(PRESET_2_BUTTON)) {

            if (yukari_cikildi == true){
                while (!asagiSwitch.get()) { // 9. DIO pinine bağlı switch tetiklenene kadar
                    elevatorMotor1.set(0.7); // Aşağı in
                    elevatorMotor2.set(0.7);
                }
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
        }else if (joystick.getRawButtonPressed(PRESET_3_BUTTON)){            
            while (!yukariSwitch.get()){
                elevatorMotor1.set(-0.7);
                elevatorMotor2.set(-0.7);
                yukari_cikildi = true;
            }
        }else if (joystick.getRawButtonPressed(PRESET_1_BUTTON)){
            while (!asagiSwitch.get()){
                elevatorMotor1.set(0.7);
                elevatorMotor2.set(0.7);
                yukari_cikildi = false;
            }
        }

        // if (joystick.getRawButton(UP_BUTTON) ) {
        //     elevatorMotor1.set(-0.7); // Up
        //     elevatorMotor2.set(-0.7);
        //     // movingDown = false;
        // } else if (joystick.getRawButton(DOWN_BUTTON)) {
        //     elevatorMotor1.set(0.7);  // Down
        //     elevatorMotor2.set(0.7);
        //     // movingDown = true;
        // } else {
        //     stopMotors();
        // }
    


        // **Manuel Kontrol**
        if (joystick.getRawButton(UP_BUTTON) && !yukariSwitch.get()) {
            elevatorMotor1.set(-0.7); // Yukarı
            elevatorMotor2.set(-0.7);
        } else if (joystick.getRawButton(DOWN_BUTTON) && !asagiSwitch.get()) {
            elevatorMotor1.set(0.7);  // Aşağı
            elevatorMotor2.set(0.7);
        } else {
            stopMotors();
        }

        // **9. DIO Pinindeki Değeri Yazdır**
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
    
    private void stopMotors() {
        elevatorMotor1.set(0.0);
        elevatorMotor2.set(0.0);
    }
}
