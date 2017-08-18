package csci4401.service;

import java.util.Scanner;
/**
 * Example test code; lacks timing. Use as needed.
 */
public class TestService {

    private static int calcType;

    public static void main(String[] argv) throws Exception {

        long before;
        long after;

        //Scanner input = new Scanner(System.in);
        // for testing
        // System.out.println("Welcome to testService!");
        // System.out.println("| To run multiply test, enter \"0\" |");
        // System.out.println("| To run determinant test, enter \"1\" |");
        // System.out.println("\nYour choice? : ");
        
        // calcType = input.nextInt();
        calcType = 0;
         
        // for testing    
        if(calcType == 0) {
            System.out.println("Begin variable matrix size multiplication tests ...");
                for(int i = 100; i <= 1000; i*=2)  {
                    int iterations = 1;
                    int size = i;
                    System.out.println("Start sequential test " + i + " ...");
                    before = System.currentTimeMillis();
                    //sequentialTest(size, iterations);
                    after = System.currentTimeMillis();
                    System.out.println("Total sequential test time = " + (after-before));
                    System.out.println("Start parallel test " + i + "...");
                    before = System.currentTimeMillis();
                    parallelTest(size, iterations);
                    after = System.currentTimeMillis();
                    System.out.println("Total parallel test time = " + (after-before));
                }
            System.out.println("Begin variable iterations multiplication tests ...");
                for(int i = 1; i <= 10; i+=2)  {
                    int size = 100;
                    int iterations = i;
                    System.out.println("Start sequential test " + i + " ...");
                     before = System.currentTimeMillis();
                    sequentialTest(size, iterations);
                    after = System.currentTimeMillis();
                    System.out.println("Total sequential test time = " + (after-before));
                    System.out.println("Start parallel test " + i + "...");
                     before = System.currentTimeMillis();
                    parallelTest(size, iterations);
                    after = System.currentTimeMillis();
                    System.out.println("Total parallel test time = " + (after-before));
                }
            // for testing
           } else if(calcType == 1){
            // calcType = 1;

            //     System.out.println("Begin variable matrix size determinant tests ...");
            //     for(int i = 9; i < 12; i+=1)  {
            //         int iterations = 1;
            //         int size = i;
            //         System.out.println("Start sequential test " + i + " ...");
            //          before = System.currentTimeMillis();
            //         sequentialTest(size, iterations);
            //         after = System.currentTimeMillis();
            //         System.out.println("Total sequential test time = " + (after-before));
            //         System.out.println("Start parallel test " + i + "...");
            //         parallelTest(size, iterations);
            //         after = System.currentTimeMillis();
            //         System.out.println("Total parallel test time = " + (after-before));
            //     }
                //System.out.println("Begin variable multiplication iterations determinant tests ...");
                //for(int i = 1; i <= 10; i++)  {
                  //  int size = 10;
                   // int iterations = i;
                   // System.out.println("Start sequential test " + i + " ...");
                    // before = System.currentTimeMillis();
                    //sequentialTest(size, iterations);
                    //after = System.currentTimeMillis();
                    //System.out.println("Total sequential test time = " + (after-before));
                    //System.out.println("Start parallel test " + i + "...");
                    //parallelTest(size, iterations);
                    //after = System.currentTimeMillis();
                    //System.out.println("Total parallel test time = " + (after-before));
                //} 

            } else
               System.err.println("INVALID INPUT!");
    }//end main
    /**
     * Launches the workers one after the other.
     */
    private static void sequentialTest(int size, int iterations) throws Exception {
        //MatrixMultiplyServicePool servicePool = new MatrixMultiplyServicePool(5, 10, calcType);
        BalancedMMServicePool servicePool = new BalancedMMServicePool(5, 10);

        int max = 4;
        for(int i=0; i<max; i++) {
            servicePool.addRequest(new MatrixMultiplyParameters(size, iterations));
            System.out.println("Completion time: " + servicePool.getResponse());
        }
    }

    /**
     * Launches the workers in parallel.
     */
    private static void parallelTest(int size, int iterations) throws Exception {
        //MatrixMultiplyServicePool servicePool = new MatrixMultiplyServicePool(5, 10, calcType);
        BalancedMMServicePool servicePool = new BalancedMMServicePool(5, 10);
        int max =6;
        for(int i=0; i<max; i++) {
            servicePool.addRequest(new MatrixMultiplyParameters(size, iterations));
        }

        //System.out.println("beginning getting respones");
        for(int i=0; i<max; i++) {
            //System.out.println("response " + i);
            System.out.println("Completion time: " + servicePool.getResponse());
        }
    }
}//end class
