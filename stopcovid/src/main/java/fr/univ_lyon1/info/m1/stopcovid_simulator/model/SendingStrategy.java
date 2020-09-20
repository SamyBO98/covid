package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public abstract class SendingStrategy {
    /**
     * @param contact The contact to send or not.
     * @return if must to send the contact or not.
     */
    public abstract boolean mustSendContact(Contact contact);

    public abstract static class SendingStrategyByThreshold extends SendingStrategy {
        private final int threshold;

        /**
         * Constructor.
         *
         * @param threshold Minimum threshold for sending contacts.
         */
        protected SendingStrategyByThreshold(final int threshold) {
            this.threshold = threshold;
        }

        @Override
        public boolean mustSendContact(final Contact contact) {
            return contact.getValue() >= threshold;
        }
    }

    public static class SendAll extends SendingStrategyByThreshold {
        private static final int THRESHOLD = 1;

        /**
         * Constructor.
         */
        public SendAll() {
            super(THRESHOLD);
        }

        @Override
        public String toString() {
            return "Send all";
        }
    }

    public static class SendRepeated extends SendingStrategyByThreshold {
        private static final int THRESHOLD = 2;

        /**
         * Constructor.
         */
        public SendRepeated() {
            super(THRESHOLD);
        }

        @Override
        public String toString() {
            return "Send repeated";
        }
    }

    public static class SendFrequent extends SendingStrategyByThreshold {
        private static final int THRESHOLD = 5;

        /**
         * Constructor.
         */
        public SendFrequent() {
            super(THRESHOLD);
        }

        @Override
        public String toString() {
            return "Send frequent";
        }
    }
}
