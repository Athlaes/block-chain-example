package fr.ul.sid.wallet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.ul.sid.serialization.PrivateKeyDeserializer;
import fr.ul.sid.serialization.PrivateKeySerializer;
import fr.ul.sid.serialization.PublicKeyDeserializer;
import fr.ul.sid.serialization.PublicKeySerializer;
import fr.ul.sid.utils.SignUtils;
import fr.ul.sid.utils.StringUtils;
import fr.ul.sid.wallet.transaction.Transaction;
import fr.ul.sid.wallet.transaction.TransactionInput;
import fr.ul.sid.wallet.transaction.TransactionOutput;

import java.security.*;
import java.util.ArrayList;
import java.util.List;

public class Wallet {
    @JsonDeserialize(using = PublicKeyDeserializer.class)
    @JsonSerialize(using = PublicKeySerializer.class)
    private PublicKey publicKey;
    @JsonDeserialize(using = PrivateKeyDeserializer.class)
    @JsonSerialize(using = PrivateKeySerializer.class)
    private PrivateKey privateKey;
    public List<UTXO> utxos = new ArrayList<>();

    public Wallet() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch(Exception e) {
            throw new RuntimeException("Impossible de générer un nouveau couple de clé privée / clé publique", e);
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

    public Transaction sendFunds(PublicKey reveiver, float value) {
        if (getBalance() < value) {
            System.out.println("Pas assez de fonds pour envoyer la transaction. Transaction échouée.");
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

        newTransaction.addOutput(new TransactionOutput(reveiver, value));
        if (total>value){
            newTransaction.addOutput(new TransactionOutput(this.publicKey, total-value));
        }

        newTransaction.setSignature(SignUtils.generateSignature(this.privateKey, newTransaction));
        newTransaction.setTransactionId(StringUtils.applySha256(newTransaction));

        return newTransaction;
    }

    public void addFound(String transactionId, TransactionOutput to) {
        this.utxos.add(new UTXO(transactionId, to));
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public List<UTXO> getUtxos() {
        return utxos;
    }

    public void setUtxos(List<UTXO> utxos) {
        this.utxos = utxos;
    }
}
