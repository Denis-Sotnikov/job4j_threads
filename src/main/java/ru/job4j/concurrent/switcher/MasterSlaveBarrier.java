package ru.job4j.concurrent.switcher;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class MasterSlaveBarrier {

    /**
     * Master
     */
    @GuardedBy("this")
    private boolean master = true;

    /**
     * Slave
     */
    @GuardedBy("this")
    private boolean slave = false;

    /**
     * Method of run master
     */
    public synchronized void tryMaster() {
        while (isMaster()) {
            System.out.println("Thread A");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        notifyAll();
    }

    /**
     * Method of run slave
     */
    public synchronized void trySlave() {
        if (isMaster()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        setSlave(true);
        while (isSlave()) {
            System.out.println("Thread B");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method of done master
     * sends notifyAll()
     */
    public void doneMaster() {
        setMaster(false);
    }

    /**
     * Method of done slave
     * interrupt current thread
     */
    public void doneSlave() {
        setSlave(false);
    }

    public synchronized boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public synchronized boolean isSlave() {
        return slave;
    }

    public void setSlave(boolean slave) {
        this.slave = slave;
    }
}