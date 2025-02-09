package org.firstinspires.ftc.teamcode;




import androidx.annotation.NonNull;


// RR-specific imports
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;


// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.Telemetry;


@Autonomous(name="AutonRoadRunner", group="Autonomous")
public class AutonRoadRunner extends LinearOpMode {
    public static class Hooks{
        static Servo leftHook;
        static Servo rightHook;
        public Hooks(HardwareMap hardwareMap){
            leftHook = hardwareMap.get(Servo.class,"hangLeft");
            rightHook = hardwareMap.get(Servo.class, "hangRight");
        }

        public static class HooksUp implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                leftHook.setPosition(0.8);
                rightHook.setPosition(0.2);
                return false;
            }
        }
        public Action hooksUp(){
            return new HooksUp();
        }

    }
    public static class Lift {




        public Lift(HardwareMap hardwareMap) {




            DcMotorEx leftLift;
            DcMotorEx rightLift;


            leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
            rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");


            leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            leftLift.setDirection(DcMotorSimple.Direction.REVERSE);
            rightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        }


        public static class LiftUp implements Action {
            private boolean initialized = false;


            public static DcMotorEx leftLift;
            public static DcMotorEx rightLift;

            static Telemetry telemetry;


            LiftUp(@NonNull HardwareMap hardwareMap, Telemetry tel){
                leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
                leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


                rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
                telemetry = tel;


            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    leftLift.setPower(0.8);
                    rightLift.setPower(0.8);
                    initialized = true;
                }


                double pos = (double) leftLift.getCurrentPosition() /2 + (double) rightLift.getCurrentPosition() /2;
                telemetry.addData("liftPos", pos);
                if (pos < 2400.0) {
                    return true;
                } else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                    return false;
                }
            }




        }


        public static Action liftUp(HardwareMap map, Telemetry telemetry) {
            return new LiftUp(map,telemetry);
        }
        public static class miniRaise implements Action {
            private boolean initialized = false;


            public static DcMotorEx leftLift;
            public static DcMotorEx rightLift;




            static Telemetry telemetry;








            miniRaise(@NonNull HardwareMap hardwareMap, Telemetry tel){
                leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
                leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


                rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
                telemetry = tel;


            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    leftLift.setPower(0.8);
                    rightLift.setPower(0.8);
                    initialized = true;
                }


                double pos = (double) leftLift.getCurrentPosition() /2 + (double) rightLift.getCurrentPosition() /2;
                telemetry.addData("liftPos", pos);
                if (pos < 300.0) {
                    return true;
                } else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                    return false;
                }
            }




        }


        public static Action miniRaise(HardwareMap map, Telemetry telemetry) {
            return new miniRaise(map,telemetry);
        }


        public static class LiftDown2 implements Action {
            private boolean initialized = false;


            public static DcMotorEx leftLift;
            public static DcMotorEx rightLift;


            Telemetry telemetry;


            LiftDown2(HardwareMap hardwareMap, Telemetry tel){
                leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
                leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


                rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
                telemetry = tel;
            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    leftLift.setPower(-0.8);
                    rightLift.setPower(-0.8);
                    initialized = true;
                }


                double pos = (double) leftLift.getCurrentPosition() /2+ (double) rightLift.getCurrentPosition() /2;
                telemetry.addData("Position ",pos);
                if (pos > 0) {
                    return true;
                } else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                    return false;
                }
            }
        }
        public static Action liftDown2(HardwareMap map, Telemetry telemetry){
            return new LiftDown2(map,telemetry);
        }
        public static class LiftDown1 implements Action {
            private boolean initialized = false;


            public static DcMotorEx leftLift;
            public static DcMotorEx rightLift;


            Telemetry telemetry;


            LiftDown1(HardwareMap hardwareMap, Telemetry tel){
                leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
                leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


                rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
                telemetry = tel;
            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    leftLift.setPower(-0.8);
                    rightLift.setPower(-0.8);
                    initialized = true;
                }


                double pos = (double) leftLift.getCurrentPosition() /2+ (double) rightLift.getCurrentPosition() /2;
                telemetry.addData("Position ",pos);
                if (pos > 1200.0) {
                    return true;
                } else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                    return false;
                }
            }
        }
        public static Action liftDown1(HardwareMap map, Telemetry telemetry){
            return new LiftDown1(map,telemetry);
        }
        public static class miniLower implements Action {
            private boolean initialized = false;


            public static DcMotorEx leftLift;
            public static DcMotorEx rightLift;


            Telemetry telemetry;


            miniLower(HardwareMap hardwareMap, Telemetry tel){
                leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
                leftLift.setDirection(DcMotorSimple.Direction.REVERSE);


                rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
                telemetry = tel;
            }


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    leftLift.setPower(-0.8);
                    rightLift.setPower(-0.8);
                    initialized = true;
                }


                double pos = (double) leftLift.getCurrentPosition() /2+ (double) rightLift.getCurrentPosition() /2;
                telemetry.addData("Position ",pos);
                if (pos > 0) {
                    return true;
                } else {
                    leftLift.setPower(0);
                    rightLift.setPower(0);
                    return false;
                }
            }
        }
        public static Action miniLower(HardwareMap map, Telemetry telemetry){
            return new miniLower(map,telemetry);
        }
    }
    public static class Claw {
        private final Servo claw;

        public Claw(@NonNull HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "specServo");


        }


        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.4);


                return false;
            }
        }


        public Action closeClaw() {
            return new CloseClaw();
        }


        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);


                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }




    }




    @Override
    public void runOpMode() {
        telemetry.addData("hello",1);
        // instantiate your MecanumDrive at a particular pose.
        //ROBOT STARTS BACKWARDS
        //note: positive is forward left, y is side to side and x is front and back


        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        //diagonal going to specimen bar
        TrajectoryActionBuilder driveTowardsChamber = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-26, 0))
                .waitSeconds(0.1);


        TrajectoryActionBuilder finalAdjust = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-30,0))
                .waitSeconds(0.2);


        TrajectoryActionBuilder backUp = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-10,0))
                .waitSeconds(0.2);


        TrajectoryActionBuilder moveToOZ = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-10,0))
                .strafeTo(new Vector2d(-10,50))
                .turn(Math.toRadians(180))
                .waitSeconds(0.2)
                .strafeTo(new Vector2d(0,50));


        TrajectoryActionBuilder backFromOZ = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-10,50));


        TrajectoryActionBuilder goToChambers2 = drive.actionBuilder(initialPose)

                .strafeTo(new Vector2d(-15,5))
                .turn(Math.toRadians(180))
                .strafeTo(new Vector2d(-26,5));

        TrajectoryActionBuilder finalAdjust2 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-30,5))
                .waitSeconds(0.2);

        TrajectoryActionBuilder backUp2 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-10,5))
                .waitSeconds(0.2);

        TrajectoryActionBuilder parkInOZ = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-5,55));


        telemetry.addData("Starting", "Setup Complete");
        telemetry.update();
        waitForStart();


        if(isStopRequested()) return;




        //forward works
        Claw claw = new Claw(hardwareMap);
        Hooks hooks = new Hooks(hardwareMap);


        Action approachChamber = driveTowardsChamber.build();
        Action liftUp = Lift.liftUp(hardwareMap,telemetry);
        Action liftDown1 = Lift.liftDown1(hardwareMap,telemetry);
        Action liftDown2 = Lift.liftDown2(hardwareMap,telemetry);
        Action stepForward = finalAdjust.build();
        Action clawOpen = claw.openClaw();
        Action clawClose = claw.closeClaw();
        Action moveToObservationZone = moveToOZ.build();
        Action back = backUp.build();
        Action smallRaise = Lift.miniRaise(hardwareMap,telemetry);
        Action smallLower = Lift.miniLower(hardwareMap,telemetry);
        Action backUpObservationZone = backFromOZ.build();
        Action goBackToChambers2 = goToChambers2.build();
        Action raiseHooks = hooks.hooksUp();
        Action stepForward2 = finalAdjust2.build();
        Action back2 = backUp2.build();
        Action park = parkInOZ.build();


        Actions.runBlocking(
                new SequentialAction(
                        raiseHooks,
                        clawClose,
                        approachChamber,
                        liftUp,
                        stepForward,
                        liftDown1,
                        clawOpen,
                        back,
                        liftDown2,
                        moveToObservationZone,
                        clawClose,
                        smallRaise,
                        backUpObservationZone,
                        smallLower,
                        goBackToChambers2,
                        liftUp,
                        stepForward2,
                        liftDown1,
                        clawOpen,
                        back2,
                        liftDown2,
                        park


                )
        );


        //😖


    }




}


