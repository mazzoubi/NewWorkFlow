package mazzoubi.ldjobs.com.newworkflow.Util;

public class ClassAPIs {
    //Users
    public static final String InsertUser = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertUsers";
    public static final String GetUsers = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetUsers";
    /*GetUser cases: 1 getAllUser    2 login      3 getUserByUsername */
    public static final String UpdateUsers = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdateUsers";
    /*UpdateClients cases: 1- updateAllInfo    2-  3-  4-  5-  .........etc*/

    // Clients
    public static final String InsertClients = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertClients";
    public static final String GetClients = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetClients";
    /*GetClients cases: 1- getAllClients   2- getClientByClientName    3- getClientByClientId */
    public static final String UpdateClients = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdateClients";
    /*UpdateClients cases: 1- updateAllInfo    2-  3-  4-  5-  .........etc*/

    // Invoices
    public static final String InsertInvoices = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertInvoices";
    public static final String UpdateInvoices = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdateInvoices";
    public static final String GetInvoices = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetInvoices";
    public static final String GetPayments = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetPayments";
    public static final String UpdatePayments = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdatePayments";
    public static final String InsertPayments = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertPayments";
    public static final String GetInvoicesLog = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetInvoicesLog";
    public static final String GetChargeInvoice = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetChargeInvoice";


    ////////////////////////
    public static final String GetInvType = "";

/////////////////////////////
    // userExchangeCommissions

    public static final String InsertCommissionTransfer = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertCommissionTransfer";
    public static final String UpdateCommissionTransfer = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdateCommissionTransfer";
    public static final String GetCommissionTransfer = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetCommissionTransfer";

    ////////////////////////
    // banks
    public static final String InsertBanks = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertBanks";
    public static final String GetBanks = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetBanks";


    // permissions
    public static final String GetPermessions = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetPermessions";
    public static final String UpdatePermessions = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/UpdatePermessions";


}
