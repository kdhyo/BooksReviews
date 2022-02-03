package godofjava2nd.Chapter29.dto;

public class RequestDTO {

    private String requestMethod = "GET";
    private String uri = "/";
    private String httpVersion = "HTTP/1.1";

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private String requestMethod;
        private String uri;
        private String httpVersion;

        public Builder() {
        }

        public Builder requestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public RequestDTO build() {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.requestMethod = this.requestMethod;
            requestDTO.uri = this.uri;
            requestDTO.httpVersion = this.httpVersion;
            return requestDTO;
        }

    }

}
