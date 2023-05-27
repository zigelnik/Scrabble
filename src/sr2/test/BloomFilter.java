package sr2.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

import static java.lang.Math.abs;

public class BloomFilter {

    private BitSet bitArr;
    private MessageDigest[] md;
    private BigInteger bi;
    private byte[] bts;
    public BloomFilter(int size, String... args ) {
        bitArr = new BitSet(size);
        md = new MessageDigest[args.length];

        try {
            for (int i =0; i<args.length;i++) {
                md[i] = MessageDigest.getInstance(args[i]);
            }
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

    }

    public void add(String str)  {

        int ind;
        if(contains(str))
            return;

        for(MessageDigest m : md)
        {
            bts = m.digest(str.getBytes());
            bi = new BigInteger(bts);
            ind = abs(bi.intValue()) % bitArr.size();
            bitArr.set(ind);
        }
    }

    public boolean contains(String str)
    {

        for(MessageDigest m : md)
        {
            bts = m.digest(str.getBytes());
            bi = new BigInteger(bts);
            int ind = abs(bi.intValue()) % bitArr.size();
            if(!bitArr.get(ind))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
            String tmp = "";
            for(int i=0; i<bitArr.length();i++)
            {
                if(bitArr.get(i) == false)
                    tmp += "0";
                else
                    tmp += "1";
            }
        return tmp;
    }
}
