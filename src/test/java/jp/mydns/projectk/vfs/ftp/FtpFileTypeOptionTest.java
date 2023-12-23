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
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.ftp.FtpFileType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;
import test.FtpConfigUtils;

/**
 * Test of class FtpFileTypeOption.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class FtpFileTypeOptionTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new FtpFileTypeOption(Json.createValue("ASCII")).getValue())
                .isEqualTo(Json.createValue("ASCII"));

    }

    /**
     * Test constructor. If argument is valid {@code FtpFileType}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_FtpFileType() {

        assertThat(new FtpFileTypeOption(FtpFileType.ASCII).getValue())
                .isEqualTo(Json.createValue("ASCII"));

    }

    /**
     * Test constructor. If argument is invalid type.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidType() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(JsonValue.NULL))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(JsonValue.TRUE))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(JsonValue.FALSE))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(JsonValue.EMPTY_JSON_ARRAY))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(JsonValue.EMPTY_JSON_OBJECT))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpFileTypeOption(Json.createValue(1000)))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

    }

    /**
     * Test constructor. If illegal name.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalName() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new FtpFileTypeOption(Json.createValue("")))
                .withMessage("FileOption value of [ftp:fileType] must be either [ASCII, BINARY, LOCAL, EBCDIC].");

    }

    /**
     * Test apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws FileSystemException {

        FileSystemOptions opts = new FileSystemOptions();

        new FtpFileTypeOption.Resolver().newInstance(Json.createValue("ASCII")).apply(opts);

        assertThat(extractValue(opts)).isEqualTo(FtpFileType.ASCII);

        new FtpFileTypeOption.Resolver().newInstance(Json.createValue("BINARY")).apply(opts);

        assertThat(extractValue(opts)).isEqualTo(FtpFileType.BINARY);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        FtpFileTypeOption base = new FtpFileTypeOption(FtpFileType.BINARY);
        FtpFileTypeOption same = new FtpFileTypeOption(FtpFileType.BINARY);
        FtpFileTypeOption another = new FtpFileTypeOption(FtpFileType.EBCDIC);

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

        var result = new FtpFileTypeOption(FtpFileType.LOCAL).toString();

        assertThat(result).isEqualTo("{\"ftp:fileType\":\"LOCAL\"}");

    }

    FtpFileType extractValue(FileSystemOptions opts) {

        String prefix = "org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder";

        var utils = new FtpConfigUtils();

        return utils.getParam(opts, prefix + ".FILE_TYPE");

    }
}
