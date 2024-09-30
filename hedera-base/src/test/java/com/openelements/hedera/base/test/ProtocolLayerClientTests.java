package com.openelements.hedera.base.test;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.openelements.hedera.base.Account;
import com.openelements.hedera.base.implementation.ProtocolLayerClientImpl;
import com.openelements.hedera.base.protocol.ProtocolLayerClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProtocolLayerClientTests {

    @Test
    void testNullConstructorParam() {
        //then
        final Client client = Client.forTestnet();
        final Account account = new Account(AccountId.fromString("0.0.12345"), PrivateKey.generateED25519().getPublicKey(), PrivateKey.generateED25519());
        Assertions.assertThrows(NullPointerException.class, () -> new ProtocolLayerClientImpl(client, null));
        Assertions.assertThrows(NullPointerException.class, () -> new ProtocolLayerClientImpl(null, account));
        Assertions.assertThrows(NullPointerException.class, () -> new ProtocolLayerClientImpl(null, null));
    }

    @Test
    void testNullParams() {
        //given
        final Account account = new Account(AccountId.fromString("0.0.12345"), PrivateKey.generateED25519().getPublicKey(), PrivateKey.generateED25519());
        final ProtocolLayerClient client = new ProtocolLayerClientImpl(Client.forTestnet(), account);

        //then
        Assertions.assertThrows(NullPointerException.class, () -> client.executeAccountBalanceQuery(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileContentsQuery(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileAppendRequestTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileDeleteTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileCreateTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileUpdateRequestTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeContractCreateTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeContractCallTransaction(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeFileInfoQuery(null));
        Assertions.assertThrows(NullPointerException.class, () -> client.executeAccountDeleteTransaction(null));
    }
}
