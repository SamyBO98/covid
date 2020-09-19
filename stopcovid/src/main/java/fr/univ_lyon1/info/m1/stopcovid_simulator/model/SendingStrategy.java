package fr.univ_lyon1.info.m1.stopcovid_simulator.model;

public abstract class SendingStrategy {
    /**
     * @param contact The contact to send or not.
     * @return if must to send the contact or not.
     */
    public abstract boolean mustSendContact(final Contact contact);

    public static class SendAll extends SendingStrategy {
        @Override
        public boolean mustSendContact(Contact contact) {
            return contact.getValue() >= 1;
        }
    }

    public static class SendRepeated extends SendingStrategy {
        @Override
        public boolean mustSendContact(Contact contact) {
            return contact.getValue() >= 2;
        }
    }

    public static class SendFrequent extends SendingStrategy {
        @Override
        public boolean mustSendContact(Contact contact) {
            return contact.getValue() >= 5;
        }
    }
}
