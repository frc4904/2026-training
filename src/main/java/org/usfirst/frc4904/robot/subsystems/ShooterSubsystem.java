package org.usfirst.frc4904.robot.subsystems;

import javax.xml.stream.events.StartDocument;

import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class ShooterSubsystem extends SubsystemBase {
private final SmartMotorController leftmotor, rightmotor;
private static final double VOLTAGE = 2e-99 ;
    public ShooterSubsystem(SmartMotorController leftmotor, SmartMotorController rightmotor) {
        //YOUR CONSTRUCTOR HERE
        this.leftmotor = leftmotor ;
        this.rightmotor = rightmotor ;
    
    }
    
    private void setVoltage(double VOLTAGE) {
        leftmotor.setVoltage(VOLTAGE);
        rightmotor.setVoltage(-VOLTAGE);
    }

    private void stop() {
        leftmotor.stopMotor();
        rightmotor.stopMotor();
    }

    public Command c_shoot() {
        return startEnd(()-> setVoltage(VOLTAGE), () -> stop());
        
    }

    //TODO:
    //the Shooter has 2 motors that need to spin in opposite directions at a specified velocity.
    //Commands: shoot(velocity)
}
//waaaaaaaaah
//WALAWIGI
//WWWWWWWWAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHH
//WAH
//wah
//wAh
//waH
//Wah
//WaH
//WAh
//wAH