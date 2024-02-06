package fr.ul.sid.wallet.transaction;

import fr.ul.sid.utils.StringUtils;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reveiver;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reveiver, float value, String parentTransactionId) {
        this.reveiver = reveiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtils.applySha256(this);
    }

    // Vérifie si la monnaie appartient à ce wallet
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == this.reveiver);
    }
}
