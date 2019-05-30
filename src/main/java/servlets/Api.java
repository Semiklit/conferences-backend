package servlets;

interface Api {
    String PARAMETER_ACTION = "action";
    String PARAMETER_TOKEN = "token";

    String ACTION_GET_CONFERENCE_LIST = "get.conference.list";
    String ACTION_GET_CONFERENCE = "get.conference";
    String ACTION_GET_CONFERENCE_OWEND = "get.conference.list.owned";
    String ACTION_GET_CONFERENCE_FAVOURITS = "get.conference.list.fav";
    String ACTION_GET_REPORTS_FOR_SUBMIT = "get.reports.not_submitted";
    String ACTION_GET_MESSAGES = "get.messages";
    String ACTION_GET_NOTIFICATIONS = "get.notifications";
    String ACTION_CREATE_CONFERENCE = "create.conference";
    String ACTION_EDIT_CONFERENCE = "edit.conference";
    String ACTION_CREATE_REPORT = "create.report";
    String ACTION_AUTH = "auth";

    String PARAMETER_ID = "id";
    String PARAMETER_CONFERENCE_ID = "conference.id";
    String PARAMETER_USER_ID = "user.id";
    String PARAMETER_AUTH_METHOD = "auth.method";
    String PARAMETER_EXT_SERVICE_ID = "ext_service.id";
    String PARAMETER_EXT_SERVICE_TOKEN = "ext_service.name";

    String HEADER_AUTH = "Authorization";
}
