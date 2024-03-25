package fr.ul.sid;

import fr.ul.sid.chain.Block;
import fr.ul.sid.chain.Blockchain;
import fr.ul.sid.mining.Minage;
import fr.ul.sid.utils.KeyUtils;
import fr.ul.sid.wallet.Wallet;
import fr.ul.sid.wallet.transaction.Transaction;
import fr.ul.sid.wallet.transaction.TransactionOutput;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static final int delay = 1000 * 60 * 3;
    public static int difficulty = 5;

    public static final int coinbase = 8;

    private static final Wallet appWallet = new Wallet();

    private static final Scanner s = new Scanner(System.in);

    private static final List<Wallet> wallets = new ArrayList<>();
    public static Blockchain blockchain;

    public static final Wallet coinbaseFakeWallet = new Wallet();

    private static Timer miningTimer;

    public static Minage minage;

    public static void main( String[] args )
    {
        logger.info( "Starting blockchain" );
        App.blockchain = new Blockchain();
        App.minage = Minage.getInstance();

        wallets.add(App.appWallet);
        wallets.add(App.coinbaseFakeWallet);

        App.blockchain.initBlockchain(App.appWallet.getPublicKey(), 10000);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                App.blockchain.nextBlock();
                logger.info(() -> "Blockchain has changed current block");
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, App.delay, App.delay);


        System.out.println("Welcome to Blockchain CLI");

        boolean stop = false;
        while (!stop) {
            stop = readCommand();
        }
    }

    private static boolean readCommand() {
        System.out.println("""
                            What do you want to do ?
                            See block chain size [1]
                            Add transaction [2]
                            Mine available blocks [3]
                            See available wallet keys [4]
                            Add new wallet [5]
                            Amount of coins in wallet [6]
                            See how many blocks needs to be mined [7]
                            See balance of specific wallet [8]
                            Activate automatic mining [9]
                            Quit [quit]
                            """);
        String input = s.nextLine();
        switch(input) {
            case "1" -> System.out.println(App.blockchain.getBlockchain().size());
            case "2" -> {
                try {
                    System.out.println("Receiver key :");
                    String walletKey = s.nextLine();
                    System.out.println("Amount :");
                    float amount = s.nextFloat();
                    PublicKey receiver = KeyUtils.deserializePublicKey(walletKey);
                    Wallet wallet = wallets.stream().filter(w -> w.getPublicKey().equals(receiver)).findFirst().orElse(null);
                    if (Objects.nonNull(wallet)) {
                        Transaction transaction = appWallet.sendFunds(receiver, amount);
                        if (Objects.nonNull(transaction)) {
                            boolean res = App.blockchain.addTransaction(transaction);
                            if (res) {
                                logger.info(() -> "Sent : " + amount);
                            } else {
                                logger.info(() -> "Couldn't send amount");
                            }
                        } else {
                            logger.warning(() -> "Montant supérieur au total du wallet");
                        }
                    } else {
                        logger.warning(() -> "Impossible de retrouver le wallet correspondant");
                    }
                } catch (InputMismatchException e) {
                    logger.severe("Wrong input try again");
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    logger.severe(() -> "Impossible de récupérer la clé publique " + e.getMessage());
                }
            }
            case "3" -> {
                Block blockMined = App.minage.mineBlock();
                if(Objects.nonNull(blockMined) && minage.isChainValid()) {
                    App.blockchain.validateTransactions(blockMined);
                }
            }
            case "4" -> {
                wallets.forEach(w -> {
                    try {
                        System.out.println(KeyUtils.serializePublicKey(w.getPublicKey()));
                    } catch (IOException e) {
                        logger.severe(() -> "Impossible de sérialiser la clé publique " + e.getMessage());
                    }
                });
            }
            case "5" -> App.wallets.add(new Wallet());
            case "6" -> System.out.println(App.appWallet.getBalance());
            case "7" -> System.out.println(App.blockchain.getBlockToMine().size());
            case "8" -> {
                System.out.println("Wallet key :");
                String walletKey = s.nextLine();
                try {
                    PublicKey key = KeyUtils.deserializePublicKey(walletKey);
                    if (Objects.nonNull(key)) {
                        Wallet wallet = wallets.stream().filter(w -> w.getPublicKey().equals(key)).findFirst().orElse(null);
                        System.out.println(wallet.getBalance());
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    logger.severe(() -> "Impossible de désérialiser la clé publique ");
                }
            }
            case "9" -> {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Block blockMined = App.minage.mineBlock(App.appWallet.getPublicKey());
                            if(Objects.nonNull(blockMined) && minage.isChainValid()) {
                                App.blockchain.validateTransactions(blockMined);
                            }
                        } catch (NoSuchElementException e) {}
                    }
                };
                App.miningTimer = new Timer();
                App.miningTimer.scheduleAtFixedRate(task, 0, 1000 * 5);
                System.out.println("Automatic mining activated");
            }
            default -> System.out.println("Invalid input");
        }

        return input.equals("quit");
    }

    public static void processTransaction(Transaction transaction) {
        // Delete UTXO from wallet
        Wallet inputWallet = wallets.stream().filter(w -> w.getPublicKey().equals(transaction.getSender())).findFirst().orElse(null);
        if (Objects.nonNull(inputWallet)) {
            transaction.getInput().getUtxo().forEach(w -> inputWallet.getUtxos().remove(w));
            // Create UTXO
            List<TransactionOutput> outputs = transaction.getOutputs();
            for (TransactionOutput output : outputs) {
                Wallet outputWallet = wallets.stream().filter(w -> w.getPublicKey().equals(output.getReveiver())).findFirst().orElse(null);
                if (Objects.nonNull(outputWallet)) {
                    outputWallet.addFound(transaction.getTransactionId(), output);
                } else {
                    logger.warning(() -> "Impossible de retrouver le wallet correspondant à cet output");
                }
            }
        } else {
            logger.warning(() -> "Impossible de retrouver le wallet correspondant aux inputs");
        }
    }
}

