package fr.ul.sid.wallet;

import fr.ul.sid.utils.SignUtils;
import fr.ul.sid.wallet.transaction.Transaction;
import fr.ul.sid.wallet.transaction.TransactionInput;
import fr.ul.sid.wallet.transaction.TransactionOutput;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public List<UTXO> utxos = new ArrayList<>();

    public Wallet() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public float getBalance() {
        float total = 0;
        for (UTXO utxo: this.utxos) {
            TransactionOutput UTXO = utxo.getTo();
            total += UTXO.value;
        }
        return total;
    }

    public Transaction sendFunds(PublicKey reveiver,float value ) {
        if (getBalance() < value) {
            System.out.println("#Pas assez de fonds pour envoyer la transaction. Transaction échouée.");
            return null;
        }
        List<UTXO> utxos = new ArrayList<>();
        float total = 0;
        for (UTXO utxo : this.utxos) {
            TransactionOutput transactionOutput = utxo.getTo();
            total += transactionOutput.value;
            utxos.add(utxo);
            if (total >= value) break;
        }
        TransactionInput input = new TransactionInput(utxos);
        Transaction newTransaction = new Transaction(this.publicKey, reveiver, input);
        TransactionOutput outputSend = new TransactionOutput(reveiver,value,newTransaction.getTransactionId());
        newTransaction.addTransactionOutput(outputSend);
        if (total>value){
            TransactionOutput outputReceive = new TransactionOutput(this.publicKey,(total-value),newTransaction.getTransactionId());
            newTransaction.addTransactionOutput(outputReceive);
        }
        newTransaction.setSignature(SignUtils.generateSignature(this.privateKey, newTransaction));
        return newTransaction;
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }
    public void setUtxos(List<UTXO> utxos) {
        this.utxos = utxos;
    }
}
