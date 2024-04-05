package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthornerest.model.UserException;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.model.users.UserIndex;
import com.georgen.hawthornerest.tools.UserIndexComparator;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final UserIndexComparator USER_INDEX_COMPARATOR = new UserIndexComparator();
    private List<UserIndex> userIndexes;

    public User save(User user) throws Exception {
        ensureIndexesLoading();

        if (user.isNew() && isLoginTaken(user.getLogin())) throw new UserException("User with this login already exists.");

        User savedUser = Repository.save(user);
        addToIndex(savedUser);

        return savedUser;
    }

    public void addToIndex(User user) throws Exception {
        if (user.isNew()) return;
        UserIndex userIndex = getLatestUserIndex();
        userIndex.add(user);
    }

    public boolean isLoginTaken(String login) throws Exception {
        return findUserByLogin(login) != null;
    }

    public User findUserByLogin(String login) throws Exception {
        ensureIndexesLoading();

        User user = null;

        for (UserIndex userIndex : userIndexes){
            Integer userID = userIndex.getIndex().get(login);
            if (userID != null) user = Repository.get(User.class, userID);
        }

        return user;
    }

    private UserIndex getLatestUserIndex() throws Exception {
        ensureIndexesLoading();
        UserIndex userIndex = this.userIndexes.stream().max(USER_INDEX_COMPARATOR).get();

        if (userIndex.isOverwhelmed()){
            UserIndex newUserIndex = Repository.save(new UserIndex());
            this.userIndexes.add(newUserIndex);
            return newUserIndex;
        }

        return userIndex;
    }

    private void ensureIndexesLoading() throws Exception {
        if (this.userIndexes == null) userIndexes = getAllUserIndices();
    }

    public List<UserIndex> getAllUserIndices() throws Exception {
        long count = Repository.count(UserIndex.class);
        if (count == 0){
            UserIndex userIndex = Repository.save(new UserIndex());
            return new ArrayList<UserIndex>() {{ add(userIndex); }};
        }
        return Repository.list(UserIndex.class, Math.toIntExact(count), 0);
    }
}
