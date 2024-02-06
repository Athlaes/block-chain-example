package fr.ul.sid.wallet;

import fr.ul.sid.wallet.transaction.TransactionOutput;

public class UTXO {
    private TransactionOutput to;

    public TransactionOutput getTo() {
        return to;
    }

    public void setTo(TransactionOutput to) {
        this.to = to;
    }

    public UTXO(TransactionOutput to) {
        this.to = to;
    }
}
