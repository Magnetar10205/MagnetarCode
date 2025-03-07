package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Coral;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ElevatorCode;
import frc.robot.subsystems.Alg;

import java.lang.reflect.Type;

import com.ctre.phoenix6.configs.ClosedLoopGeneralConfigs;

import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
    private final Joystick joystick = new Joystick(0); // USB port 0
    private final Drivetrain drivetrain = new Drivetrain(0, 2, 3, 0, 1, joystick);
    private final DigitalInput photoSwitch = new DigitalInput(3);
    private final DigitalInput ortaSwitch = new DigitalInput(5);
    private final DigitalInput yukariSwitch = new DigitalInput(7);
    private DigitalInput AlgPhotoSwitch = new DigitalInput(0);
    private final Alg algSubsystem = new Alg(joystick, 4, AlgPhotoSwitch);
    private final Coral coralSubsystem = new Coral(joystick, 5,6, photoSwitch, algSubsystem); // iki motor bağlanacak ve birbirine ters olucak PhotoSwitch 6. DIO portuna girildi
    private final DigitalInput asagiSwitch = new DigitalInput(8);
    private final ElevatorCode elevatorSubsystem = new ElevatorCode(joystick, 8,9,asagiSwitch, ortaSwitch, yukariSwitch);
    private double startTime;
    private boolean autonomousElevatorControl = false;
    private double coralTimer;
    private boolean CoralBos = false;
    private double coralElapsedTime;
    private boolean elevatorAsagida = false;
    private Alliance alliance;
    private int startPosition;
    private String autColor;
    private int autStartPosition;



    @Override
    public void robotPeriodic() {}

    @Override
    public void robotInit() {
    CameraServer.startAutomaticCapture();
    alliance = DriverStation.getAlliance().orElse(null);    
    startPosition = DriverStation.getLocation().orElse(0);


    
    }


    // @Override
    // public void autonomousInit() {
    //     startTime = Timer.getFPGATimestamp();
    //     elevatorAsagida = false;
    //     autonomousElevatorControl = false;
    //     System.out.println("Otonom Başladı!");
    //     System.out.println("Alliance: " + alliance);
    //     System.out.println("Start Position: " + startPosition);


        
    // }

//     @Override
//     public void autonomousPeriodic() {
//         double elapsedTime = Timer.getFPGATimestamp() - startTime;
    
    
//         // if (elapsedTime < 3.0) {
//         //     drivetrain.NormalArcadeDrive(0, 0.6);
//         // } else if (elapsedTime < 4.0) {
//         //     drivetrain.NormalArcadeDrive(0.5, 0);
//         // } else if (elapsedTime < 5.0) {
//         //     drivetrain.NormalArcadeDrive(-0.5, 0);
//         // } else {
//         //     drivetrain.NormalArcadeDrive(0, 0);
//         // }

//         // ? ileri -->  drivetrain.NormalArcadeDrive(0, 0.6); 
//         // ? Sağa dönüş --> drivetrain.NormalArcadeDrive(0.5, 0);


//         // ? double startTime, double time, double x, double y
//         // drivetrain.SureliDrive(elapsedTime, 2.7, 0,0.6 ); // İleri
 
//         // Timer.delay(0.5);
//         // elevatorSubsystem.elevatorYukari();


//         // ! Otonom 1 (Robot Ortada)
//         // if (elapsedTime < 2.7){ // Resifin yanına yaklaşma
//         //     drivetrain.NormalArcadeDrive(0,0.6);

//         // }else if (!autonomousElevatorControl && elapsedTime > 3 && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
//         //     drivetrain.stopMotors();
//         //     Timer.delay(0.5);
//         //     // elevatorSubsystem.elevatorYukari();
//         //     // if (yukariSwitch.get()){
//         //     //     System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
//         //     //     autonomousElevatorControl = true;
//         //     //     coralTimer = Timer.getFPGATimestamp();
//         //     // }
//         //     // System.out.println("Asansör Çalışıyor");

