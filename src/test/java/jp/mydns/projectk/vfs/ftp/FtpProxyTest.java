/*
 * Copyright (c) 2023, Project-K
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package jp.mydns.projectk.vfs.ftp;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.net.InetSocketAddress;
import java.net.Proxy;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;
import test.FtpConfigUtils;

/**
 * Test of class FtpProxy.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class FtpProxyTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        JsonObject src = Json.createObjectBuilder().add("type", "HTTP").add("host", "127.0.0.1").add("port", 23456).build();
        JsonObject expect = Json.createObjectBuilder().add("type", "HTTP").add("host", "127.0.0.1").add("port", 23456).build();

        assertThat(new FtpProxy(src).getValue()).isEqualTo(expect);

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpProxy(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be convertible to proxy.", "ftp:proxy");

    }

    /**
     * Test constructor. If argument is valid {@code String}, {@code String} and {@code int}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_ProxyType_String_int() {

        JsonObject expect = Json.createObjectBuilder().add("type", "SOCKS").add("host", "localhost").add("port", 33333).build();

        assertThat(new FtpProxy(Proxy.Type.SOCKS, "localhost", 33333).getValue()).isEqualTo(expect);

    }

    /**
     * Test apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws FileSystemException {

        FileSystemOptions opts = new FileSystemOptions();

        JsonObject src = Json.createObjectBuilder().add("type", "HTTP").add("host", "127.0.0.1").add("port", 23456).build();
        Proxy expect = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 23456));

        new FtpProxy.Resolver().newInstance(src).apply(opts);

        assertThat(extractValue(opts)).isEqualTo(expect);

        JsonObject anotherSrc = Json.createObjectBuilder().add("type", "HTTP").add("host", "localhost").add("port", 33333).build();
        Proxy anotherExpect = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 33333));

        new FtpProxy.Resolver().newInstance(anotherSrc).apply(opts);

        assertThat(extractValue(opts)).isEqualTo(anotherExpect);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        FtpProxy base = new FtpProxy(Proxy.Type.SOCKS, "localhost", 33333);
        FtpProxy same = new FtpProxy(Proxy.Type.SOCKS, "localhost", 33333);
        FtpProxy another = new FtpProxy(Proxy.Type.SOCKS, "localhost", 33334);

        assertThat(base).hasSameHashCodeAs(same).isEqualTo(same)
                .doesNotHaveSameHashCodeAs(another).isNotEqualTo(another);

    }

    /**
     * Test of toString method.
     *
     * @since 1.0.0
     */
    @Test
    void testToString() {

        var result = new FtpProxy(Proxy.Type.SOCKS, "localhost", 33333).toString();

        assertThat(result).isEqualTo("{\"ftp:proxy\":{\"type\":\"SOCKS\",\"host\":\"localhost\",\"port\":33333}}");

    }

    Proxy extractValue(FileSystemOptions opts) {

        String prefix = "org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder";

        var utils = new FtpConfigUtils();

        return utils.getParam(opts, prefix + ".PROXY");

    }
}
