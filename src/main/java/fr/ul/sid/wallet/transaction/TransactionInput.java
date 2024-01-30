package fr.ul.sid.wallet.transaction;

import java.util.List;

public class TransactionInput {
    public String transactionOutputId; // Référence à TransactionOutput -> transactionId
    public List<TransactionOutput> UTXO; // Contient les UTXO non dépensés

    // Constructeur
    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}
