package com.pflb.hartask.junit.args.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.hartask.model.entity.InnerModelData;
import com.pflb.hartask.model.innermodel.TestProfile;

import java.io.IOException;

public class InnerModelDataSource {

    public static InnerModelData getInnerModelData(TestProfile testProfile) {
        ObjectMapper mapper = new ObjectMapper();
        InnerModelData innerModelData = null;

        try {
            innerModelData = new InnerModelData((long) testProfile.getRequests().size(), mapper.writeValueAsString(testProfile));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return innerModelData;
    }
}
