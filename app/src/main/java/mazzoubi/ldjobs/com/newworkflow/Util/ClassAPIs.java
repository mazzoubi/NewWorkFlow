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
    public static final String GetInvoices = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetInvoices";
    public static final String GetPayments = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/GetPayments";
    public static final String InsertPayments = "http://51.89.167.62/EzPayServices/WorkFlowServices.svc/InsertPayments";


    ////////////////////////
    public static final String GetInvType = "";
}
