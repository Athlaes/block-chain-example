package fr.ul.sid.chain;

import fr.ul.sid.utils.StringUtils;
import fr.ul.sid.wallet.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    private String hash;
    private String previousHash;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private long timeStamp;
    private int nonce;

    // Constructeur du Bloc
    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); // Assurez-vous d'avoir cette méthode
    }

    // Calculer le hash du bloc
    public String calculateHash() {
        String calculatedhash = StringUtils.applySha256(
                previousHash + Long.toString(timeStamp) + Integer.toString(nonce)
        );
        return calculatedhash;
    }

    // Augmente nonce jusqu'à trouver le hash cible
    public void mineBlock(int difficulty) {
        String target = StringUtils.getDifficultyString(difficulty); // Crée une chaîne de difficulté (ex: avec difficulté 5 -> "00000")
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    // Ajoute des transactions au bloc
    public boolean addTransaction(Transaction transaction) {
        // process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((!"0".equals(previousHash))) {
            if((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
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

