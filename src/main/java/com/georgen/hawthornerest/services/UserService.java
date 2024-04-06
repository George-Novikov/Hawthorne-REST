package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthornerest.model.UserException;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.model.users.UserIndex;
import com.georgen.hawthornerest.tools.UserIndexComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final UserIndexComparator USER_INDEX_COMPARATOR = new UserIndexComparator();
    private List<UserIndex> userIndexes;

    @Value("${admin-}")
    private String adminLogin;
    private String adminPassword;

    public User save(User user) throws Exception {
        ensureIndexesLoading();

        if (user.isNew() && isLoginTaken(user.getLogin())) throw new UserException("User with this login already exists.");

        User savedUser = Repository.save(user);
        addToIndex(savedUser);

        return savedUser;
    }

    public User get(Integer id) throws Exception {
        return Repository.get(User.class, id);
    }

    public User getByLogin(String login) throws Exception {
        ensureIndexesLoading();

        User user = null;

        for (UserIndex userIndex : userIndexes){
            Integer userID = userIndex.getIndex().get(login);
            if (userID != null) user = Repository.get(User.class, userID);
        }

        return user;
    }

    public Optional<User> getOptionalByLogin(String login){
        try {
            User user = getByLogin(login);
            return Optional.of(user);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    public boolean delete(Integer id) throws Exception {
        return Repository.delete(User.class, id);
    }

    public long count() throws Exception {
        return Repository.count(User.class);
    }

    public List<User> list(int limit, int offset) throws Exception {
        return Repository.list(User.class, limit, offset);
    }

    private boolean isLoginTaken(String login) throws Exception {
        return getByLogin(login) != null;
    }

    private void addToIndex(User user) throws Exception {
        if (user.isNew()) return;
        UserIndex userIndex = getLatestUserIndex();
        userIndex.add(user);
        Repository.save(userIndex);
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
