package com.openelements.hedera.base.test;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.openelements.hedera.base.Account;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HederaTestContext {

    private final static Logger log = LoggerFactory.getLogger(HederaTestContext.class);

    private final Account operationalAccount;

    private final Client client;

    public HederaTestContext() {
        final String hederaAccountIdByEnv = System.getenv("HEDERA_ACCOUNT_ID");
        final String hederaPrivateKeyByEnv = System.getenv("HEDERA_PRIVATE_KEY");
        final String hederaNetwork = System.getenv("HEDERA_NETWORK");

        if(hederaAccountIdByEnv != null && hederaPrivateKeyByEnv != null) {
            final AccountId accountId = AccountId.fromString(hederaAccountIdByEnv);
            final PrivateKey privateKey = PrivateKey.fromString(hederaPrivateKeyByEnv);
            final PublicKey publicKey = privateKey.getPublicKey();
            operationalAccount = new Account(accountId, publicKey, privateKey);
            if(Objects.equals(hederaNetwork, "testnet")) {
                client = Client.forTestnet();
                client.setOperator(accountId, privateKey);
            } else {
                //SOLO
                final Map<String, AccountId> nodes = new HashMap<>();
                nodes.put("127.0.0.1:50211", AccountId.fromString("0.0.3"));
                client = Client.forNetwork(nodes);
                try {
                    client.setMirrorNetwork(List.of("localhost:8080"));
                } catch (Exception e) {
                    log.error("Error setting mirror network", e);
                }
                client.setOperator(accountId, privateKey);
            }
        } else {
            final Dotenv dotenv = Dotenv.load();
            final String accountIdAsString = dotenv.get("hedera.accountId");
            final String privateKeyAsString = dotenv.get("hedera.privateKey");
            final AccountId accountId = AccountId.fromString(accountIdAsString);
            final PrivateKey privateKey = PrivateKey.fromString(privateKeyAsString);
            final PublicKey publicKey = privateKey.getPublicKey();
            operationalAccount = new Account(accountId, publicKey, privateKey);
            client = Client.forTestnet();
            client.setOperator(accountId, privateKey);
        }
    }

    public Account getOperationalAccount() {
        return operationalAccount;
    }

    public Client getClient() {
        return client;
    }
}
