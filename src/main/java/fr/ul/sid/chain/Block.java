package fr.ul.sid.chain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.ul.sid.App;
import fr.ul.sid.serialization.PublicKeyDeserializer;
import fr.ul.sid.serialization.PublicKeySerializer;
import fr.ul.sid.wallet.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Block {
    private static final Logger logger = Logger.getLogger(Block.class.getName());
    @JsonIgnore
    private String hash;
    private String previousHash;
    private List<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;

    public Block() {
        this.timeStamp = new Date().getTime();
    }

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }
}

