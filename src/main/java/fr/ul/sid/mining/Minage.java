package fr.ul.sid.mining;

import fr.ul.sid.chain.Block;
import fr.ul.sid.chain.Blockchain;
import fr.ul.sid.utils.StringUtils;
import fr.ul.sid.wallet.transaction.Transaction;

import java.util.List;

public class Minage {

    private Blockchain blockchain;
    private int difficulty;

    public Minage(Blockchain blockchain, int difficulty) {
        this.blockchain = blockchain;
        this.difficulty = difficulty;
    }

    public Block mineBlock(List<Transaction> transactions) {
        Block newBlock = new Block(blockchain.getLatestBlock().getHash());
        for(Transaction transaction : transactions) {
            newBlock.addTransaction(transaction);
        }
        newBlock.mine(difficulty);
        blockchain.addBlock(newBlock);
        return newBlock;
    }
}
