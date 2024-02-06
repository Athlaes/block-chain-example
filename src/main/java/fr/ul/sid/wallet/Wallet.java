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
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
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

        Transaction newTransaction = new Transaction(this.publicKey, reveiver, value, input);
        newTransaction.setSignature(SignUtils.generateSignature(this.privateKey, newTransaction));

        return newTransaction;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
