package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public abstract class SendingStrategy {
    /**
     * @param contact The contact to send or not.
     * @return if must to send the contact or not.
     */
    public abstract boolean mustSendContact(Contact contact);

    public static class SendAll extends SendingStrategy {
        private final int maxValue = 1;
        @Override
        public boolean mustSendContact(final Contact contact) {
            return contact.getValue() >= maxValue;
        }
    }

    public static class SendRepeated extends SendingStrategy {
        private final int maxValue = 2;
        @Override
        public boolean mustSendContact(final Contact contact) {
            return contact.getValue() >= maxValue;
        }
    }

    public static class SendFrequent extends SendingStrategy {
        private final int maxValue = 5;
        @Override
        public boolean mustSendContact(final Contact contact) {
            return contact.getValue() >= maxValue;
        }
    }
}
