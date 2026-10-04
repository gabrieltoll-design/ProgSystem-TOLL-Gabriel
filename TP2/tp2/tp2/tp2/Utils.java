package tp2.tp2;

public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        memory[offset] = (byte) (value >> 24);
        memory[offset + 1] = (byte) (value >> 16);
        memory[offset + 2] = (byte) (value >> 8);
        memory[offset + 3] = (byte) value;
        return 4;
    }

    public static int readInt(byte[] memory, int offset) {

        int p1 = (memory[offset + 3] & 0xFF);
        int p2 = (memory[offset + 2] & 0xFF) << 8;
        int p3 = (memory[offset + 1] & 0xFF) << 16;
        int p4 = (memory[offset] & 0xFF) << 24;

        return p1|p2|p3|p4;
    }

    public static int writeShort(byte[] memory, int offset, short value) {

        memory[offset+1] = (byte)(value & 0xFF);
        memory[offset] = (byte)(value >> 8 & 0xFF);
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        short p1 = (short)memory[offset+1];
        short p2 = (short)(memory[offset] << 8);

        return (short)(p1|p2) ;
    }

    public static int writeLong(byte[] memory, int offset, long value) {
        for (int i = 0; i < 8; i++)
            memory[offset + i] = (byte) (value >> (56- i*8));
        return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        long p1 = ((long)memory[offset] & 0xFF) << 56;
        long p2 = ((long)memory[offset + 1] & 0xFF) << 48;
        long p3 = ((long)memory[offset + 2] & 0xFF) << 40;
        long p4 = ((long)memory[offset + 3] & 0xFF) << 32;
        long p5 = ((long)memory[offset + 4] & 0xFF) << 24;
        long p6 = ((long)memory[offset + 5] & 0xFF) << 16;
        long p7 = ((long)memory[offset + 6] & 0xFF) << 8;
        long p8 = (long)memory[offset + 7] & 0xFF;

        return p1|p2|p3|p4|p5|p6|p7|p8;
    }

    public static int writeString(
            byte[] memory,
            int offset,
            String str,
            int maxLength) {

        byte[] tabBytes = str.getBytes();
        int i;

        int len = Math.min(tabBytes.length, maxLength - 1);
        for (i = 0; i < len; i++) {
            memory[offset + i] = tabBytes[i];
        }

        for (int y = i; y < maxLength; y++) {
            memory[offset + y] = 0;
        }

        return maxLength;
    }

    public static String readString(
            byte[] memory,
            int offset,
            int maxLength) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maxLength && memory[offset + i] != 0; i++) {
            sb.append((char) memory[offset + i]); //Sans le cast , lis les chiffres (appjend ne lis que int char et String)
        }

        return sb.toString();
    }

}