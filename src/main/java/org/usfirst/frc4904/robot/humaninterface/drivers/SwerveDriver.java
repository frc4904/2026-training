package org.usfirst.frc4904.robot.humaninterface.drivers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

import org.usfirst.frc4904.robot.RobotMap.Component;
import org.usfirst.frc4904.robot.RobotMap.HumanInput;
import org.usfirst.frc4904.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc4904.standard.commands.AlwaysRunnableInstantCommand;
import org.usfirst.frc4904.standard.commands.RunIf;
import org.usfirst.frc4904.standard.commands.RunUnless;
import org.usfirst.frc4904.standard.custom.controllers.CustomCommandPS4;
import org.usfirst.frc4904.standard.humaninput.Driver;
import org.usfirst.frc4904.standard.humaninput.Operator;

import static org.usfirst.frc4904.robot.humaninterface.HumanInterfaceConfig.JOYSTICK_DEADZONE;

public class SwerveDriver extends Driver {

    private static final double SPEED_EXP = 2, TURN_EXP = 2; // TODO TUNE

    public SwerveDriver() {
        super("SwerveDriver");
    }

    @Override
    public void bindCommands() {
        CustomCommandPS4 ps4 = HumanInput.Driver.ps4;

        Component.chassis.setDefaultCommand(
            Component.chassis.c_input(this::getTranslation, this::getTurnSpeed)
                .withName("Driver - swerve drive")
        );

        ps4.povDown().whileTrue(new StartEndCommand(
            () -> Component.chassis.setAutoBrickWhenStill(true),
            () -> Component.chassis.setAutoBrickWhenStill(false)
        ));

        // navx reset
        ps4.povUp().onTrue(
            new AlwaysRunnableInstantCommand(() -> Component.chassis.resetOdometry())
        );

        // flip zeroes
        ps4.povLeft().onTrue(
            new RunUnless(
                new AlwaysRunnableInstantCommand(() -> Component.chassis.flipZero()),
                DriverStation::isTeleopEnabled
            )
        );

        // swerve reset
        ps4.povRight().onTrue(
            new RunUnless(
                new AlwaysRunnableInstantCommand(() -> Component.chassis.zero()),
                DriverStation::isTeleopEnabled
            )
        );

        // ps4.povRight().onTrue(
        //     new AlwaysRunnableInstantCommand(() -> edu.wpi.first.wpilibj.Preferences.setDouble("cheese", 5))
        // );
        // ps4.povRight().whileTrue(
        //     Component.INTAKE_DO_NOT_USE_OR_YOU_WILL_BE_FIRED.c_forward(true)
        // );
        
    }

    @Override
    public void unbindCommands() {
        Component.chassis.removeDefaultCommand();
    }

    protected double getRawForward() {
        return -HumanInput.Driver.ps4.getLeftY();
    }
    protected double getRawLeft() {
        return -HumanInput.Driver.ps4.getLeftX();
    }

    @Override
    public Translation2d getTranslation() {
        try {
            Translation2d translation = new Translation2d(getRawForward(), getRawLeft());
            double mag = translation.getNorm();
            if (mag == 0) return translation;

            double len = scaleGain(MathUtil.applyDeadband(mag, JOYSTICK_DEADZONE), SPEED_EXP);
            return translation.times(len / mag); // unit translation * len
        } catch (Exception e) {
            System.err.println("womp womp");
            return Translation2d.kZero;
        }
    }

    @Override
    public double getTurnSpeed() {
        try {
            double turnSpeed = -HumanInput.Driver.ps4.getRightX();
            return scaleGain(turnSpeed, TURN_EXP);
        } catch (Exception e) {
            System.err.println("womp womp");
            return 0;
        }
    }
}
