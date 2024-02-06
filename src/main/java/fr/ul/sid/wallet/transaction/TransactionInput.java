package fr.ul.sid.wallet.transaction;

import fr.ul.sid.wallet.UTXO;

import java.util.ArrayList;
import java.util.List;

public class TransactionInput {
    public List<UTXO> UTXO;

    public TransactionInput(List<UTXO> utxos) {
        this.UTXO = new ArrayList<>();
        this.UTXO.addAll(utxos);
    }
}
