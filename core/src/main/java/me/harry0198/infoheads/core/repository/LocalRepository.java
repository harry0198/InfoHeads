package me.harry0198.infoheads.core.repository;

import me.harry0198.infoheads.core.model.Identifiable;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LocalRepository<T extends Serializable & Identifiable> implements Repository<T> {

    private final static String DATA_FILE_EXTENSION = ".dat";
    private final static Logger LOGGER = Logger.getLogger(LocalRepository.class.getName());

    private final String repositoryFolder;


    public LocalRepository(Path parent) {
        this.repositoryFolder = Path.of(parent.toFile().getAbsolutePath(), "local_data").toString();
    }

    /**
     * Saves an object to its own file on the disk under the name defined by {@link Identifiable#getId()}.
     * @param obj Object implementing serializable and identifiable to save.
     * @return If the save was a success or not.
     */
    @Override
    public boolean save(T obj) {
        if (!validateFolder(new File(repositoryFolder))) return false;

        File propertyFile = getFileForProperty(obj);

        try (FileOutputStream fileOut = new FileOutputStream(propertyFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(obj);
            return true;
        } catch (IOException i) {
            LOGGER.warning("Unable to locally save obj " + obj.getId());
            LOGGER.throwing(LocalRepository.class.getName(), "save", i);
        }

        return false;
    }

    /**
     * Fetches all {@link }
     * @return
     */
    @Override
    public List<T> getAll() {
        // If folder presence could not be verified, return empty list to prevent fatal failure and delegate notification to below function.
        File dataFolder = new File(repositoryFolder);
        if (!validateFolder(dataFolder)) return List.of();

        File[] files = dataFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(DATA_FILE_EXTENSION));
        if (files == null) return List.of();

        return Arrays.stream(files).parallel().map(file -> {
            try (FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
                // Will be caught
                @SuppressWarnings("unchecked")
                T obj = (T) in.readObject();
                return obj;
            } catch (IOException | ClassNotFoundException i) {
                LOGGER.severe("Error fetching data from file " + file.getName());
                LOGGER.throwing(LocalRepository.class.getName(), "getAll", i);
            }

            return null;
        }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(T obj) {
        File propertyFile = getFileForProperty(obj);
        try {
            return propertyFile.delete();
        } catch (SecurityException securityException) {
            LOGGER.warning("Unable to locally delete from disk InfoHead " + obj.getId());
            LOGGER.throwing(LocalRepository.class.getName(), "delete", securityException);
        }

        return false;
    }

    private boolean validateFolder(File dataFolder) {
        if (!dataFolder.isDirectory()) {
            try {
                boolean didMake = dataFolder.mkdirs();
                if (!didMake) {
                    LOGGER.severe("Failed to create missing directory " + dataFolder);
                }
                return didMake;
            } catch (SecurityException securityException) {
                LOGGER.severe("Failed to create missing directory - Missing WRITE permissions to directory " + dataFolder);
                LOGGER.throwing(LocalRepository.class.getName(), "validateFolder", securityException);
                return false;
            }
        }

        return true;
    }

    private File getFileForProperty(Identifiable identifiable) {
        return new File(repositoryFolder, identifiable.getId().toString() + DATA_FILE_EXTENSION);
    }
}
