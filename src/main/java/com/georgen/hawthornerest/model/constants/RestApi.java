package com.georgen.hawthornerest.model.constants;

public enum RestApi {
    V1("/api/v1"),

    DOCUMENT("/document"),
    FILE("/file"),
    USER("/user"),
    SETTINGS("/settings"),
    AUTH("/auth");

    private String path;

    RestApi(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    public static String getAuthPath(){
        String basePath = getGroupPath(AUTH);
        return String.format("%s%s", basePath, "/**");
    }

    public static String getGroupPath(RestApi group){
        return String.format("%s%s",V1.getPath(), group.getPath());
    }

    public static String[] getReadingPathsArray(RestApi group){
        String basePath = getGroupPath(group);

        String[] getPaths = new String[3];
        getPaths[0] = basePath;
        getPaths[1] = String.format("%s%s", basePath, "/list");
        getPaths[2] = String.format("%s%s", basePath, "/count");

        return getPaths;
    }
}
