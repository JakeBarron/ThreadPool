package csci4401.service;

import java.io.Serializable;
import java.util.*;

/**
 * <b>[TODO]</b> Basic queue implementation.
 * Implement missing functionality in this class.
 */
public class BasicMsgQ implements MsgQ {

    //linked list to serve as msQ data structure.
    private LinkedList<Serializable> threadPool = new LinkedList<Serializable>();

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public void append(Serializable message) {
        
       // for testing 
       // System.out.println("in append");

        //add message to threadpool
        this.threadPool.add(message);

        //in a synchronized block.  notify all threads that a message has been added.
        synchronized(this.threadPool) {

            this.threadPool.notifyAll();

        }
    }//end append


    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable pop() throws InterruptedException {
        //for testing
        //System.out.println("in pop.");

        //in a synchronized block. If threadPool is not empty, return head of queue
        synchronized(this.threadPool) {
            while(this.threadPool.isEmpty()) {
                this.threadPool.wait();
            }
            //remove threadPool from head of list
            try{
                return this.threadPool.remove();
            }
            catch(NoSuchElementException e) {
                System.out.println("threadPool empty.");
		        return null;
            }
        }
    } //end pop

    /**
     * <b>TODO:</b> Implement this method as per the interface specification.
     */
    public Serializable asyncPop() {
        Serializable message = null;

        //if message is not empty set message equal to head of queue.
        if(!(this.threadPool.isEmpty())) {
            message = this.threadPool.remove();
        }

        return message;
    }

}
