package org.smoodi.physalus.transfer.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * <p>A 'Socket' with I/O Stream.</p>
 */
public interface IOStreamSocket extends Socket {

    BufferedReader getInput();

    BufferedWriter getOutput();

    boolean isInputShutdown();

    boolean isOutputShutdown();
}
