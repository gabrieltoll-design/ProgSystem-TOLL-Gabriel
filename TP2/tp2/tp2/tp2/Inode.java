package tp2.tp2;

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

    public int getInodeOffset() {
        return MemoryManager.INODE_TABLE_OFFSET + (inodeNumber) * INODE_SIZE;
    }

    public int getFileType() {
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 4;
        return Utils.readInt(memory, offset);
    }

    public int getFileSize() {
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 8;
        return Utils.readInt(memory, offset);
    }

    public int[] getDirectPointers() {

        byte[] memory = memoryManager.getFilesystemMemory();
        int[] pointers = new int[DIRECT_POINTERS];

        for (int i = 0; i < DIRECT_POINTERS; i++) {
            pointers[i] = Utils.readInt(memory,(getInodeOffset()+28)+4*i);
        }

        return pointers;

    }

    public void writeToMemory(
            int fileType,
            int fileSize,
            long creationTime,
            long modificationTime,
            int[] directPointers,
            int indirectPointer,
            short permissions,
            int linkCount) {

        byte[] memory = memoryManager.getFilesystemMemory();

        int offset = getInodeOffset();

        // TODO:
        // 1. Numéro d'inode
        // 2. Type
        // 3. Taille
        // 4. Création
        // 5. Modification
        // 6. 10 pointeurs directs
        // 7. Pointeur indirect
        // 8. Permissions
        // 9. Nombre de liens

        




    }
}