import java.io.*;
import tp2.Utils;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS =
            TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET =
            2 * BLOCK_SIZE;
    public static final int DATA_OFFSET =
            129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;

    public static final int INODE_TABLE_SIZE =
            DATA_OFFSET - INODE_TABLE_OFFSET;

    public static final int MAX_INODES =
            INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        for (int i = 0; i < 129; i++) {
            memory[BITMAP_OFFSET + i] = (byte) 0xFF;
        }
    }

    private void writeSuperblock() {

        /*CORRECTION ?
        int offset = 0;
        offset += writeStr(memory,offset,16);
        offset += writeInt(memory,offset,BLOCK_SIZE);
        */

        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }

    public boolean setBlockUsed(int blockNumber, boolean used) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return false;
        }

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        if (used) {
            //Ici on fait l'opération ou avec un
            //masque qui déplace le 1 a l'indice
            //de ce que l'on veut modifier
            memory[offset] |= 1<<bitPosition;
        } else {
            //Ici on fait l'opération et avec un
            //masque qui déplace le 1 a l'indice
            //de ce que l'on veut modifier
            //Sauf que vue que on veut le metre
            //a nul on inverse les 0 et 1
            //de façon a faire un et ou les 1
            //du masque sont ce que on veut garder
            //et le seul 0 transforme le bit visé
            //en 0
            memory[offset] &= ~(1<<bitPosition);
        }

        return true;
    }

    public int isBlockUsed(int blockNumber) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
            return -1;
        }

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        //Ici on déplace le bit cherché jusque a la position la
        //plus a droite possible
        int bitChercher = memory[offset] >> bitPosition;

        /* Ce que j'ai fait
        if(bitChercher &=1){
            return 1
        }
        return -1;
        */

        //Correction
        return bitChercher &0x01;
    }

    public int allocateBlock() {
        return -1;
    }

    public byte[] getFilesystemMemory() {
        return memory;
    }







}
