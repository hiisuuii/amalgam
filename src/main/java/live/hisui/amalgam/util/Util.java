package live.hisui.amalgam.util;

public class Util {

    public static String formatString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Replace underscores with spaces
        String replaced = input.replace('_', ' ');

        // Split the string into words
        String[] words = replaced.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                // Capitalize the first letter and append to result
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        // Remove the trailing space and return
        return result.toString().trim();
    }
}
