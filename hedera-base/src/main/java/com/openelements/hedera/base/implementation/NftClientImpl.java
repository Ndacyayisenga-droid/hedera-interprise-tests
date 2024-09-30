package com.openelements.hedera.base.implementation;

import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TokenId;
import com.hedera.hashgraph.sdk.TokenType;
import com.openelements.hedera.base.Account;
import com.openelements.hedera.base.HederaException;
import com.openelements.hedera.base.NftClient;
import com.openelements.hedera.base.protocol.ProtocolLayerClient;
import com.openelements.hedera.base.protocol.TokenAssociateRequest;
import com.openelements.hedera.base.protocol.TokenBurnRequest;
import com.openelements.hedera.base.protocol.TokenCreateRequest;
import com.openelements.hedera.base.protocol.TokenCreateResult;
import com.openelements.hedera.base.protocol.TokenMintRequest;
import com.openelements.hedera.base.protocol.TokenMintResult;
import com.openelements.hedera.base.protocol.TokenTransferRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jspecify.annotations.NonNull;

public class NftClientImpl implements NftClient {

    private final ProtocolLayerClient client;

    private final Account operationalAccount;

    public NftClientImpl(@NonNull final ProtocolLayerClient client, @NonNull final Account operationalAccount) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.operationalAccount = Objects.requireNonNull(operationalAccount, "operationalAccount must not be null");
    }

    @Override
    public TokenId createNftType(@NonNull final String name, @NonNull final String symbol) throws HederaException {
        return createNftType(name, symbol, operationalAccount);
    }


    @Override
    public TokenId createNftType(@NonNull final String name, @NonNull final String symbol,
            @NonNull final PrivateKey supplierKey) throws HederaException {
        return createNftType(name, symbol, operationalAccount, supplierKey);
    }

    @Override
    public TokenId createNftType(@NonNull final String name, @NonNull final String symbol,
            @NonNull final AccountId treasuryAccountId, @NonNull final PrivateKey treasuryKey) throws HederaException {
        return createNftType(name, symbol, treasuryAccountId, treasuryKey, operationalAccount.privateKey());
    }

    @Override
    public TokenId createNftType(@NonNull final String name, @NonNull final String symbol,
            @NonNull final AccountId treasuryAccountId, @NonNull final PrivateKey treasuryKey,
            @NonNull final PrivateKey supplierKey) throws HederaException {
        final TokenCreateRequest request = TokenCreateRequest.of(name, symbol, treasuryAccountId, treasuryKey,
                TokenType.NON_FUNGIBLE_UNIQUE, supplierKey);
        final TokenCreateResult tokenCreateResult = client.executeTokenCreateTransaction(request);
        return tokenCreateResult.tokenId();
    }

    @Override
    public void associateNft(@NonNull final TokenId tokenId, @NonNull final AccountId accountId,
            @NonNull final PrivateKey accountKey) throws HederaException {
        final TokenAssociateRequest request = TokenAssociateRequest.of(tokenId, accountId, accountKey);
        client.executeTokenAssociateTransaction(request);
    }

    @Override
    public long mintNft(@NonNull TokenId tokenId, @NonNull byte[] metadata) throws HederaException {
        return mintNft(tokenId, operationalAccount.privateKey(), metadata);
    }

    @Override
    public long mintNft(@NonNull TokenId tokenId, @NonNull PrivateKey supplyKey, @NonNull byte[] metadata)
            throws HederaException {
        return mintNfts(tokenId, supplyKey, metadata).getFirst();
    }

    @Override
    public @NonNull List<Long> mintNfts(@NonNull TokenId tokenId, @NonNull byte[]... metadata) throws HederaException {
        return mintNfts(tokenId, operationalAccount.privateKey(), metadata);
    }

    @Override
    public @NonNull List<Long> mintNfts(@NonNull TokenId tokenId, @NonNull PrivateKey supplyKey,
            @NonNull byte[]... metadata) throws HederaException {
        final TokenMintRequest request = TokenMintRequest.of(tokenId, supplyKey, metadata);
        final TokenMintResult result = client.executeMintTokenTransaction(request);
        return Collections.unmodifiableList(result.serials());
    }

    @Override
    public void burnNfts(@NonNull TokenId tokenId, @NonNull Set<Long> serialNumbers) throws HederaException {
        burnNfts(tokenId, serialNumbers, operationalAccount.privateKey());
    }

    @Override
    public void burnNfts(@NonNull TokenId tokenId, @NonNull Set<Long> serialNumbers, @NonNull PrivateKey supplyKey)
            throws HederaException {
        final TokenBurnRequest request = TokenBurnRequest.of(tokenId, serialNumbers, supplyKey);
        client.executeBurnTokenTransaction(request);
    }

    @Override
    public void transferNft(@NonNull final TokenId tokenId, final long serialNumber,
            @NonNull final AccountId fromAccountId, @NonNull final PrivateKey fromAccountKey,
            @NonNull final AccountId toAccountId) throws HederaException {
        transferNfts(tokenId, List.of(serialNumber), fromAccountId, fromAccountKey, toAccountId);
    }

    @Override
    public void transferNfts(@NonNull final TokenId tokenId, @NonNull final List<Long> serialNumber,
            @NonNull final AccountId fromAccountId,
            @NonNull final PrivateKey fromAccountKey, @NonNull final AccountId toAccountId) throws HederaException {
        final TokenTransferRequest request = TokenTransferRequest.of(tokenId, serialNumber, fromAccountId, toAccountId,
                fromAccountKey);
        client.executeTransferTransactionForNft(request);
    }


}
