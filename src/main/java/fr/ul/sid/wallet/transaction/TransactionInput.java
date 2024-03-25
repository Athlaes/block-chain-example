package fr.ul.sid.wallet.transaction;

import fr.ul.sid.wallet.UTXO;

import java.util.ArrayList;
import java.util.List;

public class TransactionInput {
    private List<UTXO> utxo;

    public TransactionInput() {}

    public TransactionInput(List<UTXO> utxos) {
        this.utxo = new ArrayList<>();
        this.utxo.addAll(utxos);
    }

    public List<UTXO> getUtxo() {
        return utxo;
    }

    public void setUtxo(List<UTXO> utxo) {
        this.utxo = utxo;
    }
}
