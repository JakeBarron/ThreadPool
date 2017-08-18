package csci4401.service;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * A more advanced implementaion of a service pool for matrix multiplication workers, 
 * which matches the number of outstanding jobs to the number of available hardware-supported threads.
 * <b>TODO:</b> Implement this class.
 */
public class BalancedMMServicePool extends MatrixMultiplyServicePool {
    //delayed request queue
    LinkedList<Serializable> delayedRequests = new LinkedList<>();

    LinkedList<AbstractServiceWorker> threadPool = new LinkedList<>();

    MsgQ resultQ = new BasicMsgQ();
    MatrixMultiplyWorkerFactory factory = new MatrixMultiplyWorkerFactory();
    int poolCount;

    /**
     * @param poolMin   minimum pool size (not used)
     * @param poolMax   maximum pool size (not used)
     */
    public BalancedMMServicePool(int poolMin, int poolMax) {

        super(poolMin, Runtime.getRuntime().availableProcessors(), 0);
        this.poolCount = 0;

        System.out.println("creating threads");

        for(int i = 0; i < super.poolMax; i++) {
           //add new threads to delayed Requests queue
           threadPool.add(factory.newServiceWorker(null, super.resultQ, 0, (MatrixMultiplyServicePool)this)); 
        }

        System.out.println(" done creating threads");

    }
    //addRequest for reusable threads
    public synchronized void addRequest(Serializable request) {
         MatrixMultiplyWorker worker = (MatrixMultiplyWorker)factory.newServiceWorker(null, super.resultQ, 0, (MatrixMultiplyServicePool)this);       
        //if there are less than max threads currently running

        //System.out.println("begin request");
        synchronized(worker) {
            if (poolCount < super.poolMax && !threadPool.isEmpty()) {
                worker = (MatrixMultiplyWorker)threadPool.poll();
                worker.passParams((MatrixMultiplyParameters)request);
                worker.setReady(false);
            try{
                worker.start();
            } catch (IllegalThreadStateException e) {
                worker.run();
            }
                poolCount++; 
            }  else {
                //System.out.println("adding request to delayed request queue");
                delayedRequests.add(request);
            }
        }        
        
    }

    /**
     * Notifies the service pool that a worker has completed the computation with the given result.
     * If there are any outstanding requests, the first in line should be serviced.
     * 
     * <b>TODO:</b> Implement this method.
     */
    public synchronized void addResult(Serializable result, AbstractServiceWorker usedWorker) {
            MatrixMultiplyWorker worker = (MatrixMultiplyWorker)usedWorker;

            //System.out.println("trying to regain worker");
            worker.ready = true;
            threadPool.add(worker);
            poolCount--;
            //System.out.println("regained worker");

        if(!(delayedRequests.isEmpty())) {
            this.addRequest(this.delayedRequests.poll());
        }
    }

}
