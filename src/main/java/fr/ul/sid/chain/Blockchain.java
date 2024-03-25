package fr.ul.sid.chain;

import fr.ul.sid.App;
import fr.ul.sid.utils.SignUtils;
import fr.ul.sid.wallet.transaction.Transaction;
import fr.ul.sid.wallet.transaction.TransactionInput;
import fr.ul.sid.wallet.transaction.TransactionOutput;

import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Blockchain {
    private static final Logger logger = Logger.getLogger(Blockchain.class.getName());

    private Block currentBlock;

    private List<Block> blockToMine = new LinkedList<>();
    private List<Block> blockchain = new LinkedList<>();

    public Blockchain() {
        currentBlock = new Block();
    }

    public void initBlockchain(PublicKey wallet, float amount) {
        TransactionInput transactionInput = new TransactionInput(List.of());
        Transaction transaction = new Transaction(wallet, wallet, transactionInput);
        TransactionOutput output = new TransactionOutput(wallet, amount);
        transaction.addOutput(output);

        this.currentBlock.addTransaction(transaction);
        this.nextBlock();
        Block block = App.minage.mineBlock();
        this.validateTransactions(block);
    }

    public void addTransaction(Transaction transaction) {
        if(SignUtils.checkSignature(transaction.sender, transaction, transaction.signature)) {
            currentBlock.addTransaction(transaction);
        } else {
            logger.warning("Invalid transaction added to blockchain");
        }
    }

    public void nextBlock() {
        Block newBlock = new Block(currentBlock.getHash());
        blockToMine.add(currentBlock);
        currentBlock = newBlock;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }

    public List<Block> getBlockToMine() {
        return blockToMine;
    }

    public void setBlockToMine(List<Block> blockToMine) {
        this.blockToMine = blockToMine;
    }

    public void validateTransactions(Block blockToRemove) {
        for(Block block : blockToMine) {
            for(Transaction transaction : block.getTransactions()) {
                App.processTransaction(transaction);
            }
        }
        this.blockToMine.remove(blockToRemove);
        this.blockchain.add(blockToRemove);
    }
}
