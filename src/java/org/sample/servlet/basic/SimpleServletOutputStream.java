package org.sample.servlet.basic;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleServletOutputStream extends ServletOutputStream {


    private OutputStream stream;

    public SimpleServletOutputStream(OutputStream stream)
    {
        this.stream = stream;
    }
    /**
     * Writes the specified byte to this output stream. The general
     * contract for {@code write} is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument {@code b}. The 24
     * high-order bits of {@code b} are ignored.
     *
     * @param b the {@code byte}.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an {@code IOException} may be thrown if the
     *                     output stream has been closed.
     */
    @Override
    public void write(int b) throws IOException {
        this.stream.write(b);
    }

    public void write(String s) throws IOException
    {
        byte[] bytes = s.getBytes();
        for(int i=0;i<bytes.length;i++)
        {
            write(bytes[i]);
        }
    }

}