//         // }

//         // // ! en altta coral alt ayraç
//         // if ( elapsedTime > 3 && elapsedTime <6){ // Coralı at
//         //     autonomousElevatorControl = true;
//         //     coralSubsystem.intakeOut();
//         // }
//         // if (elapsedTime >= 6){ // Coral Durdur
//         //     coralSubsystem.stopMotor();
//         //     // elevatorSubsystem.elevatorAsagi();
//         // }
//         // // ! ayraç


//         // // if ( elapsedTime > 7 && elapsedTime <9){ // Coralı at
//         // //     autonomousElevatorControl = true;
//         // //     coralSubsystem.intakeOut();
//         // // }
//         // // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
//         // //     coralSubsystem.stopMotor();
//         // //     // elevatorSubsystem.elevatorAsagi();
//         // // }



//         // ! Otonom 2 ( Robot yanda --- sağda)

//         // Düz bir şekilde ileri git hafif sola dön sonra tekrar ileri git ve kendini hizaladın
//         // Bu kod yazılacak


//         // if (elapsedTime < 2.35){ // Resifin yanına yaklaşma
//         //     drivetrain.NormalArcadeDrive(0,0.65);
        
//         // }else if (elapsedTime >= 2.8 && elapsedTime <=3.95){
//         //     drivetrain.NormalArcadeDrive(-0.5,0);
        
//         // }else if (elapsedTime >= 3.95 && elapsedTime <= 4.3 ){
//         //     drivetrain.NormalArcadeDrive(0,0.6);

//         // }
//         // // }else if (!autonomousElevatorControl && elapsedTime > 3.7  && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
//         // //     drivetrain.stopMotors();
//         // //     Timer.delay(0.5);
//         // //     elevatorSubsystem.elevatorYukari();
//         // //     if (yukariSwitch.get()){
//         // //         System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
//         // //         autonomousElevatorControl = true;
//         // //         coralTimer = Timer.getFPGATimestamp();
//         // //     }
//         // //     System.out.println("Asansör Çalışıyor");

//         // // }

//         //         // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
//         // //     coralSubsystem.stopMotor();
//         // //     // elevatorSubsystem.elevatorAsagi();
//         // // }
//         // if ( elapsedTime > 5 && elapsedTime <7){ // Coralı at
//         //     autonomousElevatorControl = true;
//         //     coralSubsystem.intakeManuel(-0.4, -0.4);
//         // }
//         // else if ( elapsedTime >= 7){ 
//         //     coralSubsystem.stopMotor();
//         //     // elevatorSubsystem.elevatorAsagi();
//         // }


//         // ! Otonom 3 (sol)

//         // if (elapsedTime < 2.4){ // Resifin yanına yaklaşma
//         //     drivetrain.NormalArcadeDrive(0,0.65);
        
//         // }else if (elapsedTime >= 2.8 && elapsedTime <=3.65){
//         //     drivetrain.NormalArcadeDrive(0.5,0);
        
//         // }else if (elapsedTime >= 3.5 && elapsedTime <= 4.3 ){
//         //     drivetrain.NormalArcadeDrive(0,0.6);

//         // }
//         // // }else if (!autonomousElevatorControl && elapsedTime > 3.7  && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
//         // //     drivetrain.stopMotors();
//         // //     Timer.delay(0.5);
//         // //     elevatorSubsystem.elevatorYukari();
//         // //     if (yukariSwitch.get()){
//         // //         System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
//         // //         autonomousElevatorControl = true;
//         // //         coralTimer = Timer.getFPGATimestamp();
//         // //     }
//         // //     System.out.println("Asansör Çalışıyor");

//         // // }

