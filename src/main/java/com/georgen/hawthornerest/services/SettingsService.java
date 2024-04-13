package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthorne.model.exceptions.HawthorneException;
import com.georgen.hawthornerest.model.settings.Settings;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    public Settings save(Settings settings) throws HawthorneException {
        return Repository.save(settings);
    }

    public Settings get() throws Exception {
        Settings settings = Repository.get(Settings.class);
        if (settings == null) settings = Repository.save(new Settings());
        return settings;
    }
}
