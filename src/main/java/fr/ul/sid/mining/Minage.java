package fr.ul.sid.mining;

import fr.ul.sid.chain.Block;
import fr.ul.sid.chain.Blockchain;
import fr.ul.sid.utils.StringUtils;
import fr.ul.sid.wallet.transaction.Transaction;

import java.util.List;

public class Minage {

    private Blockchain blockchain;
    private int difficulty;

    // Constructeur
    public Minage(Blockchain blockchain, int difficulty) {
        this.blockchain = blockchain;
        this.difficulty = difficulty;
    }

    // Miner un nouveau bloc avec les transactions données
    public Block mineBlock(List<Transaction> transactions) {
        Block newBlock = new Block(blockchain.getLatestBlock().getHash());
        for(Transaction transaction : transactions) {
            newBlock.addTransaction(transaction);
        }

        executeProofOfWork(newBlock);
        blockchain.addBlock(newBlock);
        return newBlock;
    }

    // Exécute le processus de preuve de travail (Proof of Work)
    private void executeProofOfWork(Block block) {
        String target = StringUtils.getDifficultyString(difficulty); // Crée une chaine de caractères avec la difficulté (ex: "00000" pour la difficulté 5)
        while(!block.getHash().substring(0, difficulty).equals(target)) {
            block.setNonce(block.getNonce()+1);
            block.setHash(block.calculateHash());
        }
        System.out.println("Block Mined!!! : " + block.getHash());
    }
}
