package org.usfirst.frc4904.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc4904.standard.custom.motorcontrollers.SmartMotorController;

public class ExampleSubsystem extends SubsystemBase {

    private static final double VOLTAGE = 6;

    private final SmartMotorController[] motors;

    public ExampleSubsystem(SmartMotorController leftMotor, SmartMotorController rightMotor) {
        motors = new SmartMotorController[] { leftMotor, rightMotor };
    }

    private void setVoltage(double voltage) {
        for (SmartMotorController motor : motors) {
            motor.setVoltage(voltage);
        }
    }

    public void stop() {
        for (SmartMotorController motor : motors) {
            motor.stopMotor();
        }
    }

    public Command c_up() {
        return startEnd(() -> setVoltage(VOLTAGE), this::stop);
    }

    public Command c_down() {
        return startEnd(() -> setVoltage(-VOLTAGE), this::stop);
    }
}
