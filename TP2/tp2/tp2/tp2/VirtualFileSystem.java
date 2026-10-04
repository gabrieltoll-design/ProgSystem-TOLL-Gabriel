package tp2.tp2;

import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager = new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory = memoryManager.getFilesystemMemory();

        for (int i=0; i < MemoryManager.MAX_INODES; i++) {

            int offset = MemoryManager.INODE_TABLE_OFFSET + (i*Inode.INODE_SIZE);
            int inodeNmbr = Utils.readInt(memory, offset);
            if (inodeNmbr != i) { //Verifie que l'Inode soit vide ou des residues.
                return i;
            }
        }
        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // TODO:
        // Construire l'inode.
        // L'initialiser comme fichier vide.


        Inode inode = new Inode(memoryManager ,inodeNum);
        long timestamp = System.currentTimeMillis(); //pas sure si c'est le bon timestamp mais ca a l'aire.
        int[] ptrs = new int[Inode.DIRECT_POINTERS]; //10 , vide vue que le fichier est vide donc tableau de 10 0


        inode.writeToMemory(0,0, timestamp , timestamp , ptrs , 0 , (short)0644 , 1); //Permission obtenue par IA et
        // linkCount 1 car referencer par le dossier mere

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}