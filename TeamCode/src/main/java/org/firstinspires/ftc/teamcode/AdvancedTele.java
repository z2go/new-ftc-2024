
package org.firstinspires.ftc.teamcode;



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

    private DcMotor rightSlide;
    private DcMotor leftSlide;

    private DcMotor slideUp;

    private Servo intake;


    //private DcMotor rightSlide1;
    //private DcMotor rightSlide2;
    //private DcMotor leftSlide1;
    //private DcMotor leftSlide2;
    // private DcMotor outtake;
    //private Servo launcher;
//    private Servo claw;
    //private Servo pixelDrop;
    private boolean halfPower = false;


    //use this for max horizontal extension, should increment by one every revolution of the motor
    // if it is greater than a certain value, the motor

    private boolean canExtendSlides = false;

    private boolean servoIsActivated = false;
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

        rightSlide = hardwareMap.get(DcMotor.class, "slideRight");
        leftSlide = hardwareMap.get(DcMotor.class, "slideLeft");

        slideUp = hardwareMap.get(DcMotor.class, "slideUp");

        intake = hardwareMap.get(Servo.class, "intake");

        // TODO Add a servo called "intake" in the config and rename motors

        //rightSlide1 = hardwareMap.get(DcMotor.class,"rightSlide1");
        //rightSlide2 = hardwareMap.get(DcMotor.class,"rightSlide2");
        //leftSlide1 = hardwareMap.get(DcMotor.class,"leftSlide1");
        //leftSlide2 = hardwareMap.get(DcMotor.class,"leftSlide2");


        // outtake = hardwareMap.get(DcMotor.class,"outtake");
        //launcher = hardwareMap.get(Servo.class, "launcher");

        //claw = hardwareMap.get(Servo.class, "claw");

        // Hint: Use hardwareMap.get() method
        // Example: frontLeft = hardwareMap.get(DcMotor.class, "front_left_motor");




        // TODO: Set motor directions and modes here.
        // Hint: You'll have to reverse some motors to drive straight -- you can figure out which ones through trial and error!
        // Hint: Use motor.setDirection() and motor.setMode() methods
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);

        leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        //rightSlide1.setDirection(DcMotor.Direction.FORWARD);
        //rightSlide2.setDirection(DcMotor.Direction.FORWARD);
        //leftSlide1.setDirection(DcMotor.Direction.FORWARD);
        //leftSlide2.setDirection(DcMotor.Direction.FORWARD);
        // outtake.setDirection(DcMotor.Direction.FORWARD);




        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //rightSlide1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightSlide2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftSlide1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftSlide2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

        servoIsActivated = gamepad1.a;
        //claw.setPosition(servoIsActivated?1:0);

            if (gamepad1.b) {
                //launcher.setPosition(-1);
            }

            if (gamepad1.a) {
                //launcher.setPosition(0);
            }
                // if (gamepad1.left_bumper) {
                //      outtake.setPower(1);
                //  }
                // if (gamepad1.right_bumper) {
                //     outtake.setPower(-1);
                // }

                if (halfPower) {
                    power *= 0.5;
                    strafe *= 0.5;
                    turn *= 0.5;
                }

                double flPower = power+strafe-turn;
                double blPower = power-strafe-turn;
                double frPower = power-strafe+turn;
                double brPower = power+strafe+turn;

                frontLeft.setPower(flPower);
                backLeft.setPower(blPower);
                backRight.setPower(brPower);
                frontRight.setPower(frPower);

                leftSlide.setPower(canExtendSlides ? leftTrigger-rightTrigger : 0);
                rightSlide.setPower(canExtendSlides ? leftTrigger-rightTrigger : 0);

                if (gamepad1.right_bumper){
                    intake.setPosition(1);
                }
                else if (gamepad1.left_bumper){
                    intake.setPosition(0);
                }
                else {
                    intake.setPosition(0.5);
                }

                if(gamepad1.dpad_up){
                    slideUp.setPower(0.6);
                    canExtendSlides = false;
                }
                else if(gamepad1.dpad_down){
                    slideUp.setPower(-0.6);
                    canExtendSlides = true;
                }
                else{
                    slideUp.setPower(0);
                }
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
