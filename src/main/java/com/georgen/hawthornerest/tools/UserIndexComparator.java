package com.georgen.hawthornerest.tools;

import com.georgen.hawthornerest.model.users.UserIndex;

import java.util.Comparator;

public class UserIndexComparator implements Comparator<UserIndex> {
    @Override
    public int compare(UserIndex userIndex1, UserIndex userIndex2) {
        int indexId1 = userIndex1.getId();
        int indexId2 = userIndex2.getId();

        return Integer.compare(indexId1, indexId2);
    }
}
