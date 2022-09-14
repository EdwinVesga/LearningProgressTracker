package utils;

import java.util.Arrays;

public class Validator {

    private static final String NAME_REGEX =  "^[a-zA-Z]+([\\-\\']?[a-zA-Z]+)+";

    private static final String EMAIL_REGEX = "^\\w+\\.?\\w*@\\w+\\.\\w+";

    public static boolean validateCredentials(String firstName, String[] lastNames, String email) {
        return validateFirstName(firstName) && validateLastNames(lastNames) && validateEmail(email);
    }

    private static boolean validateFirstName(String firstName) {
        boolean valid = firstName.matches(NAME_REGEX);

        if (!valid) {
            System.out.println("Incorrect first name.");
        }

        return valid;
    }

    private static boolean validateLastNames(String... lastNames) {
        boolean valid = true;

        for (String lastName : lastNames) {
            valid = lastName.matches(NAME_REGEX);
            if (!valid) {
                System.out.println("Incorrect last name.");
                break;
            }
        }

        return valid;
    }

    private static boolean validateEmail(String email) {
        boolean valid = email.matches(EMAIL_REGEX);

        if (!valid) {
            System.out.println("Incorrect email.");
        }

        return valid;
    }
}
