package com.acentura.splashstart.util.reponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.HttpException;

public class ResponseHandler {

    //avoid create instance
    private ResponseHandler() { }

    public static ResponseData getError(@NotNull Throwable throwable){
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;

            switch (exception.code()){
                case Code.C400_BAD_REQUEST:
                    return new ResponseData(StatusCode.BAD_REQUEST, exception.getMessage() );

                case Code.C401_UNAUTHORIZED:
                    return new ResponseData(StatusCode.UNAUTHORIZED,
                            exception.getMessage() != null?
                                    exception.getMessage() : "Unauthorized to access the resource.");

                case Code.C404_NOT_FOUND:
                    return new ResponseData(StatusCode.NOT_FOUND, exception.getMessage());

                case Code.C422_UNPROCESSABLE_ENTITY:
                    return new ResponseData(StatusCode.UNPROCESSABLE_ENTITY, exception.getMessage());

                case Code.C500_INTERNAL_SERVER_ERROR:
                    return new ResponseData(StatusCode.INTERNAL_SERVER_ERROR, exception.getMessage());

                case Code.C501_NOT_IMPLEMENTED:
                    return new ResponseData(StatusCode.NOT_IMPLEMENTED, exception.getMessage());

                case Code.C502_BAD_GATEWAY:
                    return new ResponseData(StatusCode.BAD_GATEWAY, exception.getMessage());

                case Code.C503_SERVICE_UNAVAILABLE:
                    return new ResponseData(StatusCode.SERVICE_UNAVAILABLE, exception.getMessage());

                case Code.C999_REQUEST_DENIED:
                    return new ResponseData(StatusCode.REQUEST_DENIED, exception.getMessage());

                default: return new ResponseData(StatusCode.REQUEST_DENIED,
                        exception.getMessage() != null?
                                exception.getMessage() : "Unknown HTTP Error.");

            }
        }
        else if (throwable instanceof IOException) {
            IOException ioException = (IOException)throwable;
            return new ResponseData(StatusCode.CUSTOM_ERROR_NOT_CONNECTED,
                    "Make sure that Wi-Fi or mobile data is turned on that try again!");
        } else{
            return new ResponseData(StatusCode.REQUEST_DENIED, "Error, Please try again later.");
        }

    }


    public static String getStatusType( int code ) {

        switch (code) {
            /* 1xx Informational Response – The request was received, continuing process */
            case Code.C100_CONTINUE: return "Continue";
            case Code.C101_SWITCHING_PROTOCOL: return "Switching Protocols";
            case Code.C102_PROCESSING: return "Processing";
            case Code.C103_EARLY_HINTS: return "Early Hints";

            /* 2xx Successful – The request was successfully received, understood, and accepted */
            case Code.C200_OK: return "OK";
            case Code.C201_CREATED: return "Created";
            case Code.C202_ACCEPTED: return "Accepted";
            case Code.C203_NON_AUTHORITATIVE_INFORMATION: return "Non-Authoritative Information";
            case Code.C204_NO_CONTENT: return "No Content";
            case Code.C205_RESET_CONTENT: return "Reset Content";
            case Code.C206_PARTIAL_CONTENT: return "Partial Content";
            case Code.C207_MULTI_STATUS: return "Multi-Status";
            case Code.C208_ALREADY_REPORTED: return "Already Reported";
            case Code.C226_IM_USED: return "IM Used";

            /* 3xx Redirection – Further action needs to be taken in order to complete the request */
            case Code.C300_MULTIPLE_CHOICES: return "Multiple Choices";
            case Code.C301_MOVED_PERMANENTLY: return "Moved Permanently";
            case Code.C302_MOVED_TEMPORARILY: return "Moved Temporary";
            case Code.C303_SEE_OTHER: return "See Other";
            case Code.C304_NOT_MODIFIED: return "Not Modified";
            case Code.C305_USE_PROXY: return "Use Proxy";
            case Code.C307_TEMPORARY_REDIRECT: return "Temporary Redirect";
            case Code.C308_PERMANENT_REDIRECT: return "Permanent Redirect";

            /* 4xx Client Error – The request contains bad syntax or cannot be fulfilled */
            case Code.C400_BAD_REQUEST: return "Bad Request";
            case Code.C401_UNAUTHORIZED: return "Unauthorized";
            case Code.C402_PAYMENT_REQUIRED: return "Payment Required";
            case Code.C403_FORBIDDEN: return "Forbidden";
            case Code.C404_NOT_FOUND: return "Not Found";
            case Code.C405_METHOD_NOT_ALLOWED: return "Method Not Allowed";
            case Code.C406_NOT_ACCEPTABLE: return "Not Acceptable";
            case Code.C407_PROXY_AUTHENTICATION_REQUIRED: return "Proxy Authentication Required";
            case Code.C408_REQUEST_TIMEOUT: return "Request Timeout";
            case Code.C409_CONFLICT: return "Conflict";
            case Code.C410_GONE: return "Gone";
            case Code.C411_LENGTH_REQUIRED: return "Length Required";
            case Code.C412_PRECONDITION_FAILED: return "Precondition Failed";
            case Code.C413_REQUEST_TOO_LONG: return "Request Entity Too Large";
            case Code.C414_REQUEST_URI_TOO_LONG: return "Request-URI Too Long";
            case Code.C415_UNSUPPORTED_MEDIA_TYPE: return "Unsupported Media Type";
            case Code.C416_REQUESTED_RANGE_NOT_SATISFIABLE: return "Requested Range Not Satisfiable";
            case Code.C417_EXPECTATION_FAILED: return "Expectation Failed";
            case Code.C418_IM_A_TEAPOT: return "I’m a teapot (RFC 2324)";
            case Code.C420_METHOD_FAILURE: return "Method Failure";
            case Code.C421_MISDIRECTED_REQUEST: return "Misdirected Request";
            case Code.C422_UNPROCESSABLE_ENTITY: return "Unprocessable Entity";
            case Code.C423_LOCKED: return "Locked";
            case Code.C424_FAILED_DEPENDENCY: return "Failed Dependency";
            case Code.C425_TOO_EARLY: return "Too Early";
            case Code.C426_UPGRADE_REQUIRED: return "Upgrade Required";
            case Code.C428_PRECONDITION_REQUIRED: return "Precondition Required";
            case Code.C429_TOO_MANY_REQUESTS: return "Too Many Requests";
            case Code.C431_REQUEST_HEADER_FIELDS_TOO_LARGE: return "Request Header Fields Too Large";
            case Code.C444_NO_RESPONSE: return "No Response";
            case Code.C449_RETRY: return "Retry";
            case Code.C450_BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS: return "Blocked by Windows Parental Controls";
            case Code.C451_UNAVAILABLE_FOR_LEGAL_REASONS: return "Unavailable For Legal Reasons";
            case Code.C499_CLIENT_CLOSED_REQUEST: return "Client Closed Request";

            /* 5xx Server Error – The server failed to fulfil an apparently valid request */
            case Code.C500_INTERNAL_SERVER_ERROR: return "Internal Server Error";
            case Code.C501_NOT_IMPLEMENTED: return "Not Implemented";
            case Code.C502_BAD_GATEWAY: return "Bad Gateway";
            case Code.C503_SERVICE_UNAVAILABLE: return "Service Unavailable";
            case Code.C504_GATEWAY_TIMEOUT: return "Gateway Timeout";
            case Code.C505_HTTP_VERSION_NOT_SUPPORTED: return "HTTP Version Not Supported";
            case Code.C506_VARIANT_ALSO_NEGOTIATES: return "Variant Also Negotiates";
            case Code.C507_INSUFFICIENT_STORAGE: return "Insufficient Storage";
            case Code.C508_LOOP_DETECTED: return "Loop Detected";
            case Code.C510_NOT_EXTENDED: return "Not Extended";
            case Code.C511_NETWORK_AUTHENTICATION_REQUIRED: return "Network Authentication Required";

            case Code.C999_REQUEST_DENIED : return "Unable to Process Request/Request Denied";
            case Code.CE999_CUSTOM_ERROR_NOT_CONNECTED : return "No Internet Connection";
            default: return "";
        }
    }

    public static enum StatusCode {
        CONTINUE (Code.C100_CONTINUE, Category.INFORMATIONAL,"Continue"),
        SWITCHING_PROTOCOL (Code.C101_SWITCHING_PROTOCOL, Category.INFORMATIONAL, "Switching Protocols"),
        PROCESSING (Code.C102_PROCESSING, Category.INFORMATIONAL,"Processing"),
        EARLY_HINTS (Code.C103_EARLY_HINTS, Category.INFORMATIONAL,"Early Hints"),

        OK (Code.C200_OK, Category.SUCCESSFUL, "OK"),
        CREATED (Code.C201_CREATED, Category.SUCCESSFUL, "Created"),
        ACCEPTED (Code.C202_ACCEPTED, Category.SUCCESSFUL, "Accepted"),
        NON_AUTHORITATIVE_INFORMATION (Code.C203_NON_AUTHORITATIVE_INFORMATION, Category.SUCCESSFUL, "Non-Authoritative Information"),
        NO_CONTENT (Code.C204_NO_CONTENT, Category.SUCCESSFUL, "No Content"),
        RESET_CONTENT (Code.C205_RESET_CONTENT, Category.SUCCESSFUL, "Reset Content"),
        PARTIAL_CONTENT (Code.C206_PARTIAL_CONTENT, Category.SUCCESSFUL, "Partial Content"),
        MULTI_STATUS (Code.C207_MULTI_STATUS, Category.SUCCESSFUL, "Multi-Status"),
        ALREADY_REPORTED (Code.C208_ALREADY_REPORTED, Category.SUCCESSFUL, "Already Reported"),
        IM_USED (Code.C226_IM_USED, Category.SUCCESSFUL, "IM Used"),

        MULTIPLE_CHOICES (Code.C300_MULTIPLE_CHOICES, Category.REDIRECTION, "Multiple Choices"),
        MOVED_PERMANENTLY (Code.C301_MOVED_PERMANENTLY, Category.REDIRECTION, "Moved Permanently"),
        MOVED_TEMPORARILY (Code.C302_MOVED_TEMPORARILY, Category.REDIRECTION, "Moved Temporary"),
        SEE_OTHER (Code.C303_SEE_OTHER, Category.REDIRECTION, "See Other"),
        NOT_MODIFIED (Code.C304_NOT_MODIFIED, Category.REDIRECTION, "Not Modified"),
        USE_PROXY (Code.C305_USE_PROXY, Category.REDIRECTION, "Use Proxy"),
        TEMPORARY_REDIRECT (Code.C307_TEMPORARY_REDIRECT, Category.REDIRECTION, "Temporary Redirect"),
        PERMANENT_REDIRECT (Code.C308_PERMANENT_REDIRECT, Category.REDIRECTION, "Permanent Redirect"),

        BAD_REQUEST (Code.C400_BAD_REQUEST, Category.CLIENT_ERROR, "Bad Request"),
        UNAUTHORIZED (Code.C401_UNAUTHORIZED, Category.CLIENT_ERROR, "Unauthorized"),
        PAYMENT_REQUIRED (Code.C402_PAYMENT_REQUIRED, Category.CLIENT_ERROR, "Payment Required"),
        FORBIDDEN (Code.C403_FORBIDDEN, Category.CLIENT_ERROR, "Forbidden"),
        NOT_FOUND (Code.C404_NOT_FOUND, Category.CLIENT_ERROR, "Not Found"),
        METHOD_NOT_ALLOWED (Code.C405_METHOD_NOT_ALLOWED, Category.CLIENT_ERROR, "Method Not Allowed"),
        NOT_ACCEPTABLE (Code.C406_NOT_ACCEPTABLE, Category.CLIENT_ERROR, "Not Acceptable"),
        PROXY_AUTHENTICATION_REQUIRED (Code.C407_PROXY_AUTHENTICATION_REQUIRED, Category.CLIENT_ERROR, "Proxy Authentication Required"),
        REQUEST_TIMEOUT (Code.C408_REQUEST_TIMEOUT, Category.CLIENT_ERROR, "Request Timeout"),
        CONFLICT (Code.C409_CONFLICT, Category.CLIENT_ERROR, "Conflict"),
        GONE (Code.C410_GONE, Category.CLIENT_ERROR, "Gone"),
        LENGTH_REQUIRED (Code.C411_LENGTH_REQUIRED, Category.CLIENT_ERROR, "Length Required"),
        PRECONDITION_FAILED (Code.C412_PRECONDITION_FAILED, Category.CLIENT_ERROR, "Precondition Failed"),
        REQUEST_TOO_LONG (Code.C413_REQUEST_TOO_LONG, Category.CLIENT_ERROR, "Request Entity Too Large"),
        REQUEST_URI_TOO_LONG (Code.C414_REQUEST_URI_TOO_LONG, Category.CLIENT_ERROR, "Request-URI Too Long"),
        UNSUPPORTED_MEDIA_TYPE (Code.C415_UNSUPPORTED_MEDIA_TYPE, Category.CLIENT_ERROR, "Unsupported Media Type"),
        REQUESTED_RANGE_NOT_SATISFIABLE (Code.C416_REQUESTED_RANGE_NOT_SATISFIABLE, Category.CLIENT_ERROR, "Requested Range Not Satisfiable"),
        EXPECTATION_FAILED (Code.C417_EXPECTATION_FAILED, Category.CLIENT_ERROR, "Expectation Failed"),
        IM_A_TEAPOT (Code.C418_IM_A_TEAPOT, Category.CLIENT_ERROR, "I’m a teapot (RFC 2324)"),
        INSUFFICIENT_SPACE_ON_RESOURCE (Code.C419_AUTHENTICATION_TIMEOUT, Category.CLIENT_ERROR, "Authentication Timeout"),
        METHOD_FAILURE (Code.C420_METHOD_FAILURE, Category.CLIENT_ERROR, "Method Failure"),
        MISDIRECTED_REQUEST (Code.C421_MISDIRECTED_REQUEST, Category.CLIENT_ERROR, "Misdirected Request"),
        UNPROCESSABLE_ENTITY (Code.C422_UNPROCESSABLE_ENTITY, Category.CLIENT_ERROR, "Unprocessable Entity"),
        LOCKED (Code.C423_LOCKED, Category.CLIENT_ERROR, "Locked"),
        FAILED_DEPENDENCY (Code.C424_FAILED_DEPENDENCY, Category.CLIENT_ERROR, "Failed Dependency"),
        C425_TOO_EARLY (Code.C425_TOO_EARLY, Category.CLIENT_ERROR, "Too Early"),
        C426_UPGRADE_REQUIRED (Code.C426_UPGRADE_REQUIRED, Category.CLIENT_ERROR, "Upgrade Required"),
        PRECONDITION_REQUIRED (Code.C428_PRECONDITION_REQUIRED, Category.CLIENT_ERROR, "Precondition Required"),
        TOO_MANY_REQUESTS (Code.C429_TOO_MANY_REQUESTS, Category.CLIENT_ERROR, "Too Many Requests"),
        REQUEST_HEADER_FIELDS_TOO_LARGE (Code.C431_REQUEST_HEADER_FIELDS_TOO_LARGE, Category.CLIENT_ERROR, "Request Header Fields Too Large"),
        NO_RESPONSE (Code.C444_NO_RESPONSE, Category.CLIENT_ERROR, "No Response"),
        RETRY (Code.C449_RETRY, Category.CLIENT_ERROR, "Retry"),
        BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS (Code.C450_BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS, Category.CLIENT_ERROR, "Blocked by Windows Parental Controls"),
        UNAVAILABLE_FOR_LEGAL_REASONS (Code.C451_UNAVAILABLE_FOR_LEGAL_REASONS, Category.CLIENT_ERROR, "Unavailable For Legal Reasons"),
        CLIENT_CLOSED_REQUEST (Code.C499_CLIENT_CLOSED_REQUEST, Category.CLIENT_ERROR, "Client Closed Request"),

        INTERNAL_SERVER_ERROR (Code.C500_INTERNAL_SERVER_ERROR, Category.SERVER_ERROR, "Internal Server Error"),
        NOT_IMPLEMENTED (Code.C501_NOT_IMPLEMENTED, Category.SERVER_ERROR, "Not Implemented"),
        BAD_GATEWAY (Code.C502_BAD_GATEWAY, Category.SERVER_ERROR, "Bad Gateway"),
        SERVICE_UNAVAILABLE (Code.C503_SERVICE_UNAVAILABLE, Category.SERVER_ERROR, "Service Unavailable"),
        GATEWAY_TIMEOUT (Code.C504_GATEWAY_TIMEOUT, Category.SERVER_ERROR, "Gateway Timeout"),
        HTTP_VERSION_NOT_SUPPORTED (Code.C505_HTTP_VERSION_NOT_SUPPORTED, Category.SERVER_ERROR, "HTTP Version Not Supported"),
        VARIANT_ALSO_NEGOTIATES (Code.C506_VARIANT_ALSO_NEGOTIATES, Category.SERVER_ERROR, "Variant Also Negotiates"),
        INSUFFICIENT_STORAGE (Code.C507_INSUFFICIENT_STORAGE, Category.SERVER_ERROR, "Insufficient Storage"),
        LOOP_DETECTED (Code.C508_LOOP_DETECTED, Category.SERVER_ERROR, "Loop Detected"),
        NOT_EXTENDED (Code.C510_NOT_EXTENDED, Category.SERVER_ERROR, "Not Extended"),
        NETWORK_AUTHENTICATION_REQUIRED (Code.C511_NETWORK_AUTHENTICATION_REQUIRED, Category.SERVER_ERROR, "Network Authentication Required"),

        REQUEST_DENIED (Code.C999_REQUEST_DENIED, Category.UNKNOWN, "Unable to Process Request/Request Denied"),
        CUSTOM_ERROR_NOT_CONNECTED (Code.CE999_CUSTOM_ERROR_NOT_CONNECTED, Category.UNKNOWN, "No Internet Connection");

        private final int code;
        private final String type;
        private final String category;

        StatusCode(int code, String category,  String type){
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

    public static String getCategory(int code) {
        if (code >= 100 && code <= 199) return Category.INFORMATIONAL;
        else if (code >= 200 && code <= 299) return Category.SUCCESSFUL;
        else if (code >= 300 && code <= 399) return Category.REDIRECTION;
        else if (code >= 400 && code <= 499) return Category.CLIENT_ERROR;
        else if (code >= 500 && code <= 599) return Category.SERVER_ERROR;
        else return Category.UNKNOWN;
    }

    public static class Category {
        public static final String INFORMATIONAL = "Informational";
        public static final String SUCCESSFUL = "Successful";
        public static final String REDIRECTION = "Redirection";
        public static final String CLIENT_ERROR = "Client Error";
        public static final String SERVER_ERROR = "Server Error";
        public static final String UNKNOWN = "Unknown";
    }

    public static class Code {
        public static final int C100_CONTINUE = 100;
        public static final int C101_SWITCHING_PROTOCOL = 101;
        public static final int C102_PROCESSING = 102;
        public static final int C103_EARLY_HINTS = 103;

        public static final int C200_OK = 200;
        public static final int C201_CREATED = 201;
        public static final int C202_ACCEPTED = 202;
        public static final int C203_NON_AUTHORITATIVE_INFORMATION = 203;
        public static final int C204_NO_CONTENT = 204;
        public static final int C205_RESET_CONTENT = 205;
        public static final int C206_PARTIAL_CONTENT = 206;
        public static final int C207_MULTI_STATUS = 207;
        public static final int C208_ALREADY_REPORTED = 208;
        public static final int C226_IM_USED = 226;

        public static final int C300_MULTIPLE_CHOICES = 300;
        public static final int C301_MOVED_PERMANENTLY = 301;
        public static final int C302_MOVED_TEMPORARILY = 302;
        public static final int C303_SEE_OTHER = 303;
        public static final int C304_NOT_MODIFIED = 304;
        public static final int C305_USE_PROXY = 305;
        public static final int C307_TEMPORARY_REDIRECT = 307;
        public static final int C308_PERMANENT_REDIRECT = 308;

        public static final int C400_BAD_REQUEST = 400;
        public static final int C401_UNAUTHORIZED = 401;
        public static final int C402_PAYMENT_REQUIRED = 402;
        public static final int C403_FORBIDDEN = 403;
        public static final int C404_NOT_FOUND = 404;
        public static final int C405_METHOD_NOT_ALLOWED = 405;
        public static final int C406_NOT_ACCEPTABLE = 406;
        public static final int C407_PROXY_AUTHENTICATION_REQUIRED = 407;
        public static final int C408_REQUEST_TIMEOUT = 408;
        public static final int C409_CONFLICT = 409;
        public static final int C410_GONE = 410;
        public static final int C411_LENGTH_REQUIRED = 411;
        public static final int C412_PRECONDITION_FAILED = 412;
        public static final int C413_REQUEST_TOO_LONG = 413;
        public static final int C414_REQUEST_URI_TOO_LONG = 414;
        public static final int C415_UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int C416_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
        public static final int C417_EXPECTATION_FAILED = 417;
        public static final int C418_IM_A_TEAPOT = 418;
        public static final int C419_AUTHENTICATION_TIMEOUT = 419;
        public static final int C420_METHOD_FAILURE = 420;
        public static final int C421_MISDIRECTED_REQUEST = 421;
        public static final int C422_UNPROCESSABLE_ENTITY = 422;
        public static final int C423_LOCKED = 423;
        public static final int C424_FAILED_DEPENDENCY = 424;
        public static final int C425_TOO_EARLY = 425;
        public static final int C426_UPGRADE_REQUIRED = 426;
        public static final int C428_PRECONDITION_REQUIRED = 428;
        public static final int C429_TOO_MANY_REQUESTS = 429;
        public static final int C431_REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
        public static final int C444_NO_RESPONSE = 444;
        public static final int C449_RETRY = 449;
        public static final int C450_BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS = 450;
        public static final int C451_UNAVAILABLE_FOR_LEGAL_REASONS = 451;
        public static final int C499_CLIENT_CLOSED_REQUEST = 499;

        public static final int C500_INTERNAL_SERVER_ERROR = 500;
        public static final int C501_NOT_IMPLEMENTED = 501;
        public static final int C502_BAD_GATEWAY = 502;
        public static final int C503_SERVICE_UNAVAILABLE = 503;
        public static final int C504_GATEWAY_TIMEOUT = 504;
        public static final int C505_HTTP_VERSION_NOT_SUPPORTED = 505;
        public static final int C506_VARIANT_ALSO_NEGOTIATES = 506;
        public static final int C507_INSUFFICIENT_STORAGE = 507;
        public static final int C508_LOOP_DETECTED = 508;
        public static final int C510_NOT_EXTENDED = 510;
        public static final int C511_NETWORK_AUTHENTICATION_REQUIRED = 511;

        public static final int C999_REQUEST_DENIED = 999;
        public static final int CE999_CUSTOM_ERROR_NOT_CONNECTED = -999;
    }

    public static class ResponseData {
        private int code;
        private String statusType;
        private String category;
        private String statusMessage;
        private String timestamp;
        private Object data;

        public ResponseData() {
        }

        public ResponseData(int code, String statusType, String statusMessage) {
            this.code = code;
            this.statusType = statusType;
            this.statusMessage = statusMessage;
        }

        public ResponseData(StatusCode statusData, String statusMessage) {
            this.code = statusData.getCode();
            this.statusType = statusData.getType();
            this.category = statusData.getCategory();
            this.statusMessage = (statusMessage != null? statusMessage : statusData.getType());
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatusType() {
            return statusType;
        }

        public void setStatusType(String statusType) {
            this.statusType = statusType;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        public void setStatusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}