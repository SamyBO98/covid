package fr.univ_lyon1.info.m1.stopcovid_simulator.util.events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class Delegate<HANDLER extends Handler> {
    private final HashMap<String, HANDLER> handlers;

    /**
     * Constructor.
     */
    protected Delegate() {
        handlers = new HashMap<>();
    }

    /**
     * Serialize the `handler` to get an id to differentiate multiple handlers of different methods,
     * and not differentiate multiple handlers of the same method.
     *
     * @param handler The handler to serialize.
     * @return the `handler` id.
     */
    private static String getId(final Handler handler) {
        try {
            var method = handler.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return method.invoke(handler).toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return the `handlers`.
     */
    protected HashMap<String, HANDLER> getHandlers() {
        return handlers;
    }

    /**
     * Add the `handler` if it hasn't already been added.
     *
     * @param handler The handler to add.
     * @return if the `handler` has been added.
     */
    public final boolean add(final HANDLER handler) {
        if (handler != null) {
            var id = getId(handler);
            if (id != null && !handlers.containsKey(id)) {
                handlers.put(id, handler);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the `handler` if it has been added.
     *
     * @param handler The handler to remove.
     * @return if the `handler` has been removed.
     */
    public final boolean remove(final HANDLER handler) {
        if (handler == null) {
            return false;
        }
        var id = getId(handler);
        return handlers.remove(id) != null;
    }

    /**
     * Clear the `handlers`.
     */
    public final void clear() {
        handlers.clear();
    }

    public static class With0Params extends Delegate<Handler.With0Params> {
        /**
         * Invoke the `handlers`.
         */
        public void invoke() {
            for (var handler : getHandlers().values()) {
                if (handler != null) {
                    handler.handle();
                }
            }
        }
    }

    public static class With1Params<T1> extends Delegate<Handler.With1Params<T1>> {
        /**
         * Invoke the `handlers`.
         *
         * @param arg1 The first argument to handlers.
         */
        public void invoke(final T1 arg1) {
            for (var handler : getHandlers().values()) {
                if (handler != null) {
                    handler.handle(arg1);
                }
            }
        }
    }

    public static class With2Params<T1, T2> extends Delegate<Handler.With2Params<T1, T2>> {
        /**
         * Invoke the `handlers`.
         *
         * @param arg1 The first argument to handlers.
         * @param arg2 The second argument to handlers.
         */
        public void invoke(final T1 arg1, final T2 arg2) {
            for (var handler : getHandlers().values()) {
                if (handler != null) {
                    handler.handle(arg1, arg2);
                }
            }
        }
    }
}
