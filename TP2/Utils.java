package gabriel.TOLL;

public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        memory[offset] = (byte)(value & 0xFF);
        memory[offset+1] = (byte)(value >> 8 & 0xFF);
        memory[offset+2] = (byte)(value >> 16 & 0xFF);
        memory[offset+3] = (byte)(value >> 24 & 0xFF);
        return 4;
    }

    public static int readInt(byte[] memory, int offset) {

        int p1 = (int)memory[offset];
        int p2 = (int)memory[offset+1] << 8;
        int p3 = (int)memory[offset+2] << 16;
        int p4 = (int)memory[offset+3] << 24;

        return p1|p2|p3|p4;
    }

    public static int writeShort(byte[] memory, int offset, short value) {

        memory[offset] = (byte)(value & 0xFF);
        memory[offset+1] = (byte)(value >> 8 & 0xFF);

        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        short p1 = (short)memory[offset];
        short p2 = (short)(memory[offset+1] << 8);

        return (short)(p1|p2) ;
    }
}