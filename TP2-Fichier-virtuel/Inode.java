public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    byte[] memory = memoryManager.getFilesystemMemory();

    public int getInodeOffset() {
        return MemoryManager.INODE_TABLE_OFFSET + (inodeNumber-1) * INODE_SIZE;
    }

    public int getFileType() {
        return readInt(memory,getInodeOffset());
    }

    public int getFileSize() {
        // TODO:
        // Lire la taille à offset + 8.
        return 0;
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

        // TODO:
        // Lire les 10 pointeurs directs.

        return pointers;
    }
}