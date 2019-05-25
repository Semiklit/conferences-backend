package servlets;

interface Api {
    String PARAMETER_ACTION = "action";
    String PARAMETER_TOKEN = "token";

    String ACTION_GET_CONFERENCE_LIST = "get.conference.list";
    String ACTION_GET_CONFERENCE_INFO = "get.conference.info";
    String ACTION_GET_REPORTS_LIST = "get.reports.list";
    String ACTION_GET_REPORT_INFO = "get.report.info";
    String ACTION_GET_SECTION_LIST = "get.section.list";
    String ACTION_GET_SECTION_INFO = "get.section.info";
    String ACTION_GET_USER_INFO = "get.user.info";
    String ACTION_AUTH = "auth";

    String PARAMETER_ID = "id";
    String PARAMETER_CONFERENCE_ID = "conference.id";
    String PARAMETER_SECTION_ID = "section.id";
    String PARAMETER_AUTH_METHOD = "auth.method";
    String PARAMETER_AUTH_EMAIL = "auth.email";
    String PARAMETER_AUTH_S_NAME = "auth.s_name";
    String PARAMETER_AUTH_L_NAME = "auth.l_name";
    String PARAMETER_AUTH_F_NAME = "auth.f_name";
    String PARAMETER_EXT_SERVICE_ID = "ext_service.id";
    String PARAMETER_EXT_SERVICE_NAME = "ext_service.name";
    String PARAMETER_EXT_SERVICE_TOKEN = "ext_service.name";
}