//         //         // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
//         // //     coralSubsystem.stopMotor();
//         // //     // elevatorSubsystem.elevatorAsagi();
//         // // }
//         // if ( elapsedTime > 5 && elapsedTime <7){ // Coralı at
//         //     autonomousElevatorControl = true;
//         //     coralSubsystem.intakeManuel(-0.4, -0.4);
//         // }
//         // else if ( elapsedTime >= 7){ 
//         //     coralSubsystem.stopMotor();
//         //     // elevatorSubsystem.elevatorAsagi();
//         // }


//         System.out.println(elapsedTime);
        
//     }

private void redPosition1Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Kırmızı 1. Pozisyon Otonomu Çalışıyor");
    // Buraya hareket kodlarını yaz
}

private void redPosition2Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Kırmızı 2. Pozisyon Otonomu Çalışıyor");
}

private void redPosition3Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Kırmızı 3. Pozisyon Otonomu Çalışıyor");
}

private void bluePosition1Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Mavi 1. Pozisyon Otonomu Çalışıyor");
}

private void bluePosition2Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Mavi 2. Pozisyon Otonomu Çalışıyor");
}

private void bluePosition3Routine(String alliance, int startPosition) {
    this.autColor = alliance;
    this.autStartPosition = startPosition;
    System.out.println("Mavi 3. Pozisyon Otonomu Çalışıyor");
}




@Override
public void autonomousInit() {
    alliance = DriverStation.getAlliance().orElse(null); 
       
    startPosition = DriverStation.getLocation().orElse(0);
    startTime = Timer.getFPGATimestamp();
    elevatorAsagida = false;
    autonomousElevatorControl = false;
    System.out.println("Otonom Başladı!");
    System.out.println("Alliance: " + alliance);
    System.out.println("Start Position: " + startPosition);
    SmartDashboard.putString("Pozisyon Rengi", alliance.toString());
    SmartDashboard.putNumber("Başlangıç Pozisyon Numarası", startPosition);

    if (alliance == Alliance.Red) {
        // Kırmızı takım için rotalar
        if (startPosition == 1) {
            redPosition1Routine(alliance.toString(),startPosition);
        } else if (startPosition == 2) {
            redPosition2Routine(alliance.toString(),startPosition);
        } else if (startPosition == 3) {
            redPosition3Routine(alliance.toString(),startPosition);
        }
    } else if (alliance == Alliance.Blue) {
        // Mavi takım için rotalar
        if (startPosition == 1) {
            bluePosition1Routine(alliance.toString(),startPosition);
        } else if (startPosition == 2) {
            bluePosition2Routine(alliance.toString(),startPosition);
        } else if (startPosition == 3) {
            bluePosition3Routine(alliance.toString(),startPosition);
        }
    }
}



// ! Team station auto start


// // @Override
// // public void autonomousPeriodic() {
// //     double elapsedTime = Timer.getFPGATimestamp() - startTime;

// //     if (("Red".equals(autColor) && autStartPosition == 1) || ("Blue".equals(autColor) && autStartPosition == 3)) { // Kırmızı 1 Mavi 3
// //         // ! Kırmızı 1 Mavi 3 (sağ)

// //         if (elapsedTime < 2.35){ // Resifin yanına yaklaşma
// //             drivetrain.NormalArcadeDrive(0,0.65);
        
// //         }else if (elapsedTime >= 2.8 && elapsedTime <=3.95){
// //             drivetrain.NormalArcadeDrive(-0.5,0);
        
// //         }else if (elapsedTime >= 3.95 && elapsedTime <= 4.3 ){
// //             drivetrain.NormalArcadeDrive(0,0.6);

// //         }
// //         // }else if (!autonomousElevatorControl && elapsedTime > 3.7  && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
// //         //     drivetrain.stopMotors();
// //         //     Timer.delay(0.5);
// //         //     elevatorSubsystem.elevatorYukari();
// //         //     if (yukariSwitch.get()){
// //         //         System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
// //         //         autonomousElevatorControl = true;
// //         //         coralTimer = Timer.getFPGATimestamp();
// //         //     }
// //         //     System.out.println("Asansör Çalışıyor");

