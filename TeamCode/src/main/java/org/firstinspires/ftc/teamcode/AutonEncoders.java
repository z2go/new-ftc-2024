package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "auton w encoders", group = "Auto")
public class AutonEncoders extends LinearOpMode {

    //TeamElementSubsystem teamElementDetection = null;
    boolean togglePreview = true;

    // Drive constants for encoder setup:
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    // Motors:
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;


    private ElapsedTime runtime = new ElapsedTime();

    public void HardwareStart() {
        leftFront  = hardwareMap.get(DcMotor.class, "fl");
        rightFront = hardwareMap.get(DcMotor.class, "fr");
        leftBack = hardwareMap.get(DcMotor.class, "bf");
        rightBack = hardwareMap.get(DcMotor.class, "br");

        telemetry.addData("Object Creation", "Start");
        telemetry.update();

        //teamElementDetection = new TeamElementSubsystem(hardwareMap);

        telemetry.addData("Object Creation", "Done");
        telemetry.update();

    }

    public void runOpMode() {

        //this might need to be redone between different times when you use the encoder drive method:
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //put the specific encoder drive stuff w the measurments that we want here:

    }


    public void encoderDrive(double timeout, double dist, double power, int target, String direc) {
        double ROTATIONS = dist / (Math.PI * WHEEL_DIAMETER_INCHES);
        /*if(direc.equals("forward") || direc.equals("backward")) {
            target = leftFront.getCurrentPosition() + (int) (dist * COUNTS_PER_INCH);
        }*/

        double counts =  COUNTS_PER_MOTOR_REV * ROTATIONS * DRIVE_GEAR_REDUCTION;

        target = leftFront.getCurrentPosition() + (int) counts;

        switch (direc) {
            case "Forward": // robot will move forward
                leftFront.setTargetPosition((int) target);
                rightFront.setTargetPosition((int) target);
                leftBack.setTargetPosition((int) target);
                rightBack.setTargetPosition((int) target);
                break;
            case "Backward": // robot will move backward
                leftFront.setTargetPosition((int) -target);
                rightFront.setTargetPosition((int) -target);
                leftBack.setTargetPosition((int) -target);
                rightBack.setTargetPosition((int) -target);
                break;
            case "Left": // robot will strafe left
                leftFront.setTargetPosition((int) -target);
                rightFront.setTargetPosition((int) target);
                leftBack.setTargetPosition((int) target);
                rightBack.setTargetPosition((int) -target);
                break;
            case "Right": // robot will strafe right
                leftFront.setTargetPosition((int) target);
                rightFront.setTargetPosition((int) -target);
                leftBack.setTargetPosition((int) -target);
                rightBack.setTargetPosition((int) target);
                break;
            case "RLeft": // robot will rotate left
                leftFront.setTargetPosition((int) -counts);
                rightFront.setTargetPosition((int) counts);
                leftBack.setTargetPosition((int) -counts);
                rightBack.setTargetPosition((int) counts);
                break;
            case "RRight": // robot will rotate right
                leftFront.setTargetPosition((int) counts);
                rightFront.setTargetPosition((int) -counts);
                leftBack.setTargetPosition((int) counts);
                rightBack.setTargetPosition((int) -counts);
                break;
        }

        /*leftFront.setTargetPosition(target);
        leftBack.setTargetPosition(target);
        rightFront.setTargetPosition(target);
        rightBack.setTargetPosition(target);*/

        runtime.reset();

        if(direc.equals("Forward") || direc.equals("Backward")) {
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftFront.setPower(power);
            leftBack.setPower(power);
            rightFront.setPower(power);
            rightBack.setPower(power);

            while (leftFront.isBusy()
                    && rightFront.isBusy()
                    && leftBack.isBusy()
                    && rightBack.isBusy()
                    && !(runtime.seconds() > timeout) ) {
            }
        }


        stop(leftFront, rightFront, leftBack, rightBack);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void stop(DcMotor motorlf, DcMotor motorrf, DcMotor motorlb, DcMotor motorrb) {
        //robot stops moving
        motorlf.setPower(0.0);
        motorrf.setPower(0.0);
        motorlb.setPower(0.0);
        motorrb.setPower(0.0);
    }


}