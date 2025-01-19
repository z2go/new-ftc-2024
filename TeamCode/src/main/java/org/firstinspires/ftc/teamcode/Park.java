package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Park", group="Autonomous")
public class Park extends LinearOpMode {

    Pose2d beginPose;
    MecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        beginPose = new Pose2d(0,0,0);
        drive = new MecanumDrive(hardwareMap, beginPose);



        waitForStart();
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(0, -20), 0)
                        .build());
    }
}
