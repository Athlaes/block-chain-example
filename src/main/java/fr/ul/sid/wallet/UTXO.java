package fr.ul.sid.wallet;

import fr.ul.sid.wallet.transaction.TransactionOutput;

public class UTXO {
    private String parentId;

    private TransactionOutput to;

    public  UTXO(String parentId, TransactionOutput to) {
        this.to = to;
        this.parentId = parentId;
    }

    public TransactionOutput getTo() {
        return to;
    }

    public void setTo(TransactionOutput to) {
        this.to = to;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
