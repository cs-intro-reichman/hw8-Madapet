/**
 * Represents a social network. The network has users, who follow other uesrs.
 * Each user is an instance of the User class.
 */
public class Network {

    // Fields
    private User[] users; // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users. The only purpose of this constructor is
     * to allow testing the toString and getUser methods, before implementing other
     * methods.
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /**
     * Finds in this network, and returns, the user that has the given name.
     * If there is no such user, returns null.
     * Notice that the method receives a String, and returns a User object.
     */
    public User getUser(String name) {
        name = name.toUpperCase().substring(0, 1) + name.substring(1, name.length());
        if (this.userCount != 0) {
            for (int i = 0; i < this.userCount; i++) {
                if (this.users[i].getName().equals(name)) {
                    return this.users[i];
                }
            }
        }
        return null;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /**
     * Adds a new user with the given name to this network.
     * If ths network is full, does nothing and returns false;
     * If the given name is already a user in this network, does nothing and returns
     * false;
     * Otherwise, creates a new user with the given name, adds the user to this
     * network, and returns true.
     */
    public boolean addUser(String name) {
        if (this.getUser(name) != null || this.userCount == this.users.length || name == null) {
            return false;
        } else {
            User new_user = new User(name);
            this.users[this.userCount] = new_user;
            this.userCount++;

            return true;
        }

    }

    /**
     * Makes the user with name1 follow the user with name2. If successful, returns
     * true.
     * If any of the two names is not a user in this network,
     * or if the "follows" addition failed for some reason, returns false.
     */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null) {
            return false;
        }
        if (this.getUser(name1) == null || this.getUser(name2) == null ||
                name1 == name2) {
            return false;}
            if (this.getUser(name1).follows(name2)) {
                return false;

            }
        else {
            this.getUser(name1).addFollowee(name2);
            return true;
        }

    }

    /**
     * For the user with the given name, recommends another user to follow. The
     * recommended user is
     * the user that has the maximal mutual number of followees as the user with the
     * given name.
     */
    public String recommendWhoToFollow(String name) {
        int max = 0;
        String reName = "";
        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i] == getUser(name) || getUser(name).follows(name)) {
                continue;
            } else {
                if (max < getUser(name).countMutual(this.users[i])) {
                    max = getUser(name).countMutual(this.users[i]);
                    reName = this.users[i].getName();
                }
            }
        }

        //// Replace the following statement with your code
        return reName;

    }

    /**
     * Computes and returns the name of the most popular user in this network:
     * The user who appears the most in the follow lists of all the users.
     */
    public String mostPopularUser() {
        String mostPopular = null;
        int maxApp = 0;
        for (int i = 0; i < this.userCount; i++) {
            if (maxApp < this.followeeCount(this.users[i].getName())) {
                maxApp = this.followeeCount(this.users[i].getName());
                mostPopular = this.users[i].getName();
            }
        }
        //// Replace the following statement with your code
        return mostPopular;
    }

    /**
     * Returns the number of times that the given name appears in the follows lists
     * of all
     * the users in this network. Note: A name can appear 0 or 1 times in each list.
     */
    private int followeeCount(String name) {
        int numberOfTimes = 0;
        for (int i = 0; i < this.userCount; i++) {
            numberOfTimes = (this.users[i].follows(name)) ? numberOfTimes + 1 : numberOfTimes;
        }
        return numberOfTimes;
    }

    // Returns a textual description of all the users in this network, and who they
    // follow.
    public String toString() {
        String ans = "Network:";
        for (int i = 0; i < this.userCount; i++) {
            ans += "\n" + this.users[i].toString();
        }
        return ans;
    }
}
