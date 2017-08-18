package csci4401.service;

import java.io.Serializable;

/**
 * An implementation of the factory specialized for matrix multiplication workers.
 * <b>Provided class--do not modify</b>.
 */
public class MatrixMultiplyWorkerFactory implements ServiceWorkerFactory {
	private static final int MULTIPLY = 0;
	private static final int DETERMINANT = 1; 

    /**
     * Instantiates a new matrix multiplication service worker.
     */
    public AbstractServiceWorker newServiceWorker(Serializable parameters, MsgQ resultQ, int type, MatrixMultiplyServicePool pool) {
        if(type == MULTIPLY){
        	return new MatrixMultiplyWorker( (MatrixMultiplyParameters)parameters, resultQ, pool);
        }
        if(type == DETERMINANT) {
        	return new MatrixDeterminantWorker( (MatrixMultiplyParameters)parameters, resultQ);
        } else 
        	return null;
    }
}
