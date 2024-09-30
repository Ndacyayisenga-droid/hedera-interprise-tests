package com.openelements.hedera.base.protocol;

import com.openelements.hedera.base.HederaException;
import org.jspecify.annotations.NonNull;

/**
 * Interface for interacting with the Hedera network at the protocol level.
 */
public interface ProtocolLayerClient {

    /**
     * Execute an account balance query.
     *
     * @param request the request
     * @return the response
     * @throws HederaException if the query could not be executed
     */
    @NonNull
    AccountBalanceResponse executeAccountBalanceQuery(@NonNull AccountBalanceRequest request) throws HederaException;

    /**
     * Execute a file contents query.
     *
     * @param request the request
     * @return the response
     * @throws HederaException if the query could not be executed
     */
    @NonNull
    FileContentsResponse executeFileContentsQuery(@NonNull FileContentsRequest request) throws HederaException;

    /**
     * Execute a file append transaction.
     *
     * @param request the request
     * @return the result
     * @throws HederaException if the transaction could not be executed
     */
    @NonNull
    FileAppendResult executeFileAppendRequestTransaction(@NonNull FileAppendRequest request) throws HederaException;

    /**
     * Execute a file delete transaction.
     *
     * @param request the request
     * @return the result
     * @throws HederaException if the transaction could not be executed
     */
    @NonNull
    FileDeleteResult executeFileDeleteTransaction(@NonNull FileDeleteRequest request) throws HederaException;

    /**
     * Execute a file create transaction.
     *
     * @param request the request
     * @return the result
     * @throws HederaException if the transaction could not be executed
     */
    @NonNull
    FileCreateResult executeFileCreateTransaction(@NonNull FileCreateRequest request) throws HederaException;

    FileUpdateResult executeFileUpdateRequestTransaction(FileUpdateRequest request) throws HederaException;

    @NonNull
    FileInfoResponse executeFileInfoQuery(@NonNull FileInfoRequest request) throws HederaException;

    /**
     * Execute a contract create transaction.
     *
     * @param request the request
     * @return the result
     * @throws HederaException if the transaction could not be executed
     */
    @NonNull
    ContractCreateResult executeContractCreateTransaction(@NonNull ContractCreateRequest request)
            throws HederaException;

    /**
     * Execute a contract call transaction.
     *
     * @param request the request
     * @return the result
     * @throws HederaException if the transaction could not be executed
     */
    @NonNull
    ContractCallResult executeContractCallTransaction(@NonNull ContractCallRequest request) throws HederaException;

    @NonNull
    ContractDeleteResult executeContractDeleteTransaction(@NonNull final ContractDeleteRequest request)
            throws HederaException;

    @NonNull
    AccountCreateResult executeAccountCreateTransaction(@NonNull final AccountCreateRequest request)
            throws HederaException;

    @NonNull
    AccountDeleteResult executeAccountDeleteTransaction(@NonNull AccountDeleteRequest request) throws HederaException;

    @NonNull
    TokenCreateResult executeTokenCreateTransaction(@NonNull final TokenCreateRequest request) throws HederaException;

    @NonNull
    TokenAssociateResult executeTokenAssociateTransaction(@NonNull final TokenAssociateRequest request)
            throws HederaException;

    @NonNull
    TokenMintResult executeMintTokenTransaction(@NonNull final TokenMintRequest request) throws HederaException;

    @NonNull
    TokenBurnResult executeBurnTokenTransaction(@NonNull final TokenBurnRequest request) throws HederaException;

    @NonNull
    TokenTransferResult executeTransferTransactionForNft(@NonNull final TokenTransferRequest request)
            throws HederaException;

    @NonNull
    Runnable addTransactionListener(@NonNull TransactionListener listener);
}