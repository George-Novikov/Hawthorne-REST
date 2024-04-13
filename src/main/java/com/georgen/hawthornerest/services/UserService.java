package com.georgen.hawthornerest.services;

import com.georgen.hawthorne.api.Repository;
import com.georgen.hawthornerest.model.exceptions.UserException;
import com.georgen.hawthornerest.model.users.ActivationResult;
import com.georgen.hawthornerest.model.users.User;
import com.georgen.hawthornerest.model.users.UserIndex;
import com.georgen.hawthornerest.model.users.UsersToActivate;
import com.georgen.hawthornerest.tools.UserIndexComparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final UserIndexComparator USER_INDEX_COMPARATOR = new UserIndexComparator();
    /**
     * Since Hawthorne entities currently have only one unique identifier field, marked as @Id,
     * an index must be created so that he object can be found by any other custom unique field
     */
    private List<UserIndex> userIndexes;
    private UsersToActivate activationList;
    @Value("${admin-user.login}")
    private String adminLogin;

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
        List<User> users = Repository.list(User.class, limit, offset);
        users = users.stream().filter(user -> !adminLogin.equals(user.getLogin())).collect(Collectors.toList());
        return users;
    }

    public boolean isLoginTaken(String login) throws Exception {
        return getByLogin(login) != null;
    }

    public User activate(Integer id) throws Exception {
        User user = get(id);
        if (user == null) throw new UserException(String.format("User with id %d not found.", id));

        user.setBlocked(false);
        user.setExpired(false);

        user = save(user);
        removeFromActivationList(user);

        return user;
    }

    public ActivationResult activateList(UsersToActivate activationList) throws Exception {
        ActivationResult result = new ActivationResult();

        for (Integer id : activationList.getUserIDs()){
            User user = activate(id);
            if (user != null){
                result.addToActivated(id);
            } else {
                result.addToNotFound(id);
            }
        }

        return result;
    }

    public UsersToActivate getActivationList() throws Exception {
        if (this.activationList == null){
            this.activationList = Repository.get(UsersToActivate.class);
        }

        if (this.activationList == null){
            this.activationList = new UsersToActivate();
            Repository.save(activationList);
        }

        return this.activationList;
    }

    public void addToActivationList(User user) throws Exception {
        if (user == null || user.isNew() || !user.isBlocked()) return;
        this.activationList = getActivationList();
        this.activationList.add(user);
        Repository.save(this.activationList);
    }

    public void removeFromActivationList(User user) throws Exception {
        if (user == null || user.isNew()) return;
        this.activationList = getActivationList();
        this.activationList.remove(user);
        Repository.save(this.activationList);
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
