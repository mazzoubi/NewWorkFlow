package mazzoubi.ldjobs.com.newworkflow.Util;

public class ClassAPIs {

    private static final String baseUrl="*************************************";

    //CRM_API
    public static final String OpenDistPoint = baseUrl+"/OpenDistPos";

    //Users
    public static final String InsertUser = baseUrl+"/InsertUsers";
    public static final String GetUsers = baseUrl+"/GetUsers";
    /*GetUser cases: 1 getAllUser    2 login      3 getUserByUsername */
    public static final String UpdateUsers = baseUrl+"/UpdateUsers";
    /*UpdateClients cases: 1- updateAllInfo    2-  3-  4-  5-  .........etc*/

    // Clients
    public static final String InsertClients = baseUrl+"/InsertClients";
    public static final String GetClients = baseUrl+"/GetClients";
    /*GetClients cases: 1- getAllClients   2- getClientByClientName    3- getClientByClientId */
    public static final String UpdateClients = baseUrl+"/UpdateClients";
    /*UpdateClients cases: 1- updateAllInfo    2-  3-  4-  5-  .........etc*/

    // Invoices
    public static final String InsertInvoices = baseUrl+"/InsertInvoices";
    public static final String UpdateInvoices = baseUrl+"/UpdateInvoices";
    public static final String GetInvoices = baseUrl+"/GetInvoices";
    public static final String GetPayments = baseUrl+"/GetPayments";
    public static final String UpdatePayments = baseUrl+"/UpdatePayments";
    public static final String InsertPayments = baseUrl+"/InsertPayments";
    public static final String GetInvoicesLog = baseUrl+"/GetInvoicesLog";
    public static final String GetChargeInvoice = baseUrl+"/GetChargeInvoice";


    ////////////////////////
    public static final String GetInvType = "";

/////////////////////////////
    // userExchangeCommissions

    public static final String InsertCommissionTransfer = baseUrl+"/InsertCommissionTransfer";
    public static final String UpdateCommissionTransfer = baseUrl+"/UpdateCommissionTransfer";
    public static final String GetCommissionTransfer = baseUrl+"/GetCommissionTransfer";

    ////////////////////////
    // banks
    public static final String InsertBanks = baseUrl+"/InsertBanks";
    public static final String GetBanks = baseUrl+"/GetBanks";


    // permissions
    public static final String GetPermessions = baseUrl+"/GetPermessions";
    public static final String UpdatePermessions = baseUrl+"/UpdatePermessions";


}
