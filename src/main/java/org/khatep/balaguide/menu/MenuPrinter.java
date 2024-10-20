package org.khatep.balaguide.menu;

import static org.khatep.balaguide.models.enums.Colors.*;
import static org.khatep.balaguide.models.enums.Colors.RESET;

public class MenuPrinter {

    /**
     * Prints the main menu options to the console.
     * The main menu includes options for accessing the parent menu, child menu, and exiting the system.
     */
    public void printMainMenu() {
        System.out.print(BLUE.getCode() + """
                1. Parent menu
                2. Child menu
                3. Exit from system
                """ + RESET.getCode());
    }

    /**
     * Prints the parent menu options to the console.
     * The parent menu includes options for signing up, logging in, adding and removing children,
     * retrieving the list of children, searching for courses, enrolling and unenrolling children from courses,
     * and adding balance.
     */
    public void printParentMenu() {
        System.out.print(PURPLE.getCode() + """
                1. signUp
                2. login
                3. addChild
                4. removeChild
                5. getMyChildren
                6. searchCourse
                7. enrollChildToCourse
                8. unEnrollChildFromCourse
                9. addBalance
                10. Back
                """ + RESET.getCode());
    }

    /**
     * Prints the child menu options to the console.
     * The child menu includes options for logging in, searching for courses, retrieving enrolled courses,
     * and going back to the previous menu.
     */
    public void printChildMenu() {
        System.out.print(PURPLE.getCode() + """
                1. login
                2. searchCourse
                3. getEnrolledCourses
                4. Back
                """ + RESET.getCode());
    }
}
