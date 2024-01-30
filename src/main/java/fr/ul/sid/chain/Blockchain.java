package fr.ul.sid.chain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> blockchain = new ArrayList<>();
    private int difficulty = 5; // Ajustez la difficulté selon vos besoins

    // Ajouter un bloc à la blockchain
    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    // Vérifier l'intégrité de la blockchain
    public boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // Boucle à travers la blockchain pour vérifier les hashes
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            // Comparer le hash enregistré et le hash calculé
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            // Comparer le hash précédent avec le hash enregistré dans le bloc actuel
            if(!previousBlock.getPreviousHash().equals(currentBlock.getPreviousHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            // Vérifier si le hash a été miné
            if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }


    public Block getLatestBlock() {
        return this.blockchain.getLast();
    }
}
