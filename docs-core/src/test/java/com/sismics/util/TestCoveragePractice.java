package com.sismics.util;

import com.sismics.util.mime.MimeType;
import com.sismics.util.mime.MimeTypeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Extra tests for Practice 8 coverage.
 */
public class TestCoveragePractice {
    @Test
    public void getLocaleShouldHandleCommonFormats() {
        Assert.assertEquals(Locale.ENGLISH, LocaleUtil.getLocale(null));
        Assert.assertEquals(Locale.ENGLISH, LocaleUtil.getLocale(""));
        Assert.assertEquals(new Locale("fr"), LocaleUtil.getLocale("fr"));
        Assert.assertEquals(Locale.CANADA_FRENCH, LocaleUtil.getLocale("fr_CA"));
        Assert.assertEquals(new Locale("zh", "CN", "Hans"), LocaleUtil.getLocale("zh_CN_Hans"));
    }

    @Test
    public void getFileExtensionShouldMapKnownMimeTypes() {
        Assert.assertEquals("zip", MimeTypeUtil.getFileExtension(MimeType.APPLICATION_ZIP));
        Assert.assertEquals("gif", MimeTypeUtil.getFileExtension(MimeType.IMAGE_GIF));
        Assert.assertEquals("jpg", MimeTypeUtil.getFileExtension(MimeType.IMAGE_JPEG));
        Assert.assertEquals("png", MimeTypeUtil.getFileExtension(MimeType.IMAGE_PNG));
        Assert.assertEquals("pdf", MimeTypeUtil.getFileExtension(MimeType.APPLICATION_PDF));
        Assert.assertEquals("odt", MimeTypeUtil.getFileExtension(MimeType.OPEN_DOCUMENT_TEXT));
        Assert.assertEquals("docx", MimeTypeUtil.getFileExtension(MimeType.OFFICE_DOCUMENT));
        Assert.assertEquals("txt", MimeTypeUtil.getFileExtension(MimeType.TEXT_PLAIN));
        Assert.assertEquals("csv", MimeTypeUtil.getFileExtension(MimeType.TEXT_CSV));
        Assert.assertEquals("mp4", MimeTypeUtil.getFileExtension(MimeType.VIDEO_MP4));
        Assert.assertEquals("webm", MimeTypeUtil.getFileExtension(MimeType.VIDEO_WEBM));
        Assert.assertEquals("bin", MimeTypeUtil.getFileExtension("application/octet-stream"));
    }
}
