package io.ktiejencien.frc_test1.pid;

public class Test {
    public static void main(String[] args) {

        PID pid = new PID(1,0.02);
        double error = 40;

        while(true){
            pid.onTick(error);
            double deltaError = pid.output;
            error -= deltaError;
            System.out.println(error);

            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
