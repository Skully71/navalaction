package navalaction.pricing;

/**
 *
 */
class Util {
    static String t(final String s, final int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength - 2) + "..";
        }
        return s;
    }
}
