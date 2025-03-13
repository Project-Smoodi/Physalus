package org.smoodi.physalus.transfer.socket;

import java.io.Reader;
import java.io.Writer;

/**
 * <p>A 'Socket' with I/O Stream.</p>
 */
public interface IOStreamSocket extends Socket {

    Reader getInput();

    Writer getOutput();
}
