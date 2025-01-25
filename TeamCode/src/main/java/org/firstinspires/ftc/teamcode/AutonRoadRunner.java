package org.firstinspires.ftc.teamcode;


import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="AutonRoadRunner", group="Autonomous")
public class AutonRoadRunner extends LinearOpMode {

    public class Lift {
        private DcMotorEx lift;

        public Lift(HardwareMap hardwareMap) {
            lift = hardwareMap.get(DcMotorEx.class, "leftLift");
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lift.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        public class LiftUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(0.5);
                    initialized = true;
                }

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 1000.0) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }


            public Action liftUp() {
                return new LiftUp();
            }

        }
        public class LiftDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(-0.8);
                    initialized = true;
                }

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 100.0) {
                    return true;
                } else {
                    lift.setPower(0);
                    return false;
                }
            }
        }
        public Action liftDown(){
            return new LiftDown();
        }
    }

    public class Claw {
        private Servo claw;

        public Claw(HardwareMap hardwareMap) {
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

        // instantiate your MecanumDrive at a particular pose.
        //ROBOT STARTS BACKWARDS
        //note: positive is forward left, y is side to side and x is front and back

        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        //diagonal going to specimen bar
        TrajectoryActionBuilder goingTo = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-25, -36))
                .turn(Math.PI)
                .waitSeconds(2);

        TrajectoryActionBuilder goingForward = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(25, 0))
                .waitSeconds(2);

        // diagonal going back to loading area
        TrajectoryActionBuilder goingBack = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d (0,0))
                .turn(Math.PI)
                .waitSeconds(2);

        //parking:
        Action parking = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(0, -20))
                .build();

        Action Action1 = goingForward.build();
        Action Action2 = goingTo.build();
        Action Action3 = goingBack.build();


        Actions.runBlocking(
                new SequentialAction(
                        //put the lift and claw stuff in between action 1 and 2
                        Action1,
                        Action3,
                        Action2,
                        parking
                )
        );

        }


    }