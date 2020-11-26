package io.ktiejencien.frc_test1.pid;

import java.util.ArrayList;

/**
 * The central math controlling system for PID
 * * Proportional , Integral , Derivative
 * @author KiteJencien
 */
public class PID {

    public final double OUTPUT_CONSTANT;
    public final double K_PROPORTIONAL = 0.05;
    public final double K_DERIVATIVE = 0.006;
    public final double K_INTEGRAL = 0.001;

    public final double TICK_GAP;

    public double tickCount;

    public double errorIntegral;
    public double errorDerivative;
    public double errorCurrent;

    public double output;

    ArrayList<Tick> ticks = new ArrayList<>();

    class Tick{
        Tick(double error, double tickCount){
            this.error = error;
            this.tickCount = tickCount;
        }

        public double error;
        public double tickCount;

        public double getError() {
            return error;
        }
    }

    public PID(double OUTPUT_CONSTANT, double TICK_GAP){
        this.OUTPUT_CONSTANT = OUTPUT_CONSTANT;
        this.TICK_GAP = TICK_GAP;
    }

    public void onTick(double errorCurrent){

        this.errorCurrent = errorCurrent;

        if(tickCount == 0){
            this.output = K_PROPORTIONAL * errorCurrent;
            tickCount ++;
            Tick currentTick = new Tick(errorCurrent,tickCount);
            this.ticks.add(currentTick);
            return;
        }

        //I controller
        this.errorIntegral += K_INTEGRAL * errorCurrent * TICK_GAP;

        //D controller
        this.errorDerivative = (errorCurrent - this.ticks.get(ticks.size()-1).error)/TICK_GAP * K_DERIVATIVE;

        Tick currentTick = new Tick(errorCurrent, tickCount);
        ticks.add(currentTick);

        this.output = K_PROPORTIONAL * errorCurrent + errorIntegral + errorDerivative;
    }

}
