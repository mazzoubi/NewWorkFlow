package mazzoubi.ldjobs.com.newworkflow.Data.Banks;

public class BanksModel {
    private String name ;
    private String id ;
    private String commission="0" ;

    public BanksModel() {}

    public BanksModel(String name, String id, String commission) {
        this.name = name;
        this.id = id;
        this.commission = commission;
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

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
