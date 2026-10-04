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
        return MemoryManager.INODE_TABLE_OFFSET + (inodeNumber * INODE_SIZE);
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

    public long getCreationTime(){ //sert pour écrire un fichier (VirtualFileSystem)
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 12;
        return Utils.readLong(memory, offset);
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

        // 1. Numéro d'inode
        // 2. Type
        // 3. Taille
        // 4. Création
        // 5. Modification


        //J'ai mis du temps a me rapeller que writeInt renvoyer 4 ...
        offset += Utils.writeInt(memory , offset , inodeNumber);
        offset += Utils.writeInt(memory , offset , fileType);
        offset += Utils.writeInt(memory , offset , fileSize);
        offset += Utils.writeLong(memory, offset, creationTime);
        offset += Utils.writeLong(memory, offset, modificationTime);

        // 6. 10 pointeurs directs
        // 7. Pointeur indirect
        // 8. Permissions
        // 9. Nombre de liens

        if (directPointers != null && directPointers.length<=10) {
            int nbrPtr = directPointers.length;
            for (int i = 0; i < nbrPtr; i++) {
                offset += Utils.writeInt(memory, offset, directPointers[i]);
            }
            for (int i = 0; i < 10 - nbrPtr; i++) {
                offset += Utils.writeInt(memory, offset, 0);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                offset += Utils.writeInt(memory, offset, 0);
            }
        }

        offset += Utils.writeInt(memory, offset, indirectPointer);
        offset += Utils.writeShort(memory, offset, permissions);
        offset += Utils.writeInt(memory, offset, linkCount);

    }
}