// //         // }

// //                 // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
// //         //     coralSubsystem.stopMotor();
// //         //     // elevatorSubsystem.elevatorAsagi();
// //         // }
// //         if ( elapsedTime > 5 && elapsedTime <7){ // Coralı at
// //             autonomousElevatorControl = true;
// //             coralSubsystem.intakeManuel(-0.4, -0.4);
// //         }
// //         else if ( elapsedTime >= 7){ 
// //             coralSubsystem.stopMotor();
// //             // elevatorSubsystem.elevatorAsagi();
// //         }


// //     }
// //     else if (("Red".equals(autColor) && autStartPosition == 2) || ("Blue".equals(autColor) && autStartPosition == 2)) { // Kırmızı 2 Mavi 2
// //         //! Kırmızı 2 Mavi 2 (orta)
// //         //         ! Otonom 1 (Robot Ortada)

        
// //         if (elapsedTime < 2.7){ // Resifin yanına yaklaşma
// //             drivetrain.NormalArcadeDrive(0,0.6);

// //         }else if (!autonomousElevatorControl && elapsedTime > 3 && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
// //             drivetrain.stopMotors();
// //             Timer.delay(0.5);
// //             // elevatorSubsystem.elevatorYukari();
// //             // if (yukariSwitch.get()){
// //             //     System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
// //             //     autonomousElevatorControl = true;
// //             //     coralTimer = Timer.getFPGATimestamp();
// //             // }
// //             // System.out.println("Asansör Çalışıyor");

// //         }

// //         // ! en altta coral alt ayraç
// //         if ( elapsedTime > 3 && elapsedTime <6){ // Coralı at
// //             autonomousElevatorControl = true;
// //             coralSubsystem.intakeOut();
// //         }
// //         if (elapsedTime >= 6){ // Coral Durdur
// //             coralSubsystem.stopMotor();
// //             // elevatorSubsystem.elevatorAsagi();
// //         }
// //         // ! ayraç


// //         // if ( elapsedTime > 7 && elapsedTime <9){ // Coralı at
// //         //     autonomousElevatorControl = true;
// //         //     coralSubsystem.intakeOut();
// //         // }
// //         // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
// //         //     coralSubsystem.stopMotor();
// //         //     // elevatorSubsystem.elevatorAsagi();
// //         // }
// //     }

    
// //     else if (("Red".equals(autColor) && autStartPosition == 3) || ("Blue".equals(autColor) && autStartPosition == 1)) { // Kırmızı 3 Mavi 1
// //         //! Kırmızı 3 Mavi 1 (Yanda)

        
// //         // ! Otonom 3 (sol)

// //         if (elapsedTime < 2.4){ // Resifin yanına yaklaşma
// //             drivetrain.NormalArcadeDrive(0,0.65);
        
// //         }else if (elapsedTime >= 2.8 && elapsedTime <=3.65){
// //             drivetrain.NormalArcadeDrive(0.5,0);
        
// //         }else if (elapsedTime >= 3.5 && elapsedTime <= 4.3 ){
// //             drivetrain.NormalArcadeDrive(0,0.6);

// //         }
// //         // }else if (!autonomousElevatorControl && elapsedTime > 3.7  && !yukariSwitch.get() ){ // Asansör yukarıya çıkar
// //         //     drivetrain.stopMotors();
// //         //     Timer.delay(0.5);
// //         //     elevatorSubsystem.elevatorYukari();
// //         //     if (yukariSwitch.get()){
// //         //         System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
// //         //         autonomousElevatorControl = true;
// //         //         coralTimer = Timer.getFPGATimestamp();
// //         //     }
// //         //     System.out.println("Asansör Çalışıyor");

// //         // }

