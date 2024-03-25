package fr.ul.sid.mining;

import fr.ul.sid.App;
import fr.ul.sid.chain.Block;
import fr.ul.sid.chain.Blockchain;
import fr.ul.sid.utils.StringUtils;
import fr.ul.sid.wallet.transaction.Transaction;
import fr.ul.sid.wallet.transaction.TransactionInput;
import fr.ul.sid.wallet.transaction.TransactionOutput;

import java.security.PublicKey;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Minage {
    private static final Logger logger = Logger.getLogger(Minage.class.getName());

    private static Minage instance;
    private final Blockchain blockchain;

    private  Minage(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public static Minage getInstance() {
        if(Objects.isNull(instance)) {
            instance = new Minage(App.blockchain);
        }
         return instance;
    }

    public Block mineBlock(PublicKey miner) {
        Block newBlock = blockchain.getBlockToMine().getFirst();
        logger.info(() -> "Mining block ...");
        if (Objects.isNull(newBlock.getPreviousHash())) {
            logger.severe(() -> "Impossible de miner le block tant que le précédédant block n'a pas été miné ");
            return null;
        }
        TransactionInput transactionInput = new TransactionInput(List.of());
        Transaction transaction = new Transaction(App.coinbaseFakeWallet.getPublicKey(), miner, transactionInput);
        TransactionOutput output = new TransactionOutput(miner, App.coinbase);
        transaction.addOutput(output);

        newBlock.setMiner(miner);
        newBlock.addTransaction(transaction);
        executeProofOfWork(newBlock);
        return newBlock;
    }

    public boolean isChainValid() {
        for(int i=1; i < blockchain.getBlockchain().size(); i++) {
            if(!isBlockValid(blockchain.getBlockchain().get(i), blockchain.getBlockchain().get(i-1))) {
                return false;
            }
        }

        return true;
    }

    public boolean isBlockValid(Block block, Block previousBlock) {
        String hashTarget = new String(new char[App.difficulty]).replace('\0', '0');

        if(!block.getHash().equals(StringUtils.applySha256(block)) ){
            logger.warning("A block hash couldn't be verified");
            return false;
        }
        if(!previousBlock.getHash().equals(block.getPreviousHash()) ) {
            logger.warning("Previous block hash doesn't equal current block previous hash");
            return false;
        }
        if(!block.getHash().substring( 0, App.difficulty).equals(hashTarget)) {
            logger.warning("This block hasn't been mined");
            return false;
        }

        return true;
    }

    private void executeProofOfWork(Block block) {
        String target = StringUtils.getDifficultyString(App.difficulty);
        block.setHash(StringUtils.applySha256(block));

        while(!block.getHash().substring(0, App.difficulty).equals(target)) {
            block.setNonce(block.getNonce()+1);
            block.setHash(StringUtils.applySha256(block));
        }
        logger.info(() -> "Block Mined ! Hash found : " + block.getHash());
    }
}
