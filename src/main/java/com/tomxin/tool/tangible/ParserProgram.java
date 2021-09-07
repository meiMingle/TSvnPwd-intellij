package com.tomxin.tool.tangible;

import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Crypt32Util;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * This program was cobbled together by Richard Kagerer at Leapbeyond Solutions Inc.
 * It is Copyright (c) 2011 Leapbeyond Solutions Inc.
 * <p>
 * Feel free to use the code however you wish.  If modifying the code or
 * using it in your own project, it would be appreciated if you include a
 * reference to the original author in your source code.
 */
public class ParserProgram {

    private static final Logger LOG = Logger.getInstance(ParserProgram.class);
    /**
     * Error value returned by console program if failed
     */
    private static final int GENERAL_ERROR = -1;

    /**
     * Error value returned if only partial output displayed
     */
    private static final int ERROR_TOOMANY = -2;

    /**
     * Relative path to password files (from %APPDATA%)
     */
    private static final String AUTHFILE_SUBPATH = "Subversion\\auth\\svn.simple";
    /**
     * After this many password files processed, abort
     */
    private static final int MAX_FILES_COUNT = 200;


    public static List<Result> findAllSvnInfo() {
        LOG.info("TortoiseSVN Password Decrypter v" + Version());
        LOG.info("The original version of this program was created by Leapbeyond Solutions use C#.The java version created by meiMingle on github.");

        // Look for password files
        String folder = java.nio.file.Paths.get(System.getenv("APPDATA")).resolve(AUTHFILE_SUBPATH).toString();
        if (!(new File(folder)).isDirectory()) {
            throw new RuntimeException("Path not found: " + folder);
        }
        File file = new File(folder);
        File[] files = file.listFiles((path, name) -> name.length() == 32);
        if (files.length < 1) {
            throw new RuntimeException("No files with exactly 32 characters in the filename found in " + folder);
        }
        LOG.info(String.format("Found %1$s cached credentials files in %2$s%n", files.length, folder));


        // Iterate each
        List<Result> resultList = Lists.newArrayList();
        for (int i = 0; i < files.length; i++) {
            Result result = new Result();
            if (i > MAX_FILES_COUNT) {
                ExitWithError("Listing aborted.  Too many files in " + folder, ERROR_TOOMANY);
            }
            result.setFilename(files[i].getName());
            LOG.info("Parsing " + (files[i].getName()));

            RefObject<String> tempRefUsername = new RefObject<>(result.getUsername());
            RefObject<String> tempRefRepository = new RefObject<>(result.getRepository());
            RefObject<String> tempRefEncryptedPassword = new RefObject<>(result.getEncryptedPassword());
            if (TryParseAuthFile(files[i].getAbsolutePath(), tempRefUsername, tempRefRepository, tempRefEncryptedPassword)) {
                result.setEncryptedPassword(tempRefEncryptedPassword.argValue);
                result.setRepository(tempRefRepository.argValue);
                result.setUsername(tempRefUsername.argValue);
                RefObject<String> tempRefDecryptedPassword = new RefObject<>(tempRefEncryptedPassword.argValue);
                if (tryDecryptPassword(tempRefEncryptedPassword.argValue, tempRefDecryptedPassword)) {
                    result.setDecryptedPassword(tempRefDecryptedPassword.argValue);
                    LOG.info("Password: " + tempRefDecryptedPassword.argValue);
                } else {
                    result.setDecryptedPassword(tempRefDecryptedPassword.argValue);
                }
                resultList.add(result);
            }

        } // end for
        return resultList;

    }




    private static String Version() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        String osArch = props.getProperty("os.arch");
        String osVersion = props.getProperty("os.version");
        return String.format("%1$s.%2$s.%3$s", osName, osArch, osVersion);
    }

    private static void ExitWithError(String error) {
        ExitWithError(error, GENERAL_ERROR);
    }

    private static void ExitWithError(String error, int errorCode) {
        LOG.info(error);
        System.exit(errorCode);
    }

    private static boolean TryParseAuthFile(String path, RefObject<String> username, RefObject<String> repository, RefObject<String> encryptedPassword) {

        username.argValue = "";
        repository.argValue = "";
        encryptedPassword.argValue = "";

        // Read file and parse key/value pairs
        Map<String, String> results = null;
        try {
            results = AuthFileParser.readFile(path);
            if (results.get("username") != null && !"".equals(results.get("username"))) {
                username.argValue = results.get("username");
            } else {
                return false;
            }
            if (results.get("svn:realmstring") != null && !"".equals(results.get("svn:realmstring"))) {
                repository.argValue = results.get("svn:realmstring");
            } else {
                return false;
            }
            if (results.get("password") != null && !"".equals(results.get("password"))) {
                encryptedPassword.argValue = results.get("password");
            } else {
                return false;
            }
            return true;
        } catch (AuthParseException | IOException e) {
            LOG.error(e);
            return false;
        }

    }

    private static boolean tryDecryptPassword(String encrypted, RefObject<String> decrypted) {
        decrypted.argValue = "";
        try {
            byte[] data = Base64.getDecoder().decode(encrypted);
            byte[] unprotectedData = Crypt32Util.cryptUnprotectData(data);
            decrypted.argValue = Native.toString(unprotectedData);
            return true;
        } catch (RuntimeException e) {
            LOG.error("Unable to decrypt the password",e);
            return false;
        }
    }


}
