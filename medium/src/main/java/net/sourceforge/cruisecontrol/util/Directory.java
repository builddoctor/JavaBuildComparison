package net.sourceforge.cruisecontrol.util;

import java.io.File;
import java.io.Serializable;

import net.sourceforge.cruisecontrol.CruiseControlException;

public class Directory implements Serializable {

    private static final long serialVersionUID = -6515402321994084452L;

    private File directory;

    public void validate() throws CruiseControlException {
        if (directory == null) {
            throw new DirectoryNotSpecifiedException("directory");
        }
        
        if (!exists()) {
            throw new DirectoryDoesNotExistException();
        }
        
        if (!isDirectory()) {
            throw new FileInsteadOfDirectoryException();
        }
    }

    public boolean exists() {
        return directory.exists();
    }
    
    public boolean isDirectory() {
        return directory.exists();
    }
    
    public class DirectoryDoesNotExistException extends CruiseControlException {
        private static final long serialVersionUID = 6771576254394012295L;

        DirectoryDoesNotExistException() {
            super(directory.getPath() + " does not exist");
        }
    }

    public class FileInsteadOfDirectoryException extends CruiseControlException {
        private static final long serialVersionUID = -2942484775758926441L;

        FileInsteadOfDirectoryException() {
            super(directory.getPath() + " is a file instead of a directory");
        }
    }

    public static class DirectoryNotSpecifiedException extends CruiseControlException {
        private static final long serialVersionUID = 2547984652559538524L;

        public DirectoryNotSpecifiedException(String attributeName) {
            super(attributeName + " must be specified");
        }
    }

    public void setPath(String path) {
        directory = new File(path);
    }

    public File toFile() {
        return directory;
    }
}