package fr.ul.sid.wallet.transaction;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reveiver; // Nouveau propriétaire des fonds
    public float value; // Montant des fonds
    public String parentTransactionId; // ID de la transaction parente de cette sortie

    // Constructeur
    public TransactionOutput(PublicKey reveiver, float value, String parentTransactionId) {
        this.reveiver = reveiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = calculateHash();
    }

    // Calcule le hash de la transaction de sortie
    private String calculateHash() {
        // implémenter le calcul du hash
        return "";
    }

    // Vérifie si la monnaie appartient à ce wallet
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == this.reveiver);
    }
}
