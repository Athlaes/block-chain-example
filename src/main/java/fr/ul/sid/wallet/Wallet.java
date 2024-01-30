package fr.ul.sid.wallet;

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

    // Un portefeuille contient une collection de UTXOs qui lui appartiennent
    public List<UTXO> UTXOs = new ArrayList<>();

    public Wallet() {
        generateKeyPair();
    }

    // Générer la paire de clés publique et privée
    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialiser le générateur de clés et générer une paire de clés
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            // Définir les clés publiques et privées à partir de la paire de clés
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public float getBalance() {
        float total = 0;
        for (UTXO utxo: this.UTXOs){
            TransactionOutput UTXO = utxo.getTo();
            if(UTXO.isMine(publicKey)) { 
                total += UTXO.value ;
            }
        }
        return total;
    }

    // Générer et retourner une nouvelle transaction depuis ce portefeuille
    public Transaction sendFunds(PublicKey reveiver,float value ) {
        if (getBalance() < value) { // Vérifier si les fonds sont suffisants
            System.out.println("#Pas assez de fonds pour envoyer la transaction. Transaction échouée.");
            return null;
        }
        // Créer une liste des entrées
        List<TransactionInput> inputs = new ArrayList<TransactionInput>();

        float total = 0;
        for (UTXO utxo : UTXOs) {
            TransactionOutput UTXO = utxo.getTo();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }
        Transaction newTransaction = new Transaction(publicKey, reveiver, value, inputs);
        newTransaction.generateSignature(privateKey);for(TransactionInput input: inputs){
            UTXOs.remove(input.transactionOutputId);
        }

        return newTransaction;
    }

    // Getters pour les clés
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
