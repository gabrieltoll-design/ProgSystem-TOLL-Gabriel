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

        Inode inode = new Inode(memoryManager ,inodeNum);
        long timestamp = System.currentTimeMillis(); //pas sure si c'est le bon timestamp mais ca a l'aire.
        int[] ptrs = new int[Inode.DIRECT_POINTERS]; //10 , vide vue que le fichier est vide donc tableau de 10 0

        inode.writeToMemory(0,0, timestamp , timestamp , ptrs , 0 , (short)0644 , 1); //Permission obtenue par IA et
        // linkCount 1 car referencer par le dossier mere

        return true;
    }

    public boolean writeFile(
            int inodeNum,
            byte[] data) {

        int blocksNeeded =
                (data.length
                        + MemoryManager.BLOCK_SIZE - 1)
                        / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers = new int[Inode.DIRECT_POINTERS];

        for (int i = 0; i < blocksNeeded; i++) {
            int b = memoryManager.allocateBlock();
            if (b == -1) {
                return false;
            }
            blockPointers[i] = b;
        }

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        for (int i = 0; i < blocksNeeded; i++) {
            int blockNbr = blockPointers[i]; //Block dans lequel on écrit
            int blockOffset = blockNbr * MemoryManager.BLOCK_SIZE;//emplacement en oct du début du block
            int bytesToCopy = Math.min(MemoryManager.BLOCK_SIZE, bytesRemaining);//Nbr de byte a copier

            System.arraycopy(data, dataSrcOffset, memory, blockOffset, bytesToCopy);
                //arraycopy(source , sourceOffset , destination , destinationOffset , taille)
            dataSrcOffset += bytesToCopy;
            bytesRemaining -= bytesToCopy;
        }

        Inode inode = new Inode(memoryManager, inodeNum);
        long creationTime = inode.getCreationTime();
        long modificationTime = System.currentTimeMillis();

        inode.writeToMemory(
                0,
                data.length,
                creationTime,       // On garde l'ancienne date de création
                modificationTime,   // On met à jour la date de modification
                blockPointers,
                0,
                (short) 0644,
                1
        );

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
            inode.getDirectPointers();

        //Chemin inverse de l'écriture meme logique
        //Logiquement : je cherche combien de bloc j'ai besoin :
        int blocksNeeded =
                (fileSize + MemoryManager.BLOCK_SIZE - 1)
                        / MemoryManager.BLOCK_SIZE;
        //Cmb de byte j'ai besoin et offset (comme write)
        int bytesRemaining = fileSize;
        int dataDestOffset = 0;

        //Pour chaque blockNeeded , meme logique que write mais on copy de la mémoire a fileData
        for (int i = 0; i < blocksNeeded; i++) {
            int blockNbr = blockPointers[i];
            int blockOffset = blockNbr * MemoryManager.BLOCK_SIZE;
            int bytesToCopy = Math.min(MemoryManager.BLOCK_SIZE, bytesRemaining);

            System.arraycopy(memory, blockOffset, fileData, dataDestOffset, bytesToCopy);

            dataDestOffset += bytesToCopy;
            bytesRemaining -= bytesToCopy;
        }
        return fileData;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}