// //                 // if ( !elevatorAsagida &&elapsedTime >= 9){ // Asansörü aşağı indir
// //         //     coralSubsystem.stopMotor();
// //         //     // elevatorSubsystem.elevatorAsagi();
// //         // }
// //         if ( elapsedTime > 5 && elapsedTime <7){ // Coralı at
// //             autonomousElevatorControl = true;
// //             coralSubsystem.intakeManuel(-0.4, -0.4);
// //         }
// //         else if ( elapsedTime >= 7){ 
// //             coralSubsystem.stopMotor();
// //             // elevatorSubsystem.elevatorAsagi();
// //         }
// //     }
// // }



@Override
public void autonomousPeriodic(){
    double elapsedTime = Timer.getFPGATimestamp() - startTime;

    if (elapsedTime < 6){ // Resifin yanına yaklaşma
        drivetrain.NormalArcadeDrive(0,0.5);

        
    }else if (!autonomousElevatorControl && elapsedTime > 6 && !yukariSwitch.get() && !ortaSwitch.get() ){ // Asansör yukarıya çıkar
        drivetrain.stopMotors();
        Timer.delay(0.5);
        // elevatorSubsystem.elevatorYukari();
        // if (yukariSwitch.get()){
        //     System.out.println("Asansör bitti Yukarı Switch Çalışıyor");
    //     autonomousElevatorControl = true;
        //     coralTimer = Timer.getFPGATimestamp();
        // }
        // System.out.println("Asansör Çalışıyor");
        elevatorSubsystem.elevatorOrta();

    }
    if (ortaSwitch.get()){
        autonomousElevatorControl = true;
    }
    if (autonomousElevatorControl){

        if ( elapsedTime > 8.5 && elapsedTime <10.5){ // Coralı at
            autonomousElevatorControl = true;
            coralSubsystem.intakeManuel(-0.4, -0.4);
        }
        else if ( elapsedTime > 10.5){ 
            coralSubsystem.stopMotor();
            // elevatorSubsystem.elevatorAsagi();
        }
    }

    // // ! en altta coral alt ayraç
    // if ( elapsedTime > 3 && elapsedTime <6){ // Coralı at
    //     autonomousElevatorControl = true;
    //     coralSubsystem.intakeOut();
    // }
    // if (elapsedTime >= 6){ // Coral Durdur
    //     coralSubsystem.stopMotor();
    //     // elevatorSubsystem.elevatorAsagi();
    // }
    // // ! ayraç


    
        
}

// ! Team station auto finish

    @Override
    public void teleopInit() {
        System.out.println("Teleop Mode Started");
    }

    @Override
    public void teleopPeriodic() {
        drivetrain.DriveArcade();
        
        // Coral Butonları
        boolean button1 = joystick.getRawButton(7);
        boolean button2 = joystick.getRawButton(8);

        // Alg Butonları
        boolean button3 = joystick.getRawButton(9);
        boolean button4 = joystick.getRawButton(10);
        boolean button14 = joystick.getRawButton(14);



        // ! Coral Subsystem Kontrolü

        if (button1) {
            coralSubsystem.intakeIn();
        } else if (button2) {
            coralSubsystem.intakeOut();
            // if (photoSwitch.get()){
            //     Timer.delay(1);
            //     coralSubsystem.stopMotor();
            //     elevatorSubsystem.elevatorAsagi();
            // }
            Timer.delay(1);
            coralSubsystem.stopMotor();
            // elevatorSubsystem.elevatorAsagi();
        }else{
            coralSubsystem.stopMotor();
        }

        // ! Alg Subsystem Kontrolü
        if (button3 && button4) {
            algSubsystem.stopMotor();
        } else if (button3) {
            algSubsystem.intakeIn();
        } else if (button4) {
            algSubsystem.intakeOut();
        }else if (button14) {
            algSubsystem.setDuvaraYaklasildi(true);
        }
         else {
            algSubsystem.stopMotor();
        }


        elevatorSubsystem.periodic();
        coralSubsystem.periodic();
        algSubsystem.periodic();

        
        // ! ShuffleBoard Verileri Yazdırma
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
