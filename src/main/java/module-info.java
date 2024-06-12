/*
 * Copyright (c) 2024, Project-K
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

/**
 * Project-K VFS FTP implements.
 *
 * @provides jp.mydns.projectk.vfs.FileOption.Resolver
 * @uses jp.mydns.projectk.vfs.FileOption.Resolver
 *
 * @since 1.0.0
 */
module jp.mydns.projectk.vfs.ftp {
    requires jp.mydns.projectk.vfs;
    requires commons.vfs2;
    requires commons.logging;
    requires org.apache.commons.lang3;
    requires jakarta.json;
    uses jp.mydns.projectk.vfs.FileOption.Resolver;
    provides jp.mydns.projectk.vfs.FileOption.Resolver with
            jp.mydns.projectk.vfs.ftp.FtpConnectionTimeout.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpControlEncoding.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpControlKeepAliveReplyTimeout.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpControlKeepAliveTimeout.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpDataTimeout.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpDefaultDateFormat.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpEntryParser.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpFileTypeOption.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpProxy.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpRecentDateFormat.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpServerLanguageCode.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpServerTimeZoneId.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpShortMonthNames.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpSocketTimeout.Resolver
          , jp.mydns.projectk.vfs.ftp.FtpTransferAbortedOkReplyCodes.Resolver
          , jp.mydns.projectk.vfs.ftp.UseFtpAutodetectUtf8.Resolver
          , jp.mydns.projectk.vfs.ftp.UseFtpMdtmLastModifiedTime.Resolver
          , jp.mydns.projectk.vfs.ftp.UseFtpPassiveMode.Resolver
          , jp.mydns.projectk.vfs.ftp.UseFtpRemoteVerification.Resolver
          , jp.mydns.projectk.vfs.ftp.UseFtpUserDirAsRoot.Resolver;
    exports jp.mydns.projectk.vfs.ftp;
}
