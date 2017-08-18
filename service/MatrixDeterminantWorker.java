package csci4401.service;

/**
 * Matrix Determinant Worker Class
 * 
 */
public class MatrixDeterminantWorker extends AbstractServiceWorker {

    private double[][] a;
    private int mSize;
    private int loops;
    private MsgQ resultQ;

    public MatrixDeterminantWorker(MatrixMultiplyParameters parameters, MsgQ resultQ) {
        super(parameters, resultQ);
        this.mSize = parameters.matrixSize;
        this.loops = parameters.iterations;
        this.resultQ = resultQ;
    }

    /**
     * Initializes the matrixes based on the size parameter.
     * <b>TODO:</b> Implement this method.
     */
    private void initMatrixes() {
        this.a = new double[mSize][mSize];

        for(int i=0; i<mSize; i++) {
            for(int j=0; j<mSize; j++) {
                this.a[i][j] = (i+1);

            }
        }
    } //end initMatrixes()

    /**
     *
     *      */
private double doMatrixDetermination(double[][] matrix) {

        double result=0;
        if(matrix.length==1){
            result=matrix[0][0];
        }
        else if(matrix.length==2){
            result= matrix[0][0]*matrix[1][1] - matrix[1][0]*matrix[0][1];                  
        }
        else{
            for(int i=0; i<matrix.length; i++){
                double[][] subset= new double[matrix.length-1][matrix[0].length-1];
                int newJ=0;
                for(int j=0; j<matrix.length;j++){
                    if(j!=i){
                        for(int k=1; k<matrix[0].length;k++){
                            subset[newJ][k-1]=matrix[j][k];
                        }               
                    }else{
                        newJ--;
                    }
                    newJ++;
                }
                double corner=matrix[i][0];
                result += Math.pow(-1.0, i)*corner*doMatrixDetermination(subset);
            }
            
        }
       
        return result;
    }

    /**
     * Invokes the initialization of source matrices and the execution of the required number of iterations.
     * The result is a <pre>Long</pre> object, which contains the execution time in milliseconds for all computations, <b>without</b> the initialization.
     * <b>TODO:</b> Implement this method.
     */
    public void run() {
        
        this.initMatrixes();

        long start = System.currentTimeMillis();

        for(int l=0; l<loops; l++) {
            this.doMatrixDetermination(a);
        }

        long end = System.currentTimeMillis();

        //for testing
        //System.out.println("in run method.");

        //append matrix multiplication time to queue
        resultQ.append((end-start)/loops);
        
        //System.out.println("Job time is: " + (end-start));

    }

}
