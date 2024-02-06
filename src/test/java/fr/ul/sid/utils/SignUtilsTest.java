package fr.ul.sid.utils;

import fr.ul.sid.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignUtilsTest {
    private Wallet walletOne;
    private Wallet walletTwo;
    private String message;
    @BeforeEach
    void init(){
        this.walletOne = new Wallet();
        this.walletTwo = new Wallet();
        this.message = "bonjour blockChain";
    }
    @Test
    @DisplayName("test de génération de signature - signature non null")
    void testgenerateSignature() {
        byte[] signature = SignUtils.generateSignature(this.walletOne.getPrivateKey(),this.message);
        assertNotNull(signature);
    }
    @Test
    @DisplayName("test de génération de signature - signature non vides")
    void testgenerateSignatureEmpty() {
        byte[] signature = SignUtils.generateSignature(this.walletOne.getPrivateKey(),this.message);
        assertNotEquals(signature.length,0);
    }

    @Test
    @DisplayName("test de vérification de signature")
    void testVerifySignatureok(){
        byte[] signature = SignUtils.generateSignature(this.walletOne.getPrivateKey(),this.message);
        boolean verifiedOne = SignUtils.checkSignature(this.walletOne.getPublicKey(),this.message,signature);
        boolean verifiedTwo = SignUtils.checkSignature(this.walletTwo.getPublicKey(),this.message,signature);
        assertEquals(verifiedOne,true);
        assertEquals(verifiedTwo,false);
    }
}
