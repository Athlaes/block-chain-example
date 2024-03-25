package fr.ul.sid.wallet.transaction;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.ul.sid.serialization.PublicKeyDeserializer;
import fr.ul.sid.serialization.PublicKeySerializer;
import fr.ul.sid.utils.StringUtils;

import java.security.PublicKey;

public class TransactionOutput {
    public String id;

    @JsonDeserialize(using = PublicKeyDeserializer.class)
    @JsonSerialize(using = PublicKeySerializer.class)
    public PublicKey reveiver;
    public float value;

    public TransactionOutput() {}

    public TransactionOutput(PublicKey reveiver, float value) {
        this.reveiver = reveiver;
        this.value = value;
        this.id = StringUtils.applySha256(this);
    }

    public PublicKey getReveiver() {
        return reveiver;
    }
}
