package io.github.battorydev.springboot.demo.util.converter;

import io.github.battorydev.springboot.demo.model.JobJsonObject;

public interface Converter {

    boolean accept(JobJsonObject val);

    /**
     * Convert currency value
     * @param val JobJsonObject
     * @return updated JobJsonObject. Null if unable to convert currency value
     */
    JobJsonObject convert(JobJsonObject val);
}
