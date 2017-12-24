/*
 * Copyright (C) 2014-2016  Andrew Gunnerson <andrewgunnerson@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.moro.mtweaks.socket;

import android.content.Context;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.LocalSocketAddress.Namespace;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
/*
import com.github.chenxiaolong.dualbootpatcher.CommandUtils;
import com.github.chenxiaolong.dualbootpatcher.CommandUtils.RootExecutionException;
import com.github.chenxiaolong.dualbootpatcher.ThreadUtils;
import com.github.chenxiaolong.dualbootpatcher.Version;
import com.github.chenxiaolong.dualbootpatcher.patcher.PatcherUtils;
import com.github.chenxiaolong.dualbootpatcher.socket.MbtoolUtils.Feature;
import com.github.chenxiaolong.dualbootpatcher.socket.exceptions.MbtoolCommandException;
*/
import com.moro.mtweaks.socket.exceptions.MbtoolException;
import com.moro.mtweaks.socket.interfaces.MbtoolInterface;
/*
import com.github.chenxiaolong.dualbootpatcher.socket.exceptions.MbtoolException.Reason;
import com.github.chenxiaolong.dualbootpatcher.socket.interfaces.MbtoolInterfaceV3;
import com.github.chenxiaolong.dualbootpatcher.socket.interfaces.SignedExecCompletion;
import com.stericson.RootShell.exceptions.RootDeniedException;
*/

import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mbtool.daemon.v3.SignedExecResult;

public class MbtoolConnection implements Closeable {
    private static final String TAG = MbtoolConnection.class.getSimpleName();

    /** mbtool daemon's abstract socket address */
    private static final String SOCKET_ADDRESS = "mbtool.daemon";

    /** Protocol version to use */
    private static final int PROTOCOL_VERSION = 3;

    /** Minimum protocol version for signed exec */
    private static final int SIGNED_EXEC_MIN_PROTOCOL_VERSION = 3;
    /** Maximum protocol version for signed exec */
    private static final int SIGNED_EXEC_MAX_PROTOCOL_VERSION = 3;

    // Handshake responses

    /**
     * Handshake response indicating that the app signature has been verified and the connection
     * is allowed.
     */
    private static final String HANDSHAKE_RESPONSE_ALLOW = "ALLOW";
    /**
     * Handshake response indicating the the app signature is invalid and the negotiation has been
     * denied. The daemon will terminate the connection after sending this response.
     */
    private static final String HANDSHAKE_RESPONSE_DENY = "DENY";
    /**
     * Handshake response indicating that the requested protocol version is supported.
     */
    private static final String HANDSHAKE_RESPONSE_OK = "OK";
    /**
     * Handshake response indicating that the requested protocol version is unsupported. The daemon
     * will terminate the connection after sending this response.
     */
    private static final String HANDSHAKE_RESPONSE_UNSUPPORTED = "UNSUPPORTED";

    // Paths

    private static final String TMPFS_MOUNTPOINT = "/mnt/mb_tmp";
    private static final String MBTOOL_TMPFS_PATH = TMPFS_MOUNTPOINT + "/mbtool";
    private static final String MBTOOL_ROOTFS_PATH = "/mbtool";

    /** mbtool socket */
    private LocalSocket mSocket;
    /** Socket's input stream */
    private InputStream mSocketIS;
    /** Socket's output stream */
    private OutputStream mSocketOS;

    /** mbtool interface */
    private MbtoolInterface mInterface;

    /**
     * Bind to mbtool socket
     *
     * @throws MbtoolException
     */
    /*
    private static LocalSocket initConnectToSocket() throws MbtoolException {
        try {
            LocalSocket socket = new LocalSocket();
            socket.connect(new LocalSocketAddress(SOCKET_ADDRESS, Namespace.ABSTRACT));
            return socket;
        } catch (IOException e) {
            Log.e(TAG, "Could not connect to mbtool socket", e);
            throw new MbtoolException(Reason.DAEMON_NOT_RUNNING, e);
        }
    }
    */













    @Override
    public void close() throws IOException {
        if (mSocketIS != null) {
            mSocketIS.close();
            mSocketIS = null;
        }
        if (mSocketOS != null) {
            mSocketOS.close();
            mSocketOS = null;
        }
        if (mSocket != null) {
            mSocket.close();
            mSocket = null;
        }
    }

    @NonNull
    public MbtoolInterface getInterface() {
        return mInterface;
    }

    @SuppressWarnings("deprecation")
    private static String getAbi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS[0];
        } else {
            return Build.CPU_ABI;
        }
    }








}
