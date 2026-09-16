package coding.solutions;

public class StringConcurrencyPlaygroundSolution {

    public static class ThreadSafeStringAccumulatorSolution {
        private final StringBuilder builder = new StringBuilder();

        public synchronized void append(String str) {
            if (str != null) {
                builder.append(str);
            }
        }

        @Override
        public synchronized String toString() {
            return builder.toString();
        }
    }
}
