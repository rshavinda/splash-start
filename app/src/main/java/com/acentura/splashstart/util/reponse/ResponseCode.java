package com.acentura.splashstart.util.reponse;

public enum ResponseCode {
    CONTINUE (ResponseHandler.Code.C100_CONTINUE, ResponseHandler.Category.INFORMATIONAL,"Continue"),
    SWITCHING_PROTOCOL (ResponseHandler.Code.C101_SWITCHING_PROTOCOL, ResponseHandler.Category.INFORMATIONAL, "Switching Protocols"),
    PROCESSING (ResponseHandler.Code.C102_PROCESSING, ResponseHandler.Category.INFORMATIONAL,"Processing"),
    EARLY_HINTS (ResponseHandler.Code.C103_EARLY_HINTS, ResponseHandler.Category.INFORMATIONAL,"Early Hints"),

    OK (ResponseHandler.Code.C200_OK, ResponseHandler.Category.SUCCESSFUL, "OK"),
    CREATED (ResponseHandler.Code.C201_CREATED, ResponseHandler.Category.SUCCESSFUL, "Created"),
    ACCEPTED (ResponseHandler.Code.C202_ACCEPTED, ResponseHandler.Category.SUCCESSFUL, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION (ResponseHandler.Code.C203_NON_AUTHORITATIVE_INFORMATION, ResponseHandler.Category.SUCCESSFUL, "Non-Authoritative Information"),
    NO_CONTENT (ResponseHandler.Code.C204_NO_CONTENT, ResponseHandler.Category.SUCCESSFUL, "No Content"),
    RESET_CONTENT (ResponseHandler.Code.C205_RESET_CONTENT, ResponseHandler.Category.SUCCESSFUL, "Reset Content"),
    PARTIAL_CONTENT (ResponseHandler.Code.C206_PARTIAL_CONTENT, ResponseHandler.Category.SUCCESSFUL, "Partial Content"),
    MULTI_STATUS (ResponseHandler.Code.C207_MULTI_STATUS, ResponseHandler.Category.SUCCESSFUL, "Multi-Status"),
    ALREADY_REPORTED (ResponseHandler.Code.C208_ALREADY_REPORTED, ResponseHandler.Category.SUCCESSFUL, "Already Reported"),
    IM_USED (ResponseHandler.Code.C226_IM_USED, ResponseHandler.Category.SUCCESSFUL, "IM Used"),

    MULTIPLE_CHOICES (ResponseHandler.Code.C300_MULTIPLE_CHOICES, ResponseHandler.Category.REDIRECTION, "Multiple Choices"),
    MOVED_PERMANENTLY (ResponseHandler.Code.C301_MOVED_PERMANENTLY, ResponseHandler.Category.REDIRECTION, "Moved Permanently"),
    MOVED_TEMPORARILY (ResponseHandler.Code.C302_MOVED_TEMPORARILY, ResponseHandler.Category.REDIRECTION, "Moved Temporary"),
    SEE_OTHER (ResponseHandler.Code.C303_SEE_OTHER, ResponseHandler.Category.REDIRECTION, "See Other"),
    NOT_MODIFIED (ResponseHandler.Code.C304_NOT_MODIFIED, ResponseHandler.Category.REDIRECTION, "Not Modified"),
    USE_PROXY (ResponseHandler.Code.C305_USE_PROXY, ResponseHandler.Category.REDIRECTION, "Use Proxy"),
    TEMPORARY_REDIRECT (ResponseHandler.Code.C307_TEMPORARY_REDIRECT, ResponseHandler.Category.REDIRECTION, "Temporary Redirect"),
    PERMANENT_REDIRECT (ResponseHandler.Code.C308_PERMANENT_REDIRECT, ResponseHandler.Category.REDIRECTION, "Permanent Redirect"),

    BAD_REQUEST (ResponseHandler.Code.C400_BAD_REQUEST, ResponseHandler.Category.CLIENT_ERROR, "Bad Request"),
    UNAUTHORIZED (ResponseHandler.Code.C401_UNAUTHORIZED, ResponseHandler.Category.CLIENT_ERROR, "Unauthorized"),
    PAYMENT_REQUIRED (ResponseHandler.Code.C402_PAYMENT_REQUIRED, ResponseHandler.Category.CLIENT_ERROR, "Payment Required"),
    FORBIDDEN (ResponseHandler.Code.C403_FORBIDDEN, ResponseHandler.Category.CLIENT_ERROR, "Forbidden"),
    NOT_FOUND (ResponseHandler.Code.C404_NOT_FOUND, ResponseHandler.Category.CLIENT_ERROR, "Not Found"),
    METHOD_NOT_ALLOWED (ResponseHandler.Code.C405_METHOD_NOT_ALLOWED, ResponseHandler.Category.CLIENT_ERROR, "Method Not Allowed"),
    NOT_ACCEPTABLE (ResponseHandler.Code.C406_NOT_ACCEPTABLE, ResponseHandler.Category.CLIENT_ERROR, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED (ResponseHandler.Code.C407_PROXY_AUTHENTICATION_REQUIRED, ResponseHandler.Category.CLIENT_ERROR, "Proxy Authentication Required"),
    REQUEST_TIMEOUT (ResponseHandler.Code.C408_REQUEST_TIMEOUT, ResponseHandler.Category.CLIENT_ERROR, "Request Timeout"),
    CONFLICT (ResponseHandler.Code.C409_CONFLICT, ResponseHandler.Category.CLIENT_ERROR, "Conflict"),
    GONE (ResponseHandler.Code.C410_GONE, ResponseHandler.Category.CLIENT_ERROR, "Gone"),
    LENGTH_REQUIRED (ResponseHandler.Code.C411_LENGTH_REQUIRED, ResponseHandler.Category.CLIENT_ERROR, "Length Required"),
    PRECONDITION_FAILED (ResponseHandler.Code.C412_PRECONDITION_FAILED, ResponseHandler.Category.CLIENT_ERROR, "Precondition Failed"),
    REQUEST_TOO_LONG (ResponseHandler.Code.C413_REQUEST_TOO_LONG, ResponseHandler.Category.CLIENT_ERROR, "Request Entity Too Large"),
    REQUEST_URI_TOO_LONG (ResponseHandler.Code.C414_REQUEST_URI_TOO_LONG, ResponseHandler.Category.CLIENT_ERROR, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE (ResponseHandler.Code.C415_UNSUPPORTED_MEDIA_TYPE, ResponseHandler.Category.CLIENT_ERROR, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE (ResponseHandler.Code.C416_REQUESTED_RANGE_NOT_SATISFIABLE, ResponseHandler.Category.CLIENT_ERROR, "Requested Range Not Satisfiable"),
    EXPECTATION_FAILED (ResponseHandler.Code.C417_EXPECTATION_FAILED, ResponseHandler.Category.CLIENT_ERROR, "Expectation Failed"),
    IM_A_TEAPOT (ResponseHandler.Code.C418_IM_A_TEAPOT, ResponseHandler.Category.CLIENT_ERROR, "I’m a teapot (RFC 2324)"),
    INSUFFICIENT_SPACE_ON_RESOURCE (ResponseHandler.Code.C419_AUTHENTICATION_TIMEOUT, ResponseHandler.Category.CLIENT_ERROR, "Authentication Timeout"),
    METHOD_FAILURE (ResponseHandler.Code.C420_METHOD_FAILURE, ResponseHandler.Category.CLIENT_ERROR, "Method Failure"),
    MISDIRECTED_REQUEST (ResponseHandler.Code.C421_MISDIRECTED_REQUEST, ResponseHandler.Category.CLIENT_ERROR, "Misdirected Request"),
    UNPROCESSABLE_ENTITY (ResponseHandler.Code.C422_UNPROCESSABLE_ENTITY, ResponseHandler.Category.CLIENT_ERROR, "Unprocessable Entity"),
    LOCKED (ResponseHandler.Code.C423_LOCKED, ResponseHandler.Category.CLIENT_ERROR, "Locked"),
    FAILED_DEPENDENCY (ResponseHandler.Code.C424_FAILED_DEPENDENCY, ResponseHandler.Category.CLIENT_ERROR, "Failed Dependency"),
    C425_TOO_EARLY (ResponseHandler.Code.C425_TOO_EARLY, ResponseHandler.Category.CLIENT_ERROR, "Too Early"),
    C426_UPGRADE_REQUIRED (ResponseHandler.Code.C426_UPGRADE_REQUIRED, ResponseHandler.Category.CLIENT_ERROR, "Upgrade Required"),
    PRECONDITION_REQUIRED (ResponseHandler.Code.C428_PRECONDITION_REQUIRED, ResponseHandler.Category.CLIENT_ERROR, "Precondition Required"),
    TOO_MANY_REQUESTS (ResponseHandler.Code.C429_TOO_MANY_REQUESTS, ResponseHandler.Category.CLIENT_ERROR, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE (ResponseHandler.Code.C431_REQUEST_HEADER_FIELDS_TOO_LARGE, ResponseHandler.Category.CLIENT_ERROR, "Request Header Fields Too Large"),
    NO_RESPONSE (ResponseHandler.Code.C444_NO_RESPONSE, ResponseHandler.Category.CLIENT_ERROR, "No Response"),
    RETRY (ResponseHandler.Code.C449_RETRY, ResponseHandler.Category.CLIENT_ERROR, "Retry"),
    BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS (ResponseHandler.Code.C450_BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS, ResponseHandler.Category.CLIENT_ERROR, "Blocked by Windows Parental Controls"),
    UNAVAILABLE_FOR_LEGAL_REASONS (ResponseHandler.Code.C451_UNAVAILABLE_FOR_LEGAL_REASONS, ResponseHandler.Category.CLIENT_ERROR, "Unavailable For Legal Reasons"),
    CLIENT_CLOSED_REQUEST (ResponseHandler.Code.C499_CLIENT_CLOSED_REQUEST, ResponseHandler.Category.CLIENT_ERROR, "Client Closed Request"),

    INTERNAL_SERVER_ERROR (ResponseHandler.Code.C500_INTERNAL_SERVER_ERROR, ResponseHandler.Category.SERVER_ERROR, "Internal Server Error"),
    NOT_IMPLEMENTED (ResponseHandler.Code.C501_NOT_IMPLEMENTED, ResponseHandler.Category.SERVER_ERROR, "Not Implemented"),
    BAD_GATEWAY (ResponseHandler.Code.C502_BAD_GATEWAY, ResponseHandler.Category.SERVER_ERROR, "Bad Gateway"),
    SERVICE_UNAVAILABLE (ResponseHandler.Code.C503_SERVICE_UNAVAILABLE, ResponseHandler.Category.SERVER_ERROR, "Service Unavailable"),
    GATEWAY_TIMEOUT (ResponseHandler.Code.C504_GATEWAY_TIMEOUT, ResponseHandler.Category.SERVER_ERROR, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED (ResponseHandler.Code.C505_HTTP_VERSION_NOT_SUPPORTED, ResponseHandler.Category.SERVER_ERROR, "HTTP Version Not Supported"),
    VARIANT_ALSO_NEGOTIATES (ResponseHandler.Code.C506_VARIANT_ALSO_NEGOTIATES, ResponseHandler.Category.SERVER_ERROR, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE (ResponseHandler.Code.C507_INSUFFICIENT_STORAGE, ResponseHandler.Category.SERVER_ERROR, "Insufficient Storage"),
    LOOP_DETECTED (ResponseHandler.Code.C508_LOOP_DETECTED, ResponseHandler.Category.SERVER_ERROR, "Loop Detected"),
    NOT_EXTENDED (ResponseHandler.Code.C510_NOT_EXTENDED, ResponseHandler.Category.SERVER_ERROR, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED (ResponseHandler.Code.C511_NETWORK_AUTHENTICATION_REQUIRED, ResponseHandler.Category.SERVER_ERROR, "Network Authentication Required"),

    REQUEST_DENIED (ResponseHandler.Code.C999_REQUEST_DENIED, ResponseHandler.Category.UNKNOWN, "Unable to Process Request/Request Denied"),
    CUSTOM_ERROR_NOT_CONNECTED (ResponseHandler.Code.CE999_CUSTOM_ERROR_NOT_CONNECTED, ResponseHandler.Category.UNKNOWN, "No Internet Connection");

    private final int code;
    private final String type;
    private final String category;

    ResponseCode(int code, String category,  String type){
        this.code = code;
        this.category = category;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}
