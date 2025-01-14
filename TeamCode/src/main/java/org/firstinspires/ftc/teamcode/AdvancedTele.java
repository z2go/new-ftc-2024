
package org.firstinspires.ftc.teamcode;



import android.print.PrintAttributes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




@TeleOp(name="AdvancedTele", group="Iterative Opmode")
public class AdvancedTele extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotor slideAngle;

    private DcMotor rightSlide;
    private DcMotor leftSlide;

    private DcMotor slideUp;

    private DcMotor extendClaw;

    private Servo specimenServo;

    private Servo hangRight;

    private Servo hangLeft;

    private Servo intake;

    private Servo rotateClaw;

    private Servo claw;

    //private DcMotor rightSlide1;
    //private DcMotor rightSlide2;
    //private DcMotor leftSlide1;
    //private DcMotor leftSlide2;
    // private DcMotor outtake;
    //private Servo launcher;
//    private Servo claw;
    //private Servo pixelDrop;
    private boolean halfPower = false;

    //for curr intake:
    private boolean sweepOn = false;

    private double clawRotation = 0.5;

    //for prev intake:
    private boolean lastRightBumper = false;
    private boolean clawClosed = false;
    //use this for max horizontal extension, should increment by one every revolution of the motor
    // if it is greater than a certain value, the motor

    private boolean canExtendSlides = false;

    private boolean servoIsActivated = false;

    private boolean lastA;
    private boolean lastB;
    private boolean lastX;
    private boolean lastY;
    private boolean lastRBumper;
    private boolean lastLBumper;

    private boolean lastDPadUp;
    private boolean lastDPadDown;
    private boolean lastDPadLeft;
    private boolean lastDPadRight;



    // TODO: Set any constant values here, if necessary
    // Example: private final double MAX_POWER = 1.0;
    private final double MAX_POWER = 1.0;
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");




        // TODO: Initialize motors and servos here
        frontLeft = hardwareMap.get(DcMotor.class,"fl");
        frontRight = hardwareMap.get(DcMotor.class,"fr");
        backLeft = hardwareMap.get(DcMotor.class,"bl");
        backRight = hardwareMap.get(DcMotor.class,"br");


        rightSlide = hardwareMap.get(DcMotor.class, "rightLift");
        leftSlide = hardwareMap.get(DcMotor.class, "leftLift");

        slideUp = hardwareMap.get(DcMotor.class, "slideAngle");

        specimenServo = hardwareMap.get(Servo.class, "specServo");

        hangRight = hardwareMap.get(Servo.class, "hangRight");

        hangLeft = hardwareMap.get(Servo.class, "hangLeft");


        intake = hardwareMap.get(Servo.class, "sweeper");

        rotateClaw = hardwareMap.get(Servo.class, "rotateClaw");
        claw = hardwareMap.get(Servo.class,"claw");
        extendClaw = hardwareMap.get(DcMotor.class, "intakeExtend");



        // TODO: Set motor directions and modes here.

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);

        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);

        extendClaw.setDirection(DcMotorSimple.Direction.FORWARD);

        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendClaw.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);




        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }




    @Override
    public void init_loop() {
        // This is optional: Here we add any code that needs to run repeatedly during initialization

    }

    @Override
    public void start() {
        runtime.reset();
        // This is optional: Here we add code to be run once when the PLAY button is hit
    }
    @Override
    public void loop() {


        double leftTrigger = gamepad1.left_trigger;
        double rightTrigger = gamepad1.right_trigger;

        double power = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        servoIsActivated = (gamepad1.a && !lastA) != servoIsActivated;
        specimenServo.setPosition(servoIsActivated?0.4:0);

        if (halfPower) {
            power *= 0.5;
            strafe *= 0.5;
            turn *= 0.5;
        }

        //drive stuff:

        double flPower = power - strafe - turn;
        double blPower = power + strafe - turn;
        double frPower = power + strafe + turn;
        double brPower = power - strafe + turn;

        frontLeft.setPower(flPower);
        backLeft.setPower(blPower);
        backRight.setPower(brPower);
        frontRight.setPower(frPower);

        leftSlide.setPower(leftTrigger - rightTrigger);
        rightSlide.setPower(leftTrigger - rightTrigger);

        if (gamepad2.left_bumper && !lastLBumper) {
            sweepOn = !sweepOn;
        }

        intake.setPosition(sweepOn ? 1 : 0);

        if (gamepad2.right_bumper && !lastRBumper){
            clawClosed = !clawClosed;
        }
        claw.setPosition(clawClosed ? 1 : 0.2);

        //changing the angle of the slides:
        slideUp.setPower(gamepad2.left_stick_y);

        extendClaw.setPower(-gamepad2.right_stick_y);

        if (gamepad2.dpad_right){
            clawRotation-=0.002;
        }
        else if(gamepad2.dpad_left){
            clawRotation += 0.002;
        }
        clawRotation = Math.max(Math.min(1,clawRotation),-1);
        rotateClaw.setPosition(clawRotation);

        //hanging hooks:

        if (gamepad1.x && !lastX) {
            hangLeft.setPosition(0);
            hangRight.setPosition(1);
        }

        //unhang:

        if (gamepad1.y && !lastY) {
            hangLeft.setPosition(0.8);
            hangRight.setPosition(0.2);
        }

        lastA = gamepad1.a;
        lastB = gamepad1.b;
        lastX = gamepad1.x;
        lastY = gamepad1.y;

        lastRBumper = gamepad2.right_bumper;
        lastLBumper = gamepad2.left_bumper;

        lastDPadDown = gamepad1.dpad_down;
        lastDPadUp = gamepad1.dpad_up;
        lastDPadLeft = gamepad1.dpad_left;
        lastDPadRight = gamepad1.dpad_right;
    }




            @Override
            public void stop() {
                // TODO: Stop all motors and any additional components
                backLeft.setPower(0);
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);

                //leftSlide1.setPower(0);
                //leftSlide2.setPower(0);
                //rightSlide1.setPower(0);
                //rightSlide2.setPower(0);
                // outtake.setPower(0);
                // Hint: Set all motor powers to 0
            }




        }
