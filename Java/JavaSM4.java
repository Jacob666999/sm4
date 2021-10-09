public class JavaSM4 {
           
    public static int[] key = new int[4];
    public static int[] temp = new int[4];
    public static int[] rkey = new int[32];
    public static int[] fk = {0xa3b1bac6, 0x56AA3350, 0x677d9197, 0xb27022dc}; 
    public static int[] ck = {
        0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269,
        0x50575e65, 0x6c737a81, 0x888f969d, 0xa4abb2b9,
        0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d, 0x141b2229,
        0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
        0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209,
        0x10171e25, 0x2c333a41, 0x484f565d, 0x646b7279};
    private static int[] sbi = { 
        0xd6,0x90,0xe9,0xfe,0xcc,0xe1,0x3d,0xb7,0x16,0xb6,0x14,0xc2,0x28,0xfb,0x2c,0x05,
        0x2b,0x67,0x9a,0x76,0x2a,0xbe,0x04,0xc3,0xaa,0x44,0x13,0x26,0x49,0x86,0x06,0x99,
        0x9c,0x42,0x50,0xf4,0x91,0xef,0x98,0x7a,0x33,0x54,0x0b,0x43,0xed,0xcf,0xac,0x62,
        0xe4,0xb3,0x1c,0xa9,0xc9,0x08,0xe8,0x95,0x80,0xdf,0x94,0xfa,0x75,0x8f,0x3f,0xa6,
        0x47,0x07,0xa7,0xfc,0xf3,0x73,0x17,0xba,0x83,0x59,0x3c,0x19,0xe6,0x85,0x4f,0xa8,
        0x68,0x6b,0x81,0xb2,0x71,0x64,0xda,0x8b,0xf8,0xeb,0x0f,0x4b,0x70,0x56,0x9d,0x35,
        0x1e,0x24,0x0e,0x5e,0x63,0x58,0xd1,0xa2,0x25,0x22,0x7c,0x3b,0x01,0x21,0x78,0x87,
        0xd4,0x00,0x46,0x57,0x9f,0xd3,0x27,0x52,0x4c,0x36,0x02,0xe7,0xa0,0xc4,0xc8,0x9e,
        0xea,0xbf,0x8a,0xd2,0x40,0xc7,0x38,0xb5,0xa3,0xf7,0xf2,0xce,0xf9,0x61,0x15,0xa1,
        0xe0,0xae,0x5d,0xa4,0x9b,0x34,0x1a,0x55,0xad,0x93,0x32,0x30,0xf5,0x8c,0xb1,0xe3,
        0x1d,0xf6,0xe2,0x2e,0x82,0x66,0xca,0x60,0xc0,0x29,0x23,0xab,0x0d,0x53,0x4e,0x6f,
        0xd5,0xdb,0x37,0x45,0xde,0xfd,0x8e,0x2f,0x03,0xff,0x6a,0x72,0x6d,0x6c,0x5b,0x51,
        0x8d,0x1b,0xaf,0x92,0xbb,0xdd,0xbc,0x7f,0x11,0xd9,0x5c,0x41,0x1f,0x10,0x5a,0xd8,
        0x0a,0xc1,0x31,0x88,0xa5,0xcd,0x7b,0xbd,0x2d,0x74,0xd0,0x12,0xb8,0xe5,0xb4,0xb0,
        0x89,0x69,0x97,0x4a,0x0c,0x96,0x77,0x7e,0x65,0xb9,0xf1,0x09,0xc5,0x6e,0xc6,0x84,
        0x18,0xf0,0x7d,0xec,0x3a,0xdc,0x4d,0x20,0x79,0xee,0x5f,0x3e,0xd7,0xcb,0x39,0x48};
           
           
    public static void main(String[] args)
    {
        JavaSM4 sm = new JavaSM4();
        int[] msg = {0x01234567, 0x89abcdef, 0xfedcba98, 0x76543210};
        int[] smsg = {0x595298c7, 0xc6fd271f, 0x0402f804, 0xc33d3f66};
        key[0] = 0x01234567;
        key[1] = 0x89abcdef;
        key[2] = 0xfedcba98;
        key[3] = 0x76543210;
        // int j=0,n=1000000;
        int j=0,n=1000000;
        long startTime = System.currentTimeMillis();   // start time
        for(j=0; j<n; j++)
        {
            //msg = sm4(msg,1);// encode
            smsg = sm4(smsg,0);// decode
        }
        long endTime = System.currentTimeMillis(); // end time
        System.out.println(" Run "+n+" times: "+(endTime-startTime)+"ms");
    }
           
    private static int[] sm4(int[] t,int s) 
    {
        rkey = initrk();
        if(s == 0)
        {
            rkey = r(rkey);
        }
        int[] x = new int[36];
        x[0] = t[0];
        x[1] = t[1];
        x[2] = t[2];
        x[3] = t[3];
        int i;
        for(i=0;i<32;i++)
        {
            x[i+4] = f(x[i],x[i+1],x[i+2],x[i+3],rkey[i]);
        }
        x = r(x);
        temp[0] = x[0];
        temp[1] = x[1];
        temp[2] = x[2];
        temp[3] = x[3];
        return temp;
    }
             
    private static int[] initrk()
    {
        int i;
        int[] k = new int[36];
        int[] rk = new int[32];
        k[0] = key[0] ^ fk[0];
        k[1] = key[1] ^ fk[1];
        k[2] = key[2] ^ fk[2];
        k[3] = key[3] ^ fk[3];
        for(i=0;i<32;i++)
        {
            rk[i] = k[i+4] = k[i] ^ tn(k[i+1]^k[i+2]^k[i+3]^ck[i]);
        }
        return rk;
    }
           
    private static int[] r(int[] x)
    {
        int[] t = new int[x.length];
        int i;
        for(i=0; i<x.length; i++)
        {
            t[i] = x[x.length - 1 -i];
        }
        return t;
    }
           
    private static int f(int x0,int x1,int x2,int x3,int k)
    {
        return (x0 ^ t(x1 ^ x2 ^ x3 ^ k));
    }
           
    private static int t(int ta)
    {
        return l(tj(ta));
    }
           
    private static int tn(int ta)
    {
        return ln(tj(ta));
    }
           
    private static int l(int temp)
    {
        return temp ^ Px(temp,2) ^ Px(temp,10) ^ Px(temp,18) ^ Px(temp,24);
    }
    private static int ln(int temp)
    {
        return temp ^ Px(temp,13) ^ Px(temp,23);
    }
           
    private static int tj(int a)
    {
        byte[] b = new byte[4];
        byte[] c = new byte[4];
        c = intToBytes(a);
        b[0] = sbox(c[0]);
        b[1] = sbox(c[1]);
        b[2] = sbox(c[2]);
        b[3] = sbox(c[3]);
        a = bytesToInt(b[0],b[1],b[2],b[3]);
        return a;
    }
           
    private static byte sbox(byte a) 
    {
        int t = (a << 24) >>> 24;
        return (byte)sbi[t];
    }
           
    private static int Px(int x,int n) 
    {
        return ((x<<n)|(x>>>(32-n)));
    }
    private static int bytesToInt(byte b0,byte b1,byte b2,byte b3) // int = 4 * byte = 32 bit unsigned
    {
        int tint = 0;
        int temp = b0 << 24;
        tint = temp;
        temp = (b1 << 24) >>> 8;
        tint |= temp;
        temp = (b2 << 24) >>> 16;
        tint |= temp;
        temp = (b3 << 24) >>> 24;
        tint |= temp;
        return tint;
    }
    private static byte[] intToBytes(int i)
    {
        byte[] tbyte = new byte[4];
        tbyte[0] = (byte)(i >>> 24);
        tbyte[1] = (byte)((i<<8)>>>24);
        tbyte[2] = (byte)((i<<16)>>>24);
        tbyte[3] = (byte)((i<<24)>>>24);
        return tbyte;
    }
}