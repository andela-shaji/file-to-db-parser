package checkpoint.andela.Constants;

/**
 * Created by suadahaji.
 */
public enum Constants {
    DRIVER("com.mysql.jdbc.Driver"),
    DBURL("jdbc:mysql://localhost:3306/"),
    USER("root"),
    PASS("password"),
    DBNAME("reactiondb"),
    DBTABLE("reactions"),
    FILEPATH("lib/reactions.dat"),
    LOGPATH("lib/log.txt");

    private final String constant;

    private Constants(String c) {
        constant = c;
    }

    @Override
    public String toString() {
        return this.constant;
    }
}
