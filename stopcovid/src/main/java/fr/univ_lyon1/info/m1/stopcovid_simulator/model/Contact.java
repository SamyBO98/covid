package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

import fr.univ_lyon1.info.m1.stopcovid_simulator.util.Destroyable;

public class Contact implements Destroyable {
    private int totalValue;
    private final Runnable lifeTimerHandler;
    private Thread lifeTimer;

    /**
     * Constructor.
     *
     * @param lifeTimerHandler The life timer handler.
     */
    protected Contact(final Runnable lifeTimerHandler) {
        totalValue = 0;
        this.lifeTimerHandler = lifeTimerHandler;
        startTimer();
    }

    /**
     * Destructor.
     */
    @Override
    public void destroy() {
        if (lifeTimer != null) {
            lifeTimer.interrupt();
        }
    }

    /**
     * @return the total contact value.
     */
    public int getValue() {
        return totalValue;
    }

    /**
     * Start the `life timer`.
     */
    private void startTimer() {
        lifeTimer = new Thread(lifeTimerHandler);
        lifeTimer.start();
    }

    /**
     * Stop and restart the `life timer`.
     */
    private void restartTimer() {
        if (lifeTimer != null) {
            lifeTimer.interrupt();
        }
        startTimer();
    }

    /**
     * Add `value` to the contact.
     *
     * @param value The value to add.
     */
    public void addContact(final int value) {
        totalValue += value;
        if (lifeTimerHandler != null) {
            restartTimer();
        }
    }

    /**
     * Add a value of one to the contact.
     */
    public void addContact() {
        addContact(1);
    }
}
