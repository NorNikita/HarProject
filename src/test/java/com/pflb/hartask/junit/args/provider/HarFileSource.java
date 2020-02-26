package com.pflb.hartask.junit.args.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.hartask.model.entity.HarFile;
import com.pflb.hartask.model.harmodel.Har;

import java.io.IOException;

public class HarFileSource {

    public static HarFile getHarFile(Har har) {
        ObjectMapper mapper = new ObjectMapper();
        HarFile harFile = null;

        try {
            harFile = new HarFile(har.getLog().getBrowser() == null ? null : har.getLog().getBrowser().getName(), har.getLog().getVersion(), mapper.writeValueAsString(har));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return harFile;
    }

}
