package dk.cristi.app.webshop.client.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class UIDUtils {
    /**
     * Generate a random uid from alphanumeric chars. This is an <em>alias</em> for {@link UIDUtils#getRandomUid(int, true, true)}
     * @param length how many characters should it contain
     * @return a string of length, contain alphanumeric chars
     */
    public static String getRandomUid(int length) {
        return getRandomUid(length, true, true);
    }

    /**
     * Generate a random uid from specified char set.
     * @param length how many characters should it contain
     * @param letters whether or not to use letters
     * @param numbers whether or not to use numbers
     * @return a string of length, contain chars from the specified set
     */
    public static String getRandomUid(int length, boolean letters, boolean numbers) {
        return RandomStringUtils.random(length, letters, numbers);
    }
}
