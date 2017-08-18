package csci4401.service;

/**
 * <b>[TODO]</b> Matrix multiplier worker.
 * Implement missing functionality in this class.
 */
public class MatrixMultiplyWorker extends AbstractServiceWorker {

    private double[][] a, b, c;
    private int mSize;
    private int loops;
    private MsgQ resultQ;
    private BalancedMMServicePool pool;
    //is thread ready
    protected boolean ready;

    public MatrixMultiplyWorker(MatrixMultiplyParameters parameters, MsgQ resultQ, MatrixMultiplyServicePool pool) {
        super(parameters, resultQ);
        // this.mSize = parameters.matrixSize;
        // this.loops = parameters.iterations;
        this.resultQ = resultQ;
        this.pool = (BalancedMMServicePool)pool;
        ready = true;

    }

    public boolean isReady() {
        return this.ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void passParams(MatrixMultiplyParameters parameters) {
        this.mSize = parameters.matrixSize;
        this.loops = parameters.iterations;
    }

    /**
     * Initializes the matrixes based on the size parameter.
     * <b>TODO:</b> Implement this method.
     */
    private void initMatrixes() {
        this.a = new double[mSize][mSize];
        this.b = new double[mSize][mSize];
        this.c = new double[mSize][mSize];

        for(int i=0; i<mSize; i++) {
            for(int j=0; j<mSize; j++) {
                this.a[i][j] = (i+1);
                this.b[i][j] = (i+2);

            }
        }
    } //end initMatrixes()

    /**
     * Performs one iterarion of matrix multiplication.
     * <b>TODO:</b> Implement this method.
     */
    private void doMatrixMultiplication() {

        for(int i=0; i<mSize; i++)
            for(int j=0; j<mSize; j++)
                for(int k=0; k<mSize; k++)
                    c[i][j] += a[i][k]*b[k][j];
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
            this.doMatrixMultiplication();
        }

        long end = System.currentTimeMillis();

        //for testing
        //System.out.println("in run method.");

        //append matrix multiplication time to queue
        resultQ.append((end-start)/loops);

        //System.out.println("successful append");

        //must notify pool upon completion
        pool.addResult((end-start)/loops, this);

        //System.out.println("Job time is: " + (end-start));

    }

}
