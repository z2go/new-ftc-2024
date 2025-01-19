package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="JohnAuto", group="Autonomous")
public class JohnAuto extends LinearOpMode {

    private DcMotor slideAngle;

    private DcMotor rightSlide;
    private DcMotor leftSlide;

    private DcMotor slideUp;

    private DcMotor extendClaw;

    private Servo specimenServo;

    private Servo intake;

    private Servo rotateClaw;

    private Servo claw;

    private int currentSlidesPos = 0;

    private boolean clawOpen;

    @Override
    public void runOpMode() {

        clawOpen = true;


        rightSlide = hardwareMap.get(DcMotor.class, "rightLift");
        leftSlide = hardwareMap.get(DcMotor.class, "leftLift");

        slideUp = hardwareMap.get(DcMotor.class, "slideAngleAxial");

        specimenServo = hardwareMap.get(Servo.class, "specServo");
        specimenServo.setPosition(clawOpen?0.4:0);


        intake = hardwareMap.get(Servo.class, "sweeper");

        rotateClaw = hardwareMap.get(Servo.class, "rotateClaw");
        claw = hardwareMap.get(Servo.class,"claw");
        extendClaw = hardwareMap.get(DcMotor.class, "intakeExtendLateral");


        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        //TODO PUT YOUR AUTO CODE BELOW THIS LINE

       

        // Roadrunner Pathing Planning
            /*
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(10, 10), Math.PI / 2) // Adjust path as needed
                            .build()
            );

             */

    }


    public void raiseSlides(int amount){
        int amt = currentSlidesPos - amount;

        currentSlidesPos += amount;

        leftSlide.setTargetPosition(amt);
        rightSlide.setTargetPosition(amt);

        if (opModeIsActive()) {
            leftSlide.setPower(0.4);
            rightSlide.setPower(0.4);

            leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (rightSlide.isBusy() && leftSlide.isBusy() && opModeIsActive()) {
                telemetry.addData("Lift Position", leftSlide.getCurrentPosition());
                telemetry.update();
            }

            leftSlide.setPower(0);
            rightSlide.setPower(0);

        }
    }

    public void toggleClaw(){//This Method needs some time after to run, shouldn't be an issue generally
        clawOpen = !clawOpen;
        specimenServo.setPosition(clawOpen?0.4:0);

    }
}
