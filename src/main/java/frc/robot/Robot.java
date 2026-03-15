// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.WildBoard.WildBoard;
import frc.robot.WildBoard.Panels.*;

/**
 * The methods in this class are called automatically corresponding to each
 * mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the
 * package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  public static WildBoard dashboard;

  final VelocitySimpleSubsystem WBshooter;
  final VelocitySimpleSubsystem WBintake;
  final VelocitySimpleSubsystem WBspindexer;
  final VelocitySimpleSubsystem WBindexer;
  final SimpleSubsystem WBturret;
  final SimpleSubsystem WBhood;
  final SwerveModules WBswerveModules;
  final MasterAlarms WBalarms;

  public double[] temps = new double[] { 40, 40, 40, 40, 40, 40 };
  public double[] tempDirs = new double[] { 1, 1, 1, 1, 1, 1 };
  public double[] swervePoses = new double[] { 0, 0, 0, 0 };
  public double[] swervePosesDirs = new double[] { 0, 0, 0, 0 };
  public double[] swerveVels = new double[] { 0, 0, 0, 0 };
  public double[] swerveTemps = new double[] { 40, 40, 40, 40 };
  public double[] swerveTempsDirs = new double[] { 1, 1, 1, 1 };

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  public Robot() {
    dashboard = new WildBoard(5804);

    // Checklist
    dashboard.addTab(new Tab()
        .addChild(new Checklist())
        .setTitle("Checklist"));

    // TeleOp
    dashboard.addTab(new Tab()
        .setTitle("TeleOp")
        .addChild(new Col(2).addChild(
            new FieldMap()))
        .addChild(new Col(6).addChild(
            new Row().addChild(
                new CameraFeed(5800)).addChild(
                    new CameraFeed(5801)))
            .addChild(
                new Row().addChild(
                    new CameraFeed(5802)).addChild(
                        new CameraFeed(5803))))
        .addChild(new Col(4).addChild(
            new AutoChooser(new String[] { "Go Forward", "Move Fast" }).onChange((String choice) -> {
              System.out.println(choice);
            })).addChild(
                new Overrides(new String[] { "Limelight PowerSaver", "Disable Camera Feeds", "CompMode",
                    "Disable Shot Smoothing", "Always Aim at Hub", "Disable Shoot Safeties" }, 2))));

    // Subsystems
    WBshooter = new VelocitySimpleSubsystem("Shooter");
    WBintake = new VelocitySimpleSubsystem("Intake");
    WBindexer = new VelocitySimpleSubsystem("Indexer");
    WBspindexer = new VelocitySimpleSubsystem("Spindexer");

    WBturret = new SimpleSubsystem("Turret", false);
    WBhood = new SimpleSubsystem("Turret Hood", true);

    WBswerveModules = new SwerveModules();
    WBalarms = new MasterAlarms(
        new String[] { "SWOH", "SSOH", "CNBS", "SHRT", "JOYS", "IMU", "PING", "FPS", "LOOP", "BATT" },
        new String[] { "Swerve Overheat", "Subsystem Overheat", "Canbus Error", "Short Detected",
            "Joystick Disconnect", "IMU Failure/Disconnect", "Ping High/Failed", "FPS Low", "Loop Time too High",
            "Battery Voltage Low" },
        2);

    dashboard.addTab(new Tab()
        .addChild(
            new Col(4).addChild(
                WBswerveModules).addChild(
                    new SystemsCheck().onTest(() -> {
                      // drive in square

                      // full climber squence

                      // deploy intake
                      // intake
                      // retract intake
                      // aim turret to 0
                      // aim hood to 0
                      // spin up shooter
                      // spin up transfer
                      // spin spindexer to shoot
                      System.out.println("blah");
                    })))
        .addChild(
            new Col(3).addChild(
                new Placeholder("Climb", 20)))
        .addChild(
            new Col(5).addChild(
                new Row().addChild(
                    WBshooter).addChild(
                        WBintake))
                .addChild(
                    new Row().addChild(
                        WBindexer).addChild(
                            WBspindexer))
                .addChild(
                    new Row().addChild(
                        WBturret).addChild(
                            WBhood)))
        .setTitle("Subsystems"));

    dashboard.addPanel(new LooptimeMonitor());
    dashboard.addPanel(new PingMonitor());
    dashboard.addPanel(new FPSMonitor());
    dashboard.addPanel(WBalarms);
    dashboard.start();
  }

  @Override
  public void robotPeriodic() {
    WBshooter.updateVals(10 + Math.round(Math.random() * 5), temps[0]);
    WBintake.updateVals(8 + Math.round(Math.random() * 0.55), temps[1]);
    WBspindexer.updateVals(4 + Math.round(Math.random() * 0.6), temps[2]);
    WBindexer.updateVals(0, temps[3]);

    // fake temp vals
    for (int i = 0; i < temps.length; i++) {
      temps[i] += (Math.random() - 0.5) * 0.8 + tempDirs[i] * 0.2;
      if (temps[i] < 20) {
        temps[i] += 0.5;
        tempDirs[i] = 1;
      }
      if (temps[i] > 80) {
        temps[i] -= 0.5;
        tempDirs[i] = -1;
      }

      if (Math.random() < 0.1) {
        temps[i] += (Math.random() - 0.5) * 1.5;
      }

      if (Math.random() < 0.015) {
        tempDirs[i] = Math.random() * 2 - 1;
      }
    }

    WBturret.updateVals(289 + Math.round(Math.random() * 0.6) * 10, temps[4]);
    WBhood.updateVals(21 + Math.round(Math.random() * 0.6) * 2, temps[5]);

    for (int i = 0; i < swervePoses.length; i++) {
      swervePoses[i] += (Math.random() - 0.5) * 5 + swervePosesDirs[i] * 2;

      if (Math.random() < 0.05) {
        swervePosesDirs[i] = Math.random() * 2 - 1;
      }
      if (swervePoses[i] < 0) {
        swervePoses[i] += 360;
      }
      if (swervePoses[i] > 360) {
        swervePoses[i] -= 360;
      }

      swerveVels[i] += (Math.random() - 0.5) * 0.1;
      if (swerveVels[i] < 4) {
        swerveVels[i] += 0.02;
      }
      if (swerveVels[i] < 0) {
        swerveVels[i] = 0;
      }
      if (swerveVels[i] > 6) {
        swerveVels[i] -= 0.03;
      }
      if (Math.random() < 0.01 && swerveVels[i] > 2) {
        swerveVels[i] -= (Math.random()) * 2;
      }

      swerveTemps[i] += (Math.random() - 0.5) * 0.8 + swerveTempsDirs[i] * 0.2;
      if (swerveTemps[i] < 20) {
        swerveTemps[i] += 0.5;
        swerveTempsDirs[i] = 1;
      }
      if (swerveTemps[i] > 80) {
        swerveTemps[i] -= 0.5;
        swerveTempsDirs[i] = -1;
      }
      if (Math.random() < 0.1) {
        swerveTemps[i] += (Math.random() - 0.5) * 1.5;
      }
      if (Math.random() < 0.015) {
        swerveTempsDirs[i] = Math.random() * 2 - 1;
      }
    }

    WBswerveModules.updateVals(swervePoses, swerveTemps, swerveVels);

    WBturret.onUnlock((Boolean locked) -> {
      System.out.println("turret " + (locked ? "locked" : "unlocked"));
    });

    WBturret.onCalib((Boolean pressed) -> {
      System.out.println("turret calib " + (pressed ? "pressed" : "released"));
    });

    dashboard.update();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
