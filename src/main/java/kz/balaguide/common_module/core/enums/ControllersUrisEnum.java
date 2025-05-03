package kz.balaguide.common_module.core.enums;

public final class ControllersUrisEnum {

    public static final String LOGIN = "api/v1/login";

    private static final String PARENTS = "api/v1/parents";
    //Business endpoints
    public static final String PARENT_CREATE = PARENTS + "/create";
    public static final String PARENT_UPDATE = PARENTS + "/update/{parentId}";
    public static final String PARENT_ADD_CHILD = PARENTS + "/{parentId}/add-child";
    public static final String PARENT_REMOVE_CHILD = PARENTS + "/{parentId}/remove-child/{childId}";
    public static final String PARENT_GET_CHILDREN = PARENTS + "/{parentId}/my-children";
    public static final String PARENT_ADD_BALANCE = PARENTS + "/{parentId}/add-balance";

    //Technical endpoints
    public static final String PARENT_GET_BY_ID = PARENTS + "/{parentId}";
    public static final String PARENT_GET_ALL = PARENTS;


    private static final String CHILDREN = "api/v1/children";
    //Business endpoints
    private final String CHILD_ENROLL_TO_COURSE = CHILDREN + "/enroll-to-course";
    private final String CHILD_UNENROLL_FROM_COURSE = CHILDREN + "/unenroll-from-course";
    private final String CHILD_COURSES = CHILDREN + "/{id}/my-courses";
    private final String CHILD_GET_BY_ID = CHILDREN + "/{childId}";
    private final String CHILD_UPDATE = CHILDREN + "/{childId}";
    private final String CHILD_DELETE = CHILDREN + "/{childId}";

    //Technical endpoints
    private final String CHILDREN_GET_ALL = CHILDREN;


    private static final String EDUCATION_CENTERS = "api/v1/education_centers";



    private static final String COURSES = "api/v1/courses";
    //Business endpoints
    private final String COURSE_CREATE = COURSES + "/create";
    private final String COURSE_UPDATE = COURSES + "/{courseId}";
    private final String COURSE_DELETE = COURSES + "/{courseId}";
    private final String COURSE_GET_ALL = COURSES;
    private final String COURSE_GET_BY_ID = COURSES + "/{courseId}"; //TODO impl in controller
    private final String COURSE_SEARCH = COURSES + "/search-courses";
    private final String COURSE_ADD_CONTENTS = COURSES + "/{courseId}/add-contents";
    private final String COURSE_UPDATE_CONTENTS = COURSES + "/{courseId}/add-contents";


    private static final String GROUPS = "api/v1/groups";
    private static final String GROUP_CREATE = GROUPS + "/create";
    private static final String GROUP_UPDATE = GROUPS + "/{groupId}";
    private static final String GROUP_DELETE = GROUPS + "/{groupId}";
    private static final String GROUP_GET_BY_ID = GROUPS + "/{groupId}";
    private static final String GROUP_GET_ALL = GROUPS;

    private static final String GROUP_ACTIVATE = GROUPS + "/{groupId}/activate";
    private static final String GROUP_DEACTIVATE = GROUPS + "/{groupId}/deactivate";


    private static final String LESSONS = "api/v1/lessons";
    private static final String LESSONS_FILL = "api/v1/fill-lessons"; //not endpoint, impl in service (when group created)
    private static final String LESSONS_ACTIVATE_BY_GROUP_ID = LESSONS + "/activate/{groupId}"; //Fill dates (2025-11-05, 2025-11-07 etc)


    private static final String TEACHERS = "api/v1/teachers";

}
