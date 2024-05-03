package elgamal;

import java.math.BigInteger;

public class ElGamalCipher {
	private final BigInteger c1;
    private final BigInteger c2;

    public ElGamalCipher(BigInteger c1, BigInteger c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public BigInteger getC1() {
        return c1;
    }

    public BigInteger getC2() {
        return c2;
    }
}
