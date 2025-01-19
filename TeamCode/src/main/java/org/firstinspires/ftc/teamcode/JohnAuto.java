package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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

    Pose2d beginPose;

    MecanumDrive drive;

    Servo hangRight;
    Servo hangLeft;

    @Override
    public void runOpMode() {
        Pose2d beginPose = new Pose2d(0,0,0);
        MecanumDrive drive = new MecanumDrive(hardwareMap,beginPose);

        clawOpen = false;


        rightSlide = hardwareMap.get(DcMotor.class, "rightLift");
        leftSlide = hardwareMap.get(DcMotor.class, "leftLift");

        slideUp = hardwareMap.get(DcMotor.class, "slideAngleAxial");

        specimenServo = hardwareMap.get(Servo.class, "specServo");
        specimenServo.setPosition(clawOpen?0.4:0);


        intake = hardwareMap.get(Servo.class, "sweeper");

        rotateClaw = hardwareMap.get(Servo.class, "rotateClaw");
        claw = hardwareMap.get(Servo.class,"claw");
        extendClaw = hardwareMap.get(DcMotor.class, "intakeExtendLateral");

        hangRight = hardwareMap.get(Servo.class, "hangRight");

        hangLeft = hardwareMap.get(Servo.class, "hangLeft");


        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();


        //TODO PUT YOUR AUTO CODE BELOW THIS LINE

        //1. Raise slides to place spec
        hangLeft.setPosition(0);
        hangRight.setPosition(1);

        raiseSlides(6000);

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(20, 5), 0)
                        .build());

        //raiseSlides(7000);

        //toggleClaw();








        //2. Go up to bars to place spec




        //3. Place spec



        //4. Fully retract slides



        //raiseSlides(-currentSLidesPos);

        //5a. Park and end
        /*
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(-5, 0), 0)
                        .splineTo(new Vector2d(5, -50), 0)
                        .build()
        );
        */


        //5b. Drive around to behind sample

        //

        //6. Push sample into observation zone

        //

        //7. Back out of Zone and wait for 3-5 secs

        //
        //sleep(4000);

        //8. Back into Zone and hit wall

        //9. Place Spec on wall

        //10. Park(END)



        /*
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .splineTo(new Vector2d(10, 10), Math.PI / 2)
                        .build()
        );

         */

    }
    public void move(int x, int y, int rotation, Pose2d pose){

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
