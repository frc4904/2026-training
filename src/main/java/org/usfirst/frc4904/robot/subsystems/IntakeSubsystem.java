package org.usfirst.frc4904.robot.subsystems;

import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private static final double rollerVoltage = 6;
    private static final double angleVoltage = 10;

    private final SmartMotorController rollerMotor, angleMotor;

    public IntakeSubsystem(SmartMotorController rollerMotor, SmartMotorController angleMotor) {
        //YOUR CONSTRUCTOR HERE
        this.rollerMotor = rollerMotor;
        this.angleMotor = angleMotor;
    
    }

    private void setRollerVoltage(double rollerVoltage) {
        rollerMotor.setVoltage(rollerVoltage);
    }
    private void setAngleVoltage(double angleVoltage) {
        angleMotor.setVoltage(angleVoltage);
    }


    public void rollerStop() {
        rollerMotor.stopMotor();
    }
    public void angleStop() {
        angleMotor.stopMotor();
    }


    public Command intake_in() {
        return startEnd(() -> setRollerVoltage(rollerVoltage), this::rollerStop);
    }
    public Command intake_out() {
        return startEnd(() -> setRollerVoltage(-rollerVoltage), this::rollerStop);
    }


    public Command intake_up() {
        return startEnd(() -> setAngleVoltage(angleVoltage), this::angleStop);
    }
    public Command intake_down() {
        return startEnd(() -> setAngleVoltage(-angleVoltage), this::angleStop);
    }

    //TODO:
    //the Intake has a roller motor that needs to spin to intake balls,
    //and an angle motor that needs to spin a certain amount to put it up and down.
    //for now, don't worry about encoders (we will get to that :D)
}
