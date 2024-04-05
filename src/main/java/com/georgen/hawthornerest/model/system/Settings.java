package com.georgen.hawthornerest.model.system;

import com.georgen.hawthorne.api.annotations.SingletonEntity;

@SingletonEntity
public class Settings {
    private boolean isAuthRequired;
    private long filesCountLimit;
    private long documentCountLimit;
    private long usersCountLimit;
    private int userIndexLengthLimit = 1000;
    private int latestUserIndex;
}
