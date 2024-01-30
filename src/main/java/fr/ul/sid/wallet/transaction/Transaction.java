package fr.ul.sid.wallet.transaction;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public String transactionId; // Identifiant de la transaction
    public PublicKey sender; // Clé publique de l'expéditeur
    public PublicKey reveiver; // Clé publique du destinataire
    public float value; // Montant de la transaction
    public byte[] signature; // Signature cryptographique

    public List<TransactionInput> inputs = new ArrayList<>();
    public List<TransactionOutput> outputs = new ArrayList<>();

    // Constructeur
    public Transaction(PublicKey from, PublicKey to, float value,  List<TransactionInput> inputs) {
        this.sender = from;
        this.reveiver = to;
        this.value = value;
        this.inputs = inputs;
    }

    // Cette méthode permet de calculer le hash de la transaction
    private String calculateHash() {
        // implémenter le calcul du hash
        return "";
    }

    // Cette méthode permet de signer la transaction
    public void generateSignature(PrivateKey privateKey) {
        // implémenter la signature
    }

    // Cette méthode permet de vérifier la signature
    public boolean verifySignature() {
        // implémenter la vérification de la signature
        return true;
    }

    // Traitement de la transaction
    public boolean processTransaction() {
        // implémenter le traitement de la transaction
        return true;
    }
}
