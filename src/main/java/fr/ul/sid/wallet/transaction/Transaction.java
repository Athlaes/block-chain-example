package fr.ul.sid.wallet.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.ul.sid.serialization.PublicKeyDeserializer;
import fr.ul.sid.serialization.PublicKeySerializer;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    @JsonIgnore
    public String transactionId;
    @JsonDeserialize(using = PublicKeyDeserializer.class)
    @JsonSerialize(using = PublicKeySerializer.class)
    public PublicKey sender;
    @JsonDeserialize(using = PublicKeyDeserializer.class)
    @JsonSerialize(using = PublicKeySerializer.class)
    public PublicKey reveiver;
    public byte[] signature;
    public TransactionInput input;
    public List<TransactionOutput> outputs = new ArrayList<>();

    public Transaction () {}

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

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public TransactionInput getInput() {
        return input;
    }

    public void addOutput(TransactionOutput to) {
        this.outputs.add(to);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getReveiver() {
        return reveiver;
    }


}
