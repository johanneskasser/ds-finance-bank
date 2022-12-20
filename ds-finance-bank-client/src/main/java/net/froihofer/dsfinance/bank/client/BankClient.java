package net.froihofer.dsfinance.bank.client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import at.ac.csdc23vz_02.common.BankServer;
import net.froihofer.dsfinance.bank.client.utils.UserInterface;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
  private static Logger log = LoggerFactory.getLogger(BankClient.class);
  private static BankServer bankServer;

  /**
   * Skeleton method for performing an RMI lookup
   */
  private void getRmiProxy() {
    AuthCallbackHandler.setUsername("customer");
    AuthCallbackHandler.setPassword("customerpass");
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
      bankServer = jndiHelper.lookup("BankServer", BankServer.class);
    }
    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
  }

  private void run() {
    //TODO implement the client part
    UserInterface userInterface = new UserInterface(bankServer);
    userInterface.init();
  }

  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.getRmiProxy();
    client.run();
  }
}
