// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.WildBoard.WildBoard;
import frc.robot.WildBoard.Panels.*;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  public static WildBoard dashboard;

  final VelocitySimpleSubsystem WBshooter;
  final VelocitySimpleSubsystem WBintake;
  final VelocitySimpleSubsystem WBfunnel;
  final VelocitySimpleSubsystem WBindexer;
  final SimpleSubsystem WBturret;
  final SimpleSubsystem WBhood;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    dashboard = new WildBoard(5804);

    //Checklist
    dashboard.addTab(new Tab()
      .addChild(new Checklist())
      .setTitle("Checklist")
    );

    //Setup
    dashboard.addTab(new Tab()
      .setTitle("Setup/Auto")
    );

    //TeleOp
    dashboard.addTab(new Tab()
      .setTitle("TeleOp")
    );

    //Subsystems
    WBshooter = new VelocitySimpleSubsystem("Shooter");
    WBintake = new VelocitySimpleSubsystem("Intake");
    WBindexer = new VelocitySimpleSubsystem("Indexer");
    WBfunnel = new VelocitySimpleSubsystem("Funnel");

    WBturret = new SimpleSubsystem("Turret", false);
    WBhood = new SimpleSubsystem("Turret Hood", true);
    dashboard.addTab(new Tab()
      .addChild(
        new Col(5).addChild(
          new Placeholder("SWERVES", 30)
        ).addChild(
          new Placeholder("teST", 10)
        )
      )
      .addChild(
        new Col(3).addChild(
          new Placeholder("Climb", 20)
        ).addChild(
          new Placeholder("BLAH", 10)
        )
      )
      .addChild(
        new Col(4).addChild(
          new Row().addChild(
            WBshooter
          ).addChild(
            WBintake
          )
        ).addChild(
          new Row().addChild(
            WBindexer
          ).addChild(
            WBfunnel
          )
        ).addChild(
          new Row().addChild(
            WBturret
          ).addChild(
            WBhood
          )
        )
      )
      .setTitle("Subsystems")
    );

    //Testing
    dashboard.addTab(new Tab()
      .addChild(
        new Col(3).addChild(
          new LooptimeMonitor()
        )
      ).addChild(
        new Col(6).addChild(
          new LooptimeMonitor()
        )
      ).addChild(
        new Col(3).addChild(
          new LooptimeMonitor()
        )
      )
      .setTitle("TESt")
    );

    dashboard.addPanel(new LooptimeMonitor());
    dashboard.start();
  }
 
  @Override
  public void robotPeriodic() {
    WBshooter.updateVals(10+Math.round(Math.random()*5), 42);
    WBintake.updateVals(8+Math.round(Math.random()*0.55), 37);
    WBfunnel.updateVals(4+Math.round(Math.random()*0.6), 36);
    WBindexer.updateVals(0, 31);

    WBturret.updateVals(289+Math.round(Math.random()*0.6)*10, 33);
    WBhood.updateVals(21+Math.round(Math.random()*0.6)*2, 31);

    dashboard.update();
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

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
