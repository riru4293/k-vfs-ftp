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
import java.util.List;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;
import test.FtpConfigUtils;

/**
 * Test of class FtpShortMonthNames.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class FtpShortMonthNamesTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new FtpShortMonthNames(JsonValue.EMPTY_JSON_ARRAY).getValue())
                .isEqualTo(JsonValue.EMPTY_JSON_ARRAY);

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be list of string.", "ftp:shortMonthNames");

    }

    /**
     * Test constructor. If argument is valid {@code List<String>}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_List_String() {

        assertThat(new FtpShortMonthNames(List.of("A")).getValue())
                .isEqualTo(Json.createArrayBuilder(List.of("A")).build());

    }

    /**
     * Test constructor. If argument is invalid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidArgument() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(JsonValue.NULL))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(JsonValue.TRUE))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(JsonValue.FALSE))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(JsonValue.EMPTY_JSON_OBJECT))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(Json.createValue("")))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpShortMonthNames(Json.createValue(1000)))
                .withMessage("FileOption value of [ftp:shortMonthNames] must be list of string.");

    }

    /**
     * Test apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws FileSystemException {

        FileSystemOptions opts = new FileSystemOptions();

        new FtpShortMonthNames.Resolver()
                .newInstance(Json.createArrayBuilder(List.of()).build()).apply(opts);

        assertThat(extractValue(opts)).isEmpty();

        new FtpShortMonthNames.Resolver()
                .newInstance(Json.createArrayBuilder(List.of("NO")).build()).apply(opts);

        assertThat(extractValue(opts)).containsExactly("NO");

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        FtpShortMonthNames base = new FtpShortMonthNames(List.of());
        FtpShortMonthNames same = new FtpShortMonthNames(List.of());
        FtpShortMonthNames another = new FtpShortMonthNames(List.of("X"));

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

        var result = new FtpShortMonthNames(List.of("Hell")).toString();

        assertThat(result).isEqualTo("{\"ftp:shortMonthNames\":[\"Hell\"]}");

    }

    String[] extractValue(FileSystemOptions opts) {

        String prefix = "org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder";

        var utils = new FtpConfigUtils();

        return utils.getParam(opts, prefix + ".SHORT_MONTH_NAMES");

    }
}
