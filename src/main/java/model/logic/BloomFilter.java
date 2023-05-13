package model.logic;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

import static java.lang.Math.abs;

public class BloomFilter {

    private final BitSet bitArr;
    private final MessageDigest[] md;
    private BigInteger bi;
    private byte[] bytes;
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

        if(contains(str))
            return;

        for(MessageDigest m : md)
        {
            bytes = m.digest(str.getBytes());
            bi = new BigInteger(bytes);
           int index = abs(bi.intValue()) % bitArr.size();
            bitArr.set(index);
        }
    }

    public boolean contains(String str)
    {
        for(MessageDigest m : md)
        {
            bytes = m.digest(str.getBytes());
            bi = new BigInteger(bytes);
            int index = abs(bi.intValue()) % bitArr.size();
            if(!bitArr.get(index))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
            StringBuilder tmp = new StringBuilder();
            for(int i=0; i<bitArr.length();i++)
            {
                if(!bitArr.get(i))
                    tmp.append("0");
                else
                    tmp.append("1");
            }
        return tmp.toString();
    }
}
