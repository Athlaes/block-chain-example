package fr.ul.sid.wallet.transaction;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public String transactionId;
    public PublicKey sender;
    public PublicKey reveiver;
    public byte[] signature;
    public TransactionInput input;
    public List<TransactionOutput> outputs = new ArrayList<>();

    public Transaction(PublicKey from, PublicKey to, TransactionInput input) {
        this.sender = from;
        this.reveiver = to;
        this.input = input;
    }

    public boolean processTransaction() {
        return true;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }
    public void addTransactionOutput(TransactionOutput output){
        this.outputs.add(output);
    }
}
