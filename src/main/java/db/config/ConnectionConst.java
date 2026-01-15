package db.config;

public abstract class ConnectionConst {

    private ConnectionConst(){
    }

    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
