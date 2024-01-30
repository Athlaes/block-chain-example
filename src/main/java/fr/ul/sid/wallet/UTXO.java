package fr.ul.sid.wallet;

import fr.ul.sid.wallet.transaction.TransactionOutput;

public class UTXO {
    private String id;

    private TransactionOutput to;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionOutput getTo() {
        return to;
    }

    public void setTo(TransactionOutput to) {
        this.to = to;
    }
}
