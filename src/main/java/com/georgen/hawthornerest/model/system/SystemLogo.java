package com.georgen.hawthornerest.model.system;

import com.georgen.hawthorne.api.annotations.BinaryData;
import com.georgen.hawthorne.api.annotations.SingletonEntity;

@SingletonEntity
public class SystemLogo {
    @BinaryData
    private byte[] binaryData;
}
