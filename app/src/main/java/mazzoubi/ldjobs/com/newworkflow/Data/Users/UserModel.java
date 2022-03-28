package mazzoubi.ldjobs.com.newworkflow.Data.Users;

public class UserModel {
    private String name = "" ;
    private String id = "" ;
    private String phone = "" ;
    private String username = "" ;
    private String password = "" ;
    private String debt = "0" ;
    private String token = "" ;
    private String AID = "" ;

    public UserModel() { }

    public UserModel(String name, String id, String phone, String username
            , String password, String debt, String token, String AID) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.debt = debt;
        this.token = token;
        this.AID = AID;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
