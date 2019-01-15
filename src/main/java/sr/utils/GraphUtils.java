package sr.utils;
public class GraphUtils {

    public String normalizeLabel(int number) {
        return Labels.fromValue(number).toString();
    }

    enum Labels{
        A, B, C, D, E, F, G, H, I ,J, K;

        private final int value;

        Labels() {
            this.value = ordinal();
        }

        public static Labels fromValue(int value)
                throws IllegalArgumentException {
            try {
                return Labels.values()[value];
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Unknown enum value :"+ value);
            }
        }
    }
}
