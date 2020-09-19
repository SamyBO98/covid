package fr.univ_lyon1.info.m1.stopcovid_simulator.util.events;

public class Event<HANDLER extends Handler> {
    private final Delegate<HANDLER> delegate;

    /**
     * Constructor.
     *
     * @param delegate The delegate to manage.
     */
    public Event(final Delegate<HANDLER> delegate) {
        this.delegate = delegate;
    }

    /**
     * Add the `handler` if it hasn't already been added.
     *
     * @param handler The handler to add.
     * @return if the `handler` has been added.
     */
    public final boolean add(final HANDLER handler) {
        return delegate.add(handler);
    }

    /**
     * Remove the `handler` if it has been added.
     *
     * @param handler The handler to remove.
     * @return if the `handler` has been removed.
     */
    public final boolean remove(final HANDLER handler) {
        return delegate.remove(handler);
    }

    public static class With0Params extends Event<Handler.With0Params> {
        /**
         * Constructor.
         *
         * @param delegate The delegate to manage.
         */
        public With0Params(final Delegate<Handler.With0Params> delegate) {
            super(delegate);
        }
    }

    public static class With1Params<T1> extends Event<Handler.With1Params<T1>> {
        /**
         * Constructor.
         *
         * @param delegate The delegate to manage.
         */
        public With1Params(final Delegate<Handler.With1Params<T1>> delegate) {
            super(delegate);
        }
    }

    public static class With2Params<T1, T2> extends Event<Handler.With2Params<T1, T2>> {
        /**
         * Constructor.
         *
         * @param delegate The delegate to manage.
         */
        public With2Params(final Delegate<Handler.With2Params<T1, T2>> delegate) {
            super(delegate);
        }
    }
}
