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
import jakarta.json.JsonValue;
import java.nio.charset.StandardCharsets;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;
import test.FtpConfigUtils;

/**
 * Test of class FtpControlEncoding.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class FtpControlEncodingTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new FtpControlEncoding(Json.createValue("UTF-8")).getValue())
                .isEqualTo(Json.createValue("UTF-8"));

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpControlEncoding(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be charset name.", "ftp:controlEncoding");

    }

    /**
     * Test constructor. If argument is valid {@code Charset}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_Charset() {

        assertThat(new FtpControlEncoding(StandardCharsets.US_ASCII).getValue())
                .isEqualTo(Json.createValue("US-ASCII"));

    }

    /**
     * Test apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws FileSystemException {

        FileSystemOptions opts = new FileSystemOptions();

        new FtpControlEncoding.Resolver().newInstance(Json.createValue("UTF-8")).apply(opts);

        assertThat(extractValue(opts)).isEqualTo("UTF-8");

        new FtpControlEncoding.Resolver().newInstance(Json.createValue("US-ASCII")).apply(opts);

        assertThat(extractValue(opts)).isEqualTo("US-ASCII");

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        FtpControlEncoding base = new FtpControlEncoding(StandardCharsets.ISO_8859_1);
        FtpControlEncoding same = new FtpControlEncoding(StandardCharsets.ISO_8859_1);
        FtpControlEncoding another = new FtpControlEncoding(StandardCharsets.UTF_16);

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

        var result = new FtpControlEncoding(StandardCharsets.UTF_16BE).toString();

        assertThat(result).isEqualTo("{\"ftp:controlEncoding\":\"UTF-16BE\"}");

    }

    String extractValue(FileSystemOptions opts) {

        String prefix = "org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder";

        var utils = new FtpConfigUtils();

        return utils.getParam(opts, prefix + ".ENCODING");

    }
}
