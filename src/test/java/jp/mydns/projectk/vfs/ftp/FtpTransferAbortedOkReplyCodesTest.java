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
 * Test of class FtpTransferAbortedOkReplyCodes.
 *
 * @author riru
 * @version 1.0.0
 * @since 1.0.0
 */
class FtpTransferAbortedOkReplyCodesTest {

    /**
     * Test constructor. If argument is valid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue() {

        assertThat(new FtpTransferAbortedOkReplyCodes(JsonValue.EMPTY_JSON_ARRAY).getValue())
                .isEqualTo(JsonValue.EMPTY_JSON_ARRAY);

    }

    /**
     * Test constructor. If argument is illegal {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_IllegalJsonValue() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(JsonValue.NULL))
                .withMessage("FileOption value of [%s] must be list of int.", "ftp:transferAbortedOkReplyCodes");

    }

    /**
     * Test constructor. If argument is valid {@code List<Integer>}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_List_Integer() {

        assertThat(new FtpTransferAbortedOkReplyCodes(List.of(777, 666)).getValue())
                .isEqualTo(Json.createArrayBuilder(List.of(777, 666)).build());

    }

    /**
     * Test constructor. If argument is invalid {@code JsonValue}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_InvalidArgument() {

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(JsonValue.NULL))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(JsonValue.TRUE))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(JsonValue.FALSE))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(JsonValue.EMPTY_JSON_OBJECT))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(Json.createValue("")))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

        assertThatIllegalArgumentException().isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(Json.createValue(1000)))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

    }

    /**
     * Test constructor. If element too large as {@code Integer}.
     *
     * @since 1.0.0
     */
    @Test
    void testConstructor_JsonValue_TooLarge() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new FtpTransferAbortedOkReplyCodes(Json.createArrayBuilder().add(
                Json.createValue(Long.valueOf(Integer.MAX_VALUE) + 1)).build()))
                .withMessage("FileOption value of [ftp:transferAbortedOkReplyCodes] must be list of int.");

    }

    /**
     * Test apply method.
     *
     * @since 1.0.0
     */
    @Test
    void testApply() throws FileSystemException {

        FileSystemOptions opts = new FileSystemOptions();

        new FtpTransferAbortedOkReplyCodes.Resolver()
                .newInstance(Json.createArrayBuilder(List.of()).build()).apply(opts);

        assertThat(extractValue(opts)).isEmpty();

        new FtpTransferAbortedOkReplyCodes.Resolver()
                .newInstance(Json.createArrayBuilder(List.of(4, 9)).build()).apply(opts);

        assertThat(extractValue(opts)).containsExactly(4, 9);

    }

    /**
     * Test {@code equals} method and {@code hashCode} method.
     *
     * @since 1.0.0
     */
    @Test
    void testEqualsHashCode() {

        FtpTransferAbortedOkReplyCodes base = new FtpTransferAbortedOkReplyCodes(List.of(100, 1));
        FtpTransferAbortedOkReplyCodes same = new FtpTransferAbortedOkReplyCodes(List.of(100, 1));
        FtpTransferAbortedOkReplyCodes another = new FtpTransferAbortedOkReplyCodes(List.of(100));

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

        var result = new FtpTransferAbortedOkReplyCodes(List.of(1, 2, 3)).toString();

        assertThat(result).isEqualTo("{\"ftp:transferAbortedOkReplyCodes\":[1,2,3]}");

    }

    List<Integer> extractValue(FileSystemOptions opts) {

        String prefix = "org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder";

        var utils = new FtpConfigUtils();

        return utils.getParam(opts, prefix + ".TRANSFER_ABORTED_OK_REPLY_CODES");

    }
}
